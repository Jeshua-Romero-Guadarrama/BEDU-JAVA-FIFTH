package com.himfg.hospitalinfantil.servicio;

import com.himfg.hospitalinfantil.excepciones.RecursoNoEncontradoException;
import com.himfg.hospitalinfantil.modelo.personas.Paciente;
import com.himfg.hospitalinfantil.modelo.personas.Sexo;
import com.himfg.hospitalinfantil.repositorio.PacienteRepositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de alto nivel para gestionar pacientes del HIMFG.
 */
public class PacienteServicio {

    private final PacienteRepositorio pacienteRepositorio;

    public PacienteServicio(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    public Paciente registrarPaciente(String nombre,
                                      LocalDate fechaNacimiento,
                                      Sexo sexo,
                                      String numeroExpediente) {

        Paciente paciente = new Paciente(nombre, fechaNacimiento, sexo, numeroExpediente);
        return pacienteRepositorio.guardar(paciente);
    }

    public Paciente obtenerPacientePorId(Long id) {
        return pacienteRepositorio.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontro el paciente con id " + id
                ));
    }

    public List<Paciente> listarPacientesActivos() {
        return pacienteRepositorio.buscarTodos()
                .stream()
                .filter(Paciente::isActivo)
                .collect(Collectors.toList());
    }

    public void desactivarPaciente(Long id) {
        Paciente paciente = obtenerPacientePorId(id);
        paciente.desactivar();
        pacienteRepositorio.guardar(paciente);
    }
}
