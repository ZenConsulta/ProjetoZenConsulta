package br.edu.iff.ccc.webproject.exception;

/** Lançada ao tentar cadastrar algo que viola uma restrição de unicidade (ex: email repetido). */
public class EntidadeDuplicadaException extends RuntimeException {
    public EntidadeDuplicadaException(String mensagem) {
        super(mensagem);
    }
}
