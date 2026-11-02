package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "ERP_DIST_MAST")
@Immutable
@Setter
@Getter
public class DistrictMastEntity {
    @Id
    @Column(name = "DIST_CODE")
    private  String distCode;
    @Column(name = "DIST_NAME")
    private  String distName;
    @Column(name = "STATE_CODE")
    private  String stateCode;

}
