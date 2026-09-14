package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    Optional<Profissional> findByEmail(String email);
}
