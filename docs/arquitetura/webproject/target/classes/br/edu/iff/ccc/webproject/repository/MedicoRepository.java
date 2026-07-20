java
package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.Medico;
import java.util.List;
import java.util.Optional;

public interface MedicoRepository {
    Medico salvar(Medico medico);
    List<Medico> listarTodos();
    Optional<Medico> buscarPorId(String id);
}
