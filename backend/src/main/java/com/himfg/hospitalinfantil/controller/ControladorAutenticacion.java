package com.himfg.hospitalinfantil.controller;

import com.himfg.hospitalinfantil.autenticacion.SesionActiva;
import com.himfg.hospitalinfantil.controller.dto.RespuestaInicioSesion;
import com.himfg.hospitalinfantil.controller.dto.RespuestaPerfilSesion;
import com.himfg.hospitalinfantil.controller.dto.SolicitudInicioSesion;
import com.himfg.hospitalinfantil.service.ServicioAutenticacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/autenticacion")
@Tag(name = "Autenticacion", description = "Endpoints para inicio y cierre de sesion")
public class ControladorAutenticacion {

    private final ServicioAutenticacion servicioAutenticacion;

    @Autowired
    public ControladorAutenticacion(ServicioAutenticacion servicioAutenticacion) {
        this.servicioAutenticacion = servicioAutenticacion;
    }

    @PostMapping("/iniciar-sesion")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Iniciar sesion", description = "Valida credenciales y retorna token de sesion")
    public RespuestaInicioSesion iniciarSesion(@Valid @RequestBody SolicitudInicioSesion solicitud) {
        return servicioAutenticacion.iniciarSesion(solicitud);
    }

    @GetMapping("/perfil")
    @Operation(
            summary = "Consultar perfil de sesion",
            description = "Devuelve los datos del usuario autenticado",
            security = @SecurityRequirement(name = "tokenSesion")
    )
    public RespuestaPerfilSesion consultarPerfil(@RequestHeader("X-Auth-Token") String token) {
        SesionActiva sesionActiva = servicioAutenticacion.obtenerSesionPorToken(token);
        return new RespuestaPerfilSesion(
                sesionActiva.usuario(),
                sesionActiva.nombreVisible(),
                sesionActiva.rol(),
                sesionActiva.venceEn()
        );
    }

    @PostMapping("/cerrar-sesion")
    @Operation(
            summary = "Cerrar sesion",
            description = "Invalida el token de sesion actual",
            security = @SecurityRequirement(name = "tokenSesion")
    )
    public Map<String, String> cerrarSesion(@RequestHeader("X-Auth-Token") String token) {
        servicioAutenticacion.cerrarSesion(token);
        return Map.of("mensaje", "Sesion cerrada correctamente");
    }
}
