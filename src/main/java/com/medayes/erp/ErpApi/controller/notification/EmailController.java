package com.medayes.erp.ErpApi.controller.notification;


import com.medayes.erp.ErpApi.dto.request.task.SendEmailDTO;

import com.medayes.erp.ErpApi.services.email.SendEmailBulkServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
public class EmailController {
    private final SendEmailBulkServices emailService;

    public EmailController(SendEmailBulkServices emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody SendEmailDTO sendEmailDTO)
    {
        emailService.sendEmail(sendEmailDTO);
        return  new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/sendOTP")
    public ResponseEntity<?> sendOTP(@RequestBody SendEmailDTO sendEmailDTO)
    {
        emailService.sendEmail(sendEmailDTO);
        return  new ResponseEntity<>(HttpStatus.OK);
    }


}
