package com.compatitas.excepciones;

/**
 * Excepción personalizada para encapsular errores de acceso y persistencia
 * de datos en la base de datos MySQL (JDBC / SQLException).
 */
public class PersistenciaException extends Exception {

    public PersistenciaException(String mensaje) {
        super(mensaje);
    }

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}