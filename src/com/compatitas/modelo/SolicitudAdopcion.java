package com.compatitas.modelo;

public class SolicitudAdopcion {
    private int idSolicitud;
    private String nombreInteresado;
    private String telefono;
    private String email;
    private String mensaje;
    private int idMascota; // Clave foránea

    public SolicitudAdopcion(int idSolicitud, String nombreInteresado, String telefono, String email, String mensaje, int idMascota) {
        this.idSolicitud = idSolicitud;
        this.nombreInteresado = nombreInteresado;
        this.telefono = telefono;
        this.email = email;
        this.mensaje = mensaje;
        this.idMascota = idMascota;
    }

    public boolean registrarSolicitud() {
        System.out.println("Solicitud registrada con éxito de " + this.nombreInteresado + " para la mascota ID: " + this.idMascota);
        return true;
    }

    public boolean validarDatos() {
        return this.nombreInteresado != null && !this.nombreInteresado.isEmpty() &&
               this.email != null && !this.email.isEmpty();
    }

    // Getters
    public int getIdSolicitud() { return idSolicitud; }
    public String getNombreInteresado() { return nombreInteresado; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getMensaje() { return mensaje; }
    public int getIdMascota() { return idMascota; }
}