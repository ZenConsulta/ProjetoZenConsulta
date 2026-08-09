package br.edu.iff.ccc.webproject.repository;

import java.util.List;
import java.util.Optional;

public interface Repositorio<T> {
    T salvar(T entidade);
    Optional<T> buscarPorId(Long id);
    List<T> listarTodos();
    void remover(Long id);
}
