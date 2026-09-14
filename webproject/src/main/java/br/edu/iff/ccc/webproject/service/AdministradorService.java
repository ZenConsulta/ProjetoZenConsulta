package br.edu.iff.ccc.webproject.service;

import br.edu.iff.ccc.webproject.dto.AdministradorDTO;
import br.edu.iff.ccc.webproject.exception.RegraDeNegocioException;
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
        Administrador administrador = new Administrador(null, dto.getNome(), dto.getEmail());
        return repository.salvar(administrador);
    }

    public Administrador atualizar(Long id, AdministradorDTO dto) {
        Administrador existente = buscarPorId(id);
        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        return repository.salvar(existente);
    }

    public List<Administrador> listarTodos() {
        return repository.listarTodos();
    }

    public Administrador buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Administrador não encontrado (id " + id + ")"));
    }

    public void remover(Long id) {
        repository.remover(id);
    }
}
