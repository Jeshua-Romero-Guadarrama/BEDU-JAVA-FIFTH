package com.himfg.hospitalinfantil.servicio;

import com.himfg.hospitalinfantil.archivos.ExportadorEncuestasPaciente;
import com.himfg.hospitalinfantil.excepciones.RecursoNoEncontradoException;
import com.himfg.hospitalinfantil.modelo.encuestas.EncuestaAplicada;
import com.himfg.hospitalinfantil.modelo.encuestas.EncuestaSalud;
import com.himfg.hospitalinfantil.modelo.encuestas.Pregunta;
import com.himfg.hospitalinfantil.modelo.encuestas.Respuesta;
import com.himfg.hospitalinfantil.modelo.personas.Paciente;
import com.himfg.hospitalinfantil.repositorio.EncuestaAplicadaRepositorio;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Servicio de dominio para manejar encuestas de Cuidados Paliativos y Calidad de Vida.
 */
public class EncuestaServicio {

    private final List<EncuestaSalud> catalogoEncuestas;
    private final EncuestaAplicadaRepositorio encuestaAplicadaRepositorio;

    public EncuestaServicio(List<EncuestaSalud> catalogoEncuestas,
                            EncuestaAplicadaRepositorio encuestaAplicadaRepositorio) {
        this.catalogoEncuestas = new ArrayList<>(catalogoEncuestas);
        this.encuestaAplicadaRepositorio = encuestaAplicadaRepositorio;
    }

    public List<EncuestaSalud> obtenerCatalogo() {
        return Collections.unmodifiableList(catalogoEncuestas);
    }

    public EncuestaSalud obtenerEncuestaPorIndiceBaseUno(int indiceBaseUno) {
        int indiceInterno = indiceBaseUno - 1;
        if (indiceInterno < 0 || indiceInterno >= catalogoEncuestas.size()) {
            throw new RecursoNoEncontradoException("No existe la encuesta solicitada en el catalogo.");
        }
        return catalogoEncuestas.get(indiceInterno);
    }

    /**
     * Crea y guarda una encuesta aplicada para un paciente, validando las respuestas.
     */
    public EncuestaAplicada aplicarEncuesta(Paciente paciente,
                                            EncuestaSalud encuesta,
                                            List<String> respuestasComoTexto) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente es obligatorio.");
        }
        if (encuesta == null) {
            throw new IllegalArgumentException("La encuesta es obligatoria.");
        }
        if (respuestasComoTexto == null || respuestasComoTexto.size() != encuesta.getPreguntas().size()) {
            throw new IllegalArgumentException("El numero de respuestas no coincide con las preguntas.");
        }

        EncuestaAplicada aplicada = new EncuestaAplicada(paciente, encuesta);
        List<Pregunta> preguntas = encuesta.getPreguntas();
        for (int i = 0; i < preguntas.size(); i++) {
            Pregunta pregunta = preguntas.get(i);
            String respuestaTexto = respuestasComoTexto.get(i);
            aplicada.agregarRespuesta(new Respuesta(pregunta, respuestaTexto));
        }
        return encuestaAplicadaRepositorio.guardar(aplicada);
    }

    public List<EncuestaAplicada> obtenerEncuestasDePaciente(Long pacienteId) {
        return encuestaAplicadaRepositorio.buscarPorPaciente(pacienteId);
    }

    public void exportarEncuestasPaciente(Long pacienteId,
                                          ExportadorEncuestasPaciente exportador,
                                          Path rutaArchivo) throws IOException {
        List<EncuestaAplicada> encuestasPaciente = obtenerEncuestasDePaciente(pacienteId);
        if (encuestasPaciente.isEmpty()) {
            throw new RecursoNoEncontradoException("No hay encuestas aplicadas para el paciente con id " + pacienteId);
        }
        exportador.exportar(encuestasPaciente, rutaArchivo);
    }
}
