package com.himfg.hospitalinfantil.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "pacientes",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_paciente_numero_expediente",
                columnNames = "numero_expediente"
        )
)
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 120)
    private String nombreCompleto;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Sexo sexo;

    @Column(name = "numero_expediente", nullable = false, length = 30)
    private String numeroExpediente;

    @Column(nullable = false)
    private boolean activo;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @OneToMany(
            mappedBy = "paciente",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EncuestaAplicada> encuestas = new ArrayList<>();

    protected Paciente() {
    }

    public Paciente(String nombreCompleto, LocalDate fechaNacimiento, Sexo sexo, String numeroExpediente) {
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.numeroExpediente = numeroExpediente;
        this.activo = true;
        this.fechaRegistro = LocalDateTime.now();
    }

    @PrePersist
    void alCrear() {
        // Se establece la fecha de registro cuando la entidad se persiste por primera vez.
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public boolean isActivo() {
        return activo;
    }

    public boolean estaActivo() {
        return activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public List<EncuestaAplicada> getEncuestas() {
        return encuestas;
    }

    public int getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public void desactivar() {
        this.activo = false;
    }
}
