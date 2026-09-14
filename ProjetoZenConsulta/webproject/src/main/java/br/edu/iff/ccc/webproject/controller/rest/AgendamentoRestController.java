package br.edu.iff.ccc.webproject.controller.rest;

import br.edu.iff.ccc.webproject.dto.AgendamentoDTO;
import br.edu.iff.ccc.webproject.dto.ConfirmarAgendamentoDTO;
import br.edu.iff.ccc.webproject.model.Agendamento;
import br.edu.iff.ccc.webproject.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
@Tag(name = "Agendamentos", description = "Solicitação, confirmação e cancelamento de consultas")
public class AgendamentoRestController {

    private final AgendamentoService service;

    public AgendamentoRestController(AgendamentoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista todos os agendamentos")
    public ResponseEntity<List<Agendamento>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um agendamento pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    public ResponseEntity<Agendamento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Solicita um novo agendamento (status inicial: PENDENTE)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agendamento solicitado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (RFC 9457)"),
            @ApiResponse(responseCode = "404", description = "Paciente, profissional ou horário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Horário já ocupado ou inconsistente (RN02/RN03)")
    })
    public ResponseEntity<Agendamento> solicitar(@Valid @RequestBody AgendamentoDTO dto) {
        Agendamento criado = service.solicitar(dto);
        return ResponseEntity.created(URI.create("/api/agendamentos/" + criado.getId())).body(criado);
    }

    @PatchMapping("/{id}/confirmar")
    @Operation(summary = "Confirma um agendamento pendente (RN01, RN03, RN04)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento confirmado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (RFC 9457)"),
            @ApiResponse(responseCode = "404", description = "Agendamento ou operador não encontrado"),
            @ApiResponse(responseCode = "409", description = "Status inválido para confirmação ou horário já ocupado")
    })
    public ResponseEntity<Agendamento> confirmar(@PathVariable Long id, @Valid @RequestBody ConfirmarAgendamentoDTO dto) {
        return ResponseEntity.ok(service.confirmar(id, dto.getOperadorId()));
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancela um agendamento (RN05: não reativa um já cancelado)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Agendamento já estava cancelado")
    })
    public ResponseEntity<Agendamento> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }
}
