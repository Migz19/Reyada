package com.example.Reyada.crm.tasks;


import com.example.Reyada.crm.tasks.services.TaskDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TasksListResponse {
    @JsonProperty("result")
    private TasksResult result;

    public TasksResult getResult() { return result; }
    public void setResult(TasksResult result) { this.result = result; }

    public static class TasksResult {
        @JsonProperty("tasks")
        private List<TaskDto> tasks;
        public List<TaskDto> getTasks() { return tasks; }
        public void setTasks(List<TaskDto> tasks) { this.tasks = tasks; }
    }
}

