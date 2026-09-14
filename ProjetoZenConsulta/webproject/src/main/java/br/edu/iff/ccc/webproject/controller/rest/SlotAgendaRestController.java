package br.edu.iff.ccc.webproject.controller.rest;

import br.edu.iff.ccc.webproject.dto.SlotAgendaDTO;
import br.edu.iff.ccc.webproject.model.SlotAgenda;
import br.edu.iff.ccc.webproject.service.SlotAgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Sem PUT/atualizar de propósito: editar um horário que já pode estar
 * vinculado a um agendamento quebraria a consistência dos dados — mesma
 * decisão já tomada no fluxo MVC (SlotAgendaViewController).
 */
@RestController
@RequestMapping("/api/slots")
@Tag(name = "Horários (Slots)", description = "Cadastro de horários disponíveis por profissional")
public class SlotAgendaRestController {

    private final SlotAgendaService service;

    public SlotAgendaRestController(SlotAgendaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista todos os horários cadastrados")
    public ResponseEntity<List<SlotAgenda>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Lista os horários disponíveis (não ocupados) de um profissional")
    public ResponseEntity<List<SlotAgenda>> listarDisponiveis(
            @Parameter(description = "Id do profissional", example = "1") @RequestParam Long profissionalId) {
        return ResponseEntity.ok(service.listarDisponiveisPorProfissional(profissionalId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um horário pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Horário encontrado"),
            @ApiResponse(responseCode = "404", description = "Horário não encontrado")
    })
    public ResponseEntity<SlotAgenda> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo horário para um profissional")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Horário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (RFC 9457)"),
            @ApiResponse(responseCode = "409", description = "Já existe um horário igual para esse profissional")
    })
    public ResponseEntity<SlotAgenda> criar(@Valid @RequestBody SlotAgendaDTO dto) {
        SlotAgenda criado = service.criar(dto);
        return ResponseEntity.created(URI.create("/api/slots/" + criado.getId())).body(criado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um horário pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Horário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Horário não encontrado")
    })
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
