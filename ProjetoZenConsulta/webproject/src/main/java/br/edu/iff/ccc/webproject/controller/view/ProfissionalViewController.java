package br.edu.iff.ccc.webproject.controller.view;

import br.edu.iff.ccc.webproject.dto.ProfissionalDTO;
import br.edu.iff.ccc.webproject.model.Profissional;
import br.edu.iff.ccc.webproject.service.ProfissionalService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profissionais")
public class ProfissionalViewController {

    private final ProfissionalService service;

    public ProfissionalViewController(ProfissionalService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("profissionais", service.listarTodos());
        return "profissionais/lista";
    }

    @GetMapping("novo")
    public String formularioNovo(Model model) {
        model.addAttribute("profissional", new ProfissionalDTO());
        return "profissionais/formulario";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("profissional") ProfissionalDTO dto, BindingResult bindingResult, RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            return "profissionais/formulario";
        }
        service.criar(dto);
        redirect.addFlashAttribute("sucesso", "Profissional cadastrado com sucesso!");
        return "redirect:/profissionais";
    }

    @GetMapping("{id}/editar")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Profissional profissional = service.buscarPorId(id);
        ProfissionalDTO dto = new ProfissionalDTO();
        dto.setId(profissional.getId());
        dto.setNome(profissional.getNome());
        dto.setEspecialidade(profissional.getEspecialidade());
        dto.setEmail(profissional.getEmail());
        model.addAttribute("profissional", dto);
        return "profissionais/formulario";
    }

    @PostMapping("{id}/editar")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("profissional") ProfissionalDTO dto, BindingResult bindingResult, RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            return "profissionais/formulario";
        }
        service.atualizar(id, dto);
        redirect.addFlashAttribute("sucesso", "Profissional atualizado com sucesso!");
        return "redirect:/profissionais";
    }

    @PostMapping("{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        service.remover(id);
        redirect.addFlashAttribute("sucesso", "Profissional removido com sucesso!");
        return "redirect:/profissionais";
    }
}
