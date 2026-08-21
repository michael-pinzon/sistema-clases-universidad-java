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
    void generaElSiguienteIdParaUnNuevoEstudiante() {
        Universidad universidad = DatosIniciales.crearUniversidadInicial();
        UniversidadService servicio = new UniversidadService(universidad);

        Estudiante estudiante = servicio.registrarEstudiante("Nuevo Estudiante", 15);

        assertEquals(1007, estudiante.getId());
        assertSame(estudiante, servicio.buscarEstudiantePorId(1007).orElseThrow());
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
    void muestraSoloLasAulasQueNoEstanOcupadas() {
        UniversidadService servicio = new UniversidadService(DatosIniciales.crearUniversidadInicial());

        assertEquals(List.of("E-505", "F-606", "G-707", "H-808"), servicio.obtenerAulasDisponibles());
    }

    @Test
    void rechazaUnaClaseConAulaOcupada() {
        Universidad universidad = DatosIniciales.crearUniversidadInicial();
        UniversidadService servicio = new UniversidadService(universidad);
        Profesor profesor = servicio.buscarProfesorPorNombre("Ana Martínez").orElseThrow();
        Estudiante estudiante = servicio.buscarEstudiantePorId(1001).orElseThrow();

        assertThrows(
                IllegalArgumentException.class,
                () -> servicio.crearClase("Nueva Clase", "A-101", profesor, List.of(estudiante)));
    }

    @Test
    void rechazaUnaClaseSinEstudiantes() {
        Universidad universidad = DatosIniciales.crearUniversidadInicial();
        UniversidadService servicio = new UniversidadService(universidad);
        Profesor profesor = servicio.buscarProfesorPorNombre("Ana Martínez").orElseThrow();

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.crearClase("Nueva Clase", "E-505", profesor, List.of()));

        assertEquals("La clase debe tener al menos un estudiante.", excepcion.getMessage());
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
