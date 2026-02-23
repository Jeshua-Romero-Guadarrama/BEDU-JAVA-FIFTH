package com.himfg.hospitalinfantil.archivos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Registra operaciones basicas en un archivo de auditoria usando NIO.2.
 */
public class RegistroAuditoriaArchivo {

    private final Path rutaArchivo;

    public RegistroAuditoriaArchivo(Path rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public void registrarOperacion(String mensaje) {
        String linea = java.time.LocalDateTime.now() + " - " + mensaje + System.lineSeparator();
        try {
            Files.writeString(
                    rutaArchivo,
                    linea,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el archivo de auditoria: " + e.getMessage());
        }
    }
}
