package com.himfg.hospitalinfantil.controller.dto;

import com.himfg.hospitalinfantil.autenticacion.RolUsuario;

public record RespuestaInicioSesion(
        String token,
        String usuario,
        String nombreVisible,
        RolUsuario rol,
        Long expiraEnSegundos
) {
}
