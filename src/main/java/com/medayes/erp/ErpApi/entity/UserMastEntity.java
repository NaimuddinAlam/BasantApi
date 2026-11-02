package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "ERP_USER_MAST")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMastEntity {
    @Id
    @Column(name = "EMP_CODE")
    private String empCode;

    @Column(name = "MOBILE_NO")
    private String mobile;
    @Column(name = "PUSH_NOTIFICATION")
    private  String token;
    @Column(name = "FIRSTNAME")
    private  String firstName;
    @Column(name = "LASTNAME")
    private  String lastName;

    @Column(name = "PASSWORD")
    private  String password;
    @Column(name = "INTERVAL_MINUTES")
    private  String timeInterval;

}
