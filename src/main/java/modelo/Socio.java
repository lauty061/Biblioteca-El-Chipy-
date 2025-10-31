package modelo;

public class Socio {
    private int id_socios;
    private String nombre;
    private String dni;
    private String email;
    private String numero;

    public Socio() {}

    public Socio(int id_socios, String nombre, String dni, String email, String numero) {
        this.id_socios = id_socios;
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.numero = numero;
    }

    public int getId_socios() { return id_socios; }
    public void setId_socios(int id_socios) { this.id_socios = id_socios; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    @Override
    public String toString() {
        return nombre + " (DNI: " + dni + ")";
    }
}
