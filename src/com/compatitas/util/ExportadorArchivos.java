package com.compatitas.util;

import com.compatitas.modelo.Mascota;
import com.compatitas.modelo.SolicitudAdopcion;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportadorArchivos {

    public static String exportarReporte(List<Mascota> mascotas, List<SolicitudAdopcion> solicitudes) throws IOException {
        String nombreArchivo = "reporte_compatitas.txt";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("=========================================================================");
            writer.newLine();
            writer.write("               REPORTE OPERACIONAL - SISTEMA COMPA-TITAS                 ");
            writer.newLine();
            writer.write("               Fecha de generación: " + LocalDateTime.now().format(formato));
            writer.newLine();
            writer.write("=========================================================================");
            writer.newLine();
            writer.newLine();

            writer.write("--- 1. CATÁLOGO DE MASCOTAS PERSISTIDAS EN MYSQL (" + mascotas.size() + ") ---");
            writer.newLine();
            for (Mascota m : mascotas) {
                writer.write(m.getDatosCompletos());
                writer.newLine();
            }

            writer.newLine();
            writer.write("--- 2. SOLICITUDES DE ADOPCIÓN REGISTRADAS EN MYSQL (" + solicitudes.size() + ") ---");
            writer.newLine();
            for (SolicitudAdopcion s : solicitudes) {
                writer.write(s.mostrarResumen());
                writer.newLine();
            }
            writer.write("=========================================================================");
            writer.newLine();
        }
        return nombreArchivo;
    }
}