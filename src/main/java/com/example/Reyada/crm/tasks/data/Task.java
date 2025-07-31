package com.example.Reyada.crm.tasks.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {


    @Id
    private Long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "responsible_id")
    private Long responsibleId;

    @Column(name = "real_status")
    private Integer realStatus;

    @Column(name = "responsible_last_name")
    private String responsibleLastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Long responsibleId) {
        this.responsibleId = responsibleId;
    }

    public Integer getRealStatus() {
        return realStatus;
    }

    public void setRealStatus(Integer realStatus) {
        this.realStatus = realStatus;
    }

    public String getResponsibleLastName() {
        return responsibleLastName;
    }

    public void setResponsibleLastName(String responsibleLastName) {
        this.responsibleLastName = responsibleLastName;
    }
}

