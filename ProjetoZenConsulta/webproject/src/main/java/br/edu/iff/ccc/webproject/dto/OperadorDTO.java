package br.edu.iff.ccc.webproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados de cadastro/edição de um Operador")
public class OperadorDTO {

    private Long id;

    @Schema(description = "Nome completo do operador", example = "Carlos Souza", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Schema(description = "E-mail único do operador", example = "carlos.souza@zenconsulta.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Informe um email válido")
    private String email;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
