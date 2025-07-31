package com.example.Reyada.crm.tasks.services;


import com.fasterxml.jackson.annotation.JsonProperty;


public class TaskResponse {
    @JsonProperty("result")
    private TaskResult result;

    public TaskResult getResult() { return result; }
    public void setResult(TaskResult result) { this.result = result; }

    public static class TaskResult {
        @JsonProperty("task")
        private TaskDto task;
        public TaskDto getTask() { return task; }
        public void setTask(TaskDto task) { this.task = task; }
    }
}

