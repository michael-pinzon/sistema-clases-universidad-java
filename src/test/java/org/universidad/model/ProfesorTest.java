package org.universidad.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void rechazaUnNombreDeProfesorConNumerosOSimbolos() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ProfesorTiempoCompleto("Ana2 Pérez", 2000, 5));
        assertThrows(
                IllegalArgumentException.class,
                () -> new ProfesorTiempoCompleto("Ana-Pérez", 2000, 5));
    }

    @Test
    void rechazaUnNombreDeProfesorSinApellido() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ProfesorTiempoCompleto("Ana", 2000, 5));
    }
}
