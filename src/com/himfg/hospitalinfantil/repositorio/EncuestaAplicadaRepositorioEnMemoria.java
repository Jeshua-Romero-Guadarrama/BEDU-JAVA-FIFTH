package com.himfg.hospitalinfantil.repositorio;

import com.himfg.hospitalinfantil.modelo.encuestas.EncuestaAplicada;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Almacen en memoria para encuestas aplicadas.
 */
public class EncuestaAplicadaRepositorioEnMemoria implements EncuestaAplicadaRepositorio {

    private final Map<Long, EncuestaAplicada> almacenamiento = new ConcurrentHashMap<>();
    private final AtomicLong generadorIds = new AtomicLong(1L);

    @Override
    public EncuestaAplicada guardar(EncuestaAplicada encuestaAplicada) {
        if (encuestaAplicada.getId() == null) {
            encuestaAplicada.asignarId(generadorIds.getAndIncrement());
        }
        almacenamiento.put(encuestaAplicada.getId(), encuestaAplicada);
        return encuestaAplicada;
    }

    @Override
    public List<EncuestaAplicada> buscarTodas() {
        return new ArrayList<>(almacenamiento.values());
    }

    @Override
    public List<EncuestaAplicada> buscarPorPaciente(Long pacienteId) {
        if (pacienteId == null) {
            return Collections.emptyList();
        }
        List<EncuestaAplicada> resultado = new ArrayList<>();
        for (EncuestaAplicada encuesta : almacenamiento.values()) {
            if (pacienteId.equals(encuesta.getPaciente().getId())) {
                resultado.add(encuesta);
            }
        }
        return resultado;
    }
}
