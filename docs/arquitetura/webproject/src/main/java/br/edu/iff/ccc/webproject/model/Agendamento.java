package br.edu.iff.ccc.webproject.model;

/**
 * Entidade Agendamento: vincula Paciente + Profissional + SlotAgenda (RF09),
 * controla o status (RF13) e registra o Operador responsável pela
 * confirmação (RF12).
 */
public class Agendamento {

    private Long id;
    private Long pacienteId;
    private Long profissionalId;
    private Long slotAgendaId;
    private Long operadorResponsavelId;
    private StatusAgendamento status;

    public Agendamento() {
        this.status = StatusAgendamento.PENDENTE;
    }

    public Agendamento(Long id, Long pacienteId, Long profissionalId, Long slotAgendaId) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.profissionalId = profissionalId;
        this.slotAgendaId = slotAgendaId;
        this.status = StatusAgendamento.PENDENTE;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public Long getProfissionalId() { return profissionalId; }
    public void setProfissionalId(Long profissionalId) { this.profissionalId = profissionalId; }

    public Long getSlotAgendaId() { return slotAgendaId; }
    public void setSlotAgendaId(Long slotAgendaId) { this.slotAgendaId = slotAgendaId; }

    public Long getOperadorResponsavelId() { return operadorResponsavelId; }
    public void setOperadorResponsavelId(Long operadorResponsavelId) { this.operadorResponsavelId = operadorResponsavelId; }

    public StatusAgendamento getStatus() { return status; }
    public void setStatus(StatusAgendamento status) { this.status = status; }
}
