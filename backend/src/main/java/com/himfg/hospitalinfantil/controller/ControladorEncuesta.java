package com.himfg.hospitalinfantil.controller;

import com.himfg.hospitalinfantil.controller.dto.RespuestaCatalogoEncuesta;
import com.himfg.hospitalinfantil.controller.dto.RespuestaEncuesta;
import com.himfg.hospitalinfantil.controller.dto.SolicitudAplicarEncuesta;
import com.himfg.hospitalinfantil.service.ServicioEncuesta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/encuestas")
@Tag(name = "Encuestas", description = "Catalogo y aplicacion de encuestas clinicas")
public class ControladorEncuesta {

    private final ServicioEncuesta servicioEncuesta;

    @Autowired
    public ControladorEncuesta(ServicioEncuesta servicioEncuesta) {
        this.servicioEncuesta = servicioEncuesta;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Aplicar encuesta a paciente",
            security = @SecurityRequirement(name = "tokenSesion")
    )
    public RespuestaEncuesta aplicarEncuesta(@Valid @RequestBody SolicitudAplicarEncuesta solicitud) {
        return RespuestaEncuesta.desdeEntidad(servicioEncuesta.aplicarEncuesta(solicitud));
    }

    @GetMapping("/catalogo")
    @Operation(
            summary = "Listar catalogo de encuestas",
            security = @SecurityRequirement(name = "tokenSesion")
    )
    public List<RespuestaCatalogoEncuesta> listarCatalogoEncuestas() {
        return servicioEncuesta.obtenerCatalogoEncuestas()
                .stream()
                .map(encuesta -> new RespuestaCatalogoEncuesta(
                        encuesta.nombre(),
                        encuesta.descripcion(),
                        encuesta.grupoObjetivo(),
                        encuesta.focosEvaluacion()
                ))
                .toList();
    }

    @GetMapping
    @Operation(
            summary = "Listar encuestas por paciente",
            security = @SecurityRequirement(name = "tokenSesion")
    )
    public List<RespuestaEncuesta> listarEncuestasPorPaciente(@RequestParam Long pacienteId) {
        return servicioEncuesta.listarPorPaciente(pacienteId)
                .stream()
                .map(RespuestaEncuesta::desdeEntidad)
                .toList();
    }
}
