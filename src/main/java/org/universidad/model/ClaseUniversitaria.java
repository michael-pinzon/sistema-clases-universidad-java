package org.universidad.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ClaseUniversitaria {

    private final String nombre;
    private final String aulaAsignada;
    private final Profesor profesor;
    private final List<Estudiante> estudiantes;

    public ClaseUniversitaria(String nombre, String aulaAsignada, Profesor profesor) {
        this.nombre = validarTextoConLetras(nombre, "El nombre de la clase es obligatorio.");
        this.aulaAsignada = validarTextoConLetras(aulaAsignada, "El aula asignada es obligatoria.");
        this.profesor = Objects.requireNonNull(profesor, "La clase debe tener un profesor.");
        this.estudiantes = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getAulaAsignada() {
        return aulaAsignada;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public List<Estudiante> getEstudiantes() {
        return Collections.unmodifiableList(estudiantes);
    }

    public void agregarEstudiante(Estudiante estudiante) {
        Objects.requireNonNull(estudiante, "El estudiante es obligatorio.");
        if (!estudiantes.contains(estudiante)) {
            estudiantes.add(estudiante);
        }
    }

    private static String validarTexto(String valor, String mensaje) {
        Objects.requireNonNull(valor, mensaje);
        String valorNormalizado = valor.trim();
        if (valorNormalizado.isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valorNormalizado;
    }

    private static String validarTextoConLetras(String valor, String mensaje) {
        String valorNormalizado = validarTexto(valor, mensaje);
        if (valorNormalizado.chars().noneMatch(Character::isLetter)) {
            throw new IllegalArgumentException("El valor debe contener al menos una letra.");
        }
        return valorNormalizado;
    }
}
