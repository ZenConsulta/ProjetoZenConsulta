package br.edu.iff.ccc.webproject.service;

import br.edu.iff.ccc.webproject.dto.SlotAgendaDTO;
import br.edu.iff.ccc.webproject.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc.webproject.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc.webproject.model.SlotAgenda;
import br.edu.iff.ccc.webproject.repository.SlotAgendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotAgendaService {

    private final SlotAgendaRepository repository;

    public SlotAgendaService(SlotAgendaRepository repository) {
        this.repository = repository;
    }

    public SlotAgenda criar(SlotAgendaDTO dto) {
        boolean duplicado = repository.existsByProfissionalIdAndDataAndHorario(
                dto.getProfissionalId(), dto.getData(), dto.getHorario());
        if (duplicado) {
            throw new EntidadeDuplicadaException("Já existe um horário cadastrado para este profissional nessa data/hora");
        }
        SlotAgenda slot = new SlotAgenda(null, dto.getProfissionalId(), dto.getData(), dto.getHorario(), true);
        return repository.save(slot);
    }

    public List<SlotAgenda> listarTodos() {
        return repository.findAll();
    }

    public List<SlotAgenda> listarDisponiveisPorProfissional(Long profissionalId) {
        return repository.findByProfissionalIdAndDisponivelTrue(profissionalId);
    }

    public SlotAgenda buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Horário (slot) não encontrado (id " + id + ")"));
    }

    public void remover(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    void marcarComoIndisponivel(SlotAgenda slot) {
        slot.setDisponivel(false);
        repository.save(slot);
    }

    void liberar(SlotAgenda slot) {
        slot.setDisponivel(true);
        repository.save(slot);
    }
}
