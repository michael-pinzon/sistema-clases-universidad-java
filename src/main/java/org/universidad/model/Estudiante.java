package org.universidad.model;

import java.util.Objects;

public final class Estudiante {

    private final String nombre;
    private final int id;
    private final int edad;

    public Estudiante(String nombre, int id, int edad) {
        this.nombre = validarNombre(nombre);
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del estudiante debe ser mayor que cero.");
        }
        if (edad < 15 || edad > 120) {
            throw new IllegalArgumentException("La edad debe estar entre 15 y 120 años.");
        }
        this.id = id;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof Estudiante estudiante)) {
            return false;
        }
        return id == estudiante.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    private static String validarNombre(String nombre) {
        Objects.requireNonNull(nombre, "El nombre del estudiante es obligatorio.");
        String nombreNormalizado = nombre.trim();
        if (nombreNormalizado.isEmpty()) {
            throw new IllegalArgumentException("El nombre del estudiante es obligatorio.");
        }
        if (nombreNormalizado.chars().noneMatch(Character::isLetter)) {
            throw new IllegalArgumentException("El nombre del estudiante debe contener al menos una letra.");
        }
        return nombreNormalizado;
    }
}
