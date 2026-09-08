public class AgenteVoluntario {
    private static int siguienteIdMascota = 1;
    private int idAgente;
    private String nombre;
    private String email;

    public AgenteVoluntario(int idAgente, String nombre, String email) {
        this.idAgente = idAgente;
        this.nombre = nombre;
        this.email = email;
    }

    // Método que representa parte del CU02 - Gestionar tarjeta
    public Mascota registrarNuevaMascota(String nombre, String estado, String descripcion) {
        System.out.println("El agente " + this.nombre + " está registrando a " + nombre);
        
        // NOTA DEL PROTOTIPO: 
        // En la versión final, este método recibirá los datos del formulario web 
        // y ejecutará un INSERT INTO en la tabla 'Mascota' de MySQL.
        
        return new Mascota(siguienteIdMascota++, nombre, estado, descripcion); // Retorno simulado para el prototipo
    }

    public int getIdAgente() { return idAgente; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
}