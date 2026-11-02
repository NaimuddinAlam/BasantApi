package com.medayes.erp.ErpApi.services.documents;



import com.medayes.erp.ErpApi.dto.request.DocumentRequestDTO;
import com.medayes.erp.ErpApi.dto.response.DocumentDTO;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.entity.DocumentEntity;
import com.medayes.erp.ErpApi.repository.documents.DocumentsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class DocumentsServices {

    private final DocumentsRepository repository;

    private final ModelMapper modelMapper;

    @Value("${documents.url}")
    private   String documentUrl;

    public TaskMastResponsibleResponseDto<?> getDocuments(DocumentRequestDTO type)
    {

        List<DocumentEntity> documentEntityList = repository.findByDocumentType(type.getType());



        List<DocumentDTO> documentDTOS = documentEntityList.stream()

                .filter(doc ->
                        !"IN0".equals(doc.getAttachFileSTATUS()))

                .map(doc -> {

                    DocumentDTO dto =
                            modelMapper.map(doc, DocumentDTO.class);


                    String statusValue =
                            STATUS_MAP.get(doc.getAttachFileSTATUS());
                    dto.setDocumentType(
                            statusValue);
                    dto.setAttachFileName(documentUrl+doc.getAttachFileName());

                    return dto;
                })

                .toList();

        return new TaskMastResponsibleResponseDto<>(
                "true",
                "Data fetch successful",
                HttpStatus.OK.value(),
                documentDTOS
        );
    }
    private static final Map<String, String> STATUS_MAP = Map.of(
            "UP", "Under Processing",
            "DV", "Draft Version",
            "FL", "Finalized"
    );
}