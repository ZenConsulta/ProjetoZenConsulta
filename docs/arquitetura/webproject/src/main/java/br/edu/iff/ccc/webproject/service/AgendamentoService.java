package br.edu.iff.ccc.webproject.service;

import br.edu.iff.ccc.webproject.dto.AgendamentoDTO;
import br.edu.iff.ccc.webproject.exception.RegraDeNegocioException;
import br.edu.iff.ccc.webproject.model.Agendamento;
import br.edu.iff.ccc.webproject.model.SlotAgenda;
import br.edu.iff.ccc.webproject.model.StatusAgendamento;
import br.edu.iff.ccc.webproject.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * =====================================================================
 * AgendamentoService — coração das regras de negócio do sistema (RN01..RN05)
 * =====================================================================
 * É aqui que HU01, HU02 e HU04 realmente acontecem. O Controller só recebe
 * a requisição HTTP e delega tudo para cá; o Repository só guarda dados.
 * A "inteligência" do sistema fica isolada nesta classe.
 */
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

    /**
     * HU01 — Paciente solicita um agendamento.
     * RF09: vincula Paciente + Profissional + SlotAgenda.
     * RN02: o slot só pode ser reservado se estiver disponivel = true.
     * RN04: o paciente apenas SOLICITA (fica status PENDENTE); quem confirma
     * é sempre o Operador/Administrador, no método confirmar(...).
     */
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

    /**
     * HU02 — Operador (ou Administrador) confirma um agendamento pendente.
     * RN01/RF10: revalida se o slot continua disponível antes de confirmar
     * (evita condição de corrida entre duas solicitações concorrentes).
     * RF12: registra o operador responsável. RF13/RF15: muda o status e
     * marca o slot como indisponível.
     */
    public Agendamento confirmar(Long agendamentoId, Long operadorId) {
        Agendamento agendamento = buscarPorId(agendamentoId);
        operadorService.buscarPorId(operadorId); // RN03: só Operador/Administrador confirma

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

    /**
     * HU04 — Paciente (ou Operador) cancela um agendamento.
     * RN05: um agendamento CANCELADO não pode ser reativado.
     * Se o agendamento estava CONFIRMADO, o slot volta a ficar disponível.
     */
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
