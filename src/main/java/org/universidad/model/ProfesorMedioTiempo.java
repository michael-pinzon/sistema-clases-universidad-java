package org.universidad.model;

public final class ProfesorMedioTiempo extends Profesor {

    private final int horasActivasPorSemana;

    public ProfesorMedioTiempo(String nombre, double salarioBase, int horasActivasPorSemana) {
        super(nombre, salarioBase);
        if (horasActivasPorSemana <= 0) {
            throw new IllegalArgumentException("Las horas activas deben ser mayores que cero.");
        }
        this.horasActivasPorSemana = horasActivasPorSemana;
    }

    public int getHorasActivasPorSemana() {
        return horasActivasPorSemana;
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase() * horasActivasPorSemana;
    }

    @Override
    public String getTipoContrato() {
        return "Medio tiempo";
    }
}
