package br.edu.iff.ccc.webproject.controller.rest;

import br.edu.iff.ccc.webproject.dto.OperadorDTO;
import br.edu.iff.ccc.webproject.model.Operador;
import br.edu.iff.ccc.webproject.service.OperadorService;
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
@RequestMapping("/api/operadores")
@Tag(name = "Operadores", description = "Operações de CRUD para o cadastro de operadores")
public class OperadorRestController {

    private final OperadorService service;

    public OperadorRestController(OperadorService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista todos os operadores cadastrados")
    public ResponseEntity<List<Operador>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um operador pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operador encontrado"),
            @ApiResponse(responseCode = "404", description = "Operador não encontrado")
    })
    public ResponseEntity<Operador> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo operador")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Operador criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (RFC 9457)"),
            @ApiResponse(responseCode = "409", description = "Já existe um operador com este email")
    })
    public ResponseEntity<Operador> criar(@Valid @RequestBody OperadorDTO dto) {
        Operador criado = service.criar(dto);
        return ResponseEntity.created(URI.create("/api/operadores/" + criado.getId())).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um operador existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operador atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (RFC 9457)"),
            @ApiResponse(responseCode = "404", description = "Operador não encontrado")
    })
    public ResponseEntity<Operador> atualizar(@PathVariable Long id, @Valid @RequestBody OperadorDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um operador pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Operador removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Operador não encontrado")
    })
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
