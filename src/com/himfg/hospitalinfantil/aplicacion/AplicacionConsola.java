package com.himfg.hospitalinfantil.aplicacion;

import com.himfg.hospitalinfantil.archivos.ExportadorEncuestasPaciente;
import com.himfg.hospitalinfantil.archivos.RegistroAuditoriaArchivo;
import com.himfg.hospitalinfantil.excepciones.RecursoNoEncontradoException;
import com.himfg.hospitalinfantil.excepciones.ValidacionDominioException;
import com.himfg.hospitalinfantil.modelo.encuestas.CatalogoEncuestasCuidadosPaliativos;
import com.himfg.hospitalinfantil.modelo.encuestas.EncuestaSalud;
import com.himfg.hospitalinfantil.modelo.encuestas.Pregunta;
import com.himfg.hospitalinfantil.modelo.personas.Paciente;
import com.himfg.hospitalinfantil.modelo.personas.Sexo;
import com.himfg.hospitalinfantil.repositorio.EncuestaAplicadaRepositorioEnMemoria;
import com.himfg.hospitalinfantil.repositorio.PacienteRepositorioEnMemoria;
import com.himfg.hospitalinfantil.servicio.EncuestaServicio;
import com.himfg.hospitalinfantil.servicio.PacienteServicio;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Aplicacion de consola para el sistema de encuestas del Hospital Infantil de Mexico "Federico Gomez".
 * Por ahora se enfoca en la gestion de pacientes mediante POO.
 */
public class AplicacionConsola {

