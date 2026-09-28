import com.compatitas.modelo.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Iniciando Prototipo Compa-titas ---");

        // Agregamos la contraseña al instanciar el agente
        AgenteVoluntario agente = new AgenteVoluntario(1, "Ana Rescatista", "ana@ejemplo.com", "hash1234");
        
        try {
            // Agregamos el parámetro de la edad (ej: 2 años)
            Mascota perrito = agente.registrarNuevaMascota(
                "Pipo",
                2, 
                Mascota.ESTADO_EN_RECUPERACION,
                "Perrito mestizo encontrado con pata lastimada."
            );
            
            perrito.actualizarEstado(Mascota.ESTADO_ADOPTABLE);
            
            // Simulamos la creación de una solicitud de adopción
            SolicitudAdopcion solicitud = new SolicitudAdopcion(
                1, "Juan Perez", "3515551234", "juan@email.com", "Me encantaría adoptar a Pipo", perrito.getIdMascota()
            );
            solicitud.registrarSolicitud();

        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
        }
    }
}