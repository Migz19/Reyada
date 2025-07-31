package com.example.Reyada.crm.tasks.services;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskDto {
    @Override
    public String toString() {
        return "TaskDto{" +
                "id=" + id +
                ", dateClosed=" + dateClosed +
                ", createdDate=" + createdDate +
                ", deadline=" + deadline +
                ", responsibleId=" + responsibleId +
                ", realStatus=" + realStatus +
                ", responsibleLastName='" + responsibleLastName + '\'' +
                '}';
    }

    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @JsonProperty("DATE_CLOSED") private OffsetDateTime dateClosed;
    @JsonProperty("CREATED_DATE")
    private LocalDateTime createdDate;

    private LocalDateTime deadline;

    @JsonProperty("RESPONSIBLE_ID")
    private Long responsibleId;

    @JsonProperty("REAL_STATUS")
    private Integer realStatus;

    @JsonProperty("RESPONSIBLE_LAST_NAME")
    private String responsibleLastName;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }
    public Long getResponsibleId() { return responsibleId; }
    public void setResponsibleId(Long responsibleId) { this.responsibleId = responsibleId; }
    public Integer getRealStatus() { return realStatus; }
    public void setRealStatus(Integer realStatus) { this.realStatus = realStatus; }
    public String getResponsibleLastName() { return responsibleLastName; }
    public void setResponsibleLastName(String responsibleLastName) { this.responsibleLastName = responsibleLastName; }
}