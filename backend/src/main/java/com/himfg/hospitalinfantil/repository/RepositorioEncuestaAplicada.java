package com.himfg.hospitalinfantil.repository;

import com.himfg.hospitalinfantil.domain.EncuestaAplicada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioEncuestaAplicada extends JpaRepository<EncuestaAplicada, Long> {
    List<EncuestaAplicada> findByPacienteIdOrderByFechaAplicacionDesc(Long pacienteId);
}
