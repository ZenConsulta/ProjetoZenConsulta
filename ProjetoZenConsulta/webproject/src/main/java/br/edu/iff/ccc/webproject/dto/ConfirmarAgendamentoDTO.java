package br.edu.iff.ccc.webproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Payload para confirmar um agendamento pendente")
public class ConfirmarAgendamentoDTO {

    @Schema(description = "Id do operador responsável pela confirmação", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O operador é obrigatório")
    @Positive(message = "Id de operador inválido")
    private Long operadorId;

    public Long getOperadorId() { return operadorId; }
    public void setOperadorId(Long operadorId) { this.operadorId = operadorId; }
}
