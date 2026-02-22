package com.himfg.hospitalinfantil.repositorio;

import com.himfg.hospitalinfantil.modelo.personas.Paciente;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementacion en memoria del repositorio de pacientes.
 */
public class PacienteRepositorioEnMemoria implements PacienteRepositorio {

    private final Map<Long, Paciente> almacenamiento = new ConcurrentHashMap<>();
    private final AtomicLong generadorIds = new AtomicLong(1L);

    @Override
    public Paciente guardar(Paciente paciente) {
        if (paciente.getId() == null) {
            paciente.asignarId(generadorIds.getAndIncrement());
        }
        almacenamiento.put(paciente.getId(), paciente);
        return paciente;
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) {
        return Optional.ofNullable(almacenamiento.get(id));
    }

    @Override
    public List<Paciente> buscarTodos() {
        return new ArrayList<>(almacenamiento.values());
    }
}
