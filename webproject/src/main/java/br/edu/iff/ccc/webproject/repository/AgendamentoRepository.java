package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.Agendamento;
import org.springframework.stereotype.Repository;

@Repository
public class AgendamentoRepository extends RepositorioMemoria<Agendamento> {
    public AgendamentoRepository() {
        super(Agendamento::getId, Agendamento::setId);
    }
}
