package com.compatitas.modelo;

import com.compatitas.excepciones.DatosInvalidosException;

public class Gato extends Mascota {
    private boolean requiereRedProteccion;

    public Gato(int idMascota, String nombre, int edad, String estado, String descripcion, int idAgente, boolean requiereRedProteccion)
            throws DatosInvalidosException {
        super(idMascota, nombre, edad, estado, descripcion, idAgente);
        this.requiereRedProteccion = requiereRedProteccion;
    }

    @Override
    public String getEspecie() {
        return "Gato";
    }

    @Override
    public String obtenerCuidadosEspeciales() {
        String detalleRed = requiereRedProteccion ? "Requiere red de protección en ventanas/balcones" : "Apto para vivienda sin red";
        return "Especie: Gato - " + detalleRed + ".";
    }

    @Override
    public String getDatosCompletos() {
        return super.getDatosCompletos() + " | " + obtenerCuidadosEspeciales();
    }

    public boolean isRequiereRedProteccion() { return requiereRedProteccion; }
}