package br.edu.iff.ccc.webproject.controller.advice;

import br.edu.iff.ccc.webproject.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc.webproject.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc.webproject.exception.RegraDeNegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * =====================================================================
 * ApiExceptionHandler (TR07) — respostas de erro no padrão RFC 9457
 * =====================================================================
 * @RestControllerAdvice, diferente de @ControllerAdvice, sempre serializa
 * o retorno como corpo da resposta (JSON), nunca como página HTML — é o
 * tratamento de erro específico da API REST, separado do GlobalExceptionHandler
 * (que trata o fluxo de telas MVC).
 *
 * "annotations = RestController.class" restringe esse Advice a atuar
 * SOMENTE sobre classes anotadas com @RestController (a API /api/**).
 * Sem essa restrição, ele concorreria com o GlobalExceptionHandler (que
 * cuida das telas Thymeleaf) para as MESMAS exceções, e o Spring não tem
 * como saber automaticamente qual dos dois deveria responder.
 *
 * Estender ResponseEntityExceptionHandler dá acesso a handlers prontos do
 * Spring para vários erros comuns (parâmetro inválido, método HTTP errado
 * etc.) — aqui só sobrescrevemos o de validação (@Valid) e adicionamos os
 * handlers das nossas exceções de domínio.
 */
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> tratarRecursoNaoEncontrado(RecursoNaoEncontradoException ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Recurso não encontrado");
        problem.setType(URI.create("https://zenconsulta.com/problemas/recurso-nao-encontrado"));
        problem.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ProblemDetail> tratarRegraDeNegocio(RegraDeNegocioException ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Violação de regra de negócio");
        problem.setType(URI.create("https://zenconsulta.com/problemas/regra-de-negocio"));
        problem.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    @ExceptionHandler(EntidadeDuplicadaException.class)
    public ResponseEntity<ProblemDetail> tratarEntidadeDuplicada(EntidadeDuplicadaException ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Entidade duplicada");
        problem.setType(URI.create("https://zenconsulta.com/problemas/entidade-duplicada"));
        problem.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    /**
     * Sobrescreve o handler padrão do Spring para erros de @Valid.
     * É aqui que a estrutura da RFC 9457 ganha a propriedade extra
     * "invalid_params", listando CADA campo que falhou e o motivo —
     * exatamente o que o TR07 pede.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Um ou mais campos são inválidos");
        problem.setTitle("Erro de validação");
        problem.setType(URI.create("https://zenconsulta.com/problemas/validacao"));
        problem.setProperty("timestamp", Instant.now());

        List<Map<String, String>> invalidParams = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> Map.of(
                        "name", fieldError.getField(),
                        "reason", fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Valor inválido"
                ))
                .collect(Collectors.toList());

        problem.setProperty("invalid_params", invalidParams);

        return ResponseEntity.badRequest().body(problem);
    }
}
