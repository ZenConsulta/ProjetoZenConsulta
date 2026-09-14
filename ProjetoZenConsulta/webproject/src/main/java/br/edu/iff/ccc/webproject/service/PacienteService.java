package br.edu.iff.ccc.webproject.service;

import br.edu.iff.ccc.webproject.dto.PacienteDTO;
import br.edu.iff.ccc.webproject.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc.webproject.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc.webproject.model.Paciente;
import br.edu.iff.ccc.webproject.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public Paciente criar(PacienteDTO dto) {
        repository.findByEmail(dto.getEmail()).ifPresent(p -> {
            throw new EntidadeDuplicadaException("Já existe um paciente cadastrado com este email");
        });
        Paciente paciente = new Paciente(null, dto.getNome(), dto.getEmail(), dto.getTelefone());
        return repository.save(paciente);
    }

    public Paciente atualizar(Long id, PacienteDTO dto) {
        Paciente existente = buscarPorId(id);
        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        existente.setTelefone(dto.getTelefone());
        return repository.save(existente);
    }

    public List<Paciente> listarTodos() {
        return repository.findAll();
    }

    public Paciente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado (id " + id + ")"));
    }

    public void remover(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
