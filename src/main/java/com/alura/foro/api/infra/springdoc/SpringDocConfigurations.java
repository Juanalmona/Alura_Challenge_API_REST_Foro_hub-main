 package com.alura.foro.api.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

 @Configuration
 public class SpringDocConfigurations {

     /**
      * Configura y devuelve una instancia personalizada de OpenAPI que define los esquemas de seguridad.
      *
      * @return OpenAPI configurado con el esquema de seguridad 'bearer-key'.
      */
     @Bean
     public OpenAPI customOpenAPI() {
         return new OpenAPI()
                 .components(new Components()
                         .addSecuritySchemes("bearer-key",
                                 new SecurityScheme()
                                         .type(SecurityScheme.Type.HTTP)
                                         .scheme("bearer")
                                         .bearerFormat("JWT")
                         )
                 ).info(new Info().title("Challenge API REST FORO HUB ALURA")
                         .version("1 - 2024").description("Challenge API Rest de la aplicación Foro Hub Alura ONE"));
     }
 }
