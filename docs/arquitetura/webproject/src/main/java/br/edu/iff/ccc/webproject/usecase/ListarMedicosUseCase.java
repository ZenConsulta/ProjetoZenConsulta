package br.edu.iff.ccc.webproject.usecase;

import br.edu.iff.ccc.webproject.model.Medico;
import br.edu.iff.ccc.webproject.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarMedicosUseCase {

    private final MedicoRepository medicoRepository;

    public ListarMedicosUseCase(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public List<Medico> executar() {
        return medicoRepository.listarTodos();
    }
}
