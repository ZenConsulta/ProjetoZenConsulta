package br.edu.iff.ccc.webproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "ZenConsulta API",
                version = "v1",
                description = "API REST do sistema de agendamento de consultas ZenConsulta (TR07).",
                contact = @Contact(name = "Equipe ZenConsulta")
        )
)
@Configuration
public class OpenApiConfig {
}
