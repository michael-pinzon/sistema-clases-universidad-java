package org.universidad.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ClaseUniversitariaTest {

    @Test
    void rechazaUnNombreDeClaseCompuestoSoloPorNumeros() {
        Profesor profesor = new ProfesorTiempoCompleto("Profesor Válido", 2000, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> new ClaseUniversitaria("12345", "A-101", profesor));
    }
}
