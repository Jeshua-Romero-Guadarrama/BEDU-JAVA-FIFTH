package com.himfg.hospitalinfantil.controller.dto;

import java.util.List;

public record RespuestaCatalogoEncuesta(
        String nombre,
        String descripcion,
        String grupoObjetivo,
        List<String> focosEvaluacion
) {
}
