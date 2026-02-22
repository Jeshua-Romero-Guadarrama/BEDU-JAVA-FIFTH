package com.himfg.hospitalinfantil.repositorio;

import com.himfg.hospitalinfantil.modelo.personas.Medico;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementacion en memoria del repositorio de medicos del HIMFG.
 */
public class MedicoRepositorioEnMemoria implements MedicoRepositorio {

    private final Map<Long, Medico> almacenamiento = new ConcurrentHashMap<>();
    private final AtomicLong generadorIds = new AtomicLong(1L);

    @Override
    public Medico guardar(Medico medico) {
        if (medico.getId() == null) {
            medico.asignarId(generadorIds.getAndIncrement());
        }
        almacenamiento.put(medico.getId(), medico);
        return medico;
    }

    @Override
    public Optional<Medico> buscarPorId(Long id) {
        return Optional.ofNullable(almacenamiento.get(id));
    }

    @Override
    public List<Medico> buscarTodos() {
        return new ArrayList<>(almacenamiento.values());
    }
}
