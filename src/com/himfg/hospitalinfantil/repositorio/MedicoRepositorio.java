package com.himfg.hospitalinfantil.repositorio;

import com.himfg.hospitalinfantil.modelo.personas.Medico;

import java.util.List;
import java.util.Optional;

/**
 * Persistencia de medicos.
 */
public interface MedicoRepositorio {

    Medico guardar(Medico medico);

    Optional<Medico> buscarPorId(Long id);

    List<Medico> buscarTodos();
}
