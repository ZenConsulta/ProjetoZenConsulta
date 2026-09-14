package br.edu.iff.ccc.webproject.controller.rest;

import br.edu.iff.ccc.webproject.dto.ProfissionalDTO;
import br.edu.iff.ccc.webproject.model.Profissional;
import br.edu.iff.ccc.webproject.service.ProfissionalService;
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
@RequestMapping("/api/profissionais")
@Tag(name = "Profissionais", description = "Operações de CRUD para o cadastro de profissionais")
public class ProfissionalRestController {

    private final ProfissionalService service;

    public ProfissionalRestController(ProfissionalService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista todos os profissionais cadastrados")
    public ResponseEntity<List<Profissional>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um profissional pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profissional encontrado"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    public ResponseEntity<Profissional> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo profissional")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profissional criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (RFC 9457)"),
            @ApiResponse(responseCode = "409", description = "Já existe um profissional com este email")
    })
    public ResponseEntity<Profissional> criar(@Valid @RequestBody ProfissionalDTO dto) {
        Profissional criado = service.criar(dto);
        return ResponseEntity.created(URI.create("/api/profissionais/" + criado.getId())).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um profissional existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profissional atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (RFC 9457)"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    public ResponseEntity<Profissional> atualizar(@PathVariable Long id, @Valid @RequestBody ProfissionalDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um profissional pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Profissional removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
