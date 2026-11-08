import com.compatitas.dao.IMascotaDAO;
import com.compatitas.dao.ISolicitudDAO;
import com.compatitas.dao.MascotaDAOImpl;
import com.compatitas.dao.SolicitudDAOImpl;
import com.compatitas.excepciones.DatosInvalidosException;
import com.compatitas.excepciones.PersistenciaException;
import com.compatitas.modelo.*;
import com.compatitas.util.ExportadorArchivos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    // Interfaces DAO para la persistencia en MySQL (Patrón DAO)
    private static final IMascotaDAO mascotaDAO = new MascotaDAOImpl();
    private static final ISolicitudDAO solicitudDAO = new SolicitudDAOImpl();

    // Estructura dinámica complementaria en memoria y Cola FIFO de atención
    private static ArrayList<Mascota> catalogoMascotas = new ArrayList<>();
    private static final Queue<SolicitudAdopcion> colaSolicitudes = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AgenteVoluntario agenteActivo = new AgenteVoluntario(1, "Ana Rescatista", "ana@compatitas.org", "hash1234");

        System.out.println("===============================================================");
        System.out.println("   INICIANDO PROTOTIPO OPERACIONAL COMPA-TITAS (MySQL + DAO)   ");
        System.out.println("===============================================================");

        // Sincronización inicial desde la base de datos MySQL
        sincronizarConBaseDeDatos();

        int opcion = -1;
        do {
            mostrarMenu(agenteActivo);
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                switch (opcion) {
                    case 1 -> menuRegistrarMascota(scanner, agenteActivo);
                    case 2 -> listarCatalogoDesdeMySQL();
                    case 3 -> menuActualizarEstado(scanner);
                    case 4 -> menuBuscarMascota(scanner);
                    case 5 -> ordenarMascotasPorEdad();
                    case 6 -> menuEnviarSolicitud(scanner);
                    case 7 -> procesarColaSolicitudes();
                    case 8 -> exportarReporteArchivo();
                    case 0 -> System.out.println("Cerrando conexión y saliendo del sistema Compa-titas...");
                    default -> System.out.println("[Aviso]: Opción no válida. Seleccione entre 0 y 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[Error de Entrada]: Debe ingresar un número entero válido.");
            } catch (DatosInvalidosException e) {
                System.out.println("[Error de Validación - DatosInvalidosException]: " + e.getMessage());
            } catch (PersistenciaException e) {
                System.out.println("[Error de Base de Datos - PersistenciaException]: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("[Error de Archivo - IOException]: No se pudo generar el reporte: " + e.getMessage());
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static void sincronizarConBaseDeDatos() {
        try {
            catalogoMascotas = mascotaDAO.listarTodas();
            ArrayList<SolicitudAdopcion> solicitudesBD = solicitudDAO.listarTodas();
            colaSolicitudes.clear();
            colaSolicitudes.addAll(solicitudesBD);
            System.out.println("[Conexión MySQL OK]: " + catalogoMascotas.size() +
                               " mascotas y " + colaSolicitudes.size() + " solicitudes cargadas desde compatitas_db.");
        } catch (PersistenciaException | DatosInvalidosException e) {
            System.out.println("[Advertencia Inicial]: " + e.getMessage());
        }
    }

    private static void mostrarMenu(AgenteVoluntario agente) {
        System.out.println("\n---------------- MENÚ DE GESTIÓN - COMPA-TITAS ----------------");
        System.out.println("Agente activo: " + agente.getNombre() + " (Conectado a MySQL: compatitas_db)");
        System.out.println("1. Registrar nueva mascota en MySQL (CU02)");
        System.out.println("2. Consultar y listar catálogo de mascotas desde MySQL (CU04)");
        System.out.println("3. Actualizar estado de una mascota en MySQL (CU03)");
        System.out.println("4. Buscar mascota por ID (Consulta puntual)");
        System.out.println("5. Ordenar catálogo en memoria por edad (Algoritmo Burbujeo)");
        System.out.println("6. Enviar solicitud de adopción y guardar en MySQL (CU05)");
        System.out.println("7. Procesar siguiente solicitud en cola (FIFO)");
        System.out.println("8. Exportar reporte completo a archivo de texto (.txt)");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // 1. Alta de mascota usando arreglos fijos [] para opciones y persistiendo en MySQL
    private static void menuRegistrarMascota(Scanner scanner, AgenteVoluntario agente)
            throws DatosInvalidosException, PersistenciaException {

        System.out.println("\n--- REGISTRO DE NUEVA MASCOTA ---");
        // Uso del arreglo fijo ESPECIES_VALIDAS
        System.out.print("Seleccione especie (");
        for (int i = 0; i < Mascota.ESPECIES_VALIDAS.length; i++) {
            System.out.print((i + 1) + "=" + Mascota.ESPECIES_VALIDAS[i] + (i == 0 ? ", " : ""));
        }
        System.out.print("): ");
        int idxEspecie = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (idxEspecie < 0 || idxEspecie >= Mascota.ESPECIES_VALIDAS.length) {
            throw new DatosInvalidosException("Opción de especie fuera de rango.");
        }

        System.out.print("Nombre de la mascota: ");
        String nombre = scanner.nextLine();

        System.out.print("Edad en años (0 a 25): ");
        int edad = Integer.parseInt(scanner.nextLine().trim());

        // Uso del arreglo fijo ESTADOS_VALIDOS
        System.out.print("Estado inicial (");
        for (int i = 0; i < Mascota.ESTADOS_VALIDOS.length; i++) {
            System.out.print((i + 1) + "=" + Mascota.ESTADOS_VALIDOS[i] + (i == 0 ? ", " : ""));
        }
        System.out.print("): ");
        int idxEstado = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (idxEstado < 0 || idxEstado >= Mascota.ESTADOS_VALIDOS.length) {
            throw new DatosInvalidosException("Opción de estado inicial no válida.");
        }
        String estadoElegido = Mascota.ESTADOS_VALIDOS[idxEstado];

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        Mascota nuevaMascota;
        if (idxEspecie == 0) {
            // Uso del arreglo fijo TAMANIOS_VALIDOS de la subclase Perro
            System.out.print("Seleccione tamaño (");
            for (int i = 0; i < Perro.TAMANIOS_VALIDOS.length; i++) {
                System.out.print((i + 1) + "=" + Perro.TAMANIOS_VALIDOS[i] +
                                 (i < Perro.TAMANIOS_VALIDOS.length - 1 ? ", " : ""));
            }
            System.out.print("): ");
            int idxTam = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idxTam < 0 || idxTam >= Perro.TAMANIOS_VALIDOS.length) {
                throw new DatosInvalidosException("Opción de tamaño de perro no válida.");
            }
            String tamanioElegido = Perro.TAMANIOS_VALIDOS[idxTam];
            nuevaMascota = agente.registrarNuevaMascota(nombre, edad, estadoElegido, descripcion, tamanioElegido);
        } else {
            System.out.print("¿Requiere red de protección en balcones? (s/n): ");
            boolean requiereRed = scanner.nextLine().trim().equalsIgnoreCase("s");
            nuevaMascota = agente.registrarNuevaMascota(nombre, edad, estadoElegido, descripcion, requiereRed);
        }

        // Persistencia en MySQL mediante el DAO y sincronización con el ArrayList
        mascotaDAO.insertar(nuevaMascota);
        catalogoMascotas.add(nuevaMascota);
        System.out.println("[Éxito MySQL]: Mascota registrada en la base de datos con ID asignado: " + nuevaMascota.getIdMascota());
    }

    // 2. Consulta general desde MySQL volcada al ArrayList
    private static void listarCatalogoDesdeMySQL() throws PersistenciaException, DatosInvalidosException {
        catalogoMascotas = mascotaDAO.listarTodas();
        System.out.println("\n================ CATÁLOGO ACTUAL DE MASCOTAS (MYSQL) ================");
        if (catalogoMascotas.isEmpty()) {
            System.out.println("No hay mascotas registradas en la base de datos.");
            return;
        }
        for (Mascota m : catalogoMascotas) {
            System.out.println(m.getDatosCompletos());
        }
    }

    // 3. Actualización de registro en MySQL (UPDATE)
    private static void menuActualizarEstado(Scanner scanner)
            throws DatosInvalidosException, PersistenciaException {

        System.out.print("Ingrese el ID de la mascota cuyo estado desea modificar: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Mascota mascota = mascotaDAO.buscarPorId(id);
        if (mascota == null) {
            System.out.println("[Aviso]: No existe una mascota con el ID " + id + " en MySQL.");
            return;
        }

        System.out.print("Seleccione nuevo estado (1=" + Mascota.ESTADOS_VALIDOS[0] +
                         ", 2=" + Mascota.ESTADOS_VALIDOS[1] + "): ");
        int idxEstado = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (idxEstado < 0 || idxEstado >= Mascota.ESTADOS_VALIDOS.length) {
            throw new DatosInvalidosException("Selección de estado fuera de rango.");
        }
        String nuevoEstado = Mascota.ESTADOS_VALIDOS[idxEstado];

        mascota.actualizarEstado(nuevoEstado);
        if (mascotaDAO.actualizarEstado(id, nuevoEstado)) {
            System.out.println("[Éxito MySQL]: El estado de " + mascota.getNombre() + " se actualizó en la BD a: " + nuevoEstado);
            catalogoMascotas = mascotaDAO.listarTodas();
        }
    }

    // 4. Búsqueda por ID
    private static void menuBuscarMascota(Scanner scanner) throws PersistenciaException, DatosInvalidosException {
        System.out.print("Ingrese el ID de la mascota a buscar: ");
        int idBuscado = Integer.parseInt(scanner.nextLine().trim());

        Mascota encontrada = mascotaDAO.buscarPorId(idBuscado);
        if (encontrada != null) {
            System.out.println("Resultado de la consulta en MySQL:");
            System.out.println(encontrada.getDatosCompletos());
        } else {
            System.out.println("No se encontró ninguna mascota con el ID: " + idBuscado);
        }
    }

    // 5. Algoritmo de ordenación Burbujeo sobre el ArrayList
    private static void ordenarMascotasPorEdad() throws PersistenciaException, DatosInvalidosException {
        catalogoMascotas = mascotaDAO.listarTodas();
        int n = catalogoMascotas.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (catalogoMascotas.get(j).getEdad() > catalogoMascotas.get(j + 1).getEdad()) {
                    Mascota temp = catalogoMascotas.get(j);
                    catalogoMascotas.set(j, catalogoMascotas.get(j + 1));
                    catalogoMascotas.set(j + 1, temp);
                }
            }
        }
        System.out.println("\nCatálogo ordenado exitosamente por edad (de menor a mayor):");
        for (Mascota m : catalogoMascotas) {
            System.out.println(m.getDatosCompletos());
        }
    }

    // 6. Envío y persistencia de solicitud de adopción en MySQL
    private static void menuEnviarSolicitud(Scanner scanner)
            throws DatosInvalidosException, PersistenciaException {

        System.out.print("Ingrese el ID de la mascota que desea adoptar: ");
        int idMascota = Integer.parseInt(scanner.nextLine().trim());

        Mascota mascota = mascotaDAO.buscarPorId(idMascota);
        if (mascota == null) {
            throw new DatosInvalidosException("No existe una mascota registrada con el ID " + idMascota + ".");
        }
        if (!Mascota.ESTADO_ADOPTABLE.equalsIgnoreCase(mascota.getEstado())) {
            throw new DatosInvalidosException("La mascota " + mascota.getNombre() +
                    " aún está '" + mascota.getEstado() + "' y no puede recibir solicitudes.");
        }

        System.out.print("Nombre completo del interesado: ");
        String nombreInteresado = scanner.nextLine();
        System.out.print("Teléfono de contacto: ");
        String telefono = scanner.nextLine();
        System.out.print("Email de contacto: ");
        String email = scanner.nextLine();
        System.out.print("Mensaje para el agente: ");
        String mensaje = scanner.nextLine();

        SolicitudAdopcion nuevaSolicitud = new SolicitudAdopcion(0, nombreInteresado, telefono, email, mensaje, idMascota);
        solicitudDAO.insertar(nuevaSolicitud);
        colaSolicitudes.offer(nuevaSolicitud);

        System.out.println("[Éxito MySQL]: Solicitud registrada en BD con ID #" + nuevaSolicitud.getIdSolicitud() +
                           " de " + nombreInteresado + " para adoptar a " + mascota.getNombre() + ".");
    }

    // 7. Atención de cola FIFO
    private static void procesarColaSolicitudes() {
        if (colaSolicitudes.isEmpty()) {
            System.out.println("No hay solicitudes pendientes en la cola de atención.");
            return;
        }
        SolicitudAdopcion atendida = colaSolicitudes.poll();
        System.out.println("Atendiendo solicitud por orden de llegada (FIFO):");
        System.out.println(atendida.mostrarResumen());
        System.out.println("Solicitudes restantes en cola: " + colaSolicitudes.size());
    }

    // 8. Exportación de archivo de texto (Opcional sugerido para EFIP I)
    private static void exportarReporteArchivo() throws PersistenciaException, DatosInvalidosException, IOException {
        ArrayList<Mascota> mascotasBD = mascotaDAO.listarTodas();
        ArrayList<SolicitudAdopcion> solicitudesBD = solicitudDAO.listarTodas();
        String archivo = ExportadorArchivos.exportarReporte(mascotasBD, solicitudesBD);
        System.out.println("[Archivo Generado]: Se exportó el historial correctamente en '" + archivo + "'.");
    }
}