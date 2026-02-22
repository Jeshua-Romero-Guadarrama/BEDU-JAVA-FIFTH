package com.himfg.hospitalinfantil.controller;

import com.himfg.hospitalinfantil.autenticacion.RolUsuario;
import com.himfg.hospitalinfantil.controller.dto.RespuestaPaciente;
import com.himfg.hospitalinfantil.controller.dto.SolicitudCrearPaciente;
import com.himfg.hospitalinfantil.service.ServicioAutorizacionHttp;
import com.himfg.hospitalinfantil.service.ServicioPaciente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Pacientes", description = "Gestion de pacientes del sistema")
public class ControladorPaciente {

    private final ServicioAutorizacionHttp servicioAutorizacionHttp;
    private final ServicioPaciente servicioPaciente;

    @Autowired
    public ControladorPaciente(ServicioAutorizacionHttp servicioAutorizacionHttp, ServicioPaciente servicioPaciente) {
        this.servicioAutorizacionHttp = servicioAutorizacionHttp;
        this.servicioPaciente = servicioPaciente;
    }

    @GetMapping
    @Operation(
            summary = "Listar pacientes activos",
            security = @SecurityRequirement(name = "tokenSesion")
    )
    public List<RespuestaPaciente> listarPacientesActivos() {
        return servicioPaciente.listarPacientesActivos()
                .stream()
                .map(RespuestaPaciente::desdeEntidad)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener paciente por id",
            security = @SecurityRequirement(name = "tokenSesion")
    )
    public RespuestaPaciente obtenerPaciente(@PathVariable Long id) {
        return RespuestaPaciente.desdeEntidad(servicioPaciente.obtenerPacientePorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Registrar paciente",
            description = "Operacion restringida al rol Administrador",
            security = @SecurityRequirement(name = "tokenSesion")
    )
    public RespuestaPaciente registrarPaciente(@Valid @RequestBody SolicitudCrearPaciente solicitud,
                                               HttpServletRequest solicitudHttp) {
        servicioAutorizacionHttp.exigirRol(solicitudHttp, RolUsuario.ADMINISTRADOR);
        return RespuestaPaciente.desdeEntidad(servicioPaciente.registrarPaciente(solicitud));
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(
            summary = "Desactivar paciente",
            description = "Operacion restringida al rol Administrador",
            security = @SecurityRequirement(name = "tokenSesion")
    )
    public RespuestaPaciente desactivarPaciente(@PathVariable Long id, HttpServletRequest solicitudHttp) {
        servicioAutorizacionHttp.exigirRol(solicitudHttp, RolUsuario.ADMINISTRADOR);
        return RespuestaPaciente.desdeEntidad(servicioPaciente.desactivarPaciente(id));
    }
}
