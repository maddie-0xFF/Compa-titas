public class Mascota {
    public static final String ESTADO_EN_RECUPERACION = "En recuperación";
    public static final String ESTADO_ADOPTABLE = "Adoptable";

    private int idMascota;
    private String nombre;
    private String estado; 
    private String descripcion;

    public Mascota(int idMascota, String nombre, String estado, String descripcion) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        validarEstado(estado);
        this.estado = estado;
        this.descripcion = descripcion;
    }

    // Método que representa el CU03 - Actualizar estado
    public void actualizarEstado(String nuevoEstado) {
        validarEstado(nuevoEstado);
        this.estado = nuevoEstado;
        System.out.println("El estado de " + this.nombre + " ha cambiado a: " + this.estado);
        
        // NOTA DEL PROTOTIPO: 
        // Acá espero en un momento agregar la función de conexión JDBC 
        // para ejecutar el UPDATE en la base de datos MySQL, 
        // tal como menciono en la sección de persistencia del informe.
    }

    // Getters y Setters
    public int getIdMascota() { return idMascota; }
    public String getNombre() { return nombre; }
    public String getEstado() { return estado; }
    public String getDescripcion() { return descripcion; }

    private void validarEstado(String estado) {
        if (!ESTADO_EN_RECUPERACION.equals(estado) && !ESTADO_ADOPTABLE.equals(estado)) {
            throw new IllegalArgumentException("Estado no válido: " + estado);
        }
    }
}