package br.edu.iff.ccc.webproject.repository;

import java.util.List;
import java.util.Optional;

/**
 * Contrato genérico de persistência (RNF04: separação em camadas).
 * Nesta fase (P1) só existe a implementação em memória (RepositorioMemoria),
 * mas a interface já deixa o projeto pronto para trocar por um banco de
 * dados real na P2 sem precisar mexer nos Services.
 */
public interface Repositorio<T> {
    T salvar(T entidade);
    Optional<T> buscarPorId(Long id);
    List<T> listarTodos();
    void remover(Long id);
}
