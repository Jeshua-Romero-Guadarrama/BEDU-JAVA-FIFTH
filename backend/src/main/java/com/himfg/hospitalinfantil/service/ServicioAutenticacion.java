package com.himfg.hospitalinfantil.service;

import com.himfg.hospitalinfantil.autenticacion.RolUsuario;
import com.himfg.hospitalinfantil.autenticacion.SesionActiva;
import com.himfg.hospitalinfantil.controller.dto.RespuestaInicioSesion;
import com.himfg.hospitalinfantil.controller.dto.SolicitudInicioSesion;
import com.himfg.hospitalinfantil.exception.ExcepcionNoAutorizado;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ServicioAutenticacion {

    private static final Duration DURACION_SESION = Duration.ofHours(8);

    private final Map<String, CuentaAcceso> cuentasPorUsuario = Map.of(
            "admin_himfg", new CuentaAcceso(
                    "admin_himfg",
                    "AdminHIMFG2026!",
                    "Administrador General",
                    RolUsuario.ADMINISTRADOR
            ),
            "pasante_himfg", new CuentaAcceso(
                    "pasante_himfg",
                    "PasanteHIMFG2026!",
                    "Medico Pasante",
                    RolUsuario.MEDICO_PASANTE
            )
    );

    private final Map<String, SesionActiva> sesionesActivasPorToken = new ConcurrentHashMap<>();

    public RespuestaInicioSesion iniciarSesion(SolicitudInicioSesion solicitud) {
        limpiarSesionesExpiradas();

        String usuarioNormalizado = solicitud.usuario().trim().toLowerCase();
        CuentaAcceso cuenta = cuentasPorUsuario.get(usuarioNormalizado);
        if (cuenta == null || !Objects.equals(cuenta.clave(), solicitud.clave())) {
            throw new ExcepcionNoAutorizado("Credenciales invalidas");
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        LocalDateTime venceEn = LocalDateTime.now().plus(DURACION_SESION);

        SesionActiva sesionActiva = new SesionActiva(
                token,
                cuenta.usuario(),
                cuenta.nombreVisible(),
                cuenta.rol(),
                venceEn
        );
        sesionesActivasPorToken.put(token, sesionActiva);

        return new RespuestaInicioSesion(
                sesionActiva.token(),
                sesionActiva.usuario(),
                sesionActiva.nombreVisible(),
                sesionActiva.rol(),
                DURACION_SESION.getSeconds()
        );
    }

    public SesionActiva obtenerSesionPorToken(String token) {
        limpiarSesionesExpiradas();
        if (token == null || token.isBlank()) {
            throw new ExcepcionNoAutorizado("No se envio un token de sesion");
        }

        SesionActiva sesionActiva = sesionesActivasPorToken.get(token.trim());
        if (sesionActiva == null) {
            throw new ExcepcionNoAutorizado("Sesion no valida o expirada");
        }
        return sesionActiva;
    }

    public void cerrarSesion(String token) {
        if (token == null || token.isBlank()) {
            return;
        }
        sesionesActivasPorToken.remove(token.trim());
    }

    private void limpiarSesionesExpiradas() {
        LocalDateTime ahora = LocalDateTime.now();
        sesionesActivasPorToken.entrySet().removeIf(entrada -> entrada.getValue().venceEn().isBefore(ahora));
    }

    private record CuentaAcceso(String usuario, String clave, String nombreVisible, RolUsuario rol) {
    }
}
