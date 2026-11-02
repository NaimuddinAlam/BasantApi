package com.medayes.erp.ErpApi.repository.production;



import com.medayes.erp.ErpApi.dto.request.production.ReportRequestDto;
import com.medayes.erp.ErpApi.dto.response.production.OrganiserSummaryResponseDto;
import com.medayes.erp.ErpApi.dto.response.production.ReportReposeDto;
import com.medayes.erp.ErpApi.dto.response.production.StockLedgerResponse;



import java.util.List;


public interface ReportRepository {


    List<OrganiserSummaryResponseDto> getOrganiserStockSummary(ReportRequestDto rs) ;
    List<StockLedgerResponse> getOrganiserStockLedger(ReportRequestDto rs);
    List<ReportReposeDto> getIssuedOrganiserStockSummary(ReportRequestDto rs);

}
