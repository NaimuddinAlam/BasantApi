package com.medayes.erp.ErpApi.repository.production;


import com.medayes.erp.ErpApi.dto.request.production.ReportRequestDto;
import com.medayes.erp.ErpApi.dto.response.production.OrganiserSummaryResponseDto;
import com.medayes.erp.ErpApi.dto.response.production.ReportReposeDto;
import com.medayes.erp.ErpApi.dto.response.production.StockLedgerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

@Slf4j
@Repository
public class ReportRepositoryImpl implements ReportRepository {

    private final SimpleJdbcCall stockSummaryCall;
    private final SimpleJdbcCall stockLedgerCall;
    private final SimpleJdbcCall issuedStockCall;

    public ReportRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.stockSummaryCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("PRD_ORG_CLOSING_STOCK_SUMM")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(parameters("PCOMP_CODE", "PCENT_CODE", "PBRANCH_CODE", "PORG_CODE", "PITEM_TYPE", "PITEM_CODE", "PSIZE", "PCOLOR", "PPACKING_TYPE", "PAS_ON_DATE", "PREPORT_TYPE", "PEXTRA", "PEXTRA1", "PEXTRA2", "PEXTRA3", "PEXTRA4", "PEXTRA5", "PEXTRA6", "PEXTRA7", "PEXTRA8", "PEXTRA9", "PEXTRA10"))
                .returningResultSet("data", (rs, rowNum) -> mapSummary(rs));
        this.stockLedgerCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("PRD_ORG_STOCK_LEDGER")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(parameters("PCOMP_CODE", "PCENT_CODE", "PBRANCH_CODE", "PORG_CODE", "PITEM_TYPE", "PITEM_CODE", "PSIZE", "PCOLOR", "PPACKING_TYPE", "PFROM_DATE", "PTO_DATE", "PREPORT_TYPE", "PEXTRA", "PEXTRA1", "PEXTRA2", "PEXTRA3", "PEXTRA4", "PEXTRA5", "PEXTRA6", "PEXTRA7", "PEXTRA8", "PEXTRA9", "PEXTRA10"))
                .returningResultSet("data", (rs, rowNum) -> mapLedger(rs));
        this.issuedStockCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("PRD_ORG_ISSUE_RECD_REPORT")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(parameters("PCOMP_CODE", "PCENT_CODE", "PBRANCH_CODE", "PORG_CODE", "PITEM_TYPE", "PITEM_CODE", "PSIZE", "PCOLOR", "PPACKING_TYPE", "PFROM_DATE", "PTO_DATE", "PLOT_NO", "PREPORT_TYPE", "PEXTRA", "PEXTRA1", "PEXTRA2", "PEXTRA3", "PEXTRA4", "PEXTRA5", "PEXTRA6", "PEXTRA7", "PEXTRA8", "PEXTRA9", "PEXTRA10"))
                .returningResultSet("data", (rs, rowNum) -> mapIssued(rs));
    }

    private static SqlParameter[] parameters(String... names) {
        SqlParameter[] parameters = new SqlParameter[names.length];
        for (int i = 0; i < names.length; i++) {

            parameters[i] = new SqlParameter(names[i], Types.VARCHAR);
        }
        return parameters;
    }

    private Map<String, Object> commonParams(ReportRequestDto dto) {
        Objects.requireNonNull(dto, "Report request must not be null");
        Map<String, Object> params = new HashMap<>();
        params.put("PCOMP_CODE", dto.getPcompCode());
        params.put("PCENT_CODE", dto.getPcentCode());
        params.put("PBRANCH_CODE", dto.getPbranchCode());
        params.put("PORG_CODE", dto.getPorgCode());
        params.put("PITEM_TYPE", dto.getPitemType());
        params.put("PITEM_CODE", dto.getPitemCode());
        params.put("PSIZE", dto.getPsize());
        params.put("PCOLOR", dto.getPcolor());
        params.put("PPACKING_TYPE", dto.getPpackingType());
        params.put("PREPORT_TYPE", dto.getPreportType());
        params.put("PEXTRA", dto.getPextra());
        params.put("PEXTRA1", dto.getPextra1());
        params.put("PEXTRA2", dto.getPextra2());
        params.put("PEXTRA3", dto.getPextra3());
        params.put("PEXTRA4", dto.getPextra4());
        params.put("PEXTRA5", dto.getPextra5());
        params.put("PEXTRA6", dto.getPextra6());
        params.put("PEXTRA7", dto.getPextra7());
        params.put("PEXTRA8", dto.getPextra8());
        params.put("PEXTRA9", dto.getPextra9());
        params.put("PEXTRA10", dto.getPextra10());
        return params;
    }

    @Override
    public List<OrganiserSummaryResponseDto> getOrganiserStockSummary(ReportRequestDto dto) {
        Map<String, Object> params = commonParams(dto);
        params.put("PAS_ON_DATE", dto.getPasOnDate());
        return executeSummary(stockSummaryCall, params, "stock summary");
    }

    @Override
    public List<StockLedgerResponse> getOrganiserStockLedger(
            ReportRequestDto dto
            ) {



        Map<String, Object> params = commonParams(dto);

        params.put("PFROM_DATE", dto.getPfromDate());
        params.put("PTO_DATE", dto.getPtoDate());

        return executeLedger(
                stockLedgerCall,
                params,
                "stock ledger"

        );
    }

    @Override
    public List<ReportReposeDto> getIssuedOrganiserStockSummary(ReportRequestDto dto) {
        Map<String, Object> params = commonParams(dto);
        params.put("PFROM_DATE", dto.getPfromDate());
        params.put("PTO_DATE", dto.getPtoDate());
        params.put("PLOT_NO", dto.getPlotNo());
        return execute(issuedStockCall, params, "issued stock");
    }

    private List<ReportReposeDto> execute(SimpleJdbcCall call,
                                          Map<String, Object> params,
                                          String reportName) {
        try {
            Map<String, Object> result = call.execute(params);
            Object data = result.get("data");

            if (data == null) {
                return List.of();
            }

            if (!(data instanceof List<?> rows)) {
                throw new IllegalStateException(
                        "Unexpected result type for " + reportName
                );
            }

            List<ReportReposeDto> typedRows =
                    new java.util.ArrayList<>(rows.size());

            for (Object row : rows) {
                if (!(row instanceof ReportReposeDto dto)) {
                    throw new IllegalStateException(
                            "Unexpected row type for " + reportName
                    );
                }

                typedRows.add(dto);
            }

            return typedRows;

        } catch (DataAccessException ex) {
            log.error("Database error executing {} report", reportName, ex);
            throw ex;
        }
    }


    private List<StockLedgerResponse> executeLedger(
            SimpleJdbcCall call,
            Map<String, Object> params,
            String reportName
            ) {

        try {
            Map<String, Object> result = call.execute(params);
            Object data = result.get("data");

            if (data == null) {
                return List.of();
            }

            if (!(data instanceof List<?> rows)) {
                throw new IllegalStateException(
                        "Unexpected result type for " + reportName
                );
            }

            List<StockLedgerResponse> typedRows =
                    new java.util.ArrayList<>(rows.size());

            for (Object row : rows) {
                if (!(row instanceof StockLedgerResponse dto)) {
                    throw new IllegalStateException(
                            "Unexpected row type for " + reportName
                    );
                }

                typedRows.add(dto);
            }
            return typedRows;

        } catch (DataAccessException ex) {
            log.error(
                    "Database error executing {} report",
                    reportName,
                    ex
            );
            throw ex;
        }
    }
    private List<OrganiserSummaryResponseDto> executeSummary(
            SimpleJdbcCall call,
            Map<String, Object> params,
            String reportName) {

        try {
            Map<String, Object> result = call.execute(params);
            Object data = result.get("data");

            if (data == null) {
                return List.of();
            }

            if (!(data instanceof List<?> rows)) {
                throw new IllegalStateException(
                        "Unexpected result type for " + reportName
                );
            }

            List<OrganiserSummaryResponseDto> typedRows =
                    new java.util.ArrayList<>(rows.size());

            for (Object row : rows) {
                if (!(row instanceof OrganiserSummaryResponseDto dto)) {
                    throw new IllegalStateException(
                            "Unexpected row type for " + reportName
                    );
                }

                typedRows.add(dto);
            }

            return typedRows;

        } catch (DataAccessException ex) {
            log.error("Database error executing {} report", reportName, ex);
            throw ex;
        }
    }

    private OrganiserSummaryResponseDto mapSummary(ResultSet rs) throws SQLException {
        return OrganiserSummaryResponseDto.builder()
                .orgCode(rs.getString("ORG_CODE"))
                .orgName(rs.getString("ORG_NAME"))
                .itemType(rs.getString("ITEM_TYPE"))
                .itemTypeName(rs.getString("ITEM_TYPE_NAME"))
                .itemCode(rs.getString("ITEM_CODE"))
                .itemName(rs.getString("ITEM_NAME"))
                .size(rs.getString("SIZE"))
                .sizeName(rs.getString("SIZE_NAME"))
                .color(rs.getString("COLOR"))
                .packingType(rs.getString("PACKING_TYPE"))
                .finItemPackingName(rs.getString("FIN_ITEM_PACKING_NAME"))
                .docDate(rs.getString("DOC_DATE"))
                .docNo(rs.getString("DOC_NO"))
                .docType(rs.getString("DOC_TYPE"))
                .particulars(rs.getString("Particulars"))
                .lotNo(rs.getString("LOT_NO"))
                .openingQty(rs.getString("OPENING_QTY"))
                .receiptQty(rs.getString("Receipt_Qty"))
                .receiptQtyKg(rs.getString("Receipt_Qty_KG"))
                .issueQty(rs.getString("Issue_Qty"))
                .issueQtyKg(rs.getString("Issue_Qty_KG"))
                .balanceQty(rs.getString("Balance_Qty"))
                .qtyInKg(rs.getString("QTY_IN_KG"))
                .expiryDate(rs.getString("EXPIRY_DATE"))
                .cropName(rs.getString("CROP_NAME"))
                .itemCatName(rs.getString("ITEM_CAT_NAME"))
                .cropTypeName(rs.getString("CROP_TYPE_NAME"))
                .build();
    }

    private StockLedgerResponse mapLedger(ResultSet rs) throws SQLException {
        return StockLedgerResponse.builder()
                .compCode(rs.getString("COMP_CODE"))
                .centCode(rs.getString("CENT_CODE"))
                .orgCode(rs.getString("ORG_CODE"))
                .orgName(rs.getString("ORG_NAME"))
                .itemType(rs.getString("ITEM_TYPE"))
                .itemTypeName(rs.getString("ITEM_TYPE_NAME"))
                .itemCode(rs.getString("ITEM_CODE"))
                .itemName(rs.getString("ITEM_NAME"))
                .size(rs.getString("SIZE"))
                .sizeName(rs.getString("SIZE_NAME"))
                .color(rs.getString("COLOR"))
                .packingType(rs.getString("PACKING_TYPE"))
                .finItemPackingName(rs.getString("FIN_ITEM_PACKING_NAME"))
                .docDate(rs.getString("DOC_DATE"))
                .docNo(rs.getString("DOC_NO"))
                .docType(rs.getString("DOC_TYPE"))
                .particulars(rs.getString("Particulars"))
                .lotNo(rs.getString("LOT_NO"))
                .openingQty(rs.getString("OPENING_QTY"))
                .receiptQty(rs.getString("Receipt_Qty"))
                .receiptQtyKg(rs.getString("Receipt_Qty_KG"))
                .issueQty(rs.getString("Issue_Qty"))
                .issueQtyKg(rs.getString("Issue_Qty_KG"))
                .balanceQty(rs.getString("Balance_Qty"))
                .qtyInKg(rs.getString("QTY_IN_KG"))
                .expiryDate(rs.getString("EXPIRY_DATE"))
                .build();
    }

    private ReportReposeDto mapIssued(ResultSet rs) throws SQLException {

        return ReportReposeDto.builder()
                .compCode(rs.getString("COMP_CODE"))
                .compNick(rs.getString("COMP_NICK"))
                .centCode(rs.getString("CENT_CODE"))
                .centNick(rs.getString("CENT_NICK"))
                .branchCode(rs.getString("BRANCH_CODE"))
                .branchNick(rs.getString("BRANCH_NICK"))

                .returnTypeName(rs.getString("RETURN_TYPE_NAME"))

                .seasonCode(rs.getString("SEASON_CODE"))
                .seasonName(rs.getString("SEASON_NAME"))
                .finyearCode(rs.getString("FINYEAR_CODE"))
                .finyearNick(rs.getString("FINYEAR_NICK"))

                .orgCode(rs.getString("ORG_CODE"))
                .orgName(rs.getString("ORG_NAME"))

                .farmerCode(rs.getString("FARMER_CODE"))
                .farmerName(rs.getString("FARMER_NAME"))
                .villageCode(rs.getString("VILLAGE_CODE"))
                .villageName(rs.getString("VILLAGE_NAME"))

                .docType(rs.getString("DOC_TYPE"))
                .docNo(rs.getString("DOC_NO"))
                .docDate(rs.getString("DOC_DATE"))
                .refAgrNo(rs.getString("REF_AGR_NO"))

                .cropCode(rs.getString("CROP_CODE"))
                .cropName(rs.getString("CROP_NAME"))
                .seedClassCode(rs.getString("SEED_CLASS_CODE"))
                .itemCatName(rs.getString("ITEM_CAT_NAME"))

                .cropType(rs.getString("CROP_TYPE"))
                .cropTypeName(rs.getString("CROP_TYPE_NAME"))
                .itemType(rs.getString("ITEM_TYPE"))
                .itemTypeName(rs.getString("ITEM_TYPE_NAME"))

                .itemCode(rs.getString("ITEM_CODE"))
                .itemName(rs.getString("ITEM_NAME"))

                .size(rs.getString("SIZE"))
                .sizeName(rs.getString("SIZE_NAME"))
                .mode(rs.getString("MODE"))
                .modeName(rs.getString("MODE_NAME"))
                .color(rs.getString("COLOR"))

                .packingType(rs.getString("PACKING_TYPE_CODE"))
                .packingTypeName(rs.getString("PACKING_TYPE_NAME"))

                .baseUom(rs.getString("BASE_UOM"))
                .baseUomName(rs.getString("BASE_UOM_NAME"))
                .convFactor(rs.getString("CONV_FACTOR"))
                .purUom(rs.getString("PUR_UOM"))
                .purUomName(rs.getString("PUR_UOM_NAME"))

                .batchNo(rs.getString("BATCH_NO"))
                .expiryDate(rs.getString("EXPIRY_DATE"))

                .distrQty(rs.getString("DISTR_QTY"))
                .distrQtyKg(rs.getString("DISTR_QTY_KG"))
                .areaAcre(rs.getString("AREA_ACRE"))

                .remarks(rs.getString("REMARKS"))
                .build();
    }


}
