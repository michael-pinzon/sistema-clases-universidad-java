package org.universidad.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfesorTest {

    @Test
    void calculaSalarioDeProfesorTiempoCompleto() {
        Profesor profesor = new ProfesorTiempoCompleto("Ana Martínez", 2000, 5);

        assertEquals(3000, profesor.calcularSalario(), 0.001);
    }

    @Test
    void calculaSalarioDeProfesorMedioTiempo() {
        Profesor profesor = new ProfesorMedioTiempo("Luis Torres", 100, 10);

        assertEquals(1000, profesor.calcularSalario(), 0.001);
    }
}
