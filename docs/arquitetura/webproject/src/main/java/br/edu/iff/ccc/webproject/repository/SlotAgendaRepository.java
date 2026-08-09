package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.SlotAgenda;
import org.springframework.stereotype.Repository;

@Repository
public class SlotAgendaRepository extends RepositorioMemoria<SlotAgenda> {
    public SlotAgendaRepository() {
        super(SlotAgenda::getId, SlotAgenda::setId);
    }
}
