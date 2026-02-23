INSERT INTO pacientes (nombre_completo, fecha_nacimiento, sexo, numero_expediente, activo, fecha_registro)
SELECT 'Paciente Demo A', DATE '2010-05-01', 'MASCULINO', 'EXP-DEMO-001', TRUE, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM pacientes WHERE numero_expediente = 'EXP-DEMO-001'
);

INSERT INTO pacientes (nombre_completo, fecha_nacimiento, sexo, numero_expediente, activo, fecha_registro)
SELECT 'Paciente Demo B', DATE '2012-09-15', 'FEMENINO', 'EXP-DEMO-002', TRUE, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM pacientes WHERE numero_expediente = 'EXP-DEMO-002'
);

INSERT INTO encuestas_aplicadas (
    paciente_id,
    nombre_encuesta,
    fecha_aplicacion,
    dolor,
    ansiedad,
    dificultad_respiratoria,
    promedio_sintomas,
    observaciones
)
SELECT p.id, 'Control integral de sintomas pediatricos', CURRENT_TIMESTAMP, 2, 3, 2, 2.33, 'Paciente estable en control inicial'
FROM pacientes p
WHERE p.numero_expediente = 'EXP-DEMO-001'
AND NOT EXISTS (
    SELECT 1
    FROM encuestas_aplicadas e
    WHERE e.paciente_id = p.id
      AND e.nombre_encuesta = 'Control integral de sintomas pediatricos'
);

INSERT INTO encuestas_aplicadas (
    paciente_id,
    nombre_encuesta,
    fecha_aplicacion,
    dolor,
    ansiedad,
    dificultad_respiratoria,
    promedio_sintomas,
    observaciones
)
SELECT p.id, 'Bienestar psicosocial y familiar', CURRENT_TIMESTAMP, 3, 2, 2, 2.33, 'Se recomienda seguimiento emocional semanal'
FROM pacientes p
WHERE p.numero_expediente = 'EXP-DEMO-002'
AND NOT EXISTS (
    SELECT 1
    FROM encuestas_aplicadas e
    WHERE e.paciente_id = p.id
      AND e.nombre_encuesta = 'Bienestar psicosocial y familiar'
);

INSERT INTO encuestas_aplicadas (
    paciente_id,
    nombre_encuesta,
    fecha_aplicacion,
    dolor,
    ansiedad,
    dificultad_respiratoria,
    promedio_sintomas,
    observaciones
)
SELECT p.id, 'Valoracion funcional y calidad de sueno', CURRENT_TIMESTAMP, 2, 2, 3, 2.33, 'Se detecta alteracion leve del descanso'
FROM pacientes p
WHERE p.numero_expediente = 'EXP-DEMO-001'
AND NOT EXISTS (
    SELECT 1
    FROM encuestas_aplicadas e
    WHERE e.paciente_id = p.id
      AND e.nombre_encuesta = 'Valoracion funcional y calidad de sueno'
);
