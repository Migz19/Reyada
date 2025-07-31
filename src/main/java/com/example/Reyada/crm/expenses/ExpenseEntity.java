package com.example.Reyada.crm.expenses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Map;

@Entity(name = "expenses")

public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @JsonProperty("fields")
    @Column(columnDefinition = "JSON")            // MySQL JSON column
    @Convert(converter = MapToJsonConverter.class)
    private Map<String,Double> fields;
    @JsonProperty("type")
    private String  type;

    @JsonProperty("category")
    private String category;
    @JsonProperty("date")
    private LocalDate date;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Double> getFields() {
        return fields;
    }

    public void setFields(Map<String, Double> fields) {
        this.fields = fields;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
