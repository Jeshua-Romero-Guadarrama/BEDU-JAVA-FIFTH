package com.himfg.hospitalinfantil.controller.dto;

import com.himfg.hospitalinfantil.autenticacion.RolUsuario;

import java.time.LocalDateTime;

public record RespuestaPerfilSesion(
        String usuario,
        String nombreVisible,
        RolUsuario rol,
        LocalDateTime venceEn
) {
}
