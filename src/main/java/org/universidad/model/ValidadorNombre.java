package org.universidad.model;

import java.util.Objects;
import java.util.regex.Pattern;

public final class ValidadorNombre {

    private static final Pattern TEXTO_SOLO_LETRAS =
            Pattern.compile("\\p{L}+(?:\\s+\\p{L}+)*");
    private static final Pattern NOMBRE_COMPLETO =
            Pattern.compile("\\p{L}+(?:\\s+\\p{L}+)+");

    private ValidadorNombre() {
    }

    public static String validar(String nombre, String mensajeError) {
        Objects.requireNonNull(nombre, mensajeError);
        String nombreNormalizado = normalizar(nombre);
        if (!NOMBRE_COMPLETO.matcher(nombreNormalizado).matches()) {
            throw new IllegalArgumentException(mensajeError);
        }
        return nombreNormalizado;
    }

    public static String validarTextoSoloLetras(String texto, String mensajeError) {
        Objects.requireNonNull(texto, mensajeError);
        String textoNormalizado = normalizar(texto);
        if (!TEXTO_SOLO_LETRAS.matcher(textoNormalizado).matches()) {
            throw new IllegalArgumentException(mensajeError);
        }
        return textoNormalizado;
    }

    public static boolean esNombreCompletoValido(String nombre) {
        if (nombre == null) {
            return false;
        }
        return NOMBRE_COMPLETO.matcher(normalizar(nombre)).matches();
    }

    public static boolean esTextoSoloLetras(String texto) {
        if (texto == null) {
            return false;
        }
        return TEXTO_SOLO_LETRAS.matcher(normalizar(texto)).matches();
    }

    private static String normalizar(String nombre) {
        return nombre.trim().replaceAll("\\s+", " ");
    }
}
