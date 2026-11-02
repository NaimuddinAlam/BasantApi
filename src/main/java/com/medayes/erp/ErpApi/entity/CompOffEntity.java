package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "PAY_COMP_REQUEST_MAST")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompOffEntity {

    @Id
    @Column(name = "COMP_REQ_NO")
    private String compReqNo;
    @Column(name = "COMP_HOLIDAY_DATE")
    private String compHoliday;
    @Column(name = "FULL_HALF")
    private  String fullHalf;
    @Column(name = "EMP_CODE")
    private  String empCode;
    @Column(name = "APP_NO")
    private String appNo;
    @Column(name = "COMP_CODE")
    private  String compCode;
    @Column(name = "CENT_CODE")
    private  String centCode;
    @Column(name = "BRANCH_CODE")
    private  String branchCode;
    @Column(name = "EXPIRY_DATE")
    private  String expiryDate;
    @Column(name = "CREDIT_DATE")
    private  String creditDate;
    @Column(name = "REMARKS")
    private  String remarks;
    @Column(name = "CREATE_USER")
    private  String createUser;
    @Column(name = "CREATE_DATE")
    private  LocalDateTime createDate;
    @Column(name = "APPROVAL_FLAG")
    private  String approvalFlag;
    @Column(name = "APPROVAL_USER")
    private  String approvalUser;
    @Column(name = "APPROVAL_DATE")
    private  String approvalDate;
    @Column(name = "APPROVAL_REMARKS")
    private  String approvalRemark;
    @Column(name = "CANCEL_FLAG")
    private  String cancelFlag;
    @Column(name = "CANCEL_USER")
    private  String cancelUser;
    @Column(name = "CANCEL_DATE")
    private  String cancelDate;
    @Column(name = "CANCEL_REMARKS")
    private  String cancelRemarks;







    @PrePersist
    private void createDate()
    {
        createDate= LocalDateTime.now();
    }
}
