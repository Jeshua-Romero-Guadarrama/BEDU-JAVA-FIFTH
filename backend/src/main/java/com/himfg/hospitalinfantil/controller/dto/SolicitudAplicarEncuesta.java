package com.himfg.hospitalinfantil.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SolicitudAplicarEncuesta(
        @NotNull(message = "El id del paciente es obligatorio")
        Long pacienteId,

        @NotBlank(message = "El nombre de la encuesta es obligatorio")
        @Size(min = 5, max = 120, message = "El nombre de la encuesta debe tener entre 5 y 120 caracteres")
        String nombreEncuesta,

        @NotNull(message = "La escala de dolor es obligatoria")
        @Min(value = 1, message = "La escala de dolor debe estar entre 1 y 5")
        @Max(value = 5, message = "La escala de dolor debe estar entre 1 y 5")
        Integer dolor,

        @NotNull(message = "La escala de ansiedad es obligatoria")
        @Min(value = 1, message = "La escala de ansiedad debe estar entre 1 y 5")
        @Max(value = 5, message = "La escala de ansiedad debe estar entre 1 y 5")
        Integer ansiedad,

        @NotNull(message = "La escala de dificultad respiratoria es obligatoria")
        @Min(value = 1, message = "La escala de dificultad respiratoria debe estar entre 1 y 5")
        @Max(value = 5, message = "La escala de dificultad respiratoria debe estar entre 1 y 5")
        Integer dificultadRespiratoria,

        @NotBlank(message = "Las observaciones son obligatorias")
        @Size(min = 4, max = 500, message = "Las observaciones deben tener entre 4 y 500 caracteres")
        String observaciones
) {
}
