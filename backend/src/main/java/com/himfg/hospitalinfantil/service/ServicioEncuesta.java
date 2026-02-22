package com.himfg.hospitalinfantil.service;

import com.himfg.hospitalinfantil.domain.EncuestaCatalogo;
import com.himfg.hospitalinfantil.controller.dto.SolicitudAplicarEncuesta;
import com.himfg.hospitalinfantil.domain.EncuestaAplicada;
import com.himfg.hospitalinfantil.domain.Paciente;
import com.himfg.hospitalinfantil.exception.ExcepcionReglaNegocio;
import com.himfg.hospitalinfantil.repository.RepositorioEncuestaAplicada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioEncuesta {

    private static final List<EncuestaCatalogo> CATALOGO_ENCUESTAS = List.of(
            new EncuestaCatalogo(
                    "Control integral de sintomas pediatricos",
                    "Evalua dolor, ansiedad y dificultad respiratoria para ajustar el plan clinico en cuidados paliativos.",
                    "Pacientes pediatricos con seguimiento activo",
                    List.of(
                            "Monitoreo de dolor de 24 horas",
                            "Nivel de ansiedad del paciente",
                            "Disconfort respiratorio y tolerancia al esfuerzo"
                    )
            ),
            new EncuestaCatalogo(
                    "Bienestar psicosocial y familiar",
                    "Identifica necesidades emocionales, soporte familiar y carga de cuidado en casa.",
                    "Paciente pediatrico y familia cuidadora",
                    List.of(
                            "Estado emocional de paciente y cuidadores",
                            "Calidad de comunicacion con el equipo clinico",
                            "Red de apoyo familiar y comunitaria"
                    )
            ),
            new EncuestaCatalogo(
                    "Valoracion funcional y calidad de sueno",
                    "Mide fatiga, funcionalidad diaria y descanso nocturno para prevenir descompensaciones.",
                    "Pacientes con deterioro funcional progresivo",
                    List.of(
                            "Nivel de energia y fatiga",
                            "Capacidad para actividades basicas",
                            "Calidad y continuidad del sueno"
                    )
            )
    );

    private final RepositorioEncuestaAplicada repositorioEncuestaAplicada;
    private final ServicioPaciente servicioPaciente;

    @Autowired
    public ServicioEncuesta(RepositorioEncuestaAplicada repositorioEncuestaAplicada,
                            ServicioPaciente servicioPaciente) {
        this.repositorioEncuestaAplicada = repositorioEncuestaAplicada;
        this.servicioPaciente = servicioPaciente;
    }

    @Transactional
    public EncuestaAplicada aplicarEncuesta(SolicitudAplicarEncuesta solicitud) {
        Paciente paciente = servicioPaciente.obtenerPacientePorId(solicitud.pacienteId());
        if (!paciente.estaActivo()) {
            throw new ExcepcionReglaNegocio("No se pueden aplicar encuestas a pacientes desactivados");
        }
        if (!encuestaPerteneceAlCatalogo(solicitud.nombreEncuesta())) {
            throw new ExcepcionReglaNegocio("La encuesta seleccionada no pertenece al catalogo oficial");
        }

        EncuestaAplicada encuestaAplicada = new EncuestaAplicada(
                paciente,
                solicitud.nombreEncuesta().trim(),
                solicitud.dolor(),
                solicitud.ansiedad(),
                solicitud.dificultadRespiratoria(),
                solicitud.observaciones().trim()
        );

        return repositorioEncuestaAplicada.save(encuestaAplicada);
    }

    @Transactional(readOnly = true)
    public List<EncuestaAplicada> listarPorPaciente(Long identificadorPaciente) {
        servicioPaciente.obtenerPacientePorId(identificadorPaciente);
        return repositorioEncuestaAplicada.findByPacienteIdOrderByFechaAplicacionDesc(identificadorPaciente);
    }

    @Transactional(readOnly = true)
    public List<EncuestaCatalogo> obtenerCatalogoEncuestas() {
        return CATALOGO_ENCUESTAS;
    }

    private boolean encuestaPerteneceAlCatalogo(String nombreEncuesta) {
        String nombreNormalizado = nombreEncuesta == null ? "" : nombreEncuesta.trim();
        return CATALOGO_ENCUESTAS.stream()
                .anyMatch(encuesta -> encuesta.nombre().equalsIgnoreCase(nombreNormalizado));
    }
}
