package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ERP_MODULE_MAST")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ERPModuleEntity {
    @Id
    @Column(name = "MODULE_URL")
    private String misUrl;
    @Column(name = "MODULE_CODE")
    private  String moduleCode;
}
