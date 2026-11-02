package com.medayes.erp.ErpApi.services.production;





import com.medayes.erp.ErpApi.dto.request.production.OperationRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MultipleImageUploadServices  {

    @Value("${file.upload-dir.receipt}")
    private String uploadDir;

    private static final Set<String> IMAGE_EXTENSIONS = Set.of(
            ".jpg",
            ".jpeg",
            ".png",
            ".webp"
    );

    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;


    public TaskMastResponsibleResponseDto<?> saveImage(
            OperationRequestDto request) {

        List<String> uploadedFiles = new ArrayList<>();

        try {
            if (request == null
                    || request.getPhoto() == null
                    || request.getPhoto().isEmpty()) {

                return successResponse(
                        null,
                        "Please select at least one image",
                        "false"
                );
            }

            List<MultipartFile> photos = request.getPhoto();

            // Validate all files before uploading any of them.
            for (MultipartFile photo : photos) {
                validateImage(photo);
            }

            for (MultipartFile photo : photos) {
                String fileName = uploadImage(photo);
                uploadedFiles.add(fileName);
            }

            return successResponse(
                    uploadedFiles,
                    "Images uploaded successfully",
                    "true"
            );

        } catch (IllegalArgumentException e) {
            log.warn("Invalid image upload request: {}", e.getMessage());

            return successResponse(
                    null,
                    e.getMessage(),
                    "false"
            );

        } catch (IOException e) {
            log.error("Image upload failed", e);

            // Remove files uploaded during this request if a later upload fails.
            for (String fileName : uploadedFiles) {
                try {
                    Files.deleteIfExists(
                            Path.of(uploadDir).resolve(fileName)
                    );
                } catch (IOException cleanupException) {
                    log.error(
                            "Failed to clean up uploaded image: {}",
                            fileName,
                            cleanupException
                    );
                }
            }

            return successResponse(
                    null,
                    "Image upload failed",
                    "false"
            );
        }
    }

    /*
     * Validate image before saving
     */
    private void validateImage(MultipartFile photo) {

        if (photo == null || photo.isEmpty()) {
            throw new IllegalArgumentException(
                    "Image cannot be empty"
            );
        }

        if (photo.getSize() > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException(
                    "Each image must be 5 MB or smaller"
            );
        }

        String extension = getExtension(photo);

        if (!IMAGE_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                    "Only JPG, JPEG, PNG and WEBP images are allowed"
            );
        }

        String contentType = photo.getContentType();

        if (contentType != null
                && !contentType.toLowerCase(Locale.ROOT)
                .startsWith("image/")) {
            throw new IllegalArgumentException(
                    "Invalid image content type"
            );
        }
    }


    private String uploadImage(MultipartFile photo) throws IOException {

        if (photo == null || photo.isEmpty()) {
            throw new IllegalArgumentException("Image cannot be empty");
        }

        Path uploadPath = Path.of(uploadDir)
                .toAbsolutePath()
                .normalize();

        Files.createDirectories(uploadPath);

        String extension = getExtension(photo);
        String fileName = UUID.randomUUID() + extension;

        Path destination = uploadPath.resolve(fileName).normalize();

        if (!destination.startsWith(uploadPath)) {
            throw new IllegalArgumentException("Invalid upload path");
        }

        log.info("Uploading image to {}", destination);

        try (InputStream inputStream = photo.getInputStream()) {
            Files.copy(inputStream, destination);
        }

        if (!Files.isRegularFile(destination) || Files.size(destination) == 0) {
            Files.deleteIfExists(destination);
            throw new IOException("Image upload failed");
        }

        return fileName;
    }
    /*
     * Extract and validate extension
     */
    private String getExtension(MultipartFile photo) {

        String originalName = photo.getOriginalFilename();

        if (!StringUtils.hasText(originalName)) {
            throw new IllegalArgumentException(
                    "Invalid image filename"
            );
        }

        // Prevent path-like filenames.
        String safeName = originalName.replace('\\', '/');
        safeName = safeName.substring(safeName.lastIndexOf('/') + 1);

        int dotIndex = safeName.lastIndexOf('.');

        if (dotIndex <= 0 || dotIndex == safeName.length() - 1) {
            throw new IllegalArgumentException(
                    "Invalid image filename"
            );
        }

        return safeName.substring(dotIndex).toLowerCase(Locale.ROOT);
    }

    /*
     * Standard response wrapper
     */
    private <T> TaskMastResponsibleResponseDto<T> successResponse(
            T data,
            String message,
            String status) {

        return new TaskMastResponsibleResponseDto<>(
                status,
                message,
                HttpStatus.OK.value(),
                data
        );
    }
}