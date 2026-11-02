package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "PAY_EMP_INACTIVE_APP")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppEmployeeInactiveEntity {
    @Id
    @Column(name = "EMP_CODE")
    private  String empCode;
    @Column(name = "INACTIVE_DATE")
    private LocalDate inactiveDate;
}
