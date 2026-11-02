package com.medayes.erp.ErpApi.exeption;

public class ResourceNotFoundException  extends  RuntimeException{
    public ResourceNotFoundException(String message)
    {
         super(message);
    }
}
