package br.edu.iff.ccc.webproject.controller.view;

import br.edu.iff.ccc.webproject.dto.AgendamentoDTO;
import br.edu.iff.ccc.webproject.exception.RegraDeNegocioException;
import br.edu.iff.ccc.webproject.model.Operador;
import br.edu.iff.ccc.webproject.model.Paciente;
import br.edu.iff.ccc.webproject.model.Profissional;
import br.edu.iff.ccc.webproject.model.SlotAgenda;
import br.edu.iff.ccc.webproject.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * =====================================================================
 * AgendamentoViewController
 * =====================================================================
 * Controller responsável pelas OPERAÇÕES DE NEGÓCIO que interligam as
 * entidades (Paciente + Profissional + SlotAgenda + Operador), conforme
 * pedido no roteiro da P1. Toda a lógica pesada mora no AgendamentoService
 * — aqui só existe orquestração de requisição HTTP <-> Service <-> View.
 */
@Controller
@RequestMapping("/agendamentos")
public class AgendamentoViewController {

    private final AgendamentoService service;
    private final PacienteService pacienteService;
    private final ProfissionalService profissionalService;
    private final OperadorService operadorService;
    private final SlotAgendaService slotAgendaService;

    public AgendamentoViewController(AgendamentoService service,
                                      PacienteService pacienteService,
                                      ProfissionalService profissionalService,
                                      OperadorService operadorService,
                                      SlotAgendaService slotAgendaService) {
        this.service = service;
        this.pacienteService = pacienteService;
        this.profissionalService = profissionalService;
        this.operadorService = operadorService;
        this.slotAgendaService = slotAgendaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("agendamentos", service.listarTodos());
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("profissionais", profissionalService.listarTodos());
        model.addAttribute("operadores", operadorService.listarTodos());
        model.addAttribute("nomesPacientes", pacienteService.listarTodos().stream()
                .collect(Collectors.toMap(Paciente::getId, Paciente::getNome)));
        model.addAttribute("nomesProfissionais", profissionalService.listarTodos().stream()
                .collect(Collectors.toMap(Profissional::getId, Profissional::getNome)));
        model.addAttribute("nomesOperadores", operadorService.listarTodos().stream()
                .collect(Collectors.toMap(Operador::getId, Operador::getNome)));
        model.addAttribute("horariosSlots", slotAgendaService.listarTodos().stream()
                .collect(Collectors.toMap(SlotAgenda::getId, s -> s.getData() + " às " + s.getHorario())));
        return "agendamentos/lista";
    }

    // HU01 - formulário de solicitação (paciente escolhe profissional + horário)
    @GetMapping("novo")
    public String formularioNovo(Model model) {
        model.addAttribute("agendamento", new AgendamentoDTO());
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("profissionais", profissionalService.listarTodos());
        List<SlotAgenda> slotsDisponiveis = slotAgendaService.listarTodos().stream()
                .filter(SlotAgenda::isDisponivel)
                .toList();
        model.addAttribute("slotsDisponiveis", slotsDisponiveis);
        return "agendamentos/formulario";
    }

    // HU01 - solicitar
    @PostMapping
    public String solicitar(@ModelAttribute("agendamento") AgendamentoDTO dto, RedirectAttributes redirect) {
        try {
            service.solicitar(dto);
            redirect.addFlashAttribute("sucesso", "Agendamento solicitado! Aguardando confirmação do operador.");
        } catch (RegraDeNegocioException ex) {
            redirect.addFlashAttribute("erro", ex.getMessage());
        }
        return "redirect:/agendamentos";
    }

    // HU02 - confirmar (Operador ou Administrador)
    @PostMapping("{id}/confirmar")
    public String confirmar(@PathVariable Long id, @RequestParam Long operadorId, RedirectAttributes redirect) {
        try {
            service.confirmar(id, operadorId);
            redirect.addFlashAttribute("sucesso", "Agendamento confirmado com sucesso!");
        } catch (RegraDeNegocioException ex) {
            redirect.addFlashAttribute("erro", ex.getMessage());
        }
        return "redirect:/agendamentos";
    }

    // HU04 - cancelar
    @PostMapping("{id}/cancelar")
    public String cancelar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            service.cancelar(id);
            redirect.addFlashAttribute("sucesso", "Agendamento cancelado. O horário foi liberado.");
        } catch (RegraDeNegocioException ex) {
            redirect.addFlashAttribute("erro", ex.getMessage());
        }
        return "redirect:/agendamentos";
    }
}
