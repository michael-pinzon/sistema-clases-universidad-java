package org.universidad.ui;

import org.junit.jupiter.api.Test;
import org.universidad.service.DatosIniciales;
import org.universidad.service.UniversidadService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                new Scanner("3\n123\nNuevo Estudiante\ntexto\n14\n15\n1\n6\n"),
                new PrintStream(contenido, true, StandardCharsets.UTF_8));

        menu.ejecutar();

        String salida = contenido.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("debe contener al menos una letra."));
        assertTrue(salida.contains("entero válido"));
        assertTrue(salida.contains("entre 15 y 120 años."));
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
                new Scanner("4\n123\nNueva Clase\n9\n1\n1\n1\n2\n0\n6\n"),
                new PrintStream(contenido, true, StandardCharsets.UTF_8));

        menu.ejecutar();

        String salida = contenido.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("debe contener al menos una letra."));
        assertTrue(salida.contains("Elija una de las aulas disponibles."));
        assertTrue(salida.contains("Aulas disponibles:"));
        assertTrue(salida.contains("Seleccionar uno no cierra este menú"));
        assertTrue(salida.contains("Clase registrada correctamente."));
        assertEquals(5, servicio.getUniversidad().getClases().size());
        assertEquals("E-505", servicio.getUniversidad().getClases().get(4).getAulaAsignada());
        assertEquals(2, servicio.getUniversidad().getClases().get(4).getEstudiantes().size());
    }

    @Test
    void consultaLasClasesSeleccionandoUnEstudiantePorNumero() {
        ByteArrayOutputStream contenido = new ByteArrayOutputStream();
        UniversidadService servicio = new UniversidadService(DatosIniciales.crearUniversidadInicial());
        MenuConsola menu = new MenuConsola(
                servicio,
                new Scanner("5\n3\n6\n"),
                new PrintStream(contenido, true, StandardCharsets.UTF_8));

        menu.ejecutar();

        String salida = contenido.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("Estudiantes disponibles:"));
        assertFalse(salida.contains("ID del estudiante:"));
        assertTrue(salida.contains("Clases de Valentina Gómez (ID: 1003):"));
        assertTrue(salida.contains("Programación I"));
        assertTrue(salida.contains("Bases de Datos"));
        assertTrue(salida.contains("Inglés Técnico"));
    }

    @Test
    void mantieneAlineadasLasColumnasDeProfesoresClasesYEstudiantes() {
        ByteArrayOutputStream contenido = new ByteArrayOutputStream();
        UniversidadService servicio = new UniversidadService(DatosIniciales.crearUniversidadInicial());
        MenuConsola menu = new MenuConsola(
                servicio,
                new Scanner("1\n2\n0\n5\n1\n6\n"),
                new PrintStream(contenido, true, StandardCharsets.UTF_8));

        menu.ejecutar();

        String salida = contenido.toString(StandardCharsets.UTF_8);
        List<String> filasProfesores = salida.lines()
                .filter(linea -> linea.matches("\\d+\\. .*\\| Tipo:.*"))
                .toList();
        List<String> filasClases = salida.lines()
                .filter(linea -> linea.matches("\\d+\\. .*\\| Aula:.*"))
                .toList();
        List<String> filasEstudiantes = salida.lines()
                .filter(linea -> linea.matches("\\d+\\. .*\\| ID:.*"))
                .toList();

        assertEquals(4, filasProfesores.size());
        assertEquals(4, filasClases.size());
        assertEquals(6, filasEstudiantes.size());
        assertColumnasAlineadas(filasProfesores, 3);
        assertColumnasAlineadas(filasClases, 3);
        assertColumnasAlineadas(filasEstudiantes, 1);
    }

    private static void assertColumnasAlineadas(List<String> filas, int cantidadSeparadores) {
        for (int separador = 1; separador <= cantidadSeparadores; separador++) {
            int posicionEsperada = posicionDelSeparador(filas.get(0), separador);
            for (String fila : filas) {
                assertEquals(posicionEsperada, posicionDelSeparador(fila, separador));
            }
        }
    }

    private static int posicionDelSeparador(String fila, int numeroSeparador) {
        int posicionInicial = 0;
        int posicion = -1;
        for (int indice = 0; indice < numeroSeparador; indice++) {
            posicion = fila.indexOf('|', posicionInicial);
            posicionInicial = posicion + 1;
        }
        return posicion;
    }
}
