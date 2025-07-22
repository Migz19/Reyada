package com.example.Reyada.crm.tasks;

import com.example.Reyada.crm.deals.data.DealsRepo;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service

public class TasksServices {

    private final RestTemplate restTemplate;
    private final String bitrixBaseUrl;
    private final String userId;
    private final String webhook;



    public TasksServices(RestTemplate restTemplate, DealsRepo repo) {
        this.restTemplate = restTemplate;
        this.bitrixBaseUrl = "https://b24-0r8mng.bitrix24.com";
        this.userId = "13";
        this.webhook = "xuzcmppmi95cw6u6";

    }

    public String createTask(TaskRequest request) {
        String url = String.format("%s/rest/%s/%s/task.item.add.json",
                bitrixBaseUrl,
                userId,
                webhook);
        System.out.println(" 3424324 Creating task with URL: " + url);
        Map<String, Object> fields = new HashMap<>();
        fields.put("TITLE", request.getTitle());;
        fields.put("RESPONSIBLE_ID", request.getResponsibleId());
        fields.put("DEADLINE",request.getDeadline());
        Map<String, Object> payload = Map.of("fields", fields);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );
        return response.getBody();
    }


}
