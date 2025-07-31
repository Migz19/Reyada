package com.example.Reyada.crm.expenses;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Collections;
import java.util.Map;

@Converter(autoApply = true)
public class MapToJsonConverter
        implements AttributeConverter<Map<String, Double>, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Double> map) {
        try {
            return mapper.writeValueAsString(map);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error serializing map to JSON", e);
        }
    }

    @Override
    public Map<String, Double> convertToEntityAttribute(String json) {
        try {
            if (json == null || json.isEmpty()) {
                return Collections.emptyMap();
            }
            return mapper.readValue(json, new TypeReference<Map<String, Double>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deserializing JSON to map", e);
        }
    }
}

