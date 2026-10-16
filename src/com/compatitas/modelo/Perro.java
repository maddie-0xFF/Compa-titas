package com.compatitas.modelo;

public class Perro extends Mascota {
    private String tamanio; // Ej: Pequeño, Mediano, Grande

    public Perro(int idMascota, String nombre, int edad, String estado, String descripcion, int idAgente, String tamanio) {
        super(idMascota, nombre, edad, estado, descripcion, idAgente);
        this.tamanio = tamanio;
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