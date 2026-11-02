package com.medayes.erp.ErpApi.entity;



import jakarta.persistence.*;
        import lombok.*;
        import org.hibernate.annotations.Immutable;
import java.time.LocalDateTime;

@Entity
@Table(name = "HRIS_DAILY_ACTIVITY_MAST")
@Getter
@Setter
@NoArgsConstructor
@Immutable   // 🔥 read-only protection
public class DailyActivityEntity {

    @Id
    @Column(name = "TASK_CODE")
    private String taskCode;

    @Column(name = "COMP_CODE")
    private String compCode;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "TASK_NICK")
    private String taskNick;

    @Column(name = "DEFAULT_FLAG")
    private String defaultFlag;

    @Column(name = "X1")
    private String x1;

    @Column(name = "X2")
    private String x2;

    @Column(name = "X3")
    private String x3;

    @Column(name = "X4")
    private String x4;

    @Column(name = "X5")
    private String x5;

    @Column(name = "SEQ_NO")
    private Integer seqNo;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "IP")
    private String ip;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
}
