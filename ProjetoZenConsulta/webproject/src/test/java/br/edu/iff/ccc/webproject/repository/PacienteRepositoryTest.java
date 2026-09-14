package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.Paciente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * TR05 — testes automatizados da camada de repositório com @DataJpaTest.
 * Essa anotação sobe só a fatia JPA do Spring (não o servidor web
 * inteiro), usando o H2 em memória, e cada teste roda dentro de uma
 * transação que é desfeita ao final (não "suja" um teste no outro).
 */
@DataJpaTest
class PacienteRepositoryTest {

    @Autowired
    private PacienteRepository repository;

    @Test
    void deveSalvarERecuperarPacientePorId() {
        Paciente paciente = new Paciente(null, "Maria Silva", "maria@teste.com", "21999990000");

        Paciente salvo = repository.save(paciente);

        assertThat(salvo.getId()).isNotNull();

        Optional<Paciente> encontrado = repository.findById(salvo.getId());
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("Maria Silva");
        assertThat(encontrado.get().getEmail()).isEqualTo("maria@teste.com");
    }

    @Test
    void deveListarTodosOsPacientesCadastrados() {
        repository.save(new Paciente(null, "Paciente 1", "p1@teste.com", null));
        repository.save(new Paciente(null, "Paciente 2", "p2@teste.com", null));

        assertThat(repository.findAll()).hasSize(2);
    }

    @Test
    void naoDevePermitirDoisPacientesComMesmoEmail() {
        repository.saveAndFlush(new Paciente(null, "Paciente A", "duplicado@teste.com", null));

        Paciente pacienteComEmailRepetido = new Paciente(null, "Paciente B", "duplicado@teste.com", null);

        // O e-mail tem @Column(unique = true) na entidade Paciente. O
        // banco recusa o segundo INSERT, e o Spring traduz esse erro do
        // banco para essa exceção comum a todo Spring Data.
        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.saveAndFlush(pacienteComEmailRepetido);
        });
    }

    @Test
    void deveRemoverPacientePorId() {
        Paciente salvo = repository.save(new Paciente(null, "Paciente Removível", "remover@teste.com", null));

        repository.deleteById(salvo.getId());

        assertThat(repository.findById(salvo.getId())).isEmpty();
    }
}
