package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.Profissional;
import org.springframework.stereotype.Repository;

@Repository
public class ProfissionalRepository extends RepositorioMemoria<Profissional> {
    public ProfissionalRepository() {
        super(Profissional::getId, Profissional::setId);
    }
}
