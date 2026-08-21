package org.universidad.service;

import org.universidad.model.ClaseUniversitaria;
import org.universidad.model.Estudiante;
import org.universidad.model.Profesor;
import org.universidad.model.Universidad;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class UniversidadService {

    private static final int PRIMER_ID_ESTUDIANTE = 1001;

    private final Universidad universidad;

    public UniversidadService(Universidad universidad) {
        this.universidad = Objects.requireNonNull(universidad, "La universidad es obligatoria.");
    }

    public Universidad getUniversidad() {
        return universidad;
    }

    public void registrarProfesor(Profesor profesor) {
        Objects.requireNonNull(profesor, "El profesor es obligatorio.");
        if (buscarProfesorPorNombre(profesor.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un profesor con ese nombre.");
        }
        universidad.agregarProfesor(profesor);
    }

    public void registrarEstudiante(Estudiante estudiante) {
        Objects.requireNonNull(estudiante, "El estudiante es obligatorio.");
        if (buscarEstudiantePorId(estudiante.getId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un estudiante con ese ID.");
        }
        universidad.agregarEstudiante(estudiante);
    }

    public Estudiante registrarEstudiante(String nombre, int edad) {
        Estudiante estudiante = new Estudiante(nombre, obtenerSiguienteIdEstudiante(), edad);
        registrarEstudiante(estudiante);
        return estudiante;
    }

    public int obtenerSiguienteIdEstudiante() {
        return universidad.getEstudiantes().stream()
                .mapToInt(Estudiante::getId)
                .max()
                .orElse(PRIMER_ID_ESTUDIANTE - 1) + 1;
    }

    public void registrarClase(ClaseUniversitaria clase) {
        Objects.requireNonNull(clase, "La clase es obligatoria.");
        if (buscarClasePorNombre(clase.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una clase con ese nombre.");
        }
        universidad.agregarClase(clase);
    }

    public Optional<Profesor> buscarProfesorPorNombre(String nombre) {
        String nombreNormalizado = normalizarTexto(nombre, "El nombre del profesor es obligatorio.");
        return universidad.getProfesores().stream()
                .filter(profesor -> profesor.getNombre().equalsIgnoreCase(nombreNormalizado))
                .findFirst();
    }

    public Optional<Estudiante> buscarEstudiantePorId(int id) {
        return universidad.getEstudiantes().stream()
                .filter(estudiante -> estudiante.getId() == id)
                .findFirst();
    }

    public Optional<ClaseUniversitaria> buscarClasePorNombre(String nombre) {
        String nombreNormalizado = normalizarTexto(nombre, "El nombre de la clase es obligatorio.");
        return universidad.getClases().stream()
                .filter(clase -> clase.getNombre().equalsIgnoreCase(nombreNormalizado))
                .findFirst();
    }

    public List<ClaseUniversitaria> obtenerClasesDeEstudiante(int id) {
        if (buscarEstudiantePorId(id).isEmpty()) {
            throw new IllegalArgumentException("No existe un estudiante con ese ID.");
        }
        return universidad.getClases().stream()
                .filter(clase -> clase.getEstudiantes().stream()
                        .anyMatch(estudiante -> estudiante.getId() == id))
                .toList();
    }

    public void inscribirEstudianteEnClase(Estudiante estudiante, String nombreClase) {
        Estudiante estudianteRegistrado = validarEstudianteRegistrado(estudiante);
        ClaseUniversitaria clase = buscarClasePorNombre(nombreClase)
                .orElseThrow(() -> new IllegalArgumentException("No existe una clase con ese nombre."));
        clase.agregarEstudiante(estudianteRegistrado);
    }

    public ClaseUniversitaria crearClase(
            String nombre,
            String aulaAsignada,
            Profesor profesor,
            List<Estudiante> estudiantes) {
        Profesor profesorRegistrado = validarProfesorRegistrado(profesor);
        Objects.requireNonNull(estudiantes, "La lista de estudiantes es obligatoria.");

        ClaseUniversitaria clase = new ClaseUniversitaria(nombre, aulaAsignada, profesorRegistrado);
        for (Estudiante estudiante : estudiantes) {
            clase.agregarEstudiante(validarEstudianteRegistrado(estudiante));
        }
        registrarClase(clase);
        return clase;
    }

    private Profesor validarProfesorRegistrado(Profesor profesor) {
        Objects.requireNonNull(profesor, "El profesor es obligatorio.");
        if (!universidad.getProfesores().contains(profesor)) {
            throw new IllegalArgumentException("El profesor debe estar registrado en la universidad.");
        }
        return profesor;
    }

    private Estudiante validarEstudianteRegistrado(Estudiante estudiante) {
        Objects.requireNonNull(estudiante, "El estudiante es obligatorio.");
        return buscarEstudiantePorId(estudiante.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "El estudiante debe estar registrado en la universidad."));
    }

    private static String normalizarTexto(String valor, String mensaje) {
        Objects.requireNonNull(valor, mensaje);
        String valorNormalizado = valor.trim();
        if (valorNormalizado.isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valorNormalizado;
    }
}
