package com.himfg.hospitalinfantil.controller;

import com.himfg.hospitalinfantil.controller.dto.RespuestaError;
import com.himfg.hospitalinfantil.exception.ExcepcionNoAutorizado;
import com.himfg.hospitalinfantil.exception.ExcepcionRecursoNoEncontrado;
import com.himfg.hospitalinfantil.exception.ExcepcionReglaNegocio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorExcepcionesApi {

    @ExceptionHandler(ExcepcionRecursoNoEncontrado.class)
    public ResponseEntity<RespuestaError> manejarNoEncontrado(ExcepcionRecursoNoEncontrado excepcion,
                                                              HttpServletRequest solicitudHttp) {
        return construirRespuesta(
                HttpStatus.NOT_FOUND,
                excepcion.getMessage(),
                solicitudHttp.getRequestURI(),
                Map.of()
        );
    }

    @ExceptionHandler(ExcepcionReglaNegocio.class)
    public ResponseEntity<RespuestaError> manejarReglaNegocio(ExcepcionReglaNegocio excepcion,
                                                              HttpServletRequest solicitudHttp) {
        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                excepcion.getMessage(),
                solicitudHttp.getRequestURI(),
                Map.of()
        );
    }

    @ExceptionHandler(ExcepcionNoAutorizado.class)
    public ResponseEntity<RespuestaError> manejarNoAutorizado(ExcepcionNoAutorizado excepcion,
                                                              HttpServletRequest solicitudHttp) {
        return construirRespuesta(
                HttpStatus.UNAUTHORIZED,
                excepcion.getMessage(),
                solicitudHttp.getRequestURI(),
                Map.of()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaError> manejarValidacion(MethodArgumentNotValidException excepcion,
                                                            HttpServletRequest solicitudHttp) {
        Map<String, String> erroresPorCampo = new LinkedHashMap<>();
        for (FieldError errorCampo : excepcion.getBindingResult().getFieldErrors()) {
            erroresPorCampo.put(errorCampo.getField(), errorCampo.getDefaultMessage());
        }

        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                "Hay errores de validacion en la solicitud",
                solicitudHttp.getRequestURI(),
                erroresPorCampo
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RespuestaError> manejarRestricciones(ConstraintViolationException excepcion,
                                                               HttpServletRequest solicitudHttp) {
        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                excepcion.getMessage(),
                solicitudHttp.getRequestURI(),
                Map.of()
        );
    }

    @ExceptionHandler({NoResourceFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<RespuestaError> manejarRutaNoEncontrada(Exception excepcion,
                                                                  HttpServletRequest solicitudHttp) {
        return construirRespuesta(
                HttpStatus.NOT_FOUND,
                "La ruta solicitada no existe en el backend",
                solicitudHttp.getRequestURI(),
                Map.of()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaError> manejarErrorInesperado(Exception excepcion, HttpServletRequest solicitudHttp) {
        return construirRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrio un error inesperado",
                solicitudHttp.getRequestURI(),
                Map.of()
        );
    }

    private ResponseEntity<RespuestaError> construirRespuesta(HttpStatus estatus,
                                                              String mensaje,
                                                              String ruta,
                                                              Map<String, String> erroresPorCampo) {
        RespuestaError respuesta = new RespuestaError(
                LocalDateTime.now(),
                estatus.value(),
                estatus.getReasonPhrase(),
                mensaje,
                ruta,
                erroresPorCampo
        );
        return ResponseEntity.status(estatus).body(respuesta);
    }
}
