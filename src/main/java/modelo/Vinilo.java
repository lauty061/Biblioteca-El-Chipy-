package modelo;

public class Vinilo {
    private int id_vinilos;
    private String autor;
    private String nombre;
    private String canciones;
    private int anio;
    private boolean disponibilidad;
    private int cantidad;

    public Vinilo() {}

    public Vinilo(int id_vinilos, String autor, String nombre, String canciones, int anio, boolean disponibilidad, int cantidad) {
        this.id_vinilos = id_vinilos;
        this.autor = autor;
        this.nombre = nombre;
        this.canciones = canciones;
        this.anio = anio;
        this.disponibilidad = disponibilidad;
        this.cantidad = cantidad;
    }

    public void actualizarDisponibilidad() {
        this.disponibilidad = this.cantidad > 0;
    }

    public int getId_vinilos() { return id_vinilos; }
    public void setId_vinilos(int id_vinilos) { this.id_vinilos = id_vinilos; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCanciones() { return canciones; }
    public void setCanciones(String canciones) { this.canciones = canciones; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public boolean isDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(boolean disponibilidad) { this.disponibilidad = disponibilidad; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        actualizarDisponibilidad();
    }

    @Override
    public String toString() {
        return nombre + " - " + autor + " (" + anio + ")";
    }
}
