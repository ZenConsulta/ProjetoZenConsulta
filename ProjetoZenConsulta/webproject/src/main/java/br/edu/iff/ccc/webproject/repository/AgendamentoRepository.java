package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByProfissionalId(Long profissionalId);
    List<Agendamento> findByPacienteId(Long pacienteId);
}
