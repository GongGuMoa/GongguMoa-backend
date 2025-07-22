package com.gonggumoa.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSchemas("BaseResponse", new Schema<>().$ref("#/components/schemas/BaseResponse"))
                        .addSchemas("BaseErrorResponse", new Schema<>().$ref("#/components/schemas/BaseErrorResponse"))
                )
                .info(new Info()
                        .title("GongguMoa API 문서")
                        .version("1.0.0"));
    }
}
