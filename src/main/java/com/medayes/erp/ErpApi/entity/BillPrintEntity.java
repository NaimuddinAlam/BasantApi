package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Table(name = "PPC_SALE_HDR")
@Entity
@Immutable
@Getter
public class BillPrintEntity {
    @Id
    @Column(name = "INV_NO")
    private String invNo;
}
