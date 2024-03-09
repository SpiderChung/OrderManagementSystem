package ru.schung.order.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI aggregatedAppOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Aggregated API Documentation").version("1.0.0"));
    }

    @Bean
    public GroupedOpenApi aggregatedApi() {
        return GroupedOpenApi.builder()
                .group("Aggregated API")
                .pathsToMatch("http://localhost:8080/v3/api-docs") // Подставьте пути ваших контроллеров
                .build();
    }



}