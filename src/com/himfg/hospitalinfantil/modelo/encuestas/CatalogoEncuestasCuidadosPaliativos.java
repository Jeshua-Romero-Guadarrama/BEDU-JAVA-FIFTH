package com.himfg.hospitalinfantil.modelo.encuestas;

import java.util.List;

/**
 * Catalogo con encuestas recomendadas para Cuidados Paliativos Pediatricos.
 */
public final class CatalogoEncuestasCuidadosPaliativos {

    private CatalogoEncuestasCuidadosPaliativos() {
    }

    public static List<EncuestaSalud> obtenerTodas() {
        return List.of(
                crearEncuestaControlSintomas(),
                crearEncuestaBienestarIntegral()
        );
    }

    public static EncuestaSalud crearEncuestaControlSintomas() {
        EncuestaSalud encuesta = new EncuestaSalud(
                "Control de sintomas pediatrico (Paliativos)",
                "Monitorea la intensidad de los sintomas fisicos prioritarios"
        );

        encuesta.agregarPregunta(new PreguntaEscala("Dolor en las ultimas 24 horas (1=nada, 5=severo)"));
        encuesta.agregarPregunta(new PreguntaEscala("Dificultad para respirar (1=nada, 5=muy intensa)"));
        encuesta.agregarPregunta(new PreguntaEscala("Nauseas o vomito (1=nada, 5=muy intenso)"));
        encuesta.agregarPregunta(new PreguntaEscala("Apetito (1=sin apetito, 5=apetito normal)"));
        encuesta.agregarPregunta(new PreguntaEscala("Sueno o descanso (1=sin descanso, 5=descanso adecuado)"));
        encuesta.agregarPregunta(new PreguntaEscala("Ansiedad o preocupacion (1=nada, 5=muy alta)"));

        return encuesta;
    }

    public static EncuestaSalud crearEncuestaBienestarIntegral() {
        EncuestaSalud encuesta = new EncuestaSalud(
                "Bienestar psicosocial y espiritual",
                "Explora necesidades emocionales, sociales y espirituales del paciente y la familia"
        );

        encuesta.agregarPregunta(new PreguntaTexto("Que le preocupa mas en este momento?"));
        encuesta.agregarPregunta(new PreguntaTexto("Que le ayuda a sentirse tranquilo o tranquila?"));
        encuesta.agregarPregunta(new PreguntaTexto("Hay creencias o practicas espirituales que desea que el equipo conozca?"));
        encuesta.agregarPregunta(new PreguntaTexto("Como se siente la familia con el plan actual de cuidado?"));

        return encuesta;
    }
}
