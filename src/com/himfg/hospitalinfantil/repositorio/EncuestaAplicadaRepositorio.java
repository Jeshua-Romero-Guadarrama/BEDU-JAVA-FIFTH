package com.himfg.hospitalinfantil.repositorio;

import com.himfg.hospitalinfantil.modelo.encuestas.EncuestaAplicada;

import java.util.List;

/**
 * Persistencia de encuestas aplicadas a pacientes.
 */
public interface EncuestaAplicadaRepositorio {

    EncuestaAplicada guardar(EncuestaAplicada encuestaAplicada);

    List<EncuestaAplicada> buscarTodas();

    List<EncuestaAplicada> buscarPorPaciente(Long pacienteId);
}
