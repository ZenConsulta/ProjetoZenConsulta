package br.edu.iff.ccc.webproject.controller.view;

import br.edu.iff.ccc.webproject.dto.SlotAgendaDTO;
import br.edu.iff.ccc.webproject.exception.RegraDeNegocioException;
import br.edu.iff.ccc.webproject.model.Profissional;
import br.edu.iff.ccc.webproject.service.ProfissionalService;
import br.edu.iff.ccc.webproject.service.SlotAgendaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/slots")
public class SlotAgendaViewController {

    private final SlotAgendaService service;
    private final ProfissionalService profissionalService;

    public SlotAgendaViewController(SlotAgendaService service, ProfissionalService profissionalService) {
        this.service = service;
        this.profissionalService = profissionalService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("slots", service.listarTodos());
        model.addAttribute("profissionais", profissionalService.listarTodos());
        model.addAttribute("nomesProfissionais", mapaNomesProfissionais());
        return "slots/lista";
    }

    @GetMapping("novo")
    public String formularioNovo(Model model) {
        model.addAttribute("slot", new SlotAgendaDTO());
        model.addAttribute("profissionais", profissionalService.listarTodos());
        return "slots/formulario";
    }

    @PostMapping
    public String salvar(@ModelAttribute("slot") SlotAgendaDTO dto, RedirectAttributes redirect) {
        try {
            service.criar(dto);
            redirect.addFlashAttribute("sucesso", "Horário cadastrado com sucesso!");
        } catch (RegraDeNegocioException ex) {
            redirect.addFlashAttribute("erro", ex.getMessage());
        }
        return "redirect:/slots";
    }

    @PostMapping("{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            service.remover(id);
            redirect.addFlashAttribute("sucesso", "Horário removido com sucesso!");
        } catch (RegraDeNegocioException ex) {
            redirect.addFlashAttribute("erro", ex.getMessage());
        }
        return "redirect:/slots";
    }

    private Map<Long, String> mapaNomesProfissionais() {
        return profissionalService.listarTodos().stream()
                .collect(Collectors.toMap(Profissional::getId, Profissional::getNome));
    }
}
