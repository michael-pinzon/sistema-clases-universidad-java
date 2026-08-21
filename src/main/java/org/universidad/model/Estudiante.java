package org.universidad.model;

public final class Estudiante {

    private final String nombre;
    private final int id;
    private final int edad;

    public Estudiante(String nombre, int id, int edad) {
        this.nombre = ValidadorNombre.validar(
                nombre,
                "El nombre del estudiante debe contener solo letras y al menos un nombre y un apellido.");
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

}
