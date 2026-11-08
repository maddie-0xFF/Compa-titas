package com.compatitas.modelo;

import com.compatitas.excepciones.DatosInvalidosException;

public class AgenteVoluntario {
    private int idAgente;
    private String nombre;
    private String email;
    private String contrasena;

    public AgenteVoluntario(int idAgente, String nombre, String email, String contrasena) {
        this.idAgente = idAgente;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
    }

    public boolean iniciarSesion(String email, String pass) {
        return this.email.equalsIgnoreCase(email) && this.contrasena.equals(pass);
    }

    // Polimorfismo por Sobrecarga 1: Crear instancia de Perro (el ID definitivo lo asigna MySQL)
    public Perro registrarNuevaMascota(String nombre, int edad, String estado, String descripcion, String tamanio)
            throws DatosInvalidosException {
        System.out.println("El agente " + this.nombre + " está preparando el registro del perro " + nombre + "...");
        return new Perro(0, nombre, edad, estado, descripcion, this.idAgente, tamanio);
    }

    // Polimorfismo por Sobrecarga 2: Crear instancia de Gato (el ID definitivo lo asigna MySQL)
    public Gato registrarNuevaMascota(String nombre, int edad, String estado, String descripcion, boolean requiereRed)
            throws DatosInvalidosException {
        System.out.println("El agente " + this.nombre + " está preparando el registro del gato " + nombre + "...");
        return new Gato(0, nombre, edad, estado, descripcion, this.idAgente, requiereRed);
    }

    // Getters
    public int getIdAgente() { return idAgente; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getContrasena() { return contrasena; }
}