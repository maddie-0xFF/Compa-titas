import com.compatitas.modelo.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    // Estructuras de datos: Lista para el catálogo y Cola (FIFO) para las solicitudes
    private static List<Mascota> catalogoMascotas = new ArrayList<>();
    private static Queue<SolicitudAdopcion> colaSolicitudes = new LinkedList<>();
    private static int siguienteIdSolicitud = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Iniciando Prototipo Operacional Compa-titas ---");

        // Instanciación del Agente Voluntario
        AgenteVoluntario agente = new AgenteVoluntario(1, "Ana Rescatista", "ana@ejemplo.com", "hash1234");

        // Carga inicial de mascotas de prueba (Polimorfismo: Perro y Gato en List<Mascota>)
        catalogoMascotas.add(agente.registrarNuevaMascota(
            "Pipo", 2, Mascota.ESTADO_EN_RECUPERACION, "Perrito mestizo encontrado con pata lastimada.", "Mediano"
        ));
        catalogoMascotas.add(agente.registrarNuevaMascota(
            "Luna", 1, Mascota.ESTADO_ADOPTABLE, "Mestiza, muy juguetona.", "Pequeño"
        ));
        catalogoMascotas.add(agente.registrarNuevaMascota(
            "Milo", 5, Mascota.ESTADO_EN_RECUPERACION, "Gato rubio, tranquilo, requiere dieta especial.", true
        ));

        int opcion = -1;

        // Estructura repetitiva do-while para mantener activo el menú de selección
        do {
            System.out.println("\n==================================================");
            System.out.println("          MENÚ DE GESTIÓN - COMPA-TITAS           ");
            System.out.println("          Agente activo: " + agente.getNombre());
            System.out.println("==================================================");
            System.out.println("1. Registrar nueva mascota (CU02)");
            System.out.println("2. Listar catálogo de mascotas");
            System.out.println("3. Actualizar estado de una mascota");
            System.out.println("4. Buscar mascota por ID (Algoritmo de búsqueda)");
            System.out.println("5. Ordenar catálogo por edad (Algoritmo de ordenación)");
            System.out.println("6. Enviar solicitud de adopción (CU05)");
            System.out.println("7. Procesar siguiente solicitud en cola (FIFO)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            // Tratamiento y manejo de excepciones
            try {
                opcion = Integer.parseInt(scanner.nextLine());

                // Estructura condicional múltiple switch-case
                switch (opcion) {
                    case 1:
                        menuRegistrarMascota(scanner, agente);
                        break;
                    case 2:
                        listarCatalogo();
                        break;
                    case 3:
                        menuActualizarEstado(scanner);
                        break;
                    case 4:
                        menuBuscarMascota(scanner);
                        break;
                    case 5:
                        ordenarMascotasPorEdad();
                        break;
                    case 6:
                        menuEnviarSolicitud(scanner);
                        break;
                    case 7:
                        procesarColaSolicitudes();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema Compa-titas. ¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("Opción no válida. Ingrese un número entre 0 y 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[Error de Entrada]: Debe ingresar un valor numérico entero.");
            } catch (IllegalArgumentException e) {
                System.out.println("[Error de Validación]: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("[Error Inesperado]: " + e.getMessage());
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static void menuRegistrarMascota(Scanner sc, AgenteVoluntario agente) {
        System.out.print("Seleccione especie (1 = Perro, 2 = Gato): ");
        int especie = Integer.parseInt(sc.nextLine());
        if (especie != 1 && especie != 2) {
            throw new IllegalArgumentException("Especie no válida. Debe seleccionar 1 (Perro) o 2 (Gato).");
        }

        System.out.print("Nombre de la mascota: ");
        String nombre = sc.nextLine();

        System.out.print("Edad en años (0 a 25): ");
        int edad = Integer.parseInt(sc.nextLine());

        System.out.print("Estado inicial (1 = En recuperación, 2 = Adoptable): ");
        int opEstado = Integer.parseInt(sc.nextLine());
        String estado = (opEstado == 2) ? Mascota.ESTADO_ADOPTABLE : Mascota.ESTADO_EN_RECUPERACION;

        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        Mascota nueva;
        if (especie == 1) {
            System.out.print("Tamaño (Pequeño / Mediano / Grande): ");
            String tamanio = sc.nextLine();
            nueva = agente.registrarNuevaMascota(nombre, edad, estado, descripcion, tamanio);
        } else {
            System.out.print("¿Requiere red de protección en balcones? (s/n): ");
            boolean requiereRed = sc.nextLine().trim().equalsIgnoreCase("s");
            nueva = agente.registrarNuevaMascota(nombre, edad, estado, descripcion, requiereRed);
        }

        catalogoMascotas.add(nueva);
    }

    private static void listarCatalogo() {
        if (catalogoMascotas.isEmpty()) {
            System.out.println("El catálogo de mascotas está vacío.");
            return;
        }
        System.out.println("\n--- CATÁLOGO ACTUAL DE MASCOTAS ---");
        for (Mascota m : catalogoMascotas) {
            // Polimorfismo: invoca el método sobrescrito según sea Perro o Gato
            System.out.println(m.getDatosCompletos());
        }
    }

    // Algoritmo de búsqueda secuencial por ID
    private static Mascota buscarPorId(int idBuscado) {
        for (int i = 0; i < catalogoMascotas.size(); i++) {
            if (catalogoMascotas.get(i).getIdMascota() == idBuscado) {
                return catalogoMascotas.get(i);
            }
        }
        return null;
    }

    private static void menuBuscarMascota(Scanner sc) {
        System.out.print("Ingrese el ID de la mascota a buscar: ");
        int id = Integer.parseInt(sc.nextLine());
        Mascota encontrada = buscarPorId(id);
        if (encontrada != null) {
            System.out.println("Resultado de la búsqueda:\n" + encontrada.getDatosCompletos());
        } else {
            System.out.println("No se encontró ninguna mascota con el ID: " + id);
        }
    }

    private static void menuActualizarEstado(Scanner sc) {
        System.out.print("Ingrese el ID de la mascota cuyo estado desea modificar: ");
        int id = Integer.parseInt(sc.nextLine());
        Mascota m = buscarPorId(id);
        if (m == null) {
            System.out.println("Mascota no encontrada.");
            return;
        }
        System.out.print("Seleccione nuevo estado (1 = En recuperación, 2 = Adoptable): ");
        int op = Integer.parseInt(sc.nextLine());
        String nuevoEstado = (op == 2) ? Mascota.ESTADO_ADOPTABLE : Mascota.ESTADO_EN_RECUPERACION;
        m.actualizarEstado(nuevoEstado);
    }

    // Algoritmo de ordenación (Método de Burbujeo por edad de menor a mayor)
    private static void ordenarMascotasPorEdad() {
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
        System.out.println("Catálogo ordenado exitosamente por edad (de menor a mayor):");
        listarCatalogo();
    }

    private static void menuEnviarSolicitud(Scanner sc) {
        System.out.print("Ingrese el ID de la mascota que desea adoptar: ");
        int idMascota = Integer.parseInt(sc.nextLine());
        Mascota m = buscarPorId(idMascota);

        if (m == null) {
            System.out.println("No existe una mascota con el ID ingresado.");
            return;
        }
        if (!Mascota.ESTADO_ADOPTABLE.equals(m.getEstado())) {
            System.out.println("La mascota " + m.getNombre() + " aún está '" + m.getEstado() + "' y no puede recibir solicitudes.");
            return;
        }

        System.out.print("Nombre del interesado: ");
        String nombre = sc.nextLine();
        System.out.print("Teléfono de contacto: ");
        String telefono = sc.nextLine();
        System.out.print("Email de contacto: ");
        String email = sc.nextLine();
        System.out.print("Mensaje: ");
        String mensaje = sc.nextLine();

        SolicitudAdopcion solicitud = new SolicitudAdopcion(
            siguienteIdSolicitud++, nombre, telefono, email, mensaje, m.getIdMascota()
        );
        solicitud.registrarSolicitud();
        colaSolicitudes.offer(solicitud); // Se agrega al final de la cola (FIFO)
    }

    private static void procesarColaSolicitudes() {
        if (colaSolicitudes.isEmpty()) {
            System.out.println("No hay solicitudes pendientes en la cola.");
            return;
        }
        SolicitudAdopcion atendida = colaSolicitudes.poll(); // Extrae la primera en llegar
        System.out.println("Atendiendo solicitud por orden de llegada (FIFO):");
        System.out.println(atendida.mostrarResumen());
        System.out.println("Solicitudes restantes en cola: " + colaSolicitudes.size());
    }
}