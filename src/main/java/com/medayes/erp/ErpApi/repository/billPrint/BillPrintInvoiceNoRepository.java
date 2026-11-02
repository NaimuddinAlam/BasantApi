package com.medayes.erp.ErpApi.repository.billPrint;

import com.medayes.erp.ErpApi.entity.BillPrintEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillPrintInvoiceNoRepository extends JpaRepository<BillPrintEntity, String> {
}
