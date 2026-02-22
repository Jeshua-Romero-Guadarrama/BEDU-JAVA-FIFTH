package com.himfg.hospitalinfantil.controller.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record RespuestaError(
        LocalDateTime marcaTiempo,
        Integer estatus,
        String error,
        String mensaje,
        String ruta,
        Map<String, String> erroresPorCampo
) {
}
