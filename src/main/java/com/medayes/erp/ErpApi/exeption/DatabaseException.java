package com.medayes.erp.ErpApi.exeption;

public class DatabaseException extends  RuntimeException{
     public DatabaseException(String message, Throwable cause)
     {
         super(message,cause);
     }

}
