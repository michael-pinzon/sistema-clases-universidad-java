package org.universidad.model;

public abstract class Profesor {

    private static int totalProfesores;

    private final String nombre;
    private final double salarioBase;

    protected Profesor(String nombre, double salarioBase) {
        this.nombre = ValidadorNombre.validar(
                nombre,
                "El nombre del profesor debe contener solo letras y al menos un nombre y un apellido.");
        if (salarioBase <= 0) {
            throw new IllegalArgumentException("El salario base debe ser mayor que cero.");
        }
        this.salarioBase = salarioBase;
        totalProfesores++;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public static int getTotalProfesores() {
        return totalProfesores;
    }

    public abstract double calcularSalario();

    public abstract String getTipoContrato();

}
