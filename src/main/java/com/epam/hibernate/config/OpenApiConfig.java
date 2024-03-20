package com.epam.hibernate.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info().title("GYM-REST")
                        .description("EPAM Task. OpenAPI 3 because swagger 2 can not be used with spring boot 3.x version")
                        .contact(new Contact().email("nikoloz_kiladze@epam.com")));
    }
}
