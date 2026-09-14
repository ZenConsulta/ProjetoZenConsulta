package br.edu.iff.ccc.webproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados para solicitar um novo Agendamento")
public class AgendamentoDTO {

    @Schema(description = "Id do paciente que está solicitando", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O paciente é obrigatório")
    @Positive(message = "Id de paciente inválido")
    private Long pacienteId;

    @Schema(description = "Id do profissional escolhido", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O profissional é obrigatório")
    @Positive(message = "Id de profissional inválido")
    private Long profissionalId;

    @Schema(description = "Id do horário (SlotAgenda) escolhido", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O horário é obrigatório")
    @Positive(message = "Id de horário inválido")
    private Long slotAgendaId;

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public Long getProfissionalId() { return profissionalId; }
    public void setProfissionalId(Long profissionalId) { this.profissionalId = profissionalId; }
    public Long getSlotAgendaId() { return slotAgendaId; }
    public void setSlotAgendaId(Long slotAgendaId) { this.slotAgendaId = slotAgendaId; }
}
