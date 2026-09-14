package br.edu.iff.ccc.webproject.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainViewController {

    private final Map<String, String[]> medicosMock = Map.of(
            "1", new String[]{"Dra. Sarah Mitchell", "Cardiologista Sênior", "Centro do Coração e Vascular", "4.9"},
            "2", new String[]{"Dr. James Wilson", "Cirurgião Cardiovascular", "Hospital Metodista", "4.7"},
            "3", new String[]{"Dra. Alana Smith, MD", "Cardiologista e Clínica Geral", "Centro Médico Principal", "4.9"}
    );

    @GetMapping("painel")
    public String getPainel(Model model) {
        model.addAttribute("nomePaciente", "James");
        model.addAttribute("consultaMedico", "Dra. Sarah Jenkins");
        model.addAttribute("consultaEspecialidade", "Especialista em Cardiologia • Chamada de Vídeo");
        model.addAttribute("consultaHorario", "14:30");
        model.addAttribute("consultaData", "Hoje, 24 de Out, 2024");
        return "painel";
    }

    @GetMapping("buscar-medico")
    public String getBuscarMedico(Model model) {

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
        return "busca-medicos";
    }

    @GetMapping("medico/{id}")
    public String getPerfilMedico(@PathVariable("id") String id, Model model) {

        String[] medico = medicosMock.getOrDefault(id, medicosMock.get("3"));

        model.addAttribute("medicoId", id);
        model.addAttribute("medicoNome", medico[0]);
        model.addAttribute("medicoEspecialidade", medico[1]);
        model.addAttribute("medicoLocal", medico[2]);
        model.addAttribute("medicoNota", medico[3]);
        model.addAttribute("horariosDisponiveis",
                List.of("09:00", "09:30", "10:30", "11:00", "13:30", "14:00", "14:30", "15:00", "15:30"));
        return "perfil-medico";
    }

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
        return "confirmacao";
    }

    @GetMapping("consultas")
    public String getMinhasConsultas(Model model) {
        model.addAttribute("consultas", List.of(
                Map.of("medico", "Dra. Sarah Jenkins", "data", "24 de Out, 2024", "status", "Agendada"),
                Map.of("medico", "Dr. Alan Miller", "data", "28 de Set, 2024", "status", "Concluída")
        ));
        return "consultas";
    }

    @GetMapping("prontuarios")
    public String getProntuarios(Model model) {
        model.addAttribute("prontuarios", List.of(
                Map.of("titulo", "Exame de Sangue Anual", "data", "12 Out, 2024"),
                Map.of("titulo", "Raio-X do Tórax", "data", "15 Set, 2024")
        ));
        return "prontuarios";
    }

    @GetMapping("configuracoes")
    public String getConfiguracoes(Model model) {
        model.addAttribute("nomePaciente", "James");
        model.addAttribute("emailPaciente", "james@exemplo.com");
        return "configuracoes";
    }

    @GetMapping
    public String raiz() {
        return "redirect:/painel";
    }

}
