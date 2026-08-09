package br.edu.iff.ccc.webproject.controller.view;

import br.edu.iff.ccc.webproject.dto.OperadorDTO;
import br.edu.iff.ccc.webproject.exception.RegraDeNegocioException;
import br.edu.iff.ccc.webproject.model.Operador;
import br.edu.iff.ccc.webproject.service.OperadorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/operadores")
public class OperadorViewController {

    private final OperadorService service;

    public OperadorViewController(OperadorService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("operadores", service.listarTodos());
        return "operadores/lista";
    }

    @GetMapping("novo")
    public String formularioNovo(Model model) {
        model.addAttribute("operador", new OperadorDTO());
        return "operadores/formulario";
    }

    @PostMapping
    public String salvar(@ModelAttribute("operador") OperadorDTO dto, RedirectAttributes redirect) {
        service.criar(dto);
        redirect.addFlashAttribute("sucesso", "Operador cadastrado com sucesso!");
        return "redirect:/operadores";
    }

    @GetMapping("{id}/editar")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Operador operador = service.buscarPorId(id);
        OperadorDTO dto = new OperadorDTO();
        dto.setId(operador.getId());
        dto.setNome(operador.getNome());
        dto.setEmail(operador.getEmail());
        model.addAttribute("operador", dto);
        return "operadores/formulario";
    }

    @PostMapping("{id}/editar")
    public String atualizar(@PathVariable Long id, @ModelAttribute("operador") OperadorDTO dto, RedirectAttributes redirect) {
        service.atualizar(id, dto);
        redirect.addFlashAttribute("sucesso", "Operador atualizado com sucesso!");
        return "redirect:/operadores";
    }

    @PostMapping("{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            service.remover(id);
            redirect.addFlashAttribute("sucesso", "Operador removido com sucesso!");
        } catch (RegraDeNegocioException ex) {
            redirect.addFlashAttribute("erro", ex.getMessage());
        }
        return "redirect:/operadores";
    }
}
