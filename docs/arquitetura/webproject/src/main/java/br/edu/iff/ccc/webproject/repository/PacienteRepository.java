package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.Paciente;
import org.springframework.stereotype.Repository;

@Repository
public class PacienteRepository extends RepositorioMemoria<Paciente> {
    public PacienteRepository() {
        super(Paciente::getId, Paciente::setId);
    }
}
