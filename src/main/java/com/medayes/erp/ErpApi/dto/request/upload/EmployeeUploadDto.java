package com.medayes.erp.ErpApi.dto.request.upload;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class EmployeeUploadDto {

        private String empCode;

        private MultipartFile photo;

}
