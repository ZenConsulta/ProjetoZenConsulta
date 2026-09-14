package br.edu.iff.ccc.webproject.service;

import br.edu.iff.ccc.webproject.dto.OperadorDTO;
import br.edu.iff.ccc.webproject.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc.webproject.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc.webproject.model.Operador;
import br.edu.iff.ccc.webproject.repository.OperadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperadorService {

    private final OperadorRepository repository;

    public OperadorService(OperadorRepository repository) {
        this.repository = repository;
    }

    public Operador criar(OperadorDTO dto) {
        repository.findByEmail(dto.getEmail()).ifPresent(o -> {
            throw new EntidadeDuplicadaException("Já existe um operador cadastrado com este email");
        });
        Operador operador = new Operador(null, dto.getNome(), dto.getEmail());
        return repository.save(operador);
    }

    public Operador atualizar(Long id, OperadorDTO dto) {
        Operador existente = buscarPorId(id);
        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        return repository.save(existente);
    }

    public List<Operador> listarTodos() {
        return repository.findAll();
    }

    public Operador buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Operador não encontrado (id " + id + ")"));
    }

    public void remover(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
