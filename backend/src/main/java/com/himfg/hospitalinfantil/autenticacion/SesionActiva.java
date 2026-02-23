package com.himfg.hospitalinfantil.autenticacion;

import java.time.LocalDateTime;

public record SesionActiva(
        String token,
        String usuario,
        String nombreVisible,
        RolUsuario rol,
        LocalDateTime venceEn
) {
}
