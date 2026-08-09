package br.edu.iff.ccc.webproject.service;

import br.edu.iff.ccc.webproject.dto.SlotAgendaDTO;
import br.edu.iff.ccc.webproject.exception.RegraDeNegocioException;
import br.edu.iff.ccc.webproject.model.SlotAgenda;
import br.edu.iff.ccc.webproject.repository.SlotAgendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RF01: o profissional define seus horários de atendimento (SlotAgenda).
 * RF14/RN02: um slot só pode ser reservado se estiver disponivel = true.
 */
@Service
public class SlotAgendaService {

    private final SlotAgendaRepository repository;

    public SlotAgendaService(SlotAgendaRepository repository) {
        this.repository = repository;
    }

    public SlotAgenda criar(SlotAgendaDTO dto) {
        boolean duplicado = repository.listarTodos().stream()
                .anyMatch(s -> s.getProfissionalId().equals(dto.getProfissionalId())
                        && s.getData().equals(dto.getData())
                        && s.getHorario().equals(dto.getHorario()));
        if (duplicado) {
            throw new RegraDeNegocioException("Já existe um horário cadastrado para este profissional nessa data/hora");
        }
        SlotAgenda slot = new SlotAgenda(null, dto.getProfissionalId(), dto.getData(), dto.getHorario(), true);
        return repository.salvar(slot);
    }

    public List<SlotAgenda> listarTodos() {
        return repository.listarTodos();
    }

    public List<SlotAgenda> listarDisponiveisPorProfissional(Long profissionalId) {
        return repository.listarTodos().stream()
                .filter(s -> s.getProfissionalId().equals(profissionalId) && s.isDisponivel())
                .toList();
    }

    public SlotAgenda buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Horário (slot) não encontrado (id " + id + ")"));
    }

    public void remover(Long id) {
        repository.remover(id);
    }

    void marcarComoIndisponivel(SlotAgenda slot) {
        slot.setDisponivel(false);
        repository.salvar(slot);
    }

    void liberar(SlotAgenda slot) {
        slot.setDisponivel(true);
        repository.salvar(slot);
    }
}
