package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByEmail(String email);
}
