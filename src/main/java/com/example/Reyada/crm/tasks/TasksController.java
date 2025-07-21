package com.example.Reyada.crm.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {
    @Autowired
    private TasksServices taskService;

    @PostMapping("/create")
    public ResponseEntity<String> createTask(@RequestBody TaskRequest req) {
        String result = taskService.createTask(req);
        System.out.println("T5434534 " + result);
        return ResponseEntity.ok(result);
    }
}