    public static void main(String[] args) {
        var pacienteServicio = new PacienteServicio(new PacienteRepositorioEnMemoria());
        var auditoria = new RegistroAuditoriaArchivo(Path.of("auditoria-himfg.log"));
        var encuestaServicio = new EncuestaServicio(
                CatalogoEncuestasCuidadosPaliativos.obtenerTodas(),
                new EncuestaAplicadaRepositorioEnMemoria()
        );
        var exportador = new ExportadorEncuestasPaciente();
        precargarPacientesDemo(pacienteServicio, auditoria);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuar = true;
            System.out.println("=== Sistema de Encuestas HIMFG (POO) ===");

            while (continuar) {
                System.out.println("\nSeleccione una opcion:");
                System.out.println("1. Registrar paciente");
                System.out.println("2. Listar pacientes activos");
                System.out.println("3. Buscar paciente por id");
                System.out.println("4. Desactivar paciente");
                System.out.println("5. Ver encuestas de cuidados paliativos");
                System.out.println("6. Aplicar encuesta de cuidados paliativos");
                System.out.println("0. Salir");

                String opcion = scanner.nextLine();
                try {
                    switch (opcion) {
                        case "1":
                            registrarPacienteDesdeConsola(pacienteServicio, auditoria, scanner);
                            break;
                        case "2":
                            listarPacientes(pacienteServicio);
                            break;
                        case "3":
                            buscarPacientePorId(pacienteServicio, scanner);
                            break;
                        case "4":
                            desactivarPaciente(pacienteServicio, auditoria, scanner);
                            break;
                        case "5":
                            mostrarCatalogo(encuestaServicio.obtenerCatalogo());
                            break;
                        case "6":
                            aplicarEncuestaCuidadosPaliativos(
                                    encuestaServicio,
                                    pacienteServicio,
                                    auditoria,
                                    exportador,
                                    scanner
                            );
                            break;
                        case "0":
                            continuar = false;
                            System.out.println("Saliendo de la aplicacion...");
                            break;
                        default:
                            System.out.println("Opcion no valida, intente de nuevo.");
                            break;
                    }
                } catch (ValidacionDominioException e) {
                    System.out.println("Error de validacion: " + e.getMessage());
                } catch (RecursoNoEncontradoException e) {
                    System.out.println("Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Ocurrio un error inesperado: " + e.getMessage());
                }
            }
        }
    }

    private static void registrarPacienteDesdeConsola(PacienteServicio servicio,
                                                      RegistroAuditoriaArchivo auditoria,
                                                      Scanner scanner) {

        System.out.print("Nombre completo del paciente: ");
        String nombre = scanner.nextLine();

        System.out.print("Fecha de nacimiento (AAAA-MM-DD): ");
        LocalDate fechaNacimiento = LocalDate.parse(scanner.nextLine());

        System.out.print("Sexo (FEMENINO/MASCULINO/INTERSEXUAL/NO_ESPECIFICADO): ");
        Sexo sexo = Sexo.valueOf(scanner.nextLine().trim().toUpperCase());

        System.out.print("Numero de expediente: ");
        String expediente = scanner.nextLine();

        Paciente paciente = servicio.registrarPaciente(nombre, fechaNacimiento, sexo, expediente);
        auditoria.registrarOperacion("Paciente registrado: id=" + paciente.getId());
        System.out.println("Paciente registrado con id: " + paciente.getId());
    }

    private static void listarPacientes(PacienteServicio servicio) {
        List<Paciente> pacientes = servicio.listarPacientesActivos();
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes activos registrados.");
        } else {
            System.out.println("Pacientes activos en el HIMFG:");
            for (Paciente p : pacientes) {
                System.out.println(" - " + p);
            }
        }
    }

    private static void buscarPacientePorId(PacienteServicio servicio, Scanner scanner) {
        System.out.print("Ingrese el id del paciente: ");
        Long id = Long.parseLong(scanner.nextLine());
        Paciente paciente = servicio.obtenerPacientePorId(id);
        System.out.println("Paciente encontrado: " + paciente);
    }

    private static void desactivarPaciente(PacienteServicio servicio,
                                           RegistroAuditoriaArchivo auditoria,
                                           Scanner scanner) {
        System.out.print("Ingrese el id del paciente a desactivar: ");
        Long id = Long.parseLong(scanner.nextLine());
        servicio.desactivarPaciente(id);
        auditoria.registrarOperacion("Paciente desactivado: id=" + id);
        System.out.println("Paciente desactivado correctamente.");
    }

    private static void precargarPacientesDemo(PacienteServicio servicio,
                                               RegistroAuditoriaArchivo auditoria) {
        try {
            Paciente demo1 = servicio.registrarPaciente(
                    "Paciente Demo A",
                    LocalDate.of(2010, 5, 1),
                    Sexo.MASCULINO,
                    "EXP-DEMO-001"
            );
            Paciente demo2 = servicio.registrarPaciente(
                    "Paciente Demo B",
                    LocalDate.of(2012, 9, 15),
                    Sexo.FEMENINO,
                    "EXP-DEMO-002"
            );
            auditoria.registrarOperacion("Carga demo: pacientes " + demo1.getId() + " y " + demo2.getId());
            System.out.println("Se precargaron pacientes demo para facilitar las pruebas (ids: "
                    + demo1.getId() + ", " + demo2.getId() + ").");
        } catch (Exception e) {
            System.out.println("No se pudieron precargar pacientes demo: " + e.getMessage());
        }
    }

    private static void mostrarCatalogo(List<EncuestaSalud> catalogo) {
        if (catalogo.isEmpty()) {
            System.out.println("No hay encuestas de cuidados paliativos cargadas.");
            return;
        }
        System.out.println("Encuestas de Cuidados Paliativos y Calidad de Vida:");
        for (int i = 0; i < catalogo.size(); i++) {
            EncuestaSalud encuesta = catalogo.get(i);
            System.out.println((i + 1) + ". " + encuesta.getNombre());
            System.out.println("   Descripcion: " + encuesta.getDescripcion());
        }
    }

    private static void aplicarEncuestaCuidadosPaliativos(EncuestaServicio encuestaServicio,
                                                          PacienteServicio pacienteServicio,
                                                          RegistroAuditoriaArchivo auditoria,
                                                          ExportadorEncuestasPaciente exportador,
                                                          Scanner scanner) {
        List<EncuestaSalud> catalogo = encuestaServicio.obtenerCatalogo();
        if (catalogo.isEmpty()) {
            System.out.println("No hay encuestas para aplicar.");
            return;
        }
        mostrarCatalogo(catalogo);
        System.out.print("Elija el numero de encuesta a aplicar: ");
        int indiceElegido = Integer.parseInt(scanner.nextLine());
        EncuestaSalud encuestaElegida = encuestaServicio.obtenerEncuestaPorIndiceBaseUno(indiceElegido);

        System.out.print("Ingrese el id del paciente: ");
        Long id = Long.parseLong(scanner.nextLine());
        Paciente paciente = pacienteServicio.obtenerPacientePorId(id);

        System.out.println("\nAplicando: " + encuestaElegida.getNombre());
        List<String> respuestas = capturarRespuestas(encuestaElegida.getPreguntas(), scanner);

        var aplicadaGuardada = encuestaServicio.aplicarEncuesta(paciente, encuestaElegida, respuestas);
        auditoria.registrarOperacion("Encuesta aplicada (" + encuestaElegida.getNombre() + ") a paciente id=" + paciente.getId());
        System.out.println("Encuesta aplicada y almacenada en memoria con id interno: " + aplicadaGuardada.getId());

        System.out.print("Desea exportar las encuestas del paciente a CSV? (s/n): ");
        String exportar = scanner.nextLine().trim().toLowerCase();
        if ("s".equals(exportar)) {
            exportarEncuestasPaciente(encuestaServicio, paciente.getId(), exportador);
        }
    }

    private static List<String> capturarRespuestas(List<Pregunta> preguntas, Scanner scanner) {
        List<String> respuestas = new java.util.ArrayList<>();
        for (Pregunta pregunta : preguntas) {
            while (true) {
                System.out.println("\n" + pregunta.getEnunciado());
                System.out.print("Respuesta: ");
                String respuesta = scanner.nextLine();
                if (pregunta.esRespuestaValida(respuesta)) {
                    respuestas.add(respuesta);
                    break;
                }
                System.out.println("Respuesta invalida, intente de nuevo.");
            }
        }
        return respuestas;
    }

    private static void exportarEncuestasPaciente(EncuestaServicio encuestaServicio,
                                                  Long pacienteId,
                                                  ExportadorEncuestasPaciente exportador) {
        String nombreArchivo = "encuestas-paliativos-" + pacienteId + "-" +
                java.time.LocalDateTime.now().toString().replace(":", "-") + ".csv";
        try {
            encuestaServicio.exportarEncuestasPaciente(pacienteId, exportador, Path.of(nombreArchivo));
            System.out.println("Archivo exportado: " + nombreArchivo);
        } catch (RecursoNoEncontradoException e) {
            System.out.println("No hay encuestas aplicadas a este paciente para exportar.");
        } catch (IOException e) {
            System.out.println("No se pudo exportar el archivo: " + e.getMessage());
        }
    }
}
