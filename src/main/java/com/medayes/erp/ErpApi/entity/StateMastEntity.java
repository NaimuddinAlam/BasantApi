package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "ERP_STATE_MAST")
@Setter
@Getter
@Immutable
public class StateMastEntity {
    @Id
    @Column(name = "STATE_CODE")
    private  String stateCode;
    @Column(name = "STATE_NAME")
    private  String stateName;

}
