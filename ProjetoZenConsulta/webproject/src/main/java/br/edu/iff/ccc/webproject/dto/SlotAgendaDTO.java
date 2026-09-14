package br.edu.iff.ccc.webproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados de cadastro de um horário (SlotAgenda) de um Profissional")
public class SlotAgendaDTO {

    private Long id;

    @Schema(description = "Id do profissional dono do horário", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O profissional é obrigatório")
    @Positive(message = "Id de profissional inválido")
    private Long profissionalId;

    @Schema(description = "Data do horário (yyyy-MM-dd)", example = "2026-10-24", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "A data é obrigatória")
    private String data;

    @Schema(description = "Horário (HH:mm)", example = "14:30", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O horário é obrigatório")
    private String horario;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProfissionalId() { return profissionalId; }
    public void setProfissionalId(Long profissionalId) { this.profissionalId = profissionalId; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
}
