package com.example.Reyada.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Configuration
    public class JacksonConfig {
        @Bean
        public Jackson2ObjectMapperBuilderCustomizer customJson() {
            return builder -> builder
                    .propertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE)
                    .failOnUnknownProperties(false);
        }
    }

}
