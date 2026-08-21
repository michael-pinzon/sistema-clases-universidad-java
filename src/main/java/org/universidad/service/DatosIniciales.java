package org.universidad.service;

import org.universidad.model.Estudiante;
import org.universidad.model.Profesor;
import org.universidad.model.ProfesorMedioTiempo;
import org.universidad.model.ProfesorTiempoCompleto;
import org.universidad.model.Universidad;

import java.util.List;

public final class DatosIniciales {

    private DatosIniciales() {
    }

    public static Universidad crearUniversidadInicial() {
        Universidad universidad = new Universidad();
        UniversidadService servicio = new UniversidadService(universidad);

        Profesor anaMartinez = new ProfesorTiempoCompleto("Ana Martínez", 2500, 5);
        Profesor carlosRodriguez = new ProfesorTiempoCompleto("Carlos Rodríguez", 2800, 8);
        Profesor lauraGomez = new ProfesorMedioTiempo("Laura Gómez", 120, 12);
        Profesor miguelTorres = new ProfesorMedioTiempo("Miguel Torres", 150, 8);

        servicio.registrarProfesor(anaMartinez);
        servicio.registrarProfesor(carlosRodriguez);
        servicio.registrarProfesor(lauraGomez);
        servicio.registrarProfesor(miguelTorres);

        Estudiante sofiaRamirez = new Estudiante("Sofía Ramírez", 1001, 19);
        Estudiante danielPerez = new Estudiante("Daniel Pérez", 1002, 20);
        Estudiante valentinaGomez = new Estudiante("Valentina Gómez", 1003, 18);
        Estudiante andresMartinez = new Estudiante("Andrés Martínez", 1004, 21);
        Estudiante camilaTorres = new Estudiante("Camila Torres", 1005, 22);
        Estudiante nicolasHerrera = new Estudiante("Nicolás Herrera", 1006, 19);

        servicio.registrarEstudiante(sofiaRamirez);
        servicio.registrarEstudiante(danielPerez);
        servicio.registrarEstudiante(valentinaGomez);
        servicio.registrarEstudiante(andresMartinez);
        servicio.registrarEstudiante(camilaTorres);
        servicio.registrarEstudiante(nicolasHerrera);

        servicio.crearClase(
                "Programación I",
                "A-101",
                anaMartinez,
                List.of(sofiaRamirez, danielPerez, valentinaGomez));
        servicio.crearClase(
                "Bases de Datos",
                "B-202",
                carlosRodriguez,
                List.of(danielPerez, valentinaGomez, andresMartinez));
        servicio.crearClase(
                "Matemáticas Discretas",
                "C-303",
                lauraGomez,
                List.of(sofiaRamirez, andresMartinez, camilaTorres));
        servicio.crearClase(
                "Inglés Técnico",
                "D-404",
                miguelTorres,
                List.of(valentinaGomez, camilaTorres, nicolasHerrera));

        return universidad;
    }
}
