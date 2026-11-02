package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PAY_BANNER_MAST")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BannerMastEntity {
    @Id
    @Column(name = "IMG_NAME")
    private String imageName;
    @Column(name = "SEQ_NO")
    private  String seqNo;
    @Column(name = "STATUS")
    private  String status;
}
