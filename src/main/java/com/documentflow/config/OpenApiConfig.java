package com.documentflow.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(
                        new Info()
                                .title("DocumentFlow API")
                                .description("Automated Document Approval Workflow API")
                                .version("1.0"))

                .addSecurityItem(
                        new SecurityRequirement().addList(securitySchemeName))

                .components( // configurations
                        new Components().addSecuritySchemes(
                                securitySchemeName, new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer") // authentication method
                                        .bearerFormat("JWT")
                        )
                );
    }
}