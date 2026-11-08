package com.compatitas.dao;

import com.compatitas.excepciones.DatosInvalidosException;
import com.compatitas.excepciones.PersistenciaException;
import com.compatitas.modelo.Mascota;
import java.util.ArrayList;

/**
 * Interfaz que define el contrato de persistencia (Patrón DAO) para la jerarquía Mascota.
 */
public interface IMascotaDAO {
    Mascota insertar(Mascota mascota) throws PersistenciaException;
    ArrayList<Mascota> listarTodas() throws PersistenciaException, DatosInvalidosException;
    Mascota buscarPorId(int idMascota) throws PersistenciaException, DatosInvalidosException;
    boolean actualizarEstado(int idMascota, String nuevoEstado) throws PersistenciaException;
}