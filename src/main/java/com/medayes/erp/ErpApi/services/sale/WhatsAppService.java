package com.medayes.erp.ErpApi.services.sale;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WhatsAppService {

    private final RestClient restClient;

    @Value("${pinbot.whatsapp.url}")
    private String whatsappUrl;

    @Value("${pinbot.whatsapp.api-key}")
    private String apiKey;

    public void sendSalesOrderWhatsApp(
            String mobile,
            String orderNo,
            String empName,
            String customerName) {

        try {

            Map<String, Object> request = Map.of(
                    "messaging_product", "whatsapp",
                    "recipient_type", "individual",
                    "to", mobile,
                    "type", "template",
                    "template", Map.of(
                            "language", Map.of(
                                    "code", "en"
                            ),
                            "name", "so_app",
                            "components", List.of(
                                    Map.of(
                                            "type", "body",
                                            "parameters", List.of(
                                                    Map.of(
                                                            "type", "text",
                                                            "text", orderNo
                                                    ),
                                                    Map.of(
                                                            "type", "text",
                                                            "text", empName
                                                    ),
                                                    Map.of(
                                                            "type", "text",
                                                            "text", customerName
                                                    )
                                            )
                                    )
                            )
                    )
            );

            String response = restClient.post()
                    .uri(whatsappUrl)
                    .header("apikey", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(String.class);

            System.out.println(
                    "✅ WhatsApp Sales Order Sent: " + response
            );

        } catch (Exception e) {

            System.err.println(
                    "❌ WhatsApp Sales Order Failed: "
                            + e.getMessage()
            );
        }
    }
}