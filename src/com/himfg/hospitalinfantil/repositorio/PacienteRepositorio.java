package com.himfg.hospitalinfantil.repositorio;
import com.himfg.hospitalinfantil.modelo.personas.Paciente;
import java.util.List;
import java.util.Optional;

/**
 * Persistencia de pacientes.
 */
public interface PacienteRepositorio {
    Paciente guardar(Paciente paciente);
    Optional<Paciente> buscarPorId(Long id);
    List<Paciente> buscarTodos();
}
