package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.Operador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperadorRepository extends JpaRepository<Operador, Long> {
    Optional<Operador> findByEmail(String email);
}
