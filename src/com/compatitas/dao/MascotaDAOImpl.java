package com.compatitas.dao;

import com.compatitas.excepciones.DatosInvalidosException;
import com.compatitas.excepciones.PersistenciaException;
import com.compatitas.modelo.Gato;
import com.compatitas.modelo.Mascota;
import com.compatitas.modelo.Perro;
import java.sql.*;
import java.util.ArrayList;

public class MascotaDAOImpl implements IMascotaDAO {

    @Override
    public Mascota insertar(Mascota mascota) throws PersistenciaException {
        String sql = "INSERT INTO mascotas (nombre, edad, especie, estado, descripcion, tamanio, requiere_red, id_agente) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getInstancia().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, mascota.getNombre());
            ps.setInt(2, mascota.getEdad());
            ps.setString(3, mascota.getEspecie());
            ps.setString(4, mascota.getEstado());
            ps.setString(5, mascota.getDescripcion());

            if (mascota instanceof Perro perro) {
                ps.setString(6, perro.getTamanio());
                ps.setNull(7, Types.BOOLEAN);
            } else if (mascota instanceof Gato gato) {
                ps.setNull(6, Types.VARCHAR);
                ps.setBoolean(7, gato.isRequiereRedProteccion());
            }

            ps.setInt(8, mascota.getIdAgente());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    mascota.setIdMascota(rs.getInt(1));
                }
            }
            return mascota;

        } catch (SQLException e) {
            throw new PersistenciaException("Error SQL al insertar la mascota en MySQL: " + e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Mascota> listarTodas() throws PersistenciaException, DatosInvalidosException {
        ArrayList<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascotas ORDER BY id_mascota ASC";

        try (Connection conn = ConexionBD.getInstancia().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearMascota(rs));
            }
            return lista;

        } catch (SQLException e) {
            throw new PersistenciaException("Error SQL al consultar el catálogo de mascotas: " + e.getMessage(), e);
        }
    }

    @Override
    public Mascota buscarPorId(int idMascota) throws PersistenciaException, DatosInvalidosException {
        String sql = "SELECT * FROM mascotas WHERE id_mascota = ?";

        try (Connection conn = ConexionBD.getInstancia().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMascota);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearMascota(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            throw new PersistenciaException("Error SQL al buscar la mascota ID " + idMascota + ": " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizarEstado(int idMascota, String nuevoEstado) throws PersistenciaException {
        String sql = "UPDATE mascotas SET estado = ? WHERE id_mascota = ?";

        try (Connection conn = ConexionBD.getInstancia().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idMascota);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new PersistenciaException("Error SQL al actualizar el estado de la mascota: " + e.getMessage(), e);
        }
    }

    private Mascota mapearMascota(ResultSet rs) throws SQLException, DatosInvalidosException {
        int id = rs.getInt("id_mascota");
        String nombre = rs.getString("nombre");
        int edad = rs.getInt("edad");
        String especie = rs.getString("especie");
        String estado = rs.getString("estado");
        String descripcion = rs.getString("descripcion");
        int idAgente = rs.getInt("id_agente");

        if ("Perro".equalsIgnoreCase(especie)) {
            String tamanio = rs.getString("tamanio");
            return new Perro(id, nombre, edad, estado, descripcion, idAgente, tamanio);
        } else {
            boolean requiereRed = rs.getBoolean("requiere_red");
            return new Gato(id, nombre, edad, estado, descripcion, idAgente, requiereRed);
        }
    }
}