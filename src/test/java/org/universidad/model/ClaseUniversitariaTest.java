package org.universidad.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ClaseUniversitariaTest {

    @Test
    void rechazaUnNombreDeClaseCompuestoSoloPorNumeros() {
        Profesor profesor = new ProfesorTiempoCompleto("Profesor Válido", 2000, 3);
        Estudiante estudiante = new Estudiante("Estudiante Válido", 1001, 20);

        assertThrows(
                IllegalArgumentException.class,
                () -> new ClaseUniversitaria("12345", "A-101", profesor, List.of(estudiante)));
    }

    @Test
    void rechazaUnNombreDeClaseConSimbolosONumeros() {
        Profesor profesor = new ProfesorTiempoCompleto("Profesor Válido", 2000, 3);
        Estudiante estudiante = new Estudiante("Estudiante Válido", 1001, 20);

        assertThrows(
                IllegalArgumentException.class,
                () -> new ClaseUniversitaria("Programación 1", "A-101", profesor, List.of(estudiante)));
        assertThrows(
                IllegalArgumentException.class,
                () -> new ClaseUniversitaria("Programación-I", "A-101", profesor, List.of(estudiante)));
    }

    @Test
    void rechazaUnaClaseSinEstudiantes() {
        Profesor profesor = new ProfesorTiempoCompleto("Profesor Válido", 2000, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> new ClaseUniversitaria("Programación I", "A-101", profesor, List.of()));
    }
}
