package com.compatitas.modelo;

import com.compatitas.excepciones.DatosInvalidosException;

public class SolicitudAdopcion {
    private int idSolicitud;
    private String nombreInteresado;
    private String telefono;
    private String email;
    private String mensaje;
    private int idMascota;

    public SolicitudAdopcion(int idSolicitud, String nombreInteresado, String telefono, String email, String mensaje, int idMascota)
            throws DatosInvalidosException {
        this.idSolicitud = idSolicitud;
        this.nombreInteresado = nombreInteresado;
        this.telefono = telefono;
        this.email = email;
        this.mensaje = mensaje;
        this.idMascota = idMascota;
        validarDatos();
    }

    public void validarDatos() throws DatosInvalidosException {
        if (this.nombreInteresado == null || this.nombreInteresado.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre del interesado es obligatorio.");
        }
        if (this.email == null || !this.email.contains("@")) {
            throw new DatosInvalidosException("Debe ingresar un correo electrónico válido (con '@').");
        }
        if (this.telefono == null || this.telefono.trim().isEmpty()) {
            throw new DatosInvalidosException("El teléfono de contacto es obligatorio.");
        }
    }

    public String mostrarResumen() {
        return "Solicitud #" + idSolicitud + " | Interesado: " + nombreInteresado +
               " (" + email + ", Tel: " + telefono + ") | Mascota ID: " + idMascota +
               " | Mensaje: \"" + mensaje + "\"";
    }

    // Getters y Setters
    public int getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(int idSolicitud) { this.idSolicitud = idSolicitud; }
    public String getNombreInteresado() { return nombreInteresado; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getMensaje() { return mensaje; }
    public int getIdMascota() { return idMascota; }
}