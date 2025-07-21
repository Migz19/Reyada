package com.example.Reyada.crm.deals.data;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "deals")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deal {

    @Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("ID")
    private Long id;

    @Column(name = "title", nullable = false)
    @JsonProperty("TITLE")
    private String title;

    @Column(name = "type_id", nullable = false)
    @JsonProperty("TYPE_ID")
    private String typeId;

    @Column(name = "category_id", nullable = false)
    @JsonProperty("CATEGORY_ID")
    private String categoryId;

    @Column(name = "stage_id", nullable = false)
    @JsonProperty("STAGE_ID")
    private String stageId;

    @Column(name = "opportunity", nullable = false)
    @JsonProperty("OPPORTUNITY")
    private Double opportunity;

    @Column(name = "is_manual_opportunity", nullable = false)
    @JsonProperty("IS_MANUAL_OPPORTUNITY")
    private String isManualOpportunity;

    @Column(name = "assigned_by_id", nullable = false)
    @JsonProperty("ASSIGNED_BY_ID")
    private String assignedById;



    @Column(name = "contract")
    private String contract;

    @Column(name = "tax_registration_no")
    private String taxRegistrationId;

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getTaxRegistrationId() {
        return taxRegistrationId;
    }

    public void setTaxRegistrationId(String taxRegistrationId) {
        this.taxRegistrationId = taxRegistrationId;
    }

    // Getters and Setters
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public Double getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(Double opportunity) {
        this.opportunity = opportunity;
    }

    public String getIsManualOpportunity() {
        return isManualOpportunity;
    }

    public void setIsManualOpportunity(String isManualOpportunity) {
        this.isManualOpportunity = isManualOpportunity;
    }

    public String getAssignedById() {
        return assignedById;
    }

    public void setAssignedById(String assignedById) {
        this.assignedById = assignedById;
    }


}