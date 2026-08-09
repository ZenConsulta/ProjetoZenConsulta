package br.edu.iff.ccc.webproject.service;

import br.edu.iff.ccc.webproject.dto.PacienteDTO;
import br.edu.iff.ccc.webproject.exception.RegraDeNegocioException;
import br.edu.iff.ccc.webproject.model.Paciente;
import br.edu.iff.ccc.webproject.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Regras de negócio + CRUD de Paciente (RF05: cadastro feito pelo Operador,
 * mas a listagem/consulta é usada em vários pontos do sistema).
 */
@Service
public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public Paciente criar(PacienteDTO dto) {
        Paciente paciente = new Paciente(null, dto.getNome(), dto.getEmail(), dto.getTelefone());
        return repository.salvar(paciente);
    }

    public Paciente atualizar(Long id, PacienteDTO dto) {
        Paciente existente = buscarPorId(id);
        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        existente.setTelefone(dto.getTelefone());
        return repository.salvar(existente);
    }

    public List<Paciente> listarTodos() {
        return repository.listarTodos();
    }

    public Paciente buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Paciente não encontrado (id " + id + ")"));
    }

    public void remover(Long id) {
        repository.remover(id);
    }
}
