package com.himfg.hospitalinfantil.archivos;

import com.himfg.hospitalinfantil.modelo.encuestas.EncuestaAplicada;
import com.himfg.hospitalinfantil.modelo.encuestas.Respuesta;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Exporta encuestas aplicadas de un paciente a un archivo CSV simple.
 */
public class ExportadorEncuestasPaciente {

    /**
     * Escribe un archivo CSV con el detalle de encuestas de un paciente.
     */
    public void exportar(Iterable<EncuestaAplicada> encuestasAplicadas, Path rutaArchivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("Hospital Infantil de Mexico \"Federico Gomez\"\n");
        sb.append("Encuesta;FechaAplicacion;Pregunta;Respuesta\n");
        for (EncuestaAplicada encuestaAplicada : encuestasAplicadas) {
            String nombreEncuesta = encuestaAplicada.getEncuestaOriginal().getNombre();
            String fecha = encuestaAplicada.getFechaAplicacion().toString();
            for (Respuesta respuesta : encuestaAplicada.getRespuestas()) {
                sb.append(nombreEncuesta).append(";")
                  .append(fecha).append(";")
                  .append(respuesta.getPregunta().getEnunciado()).append(";")
                  .append(respuesta.getValor()).append("\n");
            }
        }
        Files.writeString(rutaArchivo, sb.toString(), StandardCharsets.UTF_8);
    }
}
