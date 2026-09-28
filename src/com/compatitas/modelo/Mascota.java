package com.compatitas.modelo;

public class Mascota {
    public static final String ESTADO_EN_RECUPERACION = "En recuperación";
    public static final String ESTADO_ADOPTABLE = "Adoptable";

    private int idMascota;
    private String nombre;
    private int edad; // Agregado para el caso de prueba CPF02
    private String estado; 
    private String descripcion;
    private int idAgente; // Clave foránea

    public Mascota(int idMascota, String nombre, int edad, String estado, String descripcion, int idAgente) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        validarEdad(edad);
        this.edad = edad;
        validarEstado(estado);
        this.estado = estado;
        this.descripcion = descripcion;
        this.idAgente = idAgente;
    }

    public void validarEdad(int edad) {
        if (edad < 0 || edad > 25) {
            throw new IllegalArgumentException("Edad no permitida.");
        }
    }

    public void actualizarEstado(String nuevoEstado) {
        validarEstado(nuevoEstado);
        this.estado = nuevoEstado;
        System.out.println("El estado de " + this.nombre + " ha cambiado a: " + this.estado);
    }

    // Getters
    public int getIdMascota() { return idMascota; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getEstado() { return estado; }
    public String getDescripcion() { return descripcion; }
    public int getIdAgente() { return idAgente; }

    private void validarEstado(String estado) {
        if (!ESTADO_EN_RECUPERACION.equals(estado) && !ESTADO_ADOPTABLE.equals(estado)) {
            throw new IllegalArgumentException("Estado no válido: " + estado);
        }
    }
}