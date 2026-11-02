package com.medayes.erp.ErpApi.services.upload;

import com.medayes.erp.ErpApi.dto.request.sale.SalePermissionRequestDto;
import com.medayes.erp.ErpApi.dto.request.upload.EmployeeUploadDto;
import com.medayes.erp.ErpApi.dto.response.BannerMastResponseDto;
import com.medayes.erp.ErpApi.dto.response.employee.EmployeeDto;
import com.medayes.erp.ErpApi.dto.response.mis.MisUrlResponseDto;
import com.medayes.erp.ErpApi.dto.response.sale.SalePermissionResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.entity.ERPModuleEntity;
import com.medayes.erp.ErpApi.entity.PayEmployeeMastEntity;
import com.medayes.erp.ErpApi.entity.UserMastEntity;
import com.medayes.erp.ErpApi.exeption.BusinessException;
import com.medayes.erp.ErpApi.exeption.DatabaseException;
import com.medayes.erp.ErpApi.exeption.ResourceNotFoundException;
import com.medayes.erp.ErpApi.repository.compOff.UserMastRepository;
import com.medayes.erp.ErpApi.repository.mis.ERPModulePermissionRepository;
import com.medayes.erp.ErpApi.repository.mis.ERPModuleRepository;
import com.medayes.erp.ErpApi.repository.upload.BannerMastRepository;
import com.medayes.erp.ErpApi.repository.upload.EmployeeUploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;



