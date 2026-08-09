package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.Administrador;
import org.springframework.stereotype.Repository;

@Repository
public class AdministradorRepository extends RepositorioMemoria<Administrador> {
    public AdministradorRepository() {
        super(Administrador::getId, Administrador::setId);
    }
}
