package com.example.Reyada.crm.tasks;

import com.example.Reyada.crm.tasks.services.TaskDto;
import com.example.Reyada.crm.tasks.services.TaskRequest;
import com.example.Reyada.crm.tasks.services.TasksServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    @Autowired
    private TasksServices taskService;

    @PostMapping("/create")
    public ResponseEntity<String> createTask(@RequestBody TaskRequest req) {
        String result = taskService.createTask(req);
        System.out.println("T5434534 " + result);
        return ResponseEntity.ok(result);
    }
    @GetMapping
    public List<TaskDto> getTasks(
            @RequestParam(required = false) Long minId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String createdBefore
    ) throws JsonProcessingException {
        Map<String,Object> filter = new HashMap<>();
        if (minId != null) filter.put(">ID", minId);
        if (status != null) filter.put("=REAL_STATUS", status);
        if (createdBefore != null) filter.put("<CREATED_DATE", createdBefore);
        return taskService.listTasks(filter);
    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        return taskService.getTask(id);
    }

}
