package com.himfg.hospitalinfantil.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SolicitudInicioSesion(
        @NotBlank(message = "El usuario es obligatorio")
        @Size(min = 4, max = 60, message = "El usuario debe tener entre 4 y 60 caracteres")
        String usuario,

        @NotBlank(message = "La clave es obligatoria")
        @Size(min = 8, max = 80, message = "La clave debe tener entre 8 y 80 caracteres")
        String clave
) {
}
