package com.example.Reyada.crm.leads.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LeadDto {
    private Long id;
    private String title;
    @JsonProperty("STATUS_ID") private String statusId;
    private BigDecimal opportunity;
    @JsonProperty("DATE_CREATE") private LocalDateTime dateCreate;
    @JsonProperty("DATE_CLOSED") private LocalDateTime dateClosed;
    @JsonProperty("ASSIGNED_BY_ID") private Long assignedById;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public BigDecimal getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(BigDecimal opportunity) {
        this.opportunity = opportunity;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDateTime getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(LocalDateTime dateClosed) {
        this.dateClosed = dateClosed;
    }

    public Long getAssignedById() {
        return assignedById;
    }

    public void setAssignedById(Long assignedById) {
        this.assignedById = assignedById;
    }
}
