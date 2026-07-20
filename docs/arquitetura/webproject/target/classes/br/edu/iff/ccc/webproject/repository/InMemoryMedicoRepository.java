package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.Medico;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryMedicoRepository implements MedicoRepository {

    private final Map<String, Medico> medicos = new LinkedHashMap<>();

    public InMemoryMedicoRepository() {
        salvar(new Medico("1", "Dra. Sarah Mitchell", "Cardiologista Sênior", "Centro do Coração e Vascular", "4.9"));
        salvar(new Medico("2", "Dr. James Wilson", "Cirurgião Cardiovascular", "Hospital Metodista", "4.7"));
        salvar(new Medico("3", "Dra. Alana Smith, MD", "Cardiologista e Clínica Geral", "Centro Médico Principal", "4.9"));
    }

    @Override
    public Medico salvar(Medico medico) {
        medicos.put(medico.getId(), medico);
        return medico;
    }

    @Override
    public List<Medico> listarTodos() {
        return new ArrayList<>(medicos.values());
    }

    @Override
    public Optional<Medico> buscarPorId(String id) {
        return Optional.ofNullable(medicos.get(id));
    }
}
