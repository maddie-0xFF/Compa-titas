package com.compatitas.dao;

import com.compatitas.excepciones.DatosInvalidosException;
import com.compatitas.excepciones.PersistenciaException;
import com.compatitas.modelo.SolicitudAdopcion;
import java.sql.*;
import java.util.ArrayList;

public class SolicitudDAOImpl implements ISolicitudDAO {

    @Override
    public SolicitudAdopcion insertar(SolicitudAdopcion solicitud) throws PersistenciaException {
        String sql = "INSERT INTO solicitudes_adopcion (nombre_interesado, telefono, email, mensaje, id_mascota) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getInstancia().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, solicitud.getNombreInteresado());
            ps.setString(2, solicitud.getTelefono());
            ps.setString(3, solicitud.getEmail());
            ps.setString(4, solicitud.getMensaje());
            ps.setInt(5, solicitud.getIdMascota());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    solicitud.setIdSolicitud(rs.getInt(1));
                }
            }
            return solicitud;

        } catch (SQLException e) {
            throw new PersistenciaException("Error SQL al registrar la solicitud de adopción: " + e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<SolicitudAdopcion> listarTodas() throws PersistenciaException, DatosInvalidosException {
        ArrayList<SolicitudAdopcion> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes_adopcion ORDER BY id_solicitud ASC";

        try (Connection conn = ConexionBD.getInstancia().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SolicitudAdopcion s = new SolicitudAdopcion(
                    rs.getInt("id_solicitud"),
                    rs.getString("nombre_interesado"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("mensaje"),
                    rs.getInt("id_mascota")
                );
                lista.add(s);
            }
            return lista;

        } catch (SQLException e) {
            throw new PersistenciaException("Error SQL al listar las solicitudes: " + e.getMessage(), e);
        }
    }
}