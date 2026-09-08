public class Main {
    public static void main(String[] args) {
        System.out.println("--- Iniciando Prototipo Compa-titas ---");

        AgenteVoluntario agente = new AgenteVoluntario(1, "Ana Rescatista", "ana@ejemplo.com");
        Mascota perrito = agente.registrarNuevaMascota(
            "Pipo",
            Mascota.ESTADO_EN_RECUPERACION,
            "Perrito mestizo encontrado con pata lastimada.");
        perrito.actualizarEstado(Mascota.ESTADO_ADOPTABLE);
    }
}

// Acá espero en un momento agregar el módulo web que genera 
// la galería visual para que el Visitante pueda ver a Pipo 
// y rellenar el formulario (CU04 y CU05) que menciono en el informe.
