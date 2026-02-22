package com.himfg.hospitalinfantil.modelo.personas;

import com.himfg.hospitalinfantil.excepciones.ValidacionDominioException;
import com.himfg.hospitalinfantil.modelo.comun.Persona;

import java.time.LocalDate;

/**
 * Representa a un medico del Hospital Infantil de Mexico "Federico Gomez".
 */
public class Medico extends Persona {

    private EspecialidadMedica especialidad;
    private String cedulaProfesional;

    public Medico(String nombreCompleto,
                  LocalDate fechaNacimiento,
                  EspecialidadMedica especialidad,
                  String cedulaProfesional) {
        super(nombreCompleto, fechaNacimiento);
        setEspecialidad(especialidad);
        setCedulaProfesional(cedulaProfesional);
    }

    public EspecialidadMedica getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadMedica especialidad) {
        if (especialidad == null) {
            throw new ValidacionDominioException("La especialidad del medico es obligatoria.");
        }
        this.especialidad = especialidad;
        marcarActualizado();
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        if (cedulaProfesional == null || cedulaProfesional.isBlank()) {
            throw new ValidacionDominioException("La cedula profesional es obligatoria.");
        }
        this.cedulaProfesional = cedulaProfesional.trim().toUpperCase();
        marcarActualizado();
    }
}
