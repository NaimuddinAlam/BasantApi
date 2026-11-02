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
@Table(name = "PAY_MOBILE_VERION")
@Getter
@Setter
@NoArgsConstructor
@Immutable
public class MobileVersionEntity {
    @Id
    @Column(name = "VERSION_CODE")
    String  versionCode;
    @Column(name = "MESSAGE")
    String message;
    @Column(name = "X1")
    String X1;
}
