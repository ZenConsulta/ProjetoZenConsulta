package br.edu.iff.ccc.webproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "profissional_id", nullable = false)
    private Long profissionalId;

    @Column(name = "slot_agenda_id", nullable = false)
    private Long slotAgendaId;

    @Column(name = "operador_responsavel_id")
    private Long operadorResponsavelId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status;

    public Agendamento() { this.status = StatusAgendamento.PENDENTE; }

    public Agendamento(Long id, Long pacienteId, Long profissionalId, Long slotAgendaId) {
        this.id = id; this.pacienteId = pacienteId; this.profissionalId = profissionalId;
        this.slotAgendaId = slotAgendaId; this.status = StatusAgendamento.PENDENTE;
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
