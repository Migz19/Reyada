package com.example.Reyada.crm.expenses;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Map;

@Entity(name = "Expenses")

public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;


    @Column(columnDefinition = "JSON")            // MySQL JSON column
    @Convert(converter = MapToJsonConverter.class)
    private Map<String,Double> fields;
    private String  type;
    private String category;
    private LocalDateTime date;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
