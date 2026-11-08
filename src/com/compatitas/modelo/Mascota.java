package com.compatitas.modelo;

import com.compatitas.excepciones.DatosInvalidosException;

public abstract class Mascota {
    public static final String ESTADO_EN_RECUPERACION = "En recuperación";
    public static final String ESTADO_ADOPTABLE = "Adoptable";

    // Estructuras de datos estáticas (Arreglos fijos complementarios al ArrayList)
    public static final String[] ESTADOS_VALIDOS = {
        ESTADO_EN_RECUPERACION,
        ESTADO_ADOPTABLE
    };

    public static final String[] ESPECIES_VALIDAS = {
        "Perro",
        "Gato"
    };

    protected int idMascota;
    protected String nombre;
    protected int edad;
    protected String estado;
    protected String descripcion;
    protected int idAgente;

    public Mascota(int idMascota, String nombre, int edad, String estado, String descripcion, int idAgente)
            throws DatosInvalidosException {
        validarNombre(nombre);
        validarEdad(edad);
        validarEstado(estado);
        this.idMascota = idMascota;
        this.nombre = nombre.trim();
        this.edad = edad;
        this.estado = estado;
        this.descripcion = descripcion;
        this.idAgente = idAgente;
    }

    public static void validarNombre(String nombre) throws DatosInvalidosException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre de la mascota no puede estar vacío.");
        }
    }

    public static void validarEdad(int edad) throws DatosInvalidosException {
        if (edad < 0 || edad > 25) {
            throw new DatosInvalidosException("Edad no permitida (" + edad + "). Debe estar entre 0 y 25 años.");
        }
    }

    public static void validarEstado(String estado) throws DatosInvalidosException {
        boolean encontrado = false;
        // Recorrido del arreglo fijo ESTADOS_VALIDOS
        for (int i = 0; i < ESTADOS_VALIDOS.length; i++) {
            if (ESTADOS_VALIDOS[i].equalsIgnoreCase(estado)) {
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new DatosInvalidosException("Estado no válido: " + estado);
        }
    }

    public void actualizarEstado(String nuevoEstado) throws DatosInvalidosException {
        validarEstado(nuevoEstado);
        this.estado = nuevoEstado;
    }

    // Métodos abstractos (Abstracción y Polimorfismo)
    public abstract String getEspecie();
    public abstract String obtenerCuidadosEspeciales();

    public String getDatosCompletos() {
        return "[ID: " + idMascota + "] Nombre: " + nombre + " | Edad: " + edad +
               " años | Estado: " + estado + " | Descripción: " + descripcion;
    }

    // Getters y Setters
    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getEstado() { return estado; }
    public String getDescripcion() { return descripcion; }
    public int getIdAgente() { return idAgente; }
}