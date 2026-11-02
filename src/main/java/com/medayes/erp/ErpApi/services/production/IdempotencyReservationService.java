package com.medayes.erp.ErpApi.services.production;

import com.medayes.erp.ErpApi.entity.IdempotencyEntity;
import com.medayes.erp.ErpApi.repository.production.IdempotencyRepository;
import com.medayes.erp.ErpApi.utils.IdempotencyStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdempotencyReservationService {


    private final IdempotencyRepository idempotencyRepository;

    /**
     * Tries to reserve the key. Returns the existing record if one already exists
     * (caller decides what to do with COMPLETED/PROCESSING/FAILED).
     * Returns empty if this call successfully reserved a brand-new key.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<IdempotencyEntity> reserve(String idempotencyKey) {
        Optional<IdempotencyEntity> existing = idempotencyRepository.findByIdempotencyKey(idempotencyKey);
        if (existing.isPresent()) {
            return existing;
        }

        IdempotencyEntity newRecord = new IdempotencyEntity();
        newRecord.setIdempotencyKey(idempotencyKey);
        newRecord.setStatus(IdempotencyStatus.PROCESSING.name());
        newRecord.setCreatedAt(LocalDateTime.now());

        try {
            idempotencyRepository.saveAndFlush(newRecord);
            return Optional.empty(); // fresh reservation succeeded
        } catch (DataIntegrityViolationException e) {
            // Lost the race to a concurrent request — fetch what they inserted
            return idempotencyRepository.findByIdempotencyKey(idempotencyKey);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markCompleted(String idempotencyKey, String responseBody) {
        idempotencyRepository.findByIdempotencyKey(idempotencyKey).ifPresent(record -> {
            record.setStatus(IdempotencyStatus.COMPLETED.name());
            record.setResponseBody(responseBody);
            idempotencyRepository.save(record);
        });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markFailed(String idempotencyKey) {
        idempotencyRepository.findByIdempotencyKey(idempotencyKey).ifPresent(record -> {
            record.setStatus(IdempotencyStatus.FAILED.name());
            idempotencyRepository.save(record);
        });
    }
}