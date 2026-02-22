package com.himfg.hospitalinfantil.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API HIMFG - Cuidados Paliativos Pediatricos",
                version = "1.0.0",
                description = "API para autenticacion, gestion de pacientes y encuestas clinicas.",
                contact = @Contact(
                        name = "Equipo HIMFG",
                        email = "soporte.himfg@mexico.com"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Servidor local")
        }
)
@SecurityScheme(
        name = "tokenSesion",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "X-Auth-Token",
        description = "Token de sesion generado en /api/autenticacion/iniciar-sesion"
)
public class ConfiguracionOpenApi {
}
