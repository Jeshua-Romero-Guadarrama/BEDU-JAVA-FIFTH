package com.himfg.hospitalinfantil.controller.dto;

import com.himfg.hospitalinfantil.domain.Paciente;
import com.himfg.hospitalinfantil.domain.Sexo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RespuestaPaciente(
        Long id,
        String nombreCompleto,
        LocalDate fechaNacimiento,
        Integer edad,
        Sexo sexo,
        String numeroExpediente,
        Boolean activo,
        LocalDateTime fechaRegistro
) {
    public static RespuestaPaciente desdeEntidad(Paciente paciente) {
        return new RespuestaPaciente(
                paciente.getId(),
                paciente.getNombreCompleto(),
                paciente.getFechaNacimiento(),
                paciente.getEdad(),
                paciente.getSexo(),
                paciente.getNumeroExpediente(),
                paciente.estaActivo(),
                paciente.getFechaRegistro()
        );
    }
}
