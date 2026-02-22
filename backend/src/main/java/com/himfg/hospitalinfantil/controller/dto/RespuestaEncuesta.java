package com.himfg.hospitalinfantil.controller.dto;

import com.himfg.hospitalinfantil.domain.EncuestaAplicada;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RespuestaEncuesta(
        Long id,
        Long pacienteId,
        String pacienteNombre,
        String nombreEncuesta,
        LocalDateTime fechaAplicacion,
        Integer dolor,
        Integer ansiedad,
        Integer dificultadRespiratoria,
        BigDecimal promedioSintomas,
        String observaciones
) {
    public static RespuestaEncuesta desdeEntidad(EncuestaAplicada encuestaAplicada) {
        return new RespuestaEncuesta(
                encuestaAplicada.getId(),
                encuestaAplicada.getPaciente().getId(),
                encuestaAplicada.getPaciente().getNombreCompleto(),
                encuestaAplicada.getNombreEncuesta(),
                encuestaAplicada.getFechaAplicacion(),
                encuestaAplicada.getDolor(),
                encuestaAplicada.getAnsiedad(),
                encuestaAplicada.getDificultadRespiratoria(),
                encuestaAplicada.getPromedioSintomas(),
                encuestaAplicada.getObservaciones()
        );
    }
}
