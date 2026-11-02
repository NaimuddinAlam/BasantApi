package com.medayes.erp.ErpApi.repository.production;

import com.medayes.erp.ErpApi.entity.IdempotencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IdempotencyRepository  extends JpaRepository<IdempotencyEntity,Long> {
   Optional<IdempotencyEntity>  findByIdempotencyKey(String idempotencyKey);
}
