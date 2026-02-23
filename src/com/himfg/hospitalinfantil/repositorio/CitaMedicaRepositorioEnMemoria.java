package com.himfg.hospitalinfantil.repositorio;

import com.himfg.hospitalinfantil.modelo.citas.CitaMedica;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementacion en memoria del repositorio de citas medicas.
 */
public class CitaMedicaRepositorioEnMemoria implements CitaMedicaRepositorio {

    private final Map<Long, CitaMedica> almacenamiento = new ConcurrentHashMap<>();
    private final AtomicLong generadorIds = new AtomicLong(1L);

    @Override
    public CitaMedica guardar(CitaMedica cita) {
        if (cita.getId() == null) {
            cita.asignarId(generadorIds.getAndIncrement());
        }
        almacenamiento.put(cita.getId(), cita);
        return cita;
    }

    @Override
    public Optional<CitaMedica> buscarPorId(Long id) {
        return Optional.ofNullable(almacenamiento.get(id));
    }

    @Override
    public List<CitaMedica> buscarTodas() {
        return new ArrayList<>(almacenamiento.values());
    }

    @Override
    public List<CitaMedica> buscarPorPaciente(Long pacienteId) {
        List<CitaMedica> citas = new ArrayList<>();
        for (CitaMedica cita : almacenamiento.values()) {
            if (cita.getPaciente().getId() != null && cita.getPaciente().getId().equals(pacienteId)) {
                citas.add(cita);
            }
        }
        return citas;
    }
}
