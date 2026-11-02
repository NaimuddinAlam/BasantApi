package com.medayes.erp.ErpApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.medayes.erp.ErpApi")
public class ErpApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpApiApplication.class, args);
	}

}
