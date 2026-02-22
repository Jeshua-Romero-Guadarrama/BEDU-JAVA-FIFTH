package com.himfg.hospitalinfantil.service;

import com.himfg.hospitalinfantil.controller.dto.SolicitudCrearPaciente;
import com.himfg.hospitalinfantil.domain.Paciente;
import com.himfg.hospitalinfantil.exception.ExcepcionRecursoNoEncontrado;
import com.himfg.hospitalinfantil.exception.ExcepcionReglaNegocio;
import com.himfg.hospitalinfantil.repository.RepositorioPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioPaciente {

    private final RepositorioPaciente repositorioPaciente;

    @Autowired
    public ServicioPaciente(RepositorioPaciente repositorioPaciente) {
        this.repositorioPaciente = repositorioPaciente;
    }

    @Transactional
    public Paciente registrarPaciente(SolicitudCrearPaciente solicitud) {
        // Se normaliza el expediente para evitar duplicados por mayusculas o espacios.
        String expedienteNormalizado = solicitud.numeroExpediente().trim().toUpperCase();
        if (repositorioPaciente.existsByNumeroExpedienteIgnoreCase(expedienteNormalizado)) {
            throw new ExcepcionReglaNegocio("Ya existe un paciente con el expediente: " + expedienteNormalizado);
        }

        Paciente paciente = new Paciente(
                solicitud.nombreCompleto().trim(),
                solicitud.fechaNacimiento(),
                solicitud.sexo(),
                expedienteNormalizado
        );

        return repositorioPaciente.save(paciente);
    }

    @Transactional(readOnly = true)
    public List<Paciente> listarPacientesActivos() {
        return repositorioPaciente.findByActivoTrueOrderByIdAsc();
    }

    @Transactional(readOnly = true)
    public Paciente obtenerPacientePorId(Long identificadorPaciente) {
        return repositorioPaciente.findById(identificadorPaciente)
                .orElseThrow(() -> new ExcepcionRecursoNoEncontrado(
                        "No se encontro el paciente con id " + identificadorPaciente
                ));
    }

    @Transactional
    public Paciente desactivarPaciente(Long identificadorPaciente) {
        Paciente paciente = obtenerPacientePorId(identificadorPaciente);
        if (!paciente.estaActivo()) {
            throw new ExcepcionReglaNegocio(
                    "El paciente con id " + identificadorPaciente + " ya esta desactivado"
            );
        }
        paciente.desactivar();
        return repositorioPaciente.save(paciente);
    }
}
