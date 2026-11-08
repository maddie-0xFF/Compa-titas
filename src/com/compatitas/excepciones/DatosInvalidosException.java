package com.compatitas.excepciones;

/**
 * Excepción personalizada para errores de validación en las reglas de negocio
 * del dominio Compa-titas (ej. particiones de equivalencia de edad o estados).
 */
public class DatosInvalidosException extends Exception {

    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}