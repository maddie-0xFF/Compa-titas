package com.compatitas.modelo;

public class AgenteVoluntario {
    private static int siguienteIdMascota = 1;
    
    private int idAgente;
    private String nombre;
    private String email;
    private String contrasena; // Agregado para coincidir con la base de datos

    public AgenteVoluntario(int idAgente, String nombre, String email, String contrasena) {
        this.idAgente = idAgente;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
    }

    // Método actualizado para el CU02
    public Mascota registrarNuevaMascota(String nombre, int edad, String estado, String descripcion) {
        System.out.println("El agente " + this.nombre + " está registrando a " + nombre);
        return new Mascota(siguienteIdMascota++, nombre, edad, estado, descripcion, this.idAgente); 
    }

    // Getters
    public int getIdAgente() { return idAgente; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getContrasena() { return contrasena; }
}