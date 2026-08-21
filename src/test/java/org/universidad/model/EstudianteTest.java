package org.universidad.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EstudianteTest {

    @Test
    void rechazaUnNombreCompuestoSoloPorNumeros() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Estudiante("12345", 1007, 20));
    }

    @Test
    void rechazaUnaEdadMenorDeQuinceAnios() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Estudiante("Nombre Válido", 1007, 14));
    }

    @Test
    void aceptaLaEdadMinimaPermitida() {
        assertDoesNotThrow(() -> new Estudiante("Nombre Válido", 1007, 15));
    }
}
