package com.example.Reyada.crm.tasks.services;

import com.example.Reyada.crm.deals.data.DealsRepo;
import com.example.Reyada.crm.tasks.TasksListResponse;
import com.example.Reyada.crm.tasks.data.Task;
import com.example.Reyada.crm.tasks.data.TasksRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class TasksServices {
    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    private TasksRepo repo;
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
        //System.out.println(" 3424324 Creating task with URL: " + url);
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



    public TaskDto getTask(Long taskId) {
        String url = String.format("%s/rest/%s/%s/tasks.task.list",
                bitrixBaseUrl,
                userId,
                webhook);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("taskId", taskId);
        requestBody.put("select", List.of(
                "ID",
                "CREATED_DATE",
                "DEADLINE",
                "RESPONSIBLE_ID",
                "REAL_STATUS",
                "RESPONSIBLE_LAST_NAME"
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        TaskResponse response = restTemplate.postForObject(
                url, request, TaskResponse.class);

        return response.getResult().getTask();
    }


    public List<TaskDto> listTasks(Map<String, Object> filter) throws JsonProcessingException {
        String url = String.format("%s/rest/%s/%s/tasks.task.list",
                bitrixBaseUrl,
                userId,
                webhook);
        // Build request payload
        Map<String,Object> body = Map.of(
                "filter", filter,
                "select", List.of("ID","CREATED_DATE","DEADLINE","RESPONSIBLE_ID","REAL_STATUS","RESPONSIBLE_LAST_NAME")
        );
        HttpHeaders headers = new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String,Object>> request = new HttpEntity<>(body, headers);
        String json = restTemplate.postForObject(url, request, String.class);

        // Parse manually to handle offset timestamps safely
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode tasksNode = root.path("result").path("tasks");

        List<TaskDto> list = new ArrayList<>();
        for (JsonNode node : tasksNode) {
            TaskDto dto = new TaskDto();
            dto.setId(node.path("ID").asLong());

            String createdText = node.path("CREATED_DATE").asText(null);
            if (createdText != null && !createdText.isEmpty()) {
                dto.setCreatedDate(OffsetDateTime.parse(createdText).toLocalDateTime());
            }

            String deadlineText = node.path("DEADLINE").asText(null);
            if (deadlineText != null && !deadlineText.isEmpty()) {
                dto.setDeadline(OffsetDateTime.parse(deadlineText).toLocalDateTime());
            }

            dto.setResponsibleId(node.path("RESPONSIBLE_ID").asLong());
            dto.setRealStatus(node.path("REAL_STATUS").asInt());
            dto.setResponsibleLastName(node.path("RESPONSIBLE_LAST_NAME").asText(null));
            list.add(dto);
        }
        return list;
    }
    /**
     * Fetch tasks from Bitrix and persist them to the database.
     */
    public List<Task> syncAndSaveTasks(Map<String, Object> filter) throws JsonProcessingException {
        List<TaskDto> dtos = listTasks(filter);
        List<Task> entities = dtos.stream().map(dto -> {
            Task e = new Task();
            e.setId(dto.getId());
            e.setCreatedDate(dto.getCreatedDate());
            e.setDeadline(dto.getDeadline());
            e.setResponsibleId(dto.getResponsibleId());
            e.setRealStatus(dto.getRealStatus());
            e.setResponsibleLastName(dto.getResponsibleLastName());
            return e;
        }).collect(Collectors.toList());
        return repo.saveAll(entities);
    }
}