@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeUploadServiceImpl implements EmployeeUploadService {

    private static final String BASE_IMAGE_URL =
            "http://103.205.66.82/BASANT_AGRO_PAYROLL/EMP_PHOTO/";



    private static final String[] IMAGE_EXTENSIONS = {
            ".jpg", ".jpeg", ".png", ".webp"
    };

    private final EmployeeUploadRepository employeeRepository;
    private  final BannerMastRepository bannerMastRepository;
    private  final ModelMapper mapper;
    private  final ERPModuleRepository erpModuleRepository;
    private  final UserMastRepository userMastRepository;
    private  final ERPModulePermissionRepository erpModulePermissionRepository;


    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public TaskMastResponsibleResponseDto<?> employeeUpload(EmployeeUploadDto dto) {

        validateRequest(dto);

        PayEmployeeMastEntity employee = employeeRepository.findByEmpCode(dto.getEmpCode())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found : " + dto.getEmpCode()));

        try {

            String fileName = uploadImage(dto.getEmpCode(), dto.getPhoto());

            employee.setImageName(fileName);
            employeeRepository.save(employee);

            return new TaskMastResponsibleResponseDto<>(
                    "true",
                    "Image uploaded successfully.",
                    HttpStatus.OK.value(),
                    BASE_IMAGE_URL + fileName
            );

        } catch (IOException ex) {

            log.error("Image upload failed for employee {}", dto.getEmpCode(), ex);

            throw new RuntimeException("Unable to upload employee image.", ex);
        }
    }

    @Override
    public TaskMastResponsibleResponseDto<?> employeeResignation(EmployeeUploadDto dto) {

        validateRequests(dto);

        PayEmployeeMastEntity employee = employeeRepository.findByEmpCode(dto.getEmpCode())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found: " + dto.getEmpCode()));

        String leavingReason = employee.getLeavingReason();
        String attType = employee.getAttType();

        UserMastEntity userMastEntity=  userMastRepository.findFirstByEmpCode(dto.getEmpCode()) .orElseThrow(() ->
                new ResourceNotFoundException(
                        "Employee not found: " + dto.getEmpCode()));
        String interval = userMastEntity.getTimeInterval();
        boolean isResigned = leavingReason != null && !leavingReason.isBlank();
        EmployeeDto employeeDto= new EmployeeDto(isResigned ? "Y" : "N",attType,interval);
        return new TaskMastResponsibleResponseDto<>(
               "true",
                "",
                HttpStatus.OK.value(),employeeDto

        );
    }

    @Override
    public TaskMastResponsibleResponseDto<List<BannerMastResponseDto>> getBannerMast(EmployeeUploadDto dto) {

        List<BannerMastResponseDto> banners = bannerMastRepository
                .findByStatusOrderBySeqNoAsc("AC0")
                .stream()
                .map(entity -> {
                    BannerMastResponseDto response = new BannerMastResponseDto();
                    response.setImageName(BASE_IMAGE_URL + entity.getImageName());

                    return response;
                })
                .toList();

        return new TaskMastResponsibleResponseDto<>(
                "200",
                "Banner fetched successfully.",
                HttpStatus.OK.value(),
                banners
        );
    }

    @Override
    public TaskMastResponsibleResponseDto<?> getMisUrl(EmployeeUploadDto dto) {
        validateRequests(dto);

        UserMastEntity employee = userMastRepository.findFirstByEmpCode(dto.getEmpCode())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found: " + dto.getEmpCode()));

        String password = employee.getPassword();
        ERPModuleEntity moduleEntity= erpModuleRepository.findByModuleCode("12").orElseThrow(()-> new ResourceNotFoundException("Mis url not found"));
        String url  = moduleEntity.getMisUrl();

        MisUrlResponseDto response= MisUrlResponseDto.builder()
                .misUrl(!url.isEmpty()?url:"")
                .password(!password.isEmpty()?password:"")
                .build();
        return new TaskMastResponsibleResponseDto<>(
                "true",
                "data fetched successfully.",
                HttpStatus.OK.value(),
                response
        );
    }

    @Override
    public TaskMastResponsibleResponseDto<?> misPermission(SalePermissionRequestDto salePermissionRequestDto) {
        try {
            SalePermissionResponseDto response= erpModulePermissionRepository.approvalId(salePermissionRequestDto);
            if( response==null )
            {
                throw  new BusinessException("No Approval id found");
            }
            return  new TaskMastResponsibleResponseDto<>("true",
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    response);
        }catch (DataAccessException ex)
        {
            throw new DatabaseException(
                    "Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex
            );
        }
    }

    private void validateRequest(EmployeeUploadDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        if (!StringUtils.hasText(dto.getEmpCode())) {
            throw new IllegalArgumentException("Employee code is required.");
        }

        MultipartFile photo = dto.getPhoto();

        if (photo == null || photo.isEmpty()) {
            throw new IllegalArgumentException("Please select an image.");
        }

      /*  if (!ALLOWED_CONTENT_TYPES.contains(photo.getContentType())) {
            throw new IllegalArgumentException(
                    "Only JPG, JPEG, PNG and WEBP images are allowed.");
        }*/
    }
    private void validateRequests(EmployeeUploadDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        if (!StringUtils.hasText(dto.getEmpCode())) {
            throw new IllegalArgumentException("Employee code is required.");
        }


    }

    private String uploadImage(String empCode, MultipartFile photo) throws IOException {

        Path uploadPath = Path.of(uploadDir);

        Files.createDirectories(uploadPath);

        String extension = getExtension(photo);

        deleteOldImages(uploadPath, empCode);

        String fileName = empCode + extension;

        Path destination = uploadPath.resolve(fileName);

        log.info("Uploading image to {}", destination.toAbsolutePath());

        Files.copy(
                photo.getInputStream(),
                destination,
                StandardCopyOption.REPLACE_EXISTING
        );

        if (!Files.exists(destination)) {
            throw new IOException("Image upload failed.");
        }

        return fileName;
    }

    private String getExtension(MultipartFile photo) {

        String originalName = photo.getOriginalFilename();

        if (!StringUtils.hasText(originalName)
                || !originalName.contains(".")) {

            throw new IllegalArgumentException("Invalid image name.");
        }

        return originalName
                .substring(originalName.lastIndexOf("."))
                .toLowerCase();
    }

    private void deleteOldImages(Path uploadPath, String empCode)
            throws IOException {

        for (String extension : IMAGE_EXTENSIONS) {
            Files.deleteIfExists(uploadPath.resolve(empCode + extension));
        }
    }


}