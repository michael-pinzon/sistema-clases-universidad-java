package org.universidad.ui;

import org.universidad.model.ClaseUniversitaria;
import org.universidad.model.Estudiante;
import org.universidad.model.Profesor;
import org.universidad.model.ProfesorMedioTiempo;
import org.universidad.model.ProfesorTiempoCompleto;
import org.universidad.service.UniversidadService;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public final class MenuConsola {

    private final UniversidadService servicio;
    private final Scanner scanner;
    private final PrintStream salida;

    public MenuConsola(UniversidadService servicio, Scanner scanner, PrintStream salida) {
        this.servicio = Objects.requireNonNull(servicio, "El servicio es obligatorio.");
        this.scanner = Objects.requireNonNull(scanner, "El lector es obligatorio.");
        this.salida = Objects.requireNonNull(salida, "La salida es obligatoria.");
    }

    public void ejecutar() {
        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Seleccione una opción: ");
            continuar = procesarOpcion(opcion);
        }
    }

    private boolean procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> mostrarProfesores();
            case 2 -> mostrarMenuClases();
            case 3 -> crearEstudiante();
            case 4 -> crearClase();
            case 5 -> mostrarClasesDeEstudiante();
            case 6 -> {
                salida.println("Aplicación finalizada.");
                return false;
            }
            default -> salida.println("Opción no válida.");
        }
        return true;
    }

    private void mostrarMenuPrincipal() {
        salida.println();
        salida.println("=== SISTEMA DE CLASES UNIVERSITARIAS ===");
        salida.println("1. Listar profesores");
        salida.println("2. Listar clases");
        salida.println("3. Registrar estudiante y asociarlo a una clase");
        salida.println("4. Registrar clase");
        salida.println("5. Consultar clases de un estudiante");
        salida.println("6. Salir");
    }

    private void mostrarProfesores() {
        List<Profesor> profesores = servicio.getUniversidad().getProfesores();
        salida.println();
        salida.println("--- PROFESORES ---");

        if (profesores.isEmpty()) {
            salida.println("No hay profesores registrados.");
            return;
        }

        for (int indice = 0; indice < profesores.size(); indice++) {
            Profesor profesor = profesores.get(indice);
            salida.printf(Locale.US, "%n%d. %s%n", indice + 1, profesor.getNombre());
            salida.printf("   Tipo de contrato: %s%n", profesor.getTipoContrato());
            salida.printf(Locale.US, "   Salario base: %.2f%n", profesor.getSalarioBase());
            salida.printf(Locale.US, "   Salario calculado: %.2f%n", profesor.calcularSalario());

            if (profesor instanceof ProfesorTiempoCompleto tiempoCompleto) {
                salida.printf("   Años de experiencia: %d%n", tiempoCompleto.getAniosDeExperiencia());
            } else if (profesor instanceof ProfesorMedioTiempo medioTiempo) {
                salida.printf("   Horas activas por semana: %d%n", medioTiempo.getHorasActivasPorSemana());
            }
        }
    }

    private void mostrarMenuClases() {
        List<ClaseUniversitaria> clases = servicio.getUniversidad().getClases();
        salida.println();
        salida.println("--- CLASES ---");

        if (clases.isEmpty()) {
            salida.println("No hay clases registradas.");
            return;
        }

        for (int indice = 0; indice < clases.size(); indice++) {
            ClaseUniversitaria clase = clases.get(indice);
            salida.printf(
                    "%d. %s | Aula: %s | Profesor: %s | Estudiantes: %d%n",
                    indice + 1,
                    clase.getNombre(),
                    clase.getAulaAsignada(),
                    clase.getProfesor().getNombre(),
                    clase.getEstudiantes().size());
        }

        salida.println("0. Regresar");
        int seleccion = leerEntero("Seleccione una clase: ");
        if (seleccion == 0) {
            return;
        }
        if (seleccion < 1 || seleccion > clases.size()) {
            salida.println("Selección no válida.");
            return;
        }

        mostrarDetalleClase(clases.get(seleccion - 1));
    }

    private void mostrarDetalleClase(ClaseUniversitaria clase) {
        salida.println();
        salida.println("--- DETALLE DE LA CLASE ---");
        salida.println("Nombre: " + clase.getNombre());
        salida.println("Aula: " + clase.getAulaAsignada());
        salida.println("Profesor:");
        imprimirProfesor(clase.getProfesor(), "   ");
        salida.println("Estudiantes:");

        if (clase.getEstudiantes().isEmpty()) {
            salida.println("   No hay estudiantes matriculados.");
            return;
        }

        for (Estudiante estudiante : clase.getEstudiantes()) {
            salida.printf(
                    "   - %s | ID: %d | Edad: %d%n",
                    estudiante.getNombre(),
                    estudiante.getId(),
                    estudiante.getEdad());
        }
    }

    private void imprimirProfesor(Profesor profesor, String prefijo) {
        salida.println(prefijo + "Nombre: " + profesor.getNombre());
        salida.println(prefijo + "Tipo de contrato: " + profesor.getTipoContrato());
        salida.printf(Locale.US, prefijo + "Salario base: %.2f%n", profesor.getSalarioBase());
        salida.printf(Locale.US, prefijo + "Salario calculado: %.2f%n", profesor.calcularSalario());
    }

    private void crearEstudiante() {
        salida.println();
        salida.println("--- REGISTRAR ESTUDIANTE ---");
        String nombre = leerNombre("Nombre: ");
        int edad = leerEdad("Edad: ");
        ClaseUniversitaria clase = seleccionarClase("Seleccione la clase de inscripción:");

        if (clase == null) {
            return;
        }

        try {
            Estudiante estudiante = servicio.registrarEstudiante(nombre, edad);
            servicio.inscribirEstudianteEnClase(estudiante, clase.getNombre());
            salida.printf("Estudiante registrado y asociado correctamente. ID asignado: %d.%n",
                    estudiante.getId());
        } catch (IllegalArgumentException excepcion) {
            salida.println("No se pudo registrar el estudiante: " + excepcion.getMessage());
        }
    }

    private void crearClase() {
        salida.println();
        salida.println("--- REGISTRAR CLASE ---");
        String nombre = leerTexto("Nombre de la clase: ");
        String aula = leerTexto("Aula asignada: ");
        Profesor profesor = seleccionarProfesor();

        if (profesor == null) {
            return;
        }

        List<Estudiante> estudiantes = seleccionarEstudiantes();
        if (estudiantes == null) {
            return;
        }

        try {
            servicio.crearClase(nombre, aula, profesor, estudiantes);
            salida.println("Clase registrada correctamente.");
        } catch (IllegalArgumentException excepcion) {
            salida.println("No se pudo registrar la clase: " + excepcion.getMessage());
        }
    }

    private void mostrarClasesDeEstudiante() {
        salida.println();
        salida.println("--- CONSULTA POR ESTUDIANTE ---");
        int id = leerEntero("ID del estudiante: ");

        try {
            List<ClaseUniversitaria> clases = servicio.obtenerClasesDeEstudiante(id);
            salida.println("Clases del estudiante:");
            for (ClaseUniversitaria clase : clases) {
                salida.printf("- %s | Aula: %s | Profesor: %s%n",
                        clase.getNombre(),
                        clase.getAulaAsignada(),
                        clase.getProfesor().getNombre());
            }
            if (clases.isEmpty()) {
                salida.println("El estudiante no está asociado a ninguna clase.");
            }
        } catch (IllegalArgumentException excepcion) {
            salida.println("No se pudo realizar la consulta: " + excepcion.getMessage());
        }
    }

    private Profesor seleccionarProfesor() {
        List<Profesor> profesores = servicio.getUniversidad().getProfesores();
        salida.println("Profesores disponibles:");
        for (int indice = 0; indice < profesores.size(); indice++) {
            salida.printf("%d. %s (%s)%n",
                    indice + 1,
                    profesores.get(indice).getNombre(),
                    profesores.get(indice).getTipoContrato());
        }
        salida.println("0. Cancelar");

        int seleccion = leerEntero("Seleccione un profesor: ");
        if (seleccion == 0) {
            return null;
        }
        if (seleccion < 1 || seleccion > profesores.size()) {
            salida.println("Selección no válida.");
            return null;
        }
        return profesores.get(seleccion - 1);
    }

    private ClaseUniversitaria seleccionarClase(String mensaje) {
        List<ClaseUniversitaria> clases = servicio.getUniversidad().getClases();
        salida.println(mensaje);
        for (int indice = 0; indice < clases.size(); indice++) {
            salida.printf("%d. %s | Aula: %s%n",
                    indice + 1,
                    clases.get(indice).getNombre(),
                    clases.get(indice).getAulaAsignada());
        }
        salida.println("0. Cancelar");

        int seleccion = leerEntero("Seleccione una clase: ");
        if (seleccion == 0) {
            return null;
        }
        if (seleccion < 1 || seleccion > clases.size()) {
            salida.println("Selección no válida.");
            return null;
        }
        return clases.get(seleccion - 1);
    }

    private List<Estudiante> seleccionarEstudiantes() {
        List<Estudiante> estudiantesDisponibles = servicio.getUniversidad().getEstudiantes();
        List<Estudiante> seleccionados = new ArrayList<>();

        salida.println("Estudiantes disponibles:");
        for (int indice = 0; indice < estudiantesDisponibles.size(); indice++) {
            Estudiante estudiante = estudiantesDisponibles.get(indice);
            salida.printf("%d. %s | ID: %d%n", indice + 1, estudiante.getNombre(), estudiante.getId());
        }
        salida.println("0. Finalizar selección");

        while (true) {
            int seleccion = leerEntero("Seleccione un estudiante: ");
            if (seleccion == 0) {
                if (seleccionados.isEmpty()) {
                    salida.println("Debe seleccionar al menos un estudiante.");
                    return null;
                }
                return seleccionados;
            }
            if (seleccion < 1 || seleccion > estudiantesDisponibles.size()) {
                salida.println("Selección no válida.");
                continue;
            }

            Estudiante estudiante = estudiantesDisponibles.get(seleccion - 1);
            if (seleccionados.contains(estudiante)) {
                salida.println("El estudiante ya fue seleccionado.");
            } else {
                seleccionados.add(estudiante);
                salida.println("Estudiante agregado.");
            }
        }
    }

    private String leerTexto(String mensaje) {
        while (true) {
            salida.print(mensaje);
            String valor = scanner.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            salida.println("El valor es obligatorio.");
        }
    }

    private String leerNombre(String mensaje) {
        while (true) {
            String valor = leerTexto(mensaje);
            if (valor.chars().anyMatch(Character::isLetter)) {
                return valor;
            }
            salida.println("Ingrese un nombre válido; debe contener al menos una letra.");
        }
    }

    private int leerEdad(String mensaje) {
        while (true) {
            int edad = leerEntero(mensaje);
            if (edad >= 15 && edad <= 120) {
                return edad;
            }
            salida.println("La edad debe ser un número entero entre 15 y 120 años.");
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            salida.print(mensaje);
            String valor = scanner.nextLine().trim();
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException excepcion) {
                salida.println("Ingrese un número entero válido.");
            }
        }
    }
}
