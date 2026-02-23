package com.himfg.hospitalinfantil.modelo.comun;

import java.time.LocalDateTime;

/**
 * Clase base para todas las entidades del dominio del Hospital Infantil de Mexico "Federico Gomez".
 * Proporciona un identificador y marcas de tiempo.
 */
public abstract class EntidadBase {

    private Long id;
    private final LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    protected EntidadBase() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = this.fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la entidad.
     * Solo deberia ser utilizado por la capa de persistencia.
     */
    public void asignarId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("El identificador ya fue asignado y no puede modificarse.");
        }
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El identificador debe ser un numero positivo.");
        }
        this.id = id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * Marca la entidad como actualizada estableciendo la fechaActualizacion a ahora.
     */
    protected void marcarActualizado() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}
