package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PAY_EMP_MAST")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayEmployeeMastEntity {
    @Id
    @Column(name = "EMP_CODE")
    private String empCode;

    @Column(name = "REPORTING_TO")
    private String reportingTo;
    @Column(name = "REPORTING_HR")
    private  String reportingHr;

    @Column(name = "IMG_NAME")
    private  String imageName;
    @Column(name = "LEAVING_REASON")
    private  String leavingReason;
    @Column(name = "ATTN_TYPE")
    private  String attType;






}
