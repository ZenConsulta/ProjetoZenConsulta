package br.edu.iff.ccc.webproject.dto;

/**
 * DTO usado exclusivamente para SOLICITAR um novo agendamento (HU01).
 * O status, o operador responsável etc. não vêm do formulário: são
 * controlados pelo AgendamentoService, de acordo com as regras de negócio.
 */
public class AgendamentoDTO {
    private Long pacienteId;
    private Long profissionalId;
    private Long slotAgendaId;

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public Long getProfissionalId() { return profissionalId; }
    public void setProfissionalId(Long profissionalId) { this.profissionalId = profissionalId; }
    public Long getSlotAgendaId() { return slotAgendaId; }
    public void setSlotAgendaId(Long slotAgendaId) { this.slotAgendaId = slotAgendaId; }
}
