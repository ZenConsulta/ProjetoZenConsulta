package br.edu.iff.ccc.webproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados de cadastro/edição de um Paciente")
public class PacienteDTO {

    private Long id;

    @Schema(description = "Nome completo do paciente", example = "Maria Silva", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Schema(description = "E-mail único do paciente", example = "maria.silva@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Informe um email válido")
    private String email;

    @Schema(description = "Telefone de contato (opcional)", example = "21999990000")
    private String telefone;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
