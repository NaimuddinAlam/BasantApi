package com.medayes.erp.ErpApi.services.email;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WhatsAppServices {

    private final RestTemplate restTemplate = new RestTemplate();

    public String sendWhatsSms(String mobileNo, String taskName, String assignedBy, String deadline, String priority) {
        String whatsappMessage =
                "Hi,\n\n" +
                        "Please complete the assigned task *\"" + taskName + "\"*\n\n" +
                        "*Deadline:* " + deadline + "\n" +
                        "*Priority:* " + priority + "\n\n" +
                        "Please share the update once it is done.\n\n" +
                        "Thanks,\n" +
                        "*" + assignedBy + "*";

        String url = UriComponentsBuilder
                .fromHttpUrl("https://ai.advanzala.in/api/sendtemplate.php")
                .queryParam("LicenseNumber", "46039350505")
                .queryParam("APIKey", "O2sl71Q9Vkx3BUJEngNc5TaDW")
                .queryParam("Contact", "8791966956")
                .queryParam("Template", "test")
                .queryParam("Param", whatsappMessage)
                .queryParam("URLParam", "0000")
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }
}
