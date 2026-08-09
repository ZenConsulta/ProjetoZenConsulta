package br.edu.iff.ccc.webproject.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class RepositorioMemoria<T> implements Repositorio<T> {

    private final Map<Long, T> dados = new ConcurrentHashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    private final Function<T, Long> getId;
    private final BiConsumer<T, Long> setId;

    protected RepositorioMemoria(Function<T, Long> getId, BiConsumer<T, Long> setId) {
        this.getId = getId;
        this.setId = setId;
    }

    @Override
    public T salvar(T entidade) {
        Long id = getId.apply(entidade);
        if (id == null) {
            id = proximoId.getAndIncrement();
            setId.accept(entidade, id);
        }
        dados.put(id, entidade);
        return entidade;
    }

    @Override
    public Optional<T> buscarPorId(Long id) {
        return Optional.ofNullable(dados.get(id));
    }

    @Override
    public List<T> listarTodos() {
        return List.copyOf(dados.values());
    }

    @Override
    public void remover(Long id) {
        dados.remove(id);
    }
}
