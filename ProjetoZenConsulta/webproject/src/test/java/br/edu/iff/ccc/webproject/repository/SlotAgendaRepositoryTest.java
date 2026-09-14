package br.edu.iff.ccc.webproject.repository;

import br.edu.iff.ccc.webproject.model.SlotAgenda;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SlotAgendaRepositoryTest {

    @Autowired
    private SlotAgendaRepository repository;

    @Test
    void deveEncontrarApenasSlotsDisponiveisDeUmProfissional() {
        repository.save(new SlotAgenda(null, 1L, "2026-10-10", "09:00", true));
        repository.save(new SlotAgenda(null, 1L, "2026-10-10", "10:00", false));
        repository.save(new SlotAgenda(null, 2L, "2026-10-10", "09:00", true));

        List<SlotAgenda> disponiveis = repository.findByProfissionalIdAndDisponivelTrue(1L);

        assertThat(disponiveis).hasSize(1);
        assertThat(disponiveis.get(0).getHorario()).isEqualTo("09:00");
    }

    @Test
    void deveDetectarSlotJaCadastradoParaMesmoProfissionalDataHorario() {
        repository.save(new SlotAgenda(null, 5L, "2026-11-01", "14:00", true));

        boolean existe = repository.existsByProfissionalIdAndDataAndHorario(5L, "2026-11-01", "14:00");
        boolean naoExiste = repository.existsByProfissionalIdAndDataAndHorario(5L, "2026-11-01", "15:00");

        assertThat(existe).isTrue();
        assertThat(naoExiste).isFalse();
    }
}
