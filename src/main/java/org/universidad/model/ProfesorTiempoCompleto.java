package org.universidad.model;

public final class ProfesorTiempoCompleto extends Profesor {

    private static final double INCREMENTO_ANUAL = 0.10;

    private final int aniosDeExperiencia;

    public ProfesorTiempoCompleto(String nombre, double salarioBase, int aniosDeExperiencia) {
        super(nombre, salarioBase);
        if (aniosDeExperiencia < 0) {
            throw new IllegalArgumentException("Los años de experiencia no pueden ser negativos.");
        }
        this.aniosDeExperiencia = aniosDeExperiencia;
    }

    public int getAniosDeExperiencia() {
        return aniosDeExperiencia;
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase() * (1 + INCREMENTO_ANUAL * aniosDeExperiencia);
    }

    @Override
    public String getTipoContrato() {
        return "Tiempo completo";
    }
}
