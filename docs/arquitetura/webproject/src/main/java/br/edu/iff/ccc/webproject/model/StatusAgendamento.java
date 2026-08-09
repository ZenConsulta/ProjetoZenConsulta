package br.edu.iff.ccc.webproject.model;

/**
 * Estados possíveis de um Agendamento (RF13).
 * Fluxo obrigatório: PENDENTE -> CONFIRMADO -> CANCELADO (RN05: uma vez
 * CANCELADO, não pode voltar a nenhum outro estado).
 */
public enum StatusAgendamento {
    PENDENTE,
    CONFIRMADO,
    CANCELADO
}
