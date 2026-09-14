package br.edu.iff.ccc.webproject.controller.rest;

import br.edu.iff.ccc.webproject.dto.AdministradorDTO;
import br.edu.iff.ccc.webproject.model.Administrador;
import br.edu.iff.ccc.webproject.service.AdministradorService;
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
@RequestMapping("/api/administradores")
@Tag(name = "Administradores", description = "Operações de CRUD para o cadastro de administradores")
public class AdministradorRestController {

    private final AdministradorService service;

    public AdministradorRestController(AdministradorService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista todos os administradores cadastrados")
    public ResponseEntity<List<Administrador>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um administrador pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador encontrado"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    })
    public ResponseEntity<Administrador> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo administrador")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Administrador criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (RFC 9457)"),
            @ApiResponse(responseCode = "409", description = "Já existe um administrador com este email")
    })
    public ResponseEntity<Administrador> criar(@Valid @RequestBody AdministradorDTO dto) {
        Administrador criado = service.criar(dto);
        return ResponseEntity.created(URI.create("/api/administradores/" + criado.getId())).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um administrador existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (RFC 9457)"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    })
    public ResponseEntity<Administrador> atualizar(@PathVariable Long id, @Valid @RequestBody AdministradorDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um administrador pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Administrador removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    })
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
