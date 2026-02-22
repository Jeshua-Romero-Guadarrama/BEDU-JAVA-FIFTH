package com.himfg.hospitalinfantil.repositorio;

import com.himfg.hospitalinfantil.modelo.citas.CitaMedica;

import java.util.List;
import java.util.Optional;

/**
 * Persistencia de citas medicas.
 */
public interface CitaMedicaRepositorio {

    CitaMedica guardar(CitaMedica cita);

    Optional<CitaMedica> buscarPorId(Long id);

    List<CitaMedica> buscarTodas();

    List<CitaMedica> buscarPorPaciente(Long pacienteId);
}
