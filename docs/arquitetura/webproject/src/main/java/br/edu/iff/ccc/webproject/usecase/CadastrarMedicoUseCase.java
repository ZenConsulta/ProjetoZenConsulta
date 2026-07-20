package br.edu.iff.ccc.webproject.usecase;

import br.edu.iff.ccc.webproject.dto.MedicoDTO;
import br.edu.iff.ccc.webproject.model.Medico;
import br.edu.iff.ccc.webproject.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CadastrarMedicoUseCase {

    private final MedicoRepository medicoRepository;

    public CadastrarMedicoUseCase(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public Medico executar(MedicoDTO dto) {
        String id = UUID.randomUUID().toString();
        Medico medico = new Medico(id, dto.getNome(), dto.getEspecialidade(), dto.getLocal(), dto.getNota());
        return medicoRepository.salvar(medico);
    }
}
