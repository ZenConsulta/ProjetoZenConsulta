package br.edu.iff.ccc.webproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * Metadados exibidos no topo da tela do Swagger UI (/swagger-ui.html).
 * Não precisa de nenhum bean além dessa anotação — o springdoc-openapi
 * já escaneia todos os @RestController automaticamente.
 */
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
