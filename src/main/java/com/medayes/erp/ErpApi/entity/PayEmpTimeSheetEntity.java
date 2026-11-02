package com.medayes.erp.ErpApi.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "PAY_EMP_TIMESHEET")
@Getter
@Setter
@NoArgsConstructor
@Immutable
public class PayEmpTimeSheetEntity {
    @Id
    @Column(name = "CLIENT_NAME")
    private String clientName;

    @Column(name = "MOBILE_NO")
    private String mobileNo;
    @Column(name = "ROW_ID")
    private Long rowId;
    @Column(name = "STATE_CODE")
    private String stateCode;
    @Column(name = "DIST_CODE")
    private String distCode;
    @Column(name = "TALUKA_CODE")
    private String talukaCode;
    @Column(name = "VILLAG_NAME")
    private String villageName;


}
