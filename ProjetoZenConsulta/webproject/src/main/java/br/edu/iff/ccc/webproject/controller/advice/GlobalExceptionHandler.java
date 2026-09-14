package br.edu.iff.ccc.webproject.controller.advice;

import br.edu.iff.ccc.webproject.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc.webproject.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc.webproject.exception.RegraDeNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ModelAndView tratarRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        ModelAndView mv = new ModelAndView("error/404");
        mv.addObject("mensagem", ex.getMessage());
        mv.setStatus(HttpStatus.NOT_FOUND);
        return mv;
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ModelAndView tratarRegraDeNegocio(RegraDeNegocioException ex) {
        ModelAndView mv = new ModelAndView("error/regra-negocio");
        mv.addObject("mensagem", ex.getMessage());
        mv.setStatus(HttpStatus.BAD_REQUEST);
        return mv;
    }

    @ExceptionHandler(EntidadeDuplicadaException.class)
    public ModelAndView tratarEntidadeDuplicada(EntidadeDuplicadaException ex) {
        ModelAndView mv = new ModelAndView("error/regra-negocio");
        mv.addObject("mensagem", ex.getMessage());
        mv.setStatus(HttpStatus.CONFLICT);
        return mv;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView tratarErroInesperado(Exception ex) {
        ModelAndView mv = new ModelAndView("error/500");
        mv.addObject("mensagem", "Ocorreu um erro inesperado. Tente novamente mais tarde.");
        mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return mv;
    }
}
