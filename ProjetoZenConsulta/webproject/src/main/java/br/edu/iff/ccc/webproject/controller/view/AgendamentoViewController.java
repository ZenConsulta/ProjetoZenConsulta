package br.edu.iff.ccc.webproject.controller.view;

import br.edu.iff.ccc.webproject.dto.AgendamentoDTO;
import br.edu.iff.ccc.webproject.model.Operador;
import br.edu.iff.ccc.webproject.model.Paciente;
import br.edu.iff.ccc.webproject.model.Profissional;
import br.edu.iff.ccc.webproject.model.SlotAgenda;
import br.edu.iff.ccc.webproject.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("novo")
    public String formularioNovo(Model model) {
        model.addAttribute("agendamento", new AgendamentoDTO());
        carregarListasDoFormulario(model);
        return "agendamentos/formulario";
    }

    @PostMapping
    public String solicitar(@Valid @ModelAttribute("agendamento") AgendamentoDTO dto, BindingResult bindingResult, Model model, RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            carregarListasDoFormulario(model);
            return "agendamentos/formulario";
        }
        service.solicitar(dto);
        redirect.addFlashAttribute("sucesso", "Agendamento solicitado! Aguardando confirmação do operador.");
        return "redirect:/agendamentos";
    }

    // Sem try/catch: violação de regra de negócio (RN01-RN05) ou id
    // inexistente sobem para o GlobalExceptionHandler (TR06), que
    // direciona para uma página de erro amigável.
    @PostMapping("{id}/confirmar")
    public String confirmar(@PathVariable Long id, @RequestParam Long operadorId, RedirectAttributes redirect) {
        service.confirmar(id, operadorId);
        redirect.addFlashAttribute("sucesso", "Agendamento confirmado com sucesso!");
        return "redirect:/agendamentos";
    }

    @PostMapping("{id}/cancelar")
    public String cancelar(@PathVariable Long id, RedirectAttributes redirect) {
        service.cancelar(id);
        redirect.addFlashAttribute("sucesso", "Agendamento cancelado. O horário foi liberado.");
        return "redirect:/agendamentos";
    }

    private void carregarListasDoFormulario(Model model) {
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("profissionais", profissionalService.listarTodos());
        List<SlotAgenda> slotsDisponiveis = slotAgendaService.listarTodos().stream()
                .filter(SlotAgenda::isDisponivel)
                .toList();
        model.addAttribute("slotsDisponiveis", slotsDisponiveis);
    }
}
