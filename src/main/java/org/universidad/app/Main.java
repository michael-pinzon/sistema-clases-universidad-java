package org.universidad.app;

import org.universidad.service.DatosIniciales;
import org.universidad.service.UniversidadService;
import org.universidad.ui.MenuConsola;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UniversidadService servicio = new UniversidadService(DatosIniciales.crearUniversidadInicial());
        try (Scanner scanner = new Scanner(System.in)) {
            new MenuConsola(servicio, scanner, System.out).ejecutar();
        }
    }
}
