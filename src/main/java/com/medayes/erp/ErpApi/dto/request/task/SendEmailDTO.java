package com.medayes.erp.ErpApi.dto.request.task;

import java.util.List;

public record SendEmailDTO(
            List<RecipientDTO> recipients, String taskName, String assignedBy, String deadline, String priority, String remake, String otp) {
}
