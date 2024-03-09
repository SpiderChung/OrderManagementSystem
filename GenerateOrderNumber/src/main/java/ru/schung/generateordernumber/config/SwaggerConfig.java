package ru.schung.generateordernumber.config;

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
    public OpenAPI mainAppOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Main API Documentation").version("1.0.0"));
    }
}