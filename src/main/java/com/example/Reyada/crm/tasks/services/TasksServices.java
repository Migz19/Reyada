package com.example.Reyada.crm.tasks.services;

import com.example.Reyada.crm.deals.data.DealsRepo;
import com.example.Reyada.crm.tasks.TasksListResponse;
import com.example.Reyada.crm.tasks.data.Task;
import com.example.Reyada.crm.tasks.data.TasksRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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


    public List<Task> listTasks(Map<String, Object> filter) throws JsonProcessingException {
        String url = String.format("%s/rest/%s/%s/tasks.task.list",
                bitrixBaseUrl, userId, webhook);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String,Object> body = Map.of(
                "filter",   filter,
                "select",  List.of("ID","CREATED_DATE","DEADLINE","RESPONSIBLE_ID","REAL_STATUS","RESPONSIBLE_LAST_NAME")
        );
        String raw = restTemplate
                .postForObject(url, new HttpEntity<>(body, headers), String.class);

        // strip junk before the first '{'
        int idx = raw.indexOf('{');
        if (idx > 0) raw = raw.substring(idx);

        // parse JSON with case‐insensitive props and JavaTime support
        ObjectMapper mapper = new ObjectMapper()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .registerModule(new JavaTimeModule());

        JsonNode tasks = mapper.readTree(raw)
                .path("result")
                .path("tasks");

        List<TaskDto> dtos = new ArrayList<>();
        for (JsonNode n : tasks) {
            TaskDto dto = new TaskDto();
            dto.setId(n.path("id").asLong());
            dto.setCreatedDate(OffsetDateTime.parse(n.path("createdDate").asText()).toLocalDateTime());
            dto.setDeadline(   OffsetDateTime.parse(n.path("deadline").asText()).toLocalDateTime());
            dto.setResponsibleId(   n.path("responsibleId").asLong());
            dto.setRealStatus(      n.path("realStatus").asInt());
            dto.setResponsibleLastName(n.path("responsible").path("name").asText());
            dtos.add(dto);
        }
        System.out.println("924389432"+dtos.get(0).toString());
        return syncAndSaveTasks(dtos);
    }

    public List<Task> syncAndSaveTasks(List<TaskDto> dtos) throws JsonProcessingException {

        List<Task> entities = dtos.stream().map(dto -> {
            Task e = new Task();
            e.setId(dto.getId());
            e.setCreatedDate(dto.getCreatedDate());
            e.setDeadline(dto.getDeadline());
            e.setResponsibleId(dto.getResponsibleId());
            e.setRealStatus(dto.getRealStatus());
            e.setResponsibleLastName(dto.getResponsibleLastName());
            System.out.println("239428934"+ dto.toString());
            return e;
        }).collect(Collectors.toList());
        return repo.saveAll(entities);
    }

    public List<Task> fetchTasksoffline(){
        return repo.findAll();
    }
}

