package modelo;

public class Libro {
    private int id_libros;
    private String autor;
    private String titulo;
    private String categoria;
    private int paginas;
    private int anio;
    private boolean disponibilidad;
    private String estante;
    private int cantidad;

    public Libro() {}

    public Libro(int id_libros, String autor, String titulo, String categoria, int paginas, int anio, boolean disponibilidad, String estante, int cantidad) {
        this.id_libros = id_libros;
        this.autor = autor;
        this.titulo = titulo;
        this.categoria = categoria;
        this.paginas = paginas;
        this.anio = anio;
        this.disponibilidad = disponibilidad;
        this.estante = estante;
        this.cantidad = cantidad;
    }

    public void actualizarDisponibilidad() {
        this.disponibilidad = this.cantidad > 0;
    }

    public int getId_libros() { return id_libros; }
    public void setId_libros(int id_libros) { this.id_libros = id_libros; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getPaginas() { return paginas; }
    public void setPaginas(int paginas) { this.paginas = paginas; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public boolean isDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(boolean disponibilidad) { this.disponibilidad = disponibilidad; }

    public String getEstante() { return estante; }
    public void setEstante(String estante) { this.estante = estante; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        actualizarDisponibilidad();
    }

    @Override
    public String toString() {
        return titulo + " (" + autor + ")";
    }
}
