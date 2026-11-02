package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "ERP_TALUKA_MAST")
@Immutable
@Getter
public class TalukaMastEntity {
    @Id
    @Column(name = "TALUKA_CODE")
    private  String talukaCode;
    @Column(name = "TALUKA_NAME")
    private  String talukaName;
    @Column(name = "DIST_CODE")
    private  String distCode;
}
