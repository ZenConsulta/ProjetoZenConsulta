package br.edu.iff.ccc.webproject.model;

/**
 * Entidade SlotAgenda: um horário específico de um Profissional (RF01).
 * "disponivel" começa true e só vira false quando um Agendamento é
 * CONFIRMADO nesse slot (RF15). Ao cancelar um agendamento confirmado,
 * o slot volta a ficar disponivel = true (HU04).
 */
public class SlotAgenda {

    private Long id;
    private Long profissionalId;
    private String data;
    private String horario;
    private boolean disponivel;

    public SlotAgenda() {
        this.disponivel = true;
    }

    public SlotAgenda(Long id, Long profissionalId, String data, String horario, boolean disponivel) {
        this.id = id;
        this.profissionalId = profissionalId;
        this.data = data;
        this.horario = horario;
        this.disponivel = disponivel;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProfissionalId() { return profissionalId; }
    public void setProfissionalId(Long profissionalId) { this.profissionalId = profissionalId; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
}
