package org.universidad.ui;

import org.junit.jupiter.api.Test;
import org.universidad.service.DatosIniciales;
import org.universidad.service.UniversidadService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MenuConsolaTest {

    @Test
    void permiteFinalizarLaAplicacionDesdeElMenu() {
        ByteArrayOutputStream contenido = new ByteArrayOutputStream();
        UniversidadService servicio = new UniversidadService(DatosIniciales.crearUniversidadInicial());
        MenuConsola menu = new MenuConsola(
                servicio,
                new Scanner("6\n"),
                new PrintStream(contenido, true, StandardCharsets.UTF_8));

        menu.ejecutar();

        String salida = contenido.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("Aplicación finalizada."));
    }

    @Test
    void registraEstudianteSinSolicitarIdYConfirmaElIdGenerado() {
        ByteArrayOutputStream contenido = new ByteArrayOutputStream();
        UniversidadService servicio = new UniversidadService(DatosIniciales.crearUniversidadInicial());
        MenuConsola menu = new MenuConsola(
                servicio,
                new Scanner("3\nNuevo Estudiante\n15\n1\n6\n"),
                new PrintStream(contenido, true, StandardCharsets.UTF_8));

        menu.ejecutar();

        String salida = contenido.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("ID asignado: 1007"));
        assertFalse(salida.contains("ID:"));
        assertTrue(servicio.buscarEstudiantePorId(1007).isPresent());
    }

    @Test
    void registraClaseConAulaSeleccionadaYMantieneElMenuDeEstudiantesAbierto() {
        ByteArrayOutputStream contenido = new ByteArrayOutputStream();
        UniversidadService servicio = new UniversidadService(DatosIniciales.crearUniversidadInicial());
        MenuConsola menu = new MenuConsola(
                servicio,
                new Scanner("4\nNueva Clase\n1\n1\n1\n2\n0\n6\n"),
                new PrintStream(contenido, true, StandardCharsets.UTF_8));

        menu.ejecutar();

        String salida = contenido.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("Aulas disponibles:"));
        assertTrue(salida.contains("Seleccionar uno no cierra este menú"));
        assertTrue(salida.contains("Clase registrada correctamente."));
        assertEquals(5, servicio.getUniversidad().getClases().size());
        assertEquals("E-505", servicio.getUniversidad().getClases().get(4).getAulaAsignada());
        assertEquals(2, servicio.getUniversidad().getClases().get(4).getEstudiantes().size());
    }
}
