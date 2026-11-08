package com.compatitas.dao;

import com.compatitas.excepciones.DatosInvalidosException;
import com.compatitas.excepciones.PersistenciaException;
import com.compatitas.modelo.SolicitudAdopcion;
import java.util.ArrayList;

public interface ISolicitudDAO {
    SolicitudAdopcion insertar(SolicitudAdopcion solicitud) throws PersistenciaException;
    ArrayList<SolicitudAdopcion> listarTodas() throws PersistenciaException, DatosInvalidosException;
}