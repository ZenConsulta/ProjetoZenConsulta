package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.SlotAgenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotAgendaRepository extends JpaRepository<SlotAgenda, Long> {
    List<SlotAgenda> findByProfissionalIdAndDisponivelTrue(Long profissionalId);
    boolean existsByProfissionalIdAndDataAndHorario(Long profissionalId, String data, String horario);
}
