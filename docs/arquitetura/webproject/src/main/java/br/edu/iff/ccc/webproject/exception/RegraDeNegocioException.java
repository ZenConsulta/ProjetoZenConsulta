package br.edu.iff.ccc.webproject.exception;

/**
 * Exceção lançada quando alguma regra de negócio (RN01..RN05) é violada.
 * Mensagens claras e descritivas (RNF07), ex: "Horário já ocupado".
 */
public class RegraDeNegocioException extends RuntimeException {
    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}
