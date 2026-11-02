package com.medayes.erp.ErpApi.exeption;

public class BadRequestException  extends  RuntimeException{
    public BadRequestException(String message)
    {
        super(message);
    }
}
