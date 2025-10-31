package modelo;

import java.time.LocalDate;

public class Prestamo {
    private int id_prestamo;
    private int id_socios;
    private int id_libro;
    private int id_vinilo;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private String estado;

    public Prestamo() {}

    public Prestamo(int id_prestamo, int id_socios, int id_libro, int id_vinilo,
                    LocalDate fecha_inicio, LocalDate fecha_fin, String estado) {
        this.id_prestamo = id_prestamo;
        this.id_socios = id_socios;
        this.id_libro = id_libro;
        this.id_vinilo = id_vinilo;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.estado = estado;
    }

    public int getId_prestamo() { return id_prestamo; }
    public void setId_prestamo(int id_prestamo) { this.id_prestamo = id_prestamo; }

    public int getId_socios() { return id_socios; }
    public void setId_socios(int id_socios) { this.id_socios = id_socios; }

    public int getId_libro() { return id_libro; }
    public void setId_libro(int id_libro) { this.id_libro = id_libro; }

    public int getId_vinilo() { return id_vinilo; }
    public void setId_vinilo(int id_vinilo) { this.id_vinilo = id_vinilo; }

    public LocalDate getFecha_inicio() { return fecha_inicio; }
    public void setFecha_inicio(LocalDate fecha_inicio) { this.fecha_inicio = fecha_inicio; }

    public LocalDate getFecha_fin() { return fecha_fin; }
    public void setFecha_fin(LocalDate fecha_fin) { this.fecha_fin = fecha_fin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
