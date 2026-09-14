package br.edu.iff.ccc.webproject.service;

import br.edu.iff.ccc.webproject.dto.OperadorDTO;
import br.edu.iff.ccc.webproject.exception.RegraDeNegocioException;
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
        Operador operador = new Operador(null, dto.getNome(), dto.getEmail());
        return repository.salvar(operador);
    }

    public Operador atualizar(Long id, OperadorDTO dto) {
        Operador existente = buscarPorId(id);
        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        return repository.salvar(existente);
    }

    public List<Operador> listarTodos() {
        return repository.listarTodos();
    }

    public Operador buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Operador não encontrado (id " + id + ")"));
    }

    public void remover(Long id) {
        repository.remover(id);
    }
}
