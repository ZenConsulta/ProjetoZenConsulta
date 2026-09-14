package br.edu.iff.ccc.webproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados de cadastro/edição de um Profissional")
public class ProfissionalDTO {

    private Long id;

    @Schema(description = "Nome completo do profissional", example = "Dra. Sarah Jenkins", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Schema(description = "Especialidade médica", example = "Cardiologia", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "A especialidade é obrigatória")
    private String especialidade;

    @Schema(description = "E-mail único do profissional", example = "sarah.jenkins@zenconsulta.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Informe um email válido")
    private String email;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
