package br.edu.iff.ccc.webproject.controller.view;

import br.edu.iff.ccc.webproject.dto.PacienteDTO;
import br.edu.iff.ccc.webproject.model.Paciente;
import br.edu.iff.ccc.webproject.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    // @Valid aciona as anotações de Bean Validation do PacienteDTO
    // (@NotBlank, @Email). BindingResult TEM que vir logo em seguida do
    // objeto validado — se algum campo falhar, hasErrors() é true e a
    // gente volta pro próprio formulário (em vez de redirecionar),
    // mostrando as mensagens de erro sem perder o que já foi digitado.
    @PostMapping
    public String salvar(@Valid @ModelAttribute("paciente") PacienteDTO dto, BindingResult bindingResult, RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            return "pacientes/formulario";
        }
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
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("paciente") PacienteDTO dto, BindingResult bindingResult, RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            return "pacientes/formulario";
        }
        service.atualizar(id, dto);
        redirect.addFlashAttribute("sucesso", "Paciente atualizado com sucesso!");
        return "redirect:/pacientes";
    }

    // Sem try/catch aqui de propósito: se o id não existir (ou qualquer
    // outra regra falhar), a exceção sobe e quem trata é o
    // GlobalExceptionHandler (TR06) — centralizado, em vez de repetido
    // em cada controller.
    @PostMapping("{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        service.remover(id);
        redirect.addFlashAttribute("sucesso", "Paciente removido com sucesso!");
        return "redirect:/pacientes";
    }
}
