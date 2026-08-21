package org.universidad.service;

import org.junit.jupiter.api.Test;
import org.universidad.model.ClaseUniversitaria;
import org.universidad.model.Estudiante;
import org.universidad.model.Profesor;
import org.universidad.model.Universidad;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UniversidadServiceTest {

    @Test
    void cargaLaCantidadMinimaDeDatosIniciales() {
        Universidad universidad = DatosIniciales.crearUniversidadInicial();

        assertEquals(4, universidad.getProfesores().size());
        assertEquals(6, universidad.getEstudiantes().size());
        assertEquals(4, universidad.getClases().size());
    }

    @Test
    void encuentraLasClasesDeUnEstudiantePorId() {
        Universidad universidad = DatosIniciales.crearUniversidadInicial();
        UniversidadService servicio = new UniversidadService(universidad);

        assertEquals(3, servicio.obtenerClasesDeEstudiante(1003).size());
    }

    @Test
    void rechazaEstudiantesConIdDuplicado() {
        Universidad universidad = new Universidad();
        UniversidadService servicio = new UniversidadService(universidad);
        servicio.registrarEstudiante(new Estudiante("Ana Pérez", 1, 20));

        assertThrows(
                IllegalArgumentException.class,
                () -> servicio.registrarEstudiante(new Estudiante("Luis Pérez", 1, 21)));
    }

    @Test
    void creaUnaClaseConProfesorYEstudiantesRegistrados() {
        Universidad universidad = DatosIniciales.crearUniversidadInicial();
        UniversidadService servicio = new UniversidadService(universidad);
        Profesor profesor = servicio.buscarProfesorPorNombre("Ana Martínez").orElseThrow();
        Estudiante estudiante = servicio.buscarEstudiantePorId(1001).orElseThrow();

        ClaseUniversitaria clase = servicio.crearClase(
                "Algoritmos",
                "E-505",
                profesor,
                List.of(estudiante));

        assertEquals("Algoritmos", clase.getNombre());
        assertSame(profesor, clase.getProfesor());
        assertEquals(1, clase.getEstudiantes().size());
        assertEquals(5, universidad.getClases().size());
    }

    @Test
    void evitaMatricularDosVecesAlMismoEstudianteEnUnaClase() {
        Universidad universidad = DatosIniciales.crearUniversidadInicial();
        UniversidadService servicio = new UniversidadService(universidad);
        Estudiante estudiante = servicio.buscarEstudiantePorId(1006).orElseThrow();

        servicio.inscribirEstudianteEnClase(estudiante, "Programación I");
        servicio.inscribirEstudianteEnClase(estudiante, "Programación I");

        ClaseUniversitaria clase = servicio.buscarClasePorNombre("Programación I").orElseThrow();
        assertEquals(4, clase.getEstudiantes().size());
    }
}
