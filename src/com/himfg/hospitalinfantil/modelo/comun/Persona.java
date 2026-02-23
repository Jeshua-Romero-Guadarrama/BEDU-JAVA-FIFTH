package com.himfg.hospitalinfantil.modelo.comun;

import com.himfg.hospitalinfantil.excepciones.ValidacionDominioException;

import java.time.LocalDate;
import java.time.Period;

/**
 * Representa a una persona dentro del contexto del hospital.
 * Una clase abstracta reutilizada por Paciente y Medico.
 */
public abstract class Persona extends EntidadBase {

    private String nombreCompleto;
    private LocalDate fechaNacimiento;

    protected Persona(String nombreCompleto, LocalDate fechaNacimiento) {
        setNombreCompleto(nombreCompleto);
        setFechaNacimiento(fechaNacimiento);
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public final void setNombreCompleto(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            throw new ValidacionDominioException("El nombre completo es obligatorio.");
        }
        if (nombreCompleto.trim().length() < 3) {
            throw new ValidacionDominioException("El nombre completo debe tener al menos 3 caracteres.");
        }
        this.nombreCompleto = nombreCompleto.trim();
        marcarActualizado();
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public final void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new ValidacionDominioException("La fecha de nacimiento es obligatoria.");
        }
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new ValidacionDominioException("La fecha de nacimiento no puede ser futura.");
        }
        this.fechaNacimiento = fechaNacimiento;
        marcarActualizado();
    }

    /**
     * Calcula la edad en anos de la persona.
     */
    public int calcularEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + getId() +
                ", nombre='" + nombreCompleto + '\'' +
                ", edad=" + calcularEdad() +
                '}';
    }
}
