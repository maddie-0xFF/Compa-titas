package com.compatitas.modelo;

import com.compatitas.excepciones.DatosInvalidosException;

public class Perro extends Mascota {
    // Arreglo estático de tamaños permitidos para perros
    public static final String[] TAMANIOS_VALIDOS = {"Pequeño", "Mediano", "Grande"};

    private String tamanio;

    public Perro(int idMascota, String nombre, int edad, String estado, String descripcion, int idAgente, String tamanio)
            throws DatosInvalidosException {
        super(idMascota, nombre, edad, estado, descripcion, idAgente);
        validarTamanio(tamanio);
        this.tamanio = tamanio;
    }

    public static void validarTamanio(String tamanio) throws DatosInvalidosException {
        for (int i = 0; i < TAMANIOS_VALIDOS.length; i++) {
            if (TAMANIOS_VALIDOS[i].equalsIgnoreCase(tamanio)) {
                return;
            }
        }
        throw new DatosInvalidosException("Tamaño de perro no válido: " + tamanio + ". Use Pequeño, Mediano o Grande.");
    }

    @Override
    public String getEspecie() {
        return "Perro";
    }

    @Override
    public String obtenerCuidadosEspeciales() {
        return "Especie: Perro (Tamaño: " + tamanio + ") - Requiere paseos diarios.";
    }

    @Override
    public String getDatosCompletos() {
        return super.getDatosCompletos() + " | " + obtenerCuidadosEspeciales();
    }

    public String getTamanio() { return tamanio; }
}