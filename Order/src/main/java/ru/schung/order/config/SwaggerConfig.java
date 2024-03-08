package ru.schung.order.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocConfigProperties.GroupConfig;
import org.springdoc.core.SpringDocConfigProperties.GroupConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.GroupedOpenApi;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server().url("http://localhost:8080"),
                                new Server().url("http://localhost:8081")
                        )
                )
                .info(
                        new Info().title("Документация")
                );
    }

    @Bean
    public GroupedOpenApi myGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("my-api")
                .pathsToMatch("/api/**")
                .build();
    }



}