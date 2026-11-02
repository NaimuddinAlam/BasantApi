package com.medayes.erp.ErpApi.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;


@Entity
@Immutable
@Table(name = "HRIS_COMPANY_ATTACHMENT")
@Setter
@Getter
public class DocumentEntity {

    @Id
    @Column (name = "ATTACH_CODE")
    private  String attachCode;
    @Column(name = "DOCUMENT_TYPE")
    private  String documentType;
    @Column(name = "ATTACH_DESC")
    private  String attachDesc;
    @Column(name = "ATTACH_FILE_NAME")
    private  String attachFileName;
    @Column(name = "PERMISSION")
    private  String filePermission;
    @Column(name = "STATUS")
    private  String attachFileSTATUS;





}
