package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PAY_ATTENDANCE_APP")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceEntity {
   @Id
    @Column(name = "TIME")
    private  String  time;

}
