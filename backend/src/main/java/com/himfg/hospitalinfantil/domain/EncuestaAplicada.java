package com.himfg.hospitalinfantil.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table(name = "encuestas_aplicadas")
public class EncuestaAplicada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "paciente_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_encuesta_paciente")
    )
    private Paciente paciente;

    @Column(name = "nombre_encuesta", nullable = false, length = 120)
    private String nombreEncuesta;

    @Column(name = "fecha_aplicacion", nullable = false)
    private LocalDateTime fechaAplicacion;

    @Column(nullable = false)
    private Integer dolor;

    @Column(nullable = false)
    private Integer ansiedad;

    @Column(name = "dificultad_respiratoria", nullable = false)
    private Integer dificultadRespiratoria;

    @Column(name = "promedio_sintomas", nullable = false, precision = 4, scale = 2)
    private BigDecimal promedioSintomas;

    @Column(nullable = false, length = 500)
    private String observaciones;

    protected EncuestaAplicada() {
    }

    public EncuestaAplicada(Paciente paciente,
                            String nombreEncuesta,
                            Integer dolor,
                            Integer ansiedad,
                            Integer dificultadRespiratoria,
                            String observaciones) {
        this.paciente = paciente;
        this.nombreEncuesta = nombreEncuesta;
        this.dolor = dolor;
        this.ansiedad = ansiedad;
        this.dificultadRespiratoria = dificultadRespiratoria;
        this.promedioSintomas = calcularPromedio(dolor, ansiedad, dificultadRespiratoria);
        this.observaciones = observaciones;
        this.fechaAplicacion = LocalDateTime.now();
    }

    @PrePersist
    void alCrear() {
        // Se recalculan y completan campos automaticos antes de guardar la encuesta.
        if (fechaAplicacion == null) {
            fechaAplicacion = LocalDateTime.now();
        }
        if (promedioSintomas == null) {
            promedioSintomas = calcularPromedio(dolor, ansiedad, dificultadRespiratoria);
        }
    }

    private BigDecimal calcularPromedio(Integer dolor, Integer ansiedad, Integer dificultadRespiratoria) {
        BigDecimal suma = BigDecimal.valueOf(dolor + ansiedad + dificultadRespiratoria);
        return suma.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP);
    }

    public Long getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getNombreEncuesta() {
        return nombreEncuesta;
    }

    public LocalDateTime getFechaAplicacion() {
        return fechaAplicacion;
    }

    public Integer getDolor() {
        return dolor;
    }

    public Integer getAnsiedad() {
        return ansiedad;
    }

    public Integer getDificultadRespiratoria() {
        return dificultadRespiratoria;
    }

    public BigDecimal getPromedioSintomas() {
        return promedioSintomas;
    }

    public String getObservaciones() {
        return observaciones;
    }
}
