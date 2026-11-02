package com.medayes.erp.ErpApi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;



@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "ERP Purchase API",
                version = "1.0",
                description = "Purchase Order, Item & Quotation APIs"
        )
)
public class SwaggerConfig {
}
