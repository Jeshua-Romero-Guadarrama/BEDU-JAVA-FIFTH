package com.himfg.hospitalinfantil.domain;

import java.util.List;

public record EncuestaCatalogo(
        String nombre,
        String descripcion,
        String grupoObjetivo,
        List<String> focosEvaluacion
) {
}
