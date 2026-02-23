package com.himfg.hospitalinfantil.repository;

import com.himfg.hospitalinfantil.domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioPaciente extends JpaRepository<Paciente, Long> {
    boolean existsByNumeroExpedienteIgnoreCase(String numeroExpediente);

    List<Paciente> findByActivoTrueOrderByIdAsc();
}
