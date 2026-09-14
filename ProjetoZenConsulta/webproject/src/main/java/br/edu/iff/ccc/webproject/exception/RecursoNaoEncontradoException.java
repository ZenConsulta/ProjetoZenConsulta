package br.edu.iff.ccc.webproject.exception;

/** Lançada quando uma busca por id não encontra o registro (mapeada para 404). */
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
