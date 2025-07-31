package com.example.Reyada.crm.tasks.services;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskRequest {
    @JsonProperty("TITLE")
    private String title;
    @JsonProperty("DEADLINE")
    private String deadline;

    @JsonProperty("RESPONSIBLE_ID")
    private int responsibleId;

    public TaskRequest() {
    }

    public TaskRequest(String title, int responsibleId, String deadline, int responsibleId1) {
        this.title = title;
        this.responsibleId = responsibleId;
        this.deadline = deadline;
        this.responsibleId = responsibleId1;
    }



    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public int getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(int responsibleId) {
        this.responsibleId = responsibleId;
    }
}
