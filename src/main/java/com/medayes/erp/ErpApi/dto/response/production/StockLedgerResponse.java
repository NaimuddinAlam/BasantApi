package com.medayes.erp.ErpApi.dto.response.production;




import lombok.Builder;
import lombok.Data;

    @Data
    @Builder
    public class StockLedgerResponse {

        private String compCode;
        private String centCode;

        private String orgCode;
        private String orgName;

        private String itemType;
        private String itemTypeName;

        private String itemCode;
        private String itemName;

        private String size;
        private String sizeName;

        private String color;

        private String packingType;
        private String finItemPackingName;

        private String docDate;
        private String docNo;
        private String docType;
        private String particulars;
        private String lotNo;

        private String openingQty;

        private String receiptQty;
        private String receiptQtyKg;

        private String issueQty;
        private String issueQtyKg;

        private String balanceQty;
        private String qtyInKg;

        private String expiryDate;
    }

