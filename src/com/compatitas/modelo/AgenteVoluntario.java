package com.compatitas.modelo;

public class AgenteVoluntario {
    private static int siguienteIdMascota = 1;
    
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

    // Sobrecarga 1: Registrar un Perro (CU02)
    public Mascota registrarNuevaMascota(String nombre, int edad, String estado, String descripcion, String tamanio) {
        System.out.println("El agente " + this.nombre + " está registrando al perro " + nombre + "...");
        Perro nuevoPerro = new Perro(siguienteIdMascota++, nombre, edad, estado, descripcion, this.idAgente, tamanio);
        System.out.println("Mascota registrada exitosamente.");
        return nuevoPerro;
    }

    // Sobrecarga 2: Registrar un Gato (CU02)
    public Mascota registrarNuevaMascota(String nombre, int edad, String estado, String descripcion, boolean requiereRed) {
        System.out.println("El agente " + this.nombre + " está registrando al gato " + nombre + "...");
        Gato nuevoGato = new Gato(siguienteIdMascota++, nombre, edad, estado, descripcion, this.idAgente, requiereRed);
        System.out.println("Mascota registrada exitosamente.");
        return nuevoGato;
    }

    // Getters
    public int getIdAgente() { return idAgente; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getContrasena() { return contrasena; }
}