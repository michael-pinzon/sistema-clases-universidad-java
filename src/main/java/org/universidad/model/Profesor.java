package org.universidad.model;

import java.util.Objects;

public abstract class Profesor {

    private static int totalProfesores;

    private final String nombre;
    private final double salarioBase;

    protected Profesor(String nombre, double salarioBase) {
        this.nombre = validarTexto(nombre, "El nombre del profesor es obligatorio.");
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

    private static String validarTexto(String valor, String mensaje) {
        Objects.requireNonNull(valor, mensaje);
        String valorNormalizado = valor.trim();
        if (valorNormalizado.isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valorNormalizado;
    }
}
