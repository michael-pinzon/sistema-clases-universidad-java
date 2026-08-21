package org.universidad.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Universidad {

    public static final String NOMBRE = "Universidad";

    private final List<Profesor> profesores;
    private final List<Estudiante> estudiantes;
    private final List<ClaseUniversitaria> clases;

    public Universidad() {
        profesores = new ArrayList<>();
        estudiantes = new ArrayList<>();
        clases = new ArrayList<>();
    }

    public List<Profesor> getProfesores() {
        return Collections.unmodifiableList(profesores);
    }

    public List<Estudiante> getEstudiantes() {
        return Collections.unmodifiableList(estudiantes);
    }

    public List<ClaseUniversitaria> getClases() {
        return Collections.unmodifiableList(clases);
    }

    public void agregarProfesor(Profesor profesor) {
        Objects.requireNonNull(profesor, "El profesor es obligatorio.");
        if (!profesores.contains(profesor)) {
            profesores.add(profesor);
        }
    }

    public void agregarEstudiante(Estudiante estudiante) {
        Objects.requireNonNull(estudiante, "El estudiante es obligatorio.");
        if (!estudiantes.contains(estudiante)) {
            estudiantes.add(estudiante);
        }
    }

    public void agregarClase(ClaseUniversitaria clase) {
        Objects.requireNonNull(clase, "La clase es obligatoria.");
        if (!clases.contains(clase)) {
            clases.add(clase);
        }
    }
}
