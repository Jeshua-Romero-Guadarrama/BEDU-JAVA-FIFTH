package com.himfg.hospitalinfantil.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ControladorSistema {

    @GetMapping("/")
    public Map<String, String> inicioBackend() {
        // Se expone un mensaje informativo para confirmar que el backend esta en linea.
        return Map.of(
                "servicio", "Backend HIMFG en ejecucion",
                "api", "/api/autenticacion, /api/pacientes, /api/encuestas",
                "estado", "OK"
        );
    }
}
