package br.edu.iff.ccc.webproject.controller.view;

import br.edu.iff.ccc.webproject.dto.AdministradorDTO;
import br.edu.iff.ccc.webproject.exception.RegraDeNegocioException;
import br.edu.iff.ccc.webproject.model.Administrador;
import br.edu.iff.ccc.webproject.service.AdministradorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administradores")
public class AdministradorViewController {

    private final AdministradorService service;

    public AdministradorViewController(AdministradorService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("administradores", service.listarTodos());
        return "administradores/lista";
    }

    @GetMapping("novo")
    public String formularioNovo(Model model) {
        model.addAttribute("administrador", new AdministradorDTO());
        return "administradores/formulario";
    }

    @PostMapping
    public String salvar(@ModelAttribute("administrador") AdministradorDTO dto, RedirectAttributes redirect) {
        service.criar(dto);
        redirect.addFlashAttribute("sucesso", "Administrador cadastrado com sucesso!");
        return "redirect:/administradores";
    }

    @GetMapping("{id}/editar")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Administrador administrador = service.buscarPorId(id);
        AdministradorDTO dto = new AdministradorDTO();
        dto.setId(administrador.getId());
        dto.setNome(administrador.getNome());
        dto.setEmail(administrador.getEmail());
        model.addAttribute("administrador", dto);
        return "administradores/formulario";
    }

    @PostMapping("{id}/editar")
    public String atualizar(@PathVariable Long id, @ModelAttribute("administrador") AdministradorDTO dto, RedirectAttributes redirect) {
        service.atualizar(id, dto);
        redirect.addFlashAttribute("sucesso", "Administrador atualizado com sucesso!");
        return "redirect:/administradores";
    }

    @PostMapping("{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            service.remover(id);
            redirect.addFlashAttribute("sucesso", "Administrador removido com sucesso!");
        } catch (RegraDeNegocioException ex) {
            redirect.addFlashAttribute("erro", ex.getMessage());
        }
        return "redirect:/administradores";
    }
}
