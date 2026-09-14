package br.edu.iff.ccc.webproject.controller.view;

import br.edu.iff.ccc.webproject.dto.PacienteDTO;
import br.edu.iff.ccc.webproject.exception.RegraDeNegocioException;
import br.edu.iff.ccc.webproject.model.Paciente;
import br.edu.iff.ccc.webproject.service.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pacientes")
public class PacienteViewController {

    private final PacienteService service;

    public PacienteViewController(PacienteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", service.listarTodos());
        return "pacientes/lista";
    }

    @GetMapping("novo")
    public String formularioNovo(Model model) {
        model.addAttribute("paciente", new PacienteDTO());
        return "pacientes/formulario";
    }

    @PostMapping
    public String salvar(@ModelAttribute("paciente") PacienteDTO dto, RedirectAttributes redirect) {
        service.criar(dto);
        redirect.addFlashAttribute("sucesso", "Paciente cadastrado com sucesso!");
        return "redirect:/pacientes";
    }

    @GetMapping("{id}/editar")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Paciente paciente = service.buscarPorId(id);
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNome(paciente.getNome());
        dto.setEmail(paciente.getEmail());
        dto.setTelefone(paciente.getTelefone());
        model.addAttribute("paciente", dto);
        return "pacientes/formulario";
    }

    @PostMapping("{id}/editar")
    public String atualizar(@PathVariable Long id, @ModelAttribute("paciente") PacienteDTO dto, RedirectAttributes redirect) {
        service.atualizar(id, dto);
        redirect.addFlashAttribute("sucesso", "Paciente atualizado com sucesso!");
        return "redirect:/pacientes";
    }

    @PostMapping("{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            service.remover(id);
            redirect.addFlashAttribute("sucesso", "Paciente removido com sucesso!");
        } catch (RegraDeNegocioException ex) {
            redirect.addFlashAttribute("erro", ex.getMessage());
        }
        return "redirect:/pacientes";
    }
}
