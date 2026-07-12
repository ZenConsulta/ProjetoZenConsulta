package br.edu.iff.ccc.webproject.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

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
 *   5) /consultas             -> Minhas Consultas
 *   6) /prontuarios           -> Prontuários Médicos
 *   7) /configuracoes         -> Configurações da Conta
 *
 * Estas 3 últimas rotas foram adicionadas na milestone "Estruturação da Camada
 * de Controllers View": o objetivo dela é só MAPEAR todas as rotas principais
 * da aplicação (retornando páginas, mesmo que com dados mock), deixando a
 * lógica de negócio de verdade (services, banco de dados, validações) para a
 * próxima fase/milestone.
 *
 * -------------------------------------------------------------------------------------
 * CONCEITOS DE SPRING MVC USADOS AQUI (para estudo):
 * -------------------------------------------------------------------------------------
 * - @Controller        -> marca a classe como um controller "clássico" do Spring MVC,
 *                         ou seja, os métodos retornam o NOME de uma página (String),
 *                         e não um JSON (isso quem faz JSON é o @RestController).
 *
 * - @RequestMapping("/")   -> define um prefixo comum de URL para TODOS os métodos
 *                             desta classe. Assim cada @GetMapping abaixo só precisa
 *                             declarar o "resto" do caminho.
 *
 * - @GetMapping("caminho") -> mapeia uma URL (HTTP GET) para um método Java.
 *
 * - @PathVariable   -> captura um pedaço da URL como variável.
 *                      Ex: /medico/7  -> id = 7
 *
 * - @RequestParam    -> captura parâmetros de query string (?chave=valor),
 *                       com valor padrão (defaultValue) caso não seja enviado.
 *                       Ex: /confirmar-agendamento?horario=14:30
 *
 * - Model            -> "mochila" de dados que o controller entrega para o HTML
 *                       (Thymeleaf). Tudo que é colocado em model.addAttribute(...)
 *                       pode ser lido no template com ${nomeDoAtributo}.
 *
 * - Retorno String    -> o nome do arquivo .html dentro de
 *                        src/main/resources/templates (sem a extensão .html),
 *                        resolvido automaticamente pelo Thymeleaf.
 * =====================================================================================
 */
@Controller
@RequestMapping("/")
public class MainViewController {

    // ---------------------------------------------------------------------------
    // "Banco de dados" fake em memória, só para o exemplo funcionar sem precisar
    // de banco de verdade. Em um projeto real isso viria de um Service + Repository.
    // ---------------------------------------------------------------------------
    private final Map<String, String[]> medicosMock = Map.of(
            "1", new String[]{"Dra. Sarah Mitchell", "Cardiologista Sênior", "Centro do Coração e Vascular", "4.9"},
            "2", new String[]{"Dr. James Wilson", "Cirurgião Cardiovascular", "Hospital Metodista", "4.7"},
            "3", new String[]{"Dra. Alana Smith, MD", "Cardiologista e Clínica Geral", "Centro Médico Principal", "4.9"}
    );

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
    // =========================================================================
    @GetMapping("buscar-medico")
    public String getBuscarMedico(Model model) {
        // Monta uma lista simples (id + dados) a partir do mapa mock, só para
        // ilustrar th:each no template iterando sobre dados vindos do controller.
        List<Map<String, String>> listaMedicos = medicosMock.entrySet().stream()
                .map(entry -> Map.of(
                        "id", entry.getKey(),
                        "nome", entry.getValue()[0],
                        "especialidade", entry.getValue()[1],
                        "local", entry.getValue()[2],
                        "nota", entry.getValue()[3]))
                .toList();

        model.addAttribute("medicos", listaMedicos);
        model.addAttribute("totalEncontrados", medicosMock.size());
        return "busca-medicos"; // -> templates/busca-medicos.html
    }

    // =========================================================================
    // 3) PERFIL DO MÉDICO (detalhe + agenda de horários)
    //    URL: http://localhost:8080/medico/1   (o "1" vira @PathVariable id)
    // =========================================================================
    @GetMapping("medico/{id}")
    public String getPerfilMedico(@PathVariable("id") String id, Model model) {
        // Busca simples no mapa mock; se não achar, usa um médico padrão.
        String[] medico = medicosMock.getOrDefault(id, medicosMock.get("3"));

        model.addAttribute("medicoId", id);
        model.addAttribute("medicoNome", medico[0]);
        model.addAttribute("medicoEspecialidade", medico[1]);
        model.addAttribute("medicoLocal", medico[2]);
        model.addAttribute("medicoNota", medico[3]);
        model.addAttribute("horariosDisponiveis",
                List.of("09:00", "09:30", "10:30", "11:00", "13:30", "14:00", "14:30", "15:00", "15:30"));
        return "perfil-medico"; // -> templates/perfil-medico.html
    }

    // =========================================================================
    // 4) CONFIRMAÇÃO DE AGENDAMENTO
    //    URL: http://localhost:8080/confirmar-agendamento?medico=Dra.%20Sarah%20Jenkins&horario=10:30
    //    (Os @RequestParam abaixo têm valor padrão, então a página funciona
    //     mesmo sem parâmetros na URL.)
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
    // 5) MINHAS CONSULTAS (listagem de consultas do paciente, futuras e passadas)
    //    URL: http://localhost:8080/consultas
    //    Ainda sem lógica de negócio (isso é o objetivo da PRÓXIMA milestone) -
    //    por enquanto só mapeia a rota e devolve dados mock, igual às outras.
    // =========================================================================
    @GetMapping("consultas")
    public String getMinhasConsultas(Model model) {
        model.addAttribute("consultas", List.of(
                Map.of("medico", "Dra. Sarah Jenkins", "data", "24 de Out, 2024", "status", "Agendada"),
                Map.of("medico", "Dr. Alan Miller", "data", "28 de Set, 2024", "status", "Concluída")
        ));
        return "consultas"; // -> templates/consultas.html
    }

    // =========================================================================
    // 6) PRONTUÁRIOS MÉDICOS (histórico clínico do paciente)
    //    URL: http://localhost:8080/prontuarios
    // =========================================================================
    @GetMapping("prontuarios")
    public String getProntuarios(Model model) {
        model.addAttribute("prontuarios", List.of(
                Map.of("titulo", "Exame de Sangue Anual", "data", "12 Out, 2024"),
                Map.of("titulo", "Raio-X do Tórax", "data", "15 Set, 2024")
        ));
        return "prontuarios"; // -> templates/prontuarios.html
    }

    // =========================================================================
    // 7) CONFIGURAÇÕES DA CONTA DO PACIENTE
    //    URL: http://localhost:8080/configuracoes
    // =========================================================================
    @GetMapping("configuracoes")
    public String getConfiguracoes(Model model) {
        model.addAttribute("nomePaciente", "James");
        model.addAttribute("emailPaciente", "james@exemplo.com");
        return "configuracoes"; // -> templates/configuracoes.html
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
