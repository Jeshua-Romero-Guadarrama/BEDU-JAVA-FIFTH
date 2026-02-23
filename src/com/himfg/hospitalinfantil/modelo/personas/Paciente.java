package com.himfg.hospitalinfantil.modelo.personas;

import com.himfg.hospitalinfantil.excepciones.ValidacionDominioException;
import com.himfg.hospitalinfantil.modelo.comun.Persona;

import java.time.LocalDate;

/**
 * Representa a un paciente atendido en el Hospital Infantil de Mexico "Federico Gomez".
 */
public class Paciente extends Persona {

    private Sexo sexo;
    private String numeroExpediente;
    private boolean activo = true;

    public Paciente(String nombreCompleto,
                    LocalDate fechaNacimiento,
                    Sexo sexo,
                    String numeroExpediente) {
        super(nombreCompleto, fechaNacimiento);
        setSexo(sexo);
        setNumeroExpediente(numeroExpediente);
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        if (sexo == null) {
            throw new ValidacionDominioException("El sexo del paciente es obligatorio.");
        }
        this.sexo = sexo;
        marcarActualizado();
    }

    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(String numeroExpediente) {
        if (numeroExpediente == null || numeroExpediente.isBlank()) {
            throw new ValidacionDominioException("El numero de expediente es obligatorio.");
        }
        if (numeroExpediente.length() < 5) {
            throw new ValidacionDominioException("El numero de expediente debe tener al menos 5 caracteres.");
        }
        this.numeroExpediente = numeroExpediente.trim().toUpperCase();
        marcarActualizado();
    }

    public boolean isActivo() {
        return activo;
    }

    public void desactivar() {
        this.activo = false;
        marcarActualizado();
    }
}
