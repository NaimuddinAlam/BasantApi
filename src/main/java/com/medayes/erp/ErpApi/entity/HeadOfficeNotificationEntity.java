package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_HEADOFFICE_MAST")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeadOfficeNotificationEntity {
    @Id
    @Column(name = "USER_ID")
    private  String userId;
    @Column(name = "MOBILE_NO")
    private  String mobileNo;
    @Column(name = "FCM_TOKEN")
    private  String token;
    @Column(name = "IS_ACTIVE")
    private  String active;

}

