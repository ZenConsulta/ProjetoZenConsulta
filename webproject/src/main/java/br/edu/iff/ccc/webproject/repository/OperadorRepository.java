package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.Operador;
import org.springframework.stereotype.Repository;

@Repository
public class OperadorRepository extends RepositorioMemoria<Operador> {
    public OperadorRepository() {
        super(Operador::getId, Operador::setId);
    }
}
