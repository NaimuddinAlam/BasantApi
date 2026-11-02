package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "app_IDEMPOTENCY")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdempotencyEntity {
    @Id
    @Column(name = "idempotency_key", length = 100)
    private String idempotencyKey;

    @Column(name = "request_hash", length = 64)
    private String requestHash;

    @Lob
    @Column(name = "response_body", columnDefinition = "NVARCHAR(MAX)")
    private String responseBody;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
