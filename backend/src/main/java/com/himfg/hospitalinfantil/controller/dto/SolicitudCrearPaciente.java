package com.himfg.hospitalinfantil.controller.dto;

import com.himfg.hospitalinfantil.domain.Sexo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record SolicitudCrearPaciente(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 120, message = "El nombre debe tener entre 3 y 120 caracteres")
        String nombreCompleto,

        @NotNull(message = "La fecha de nacimiento es obligatoria")
        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        LocalDate fechaNacimiento,

        @NotNull(message = "El sexo es obligatorio")
        Sexo sexo,

        @NotBlank(message = "El numero de expediente es obligatorio")
        @Size(min = 4, max = 30, message = "El expediente debe tener entre 4 y 30 caracteres")
        String numeroExpediente
) {
}
