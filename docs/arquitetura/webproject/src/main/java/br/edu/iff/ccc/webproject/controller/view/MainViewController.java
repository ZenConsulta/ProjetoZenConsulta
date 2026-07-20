package br.edu.iff.ccc.webproject.controller.view;

import br.edu.iff.ccc.webproject.dto.MedicoDTO;
import br.edu.iff.ccc.webproject.model.Medico;
import br.edu.iff.ccc.webproject.usecase.CadastrarMedicoUseCase;
import br.edu.iff.ccc.webproject.usecase.ListarMedicosUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * =====================================================================================
 *  MainViewController
 * =====================================================================================
 * ÚNICO controller da aplicação (conforme pedido: "fazer apenas 1 controller").
 *
 * Ele agrupa todas as telas do fluxo de agendamento do paciente que estavam,
 * no design de referência (imagens/HTML anexados), em 4 páginas separadas:
 *
 *   1) /painel                -> Dashboard do Paciente   (antigo "dashboard.html")
 *   2) /buscar-medico         -> Busca de Médicos        (antigo "busca-medicos.html")
 *   3) /medico/{id}           -> Perfil do Médico        (antigo "perfil-medico.html")
 *   4) /confirmar-agendamento -> Confirmação de Consulta (antigo "confirmacao.html")
 *   5) /medicos/novo (GET) e /medicos (POST) -> Cadastro de Médico
 *
 * -------------------------------------------------------------------------------------
 * NOVIDADE DA MILESTONE 4:
 * -------------------------------------------------------------------------------------
 * O antigo "medicosMock" (Map fixo dentro do controller) foi substituído pelo
 * fluxo real de camadas:
 *
 *   Controller (aqui)  --usa-->  UseCase (regra de negócio)  --usa-->  Repository (dados em memória)
 *
 * O Controller não sabe MAIS COMO os médicos são guardados ou como o id é gerado —
 * ele só pede pro UseCase "executar" a ação. Isso é Injeção de Dependência:
 * o Spring cria o ListarMedicosUseCase e o CadastrarMedicoUseCase automaticamente
 * (por causa do @Service neles) e "injeta" no construtor abaixo.
 * =====================================================================================
 */
@Controller
@RequestMapping("/")
public class MainViewController {

    private final ListarMedicosUseCase listarMedicosUseCase;
    private final CadastrarMedicoUseCase cadastrarMedicoUseCase;

    // Injeção via construtor: o Spring identifica esses dois parâmetros e
    // preenche automaticamente com as instâncias de ListarMedicosUseCase e
    // CadastrarMedicoUseCase (que por sua vez recebem o Repository).
    public MainViewController(ListarMedicosUseCase listarMedicosUseCase,
                               CadastrarMedicoUseCase cadastrarMedicoUseCase) {
        this.listarMedicosUseCase = listarMedicosUseCase;
        this.cadastrarMedicoUseCase = cadastrarMedicoUseCase;
    }

    // =========================================================================
    // 1) PAINEL DO PACIENTE (Dashboard)
    //    URL: http://localhost:8080/painel
    // =========================================================================
    @GetMapping("painel")
    public String getPainel(Model model) {
        model.addAttribute("nomePaciente", "James");
        model.addAttribute("consultaMedico", "Dra. Sarah Jenkins");
        model.addAttribute("consultaEspecialidade", "Especialista em Cardiologia • Chamada de Vídeo");
        model.addAttribute("consultaHorario", "14:30");
        model.addAttribute("consultaData", "Hoje, 24 de Out, 2024");
        return "painel"; // -> templates/painel.html
    }

    // =========================================================================
    // 2) BUSCAR MÉDICO (listagem / filtros)
    //    URL: http://localhost:8080/buscar-medico
    //    Agora vem do ListarMedicosUseCase -> InMemoryMedicoRepository,
    //    em vez do Map fixo.
    // =========================================================================
    @GetMapping("buscar-medico")
    public String getBuscarMedico(Model model) {
        List<Medico> listaMedicos = listarMedicosUseCase.executar();

        model.addAttribute("medicos", listaMedicos);
        model.addAttribute("totalEncontrados", listaMedicos.size());
        return "busca-medicos"; // -> templates/busca-medicos.html
    }

    // =========================================================================
    // 3) PERFIL DO MÉDICO (detalhe + agenda de horários)
    //    URL: http://localhost:8080/medico/1   (o "1" vira @PathVariable id)
    // =========================================================================
    @GetMapping("medico/{id}")
    public String getPerfilMedico(@PathVariable("id") String id, Model model) {
        Medico medico = listarMedicosUseCase.executar().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(listarMedicosUseCase.executar().get(0));

        model.addAttribute("medicoId", medico.getId());
        model.addAttribute("medicoNome", medico.getNome());
        model.addAttribute("medicoEspecialidade", medico.getEspecialidade());
        model.addAttribute("medicoLocal", medico.getLocal());
        model.addAttribute("medicoNota", medico.getNota());
        model.addAttribute("horariosDisponiveis",
                List.of("09:00", "09:30", "10:30", "11:00", "13:30", "14:00", "14:30", "15:00", "15:30"));
        return "perfil-medico"; // -> templates/perfil-medico.html
    }

    // =========================================================================
    // 4) CONFIRMAÇÃO DE AGENDAMENTO
    //    URL: http://localhost:8080/confirmar-agendamento?medico=Dra.%20Sarah%20Jenkins&horario=10:30
    // =========================================================================
    @GetMapping("confirmar-agendamento")
    public String getConfirmarAgendamento(
            @RequestParam(name = "medico", defaultValue = "Dra. Sarah Jenkins") String medico,
            @RequestParam(name = "data", defaultValue = "24 de Outubro de 2024") String data,
            @RequestParam(name = "horario", defaultValue = "10:30 — 11:15") String horario,
            Model model) {

        model.addAttribute("refAgendamento", "MS-49201");
        model.addAttribute("medico", medico);
        model.addAttribute("data", data);
        model.addAttribute("horario", horario);
        return "confirmacao"; // -> templates/confirmacao.html
    }

    // =========================================================================
    // 5) CADASTRO DE MÉDICO (novo fluxo da Milestone 4)
    //    GET  /medicos/novo -> mostra o formulário
    //    POST /medicos      -> recebe o DTO preenchido e chama o UseCase
    // =========================================================================
    @GetMapping("medicos/novo")
    public String getFormularioMedico(Model model) {
        model.addAttribute("medicoDTO", new MedicoDTO());
        return "cadastro-medico"; // -> templates/cadastro-medico.html
    }

    @PostMapping("medicos")
    public String salvarMedico(@ModelAttribute("medicoDTO") MedicoDTO medicoDTO) {
        cadastrarMedicoUseCase.executar(medicoDTO);
        return "redirect:/buscar-medico";
    }

    // =========================================================================
    // Rota raiz: redireciona "/" para o painel, para sempre haver uma página
    // inicial óbvia ao rodar a aplicação.
    // =========================================================================
    @GetMapping
    public String raiz() {
        return "redirect:/painel";
    }

}
