package com.medayes.erp.ErpApi.repository.documents;

import com.medayes.erp.ErpApi.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentsRepository  extends JpaRepository<DocumentEntity, String> {

    List<DocumentEntity> findByDocumentType(String s);
}
