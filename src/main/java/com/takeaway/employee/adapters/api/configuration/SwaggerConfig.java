package com.takeaway.employee.adapters.api.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi apiVersion1() {
        return GroupedOpenApi.builder().group("TakeawayChallenge").packagesToScan("com.takeaway.employee.adapters.api").displayName("Takeaway Challenge").build();
    }
}
