package br.edu.iff.ccc.webproject.service;

import br.edu.iff.ccc.webproject.dto.AgendamentoDTO;
import br.edu.iff.ccc.webproject.exception.RegraDeNegocioException;
import br.edu.iff.ccc.webproject.model.Agendamento;
import br.edu.iff.ccc.webproject.model.SlotAgenda;
import br.edu.iff.ccc.webproject.model.StatusAgendamento;
import br.edu.iff.ccc.webproject.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final PacienteService pacienteService;
    private final ProfissionalService profissionalService;
    private final OperadorService operadorService;
    private final SlotAgendaService slotAgendaService;

    public AgendamentoService(AgendamentoRepository repository,
                               PacienteService pacienteService,
                               ProfissionalService profissionalService,
                               OperadorService operadorService,
                               SlotAgendaService slotAgendaService) {
        this.repository = repository;
        this.pacienteService = pacienteService;
        this.profissionalService = profissionalService;
        this.operadorService = operadorService;
        this.slotAgendaService = slotAgendaService;
    }

    public Agendamento solicitar(AgendamentoDTO dto) {
        pacienteService.buscarPorId(dto.getPacienteId());
        profissionalService.buscarPorId(dto.getProfissionalId());
        SlotAgenda slot = slotAgendaService.buscarPorId(dto.getSlotAgendaId());

        if (!slot.isDisponivel()) {
            throw new RegraDeNegocioException("Horário já ocupado");
        }
        if (!slot.getProfissionalId().equals(dto.getProfissionalId())) {
            throw new RegraDeNegocioException("Esse horário não pertence ao profissional informado");
        }

        Agendamento agendamento = new Agendamento(null, dto.getPacienteId(), dto.getProfissionalId(), dto.getSlotAgendaId());
        return repository.salvar(agendamento);
    }

    public Agendamento confirmar(Long agendamentoId, Long operadorId) {
        Agendamento agendamento = buscarPorId(agendamentoId);
        operadorService.buscarPorId(operadorId);

        if (agendamento.getStatus() != StatusAgendamento.PENDENTE) {
            throw new RegraDeNegocioException("Só é possível confirmar agendamentos com status PENDENTE");
        }

        SlotAgenda slot = slotAgendaService.buscarPorId(agendamento.getSlotAgendaId());
        if (!slot.isDisponivel()) {
            throw new RegraDeNegocioException("Horário já ocupado");
        }

        slotAgendaService.marcarComoIndisponivel(slot);
        agendamento.setStatus(StatusAgendamento.CONFIRMADO);
        agendamento.setOperadorResponsavelId(operadorId);
        return repository.salvar(agendamento);
    }

    public Agendamento cancelar(Long agendamentoId) {
        Agendamento agendamento = buscarPorId(agendamentoId);

        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new RegraDeNegocioException("Este agendamento já está cancelado");
        }

        if (agendamento.getStatus() == StatusAgendamento.CONFIRMADO) {
            SlotAgenda slot = slotAgendaService.buscarPorId(agendamento.getSlotAgendaId());
            slotAgendaService.liberar(slot);
        }

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        return repository.salvar(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return repository.listarTodos();
    }

    public List<Agendamento> listarPorProfissional(Long profissionalId) {
        return repository.listarTodos().stream()
                .filter(a -> a.getProfissionalId().equals(profissionalId))
                .toList();
    }

    public List<Agendamento> listarPorPaciente(Long pacienteId) {
        return repository.listarTodos().stream()
                .filter(a -> a.getPacienteId().equals(pacienteId))
                .toList();
    }

    public Agendamento buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Agendamento não encontrado (id " + id + ")"));
    }
}
