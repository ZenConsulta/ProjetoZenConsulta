package br.edu.iff.ccc.webproject.service;

import br.edu.iff.ccc.webproject.dto.AdministradorDTO;
import br.edu.iff.ccc.webproject.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc.webproject.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc.webproject.model.Administrador;
import br.edu.iff.ccc.webproject.repository.AdministradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorService {

    private final AdministradorRepository repository;

    public AdministradorService(AdministradorRepository repository) {
        this.repository = repository;
    }

    public Administrador criar(AdministradorDTO dto) {
        repository.findByEmail(dto.getEmail()).ifPresent(a -> {
            throw new EntidadeDuplicadaException("Já existe um administrador cadastrado com este email");
        });
        Administrador administrador = new Administrador(null, dto.getNome(), dto.getEmail());
        return repository.save(administrador);
    }

    public Administrador atualizar(Long id, AdministradorDTO dto) {
        Administrador existente = buscarPorId(id);
        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        return repository.save(existente);
    }

    public List<Administrador> listarTodos() {
        return repository.findAll();
    }

    public Administrador buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Administrador não encontrado (id " + id + ")"));
    }

    public void remover(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
