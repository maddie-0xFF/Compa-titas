package com.compatitas.dao;

import com.compatitas.excepciones.PersistenciaException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión con MySQL mediante JDBC.
 * Aplica el patrón de diseño Singleton para centralizar el punto de acceso.
 */
public class ConexionBD {
    private static ConexionBD instancia;

    private static final String URL = "jdbc:mysql://localhost:3306/compatitas_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = ""; // En XAMPP por defecto es vacío

    private ConexionBD() throws PersistenciaException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new PersistenciaException("No se encontró el driver JDBC de MySQL en la carpeta lib/.", e);
        }
    }

    public static ConexionBD getInstancia() throws PersistenciaException {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection obtenerConexion() throws PersistenciaException {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (SQLException e) {
            throw new PersistenciaException("Error al conectar con MySQL (verifique que XAMPP esté activo): " + e.getMessage(), e);
        }
    }
}