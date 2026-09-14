package br.edu.iff.ccc.webproject.service;

import br.edu.iff.ccc.webproject.dto.ProfissionalDTO;
import br.edu.iff.ccc.webproject.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc.webproject.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc.webproject.model.Profissional;
import br.edu.iff.ccc.webproject.repository.ProfissionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfissionalService {

    private final ProfissionalRepository repository;

    public ProfissionalService(ProfissionalRepository repository) {
        this.repository = repository;
    }

    public Profissional criar(ProfissionalDTO dto) {
        repository.findByEmail(dto.getEmail()).ifPresent(p -> {
            throw new EntidadeDuplicadaException("Já existe um profissional cadastrado com este email");
        });
        Profissional profissional = new Profissional(null, dto.getNome(), dto.getEspecialidade(), dto.getEmail());
        return repository.save(profissional);
    }

    public Profissional atualizar(Long id, ProfissionalDTO dto) {
        Profissional existente = buscarPorId(id);
        existente.setNome(dto.getNome());
        existente.setEspecialidade(dto.getEspecialidade());
        existente.setEmail(dto.getEmail());
        return repository.save(existente);
    }

    public List<Profissional> listarTodos() {
        return repository.findAll();
    }

    public Profissional buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado (id " + id + ")"));
    }

    public void remover(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
