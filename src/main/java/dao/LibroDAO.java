package dao;

import Conexion.Conexion;
import modelo.Libro;
import java.sql.*;
import java.util.*;

public class LibroDAO {
    private final Connection con;

    public LibroDAO() {
        con = Conexion.getConnection();
    }

    public boolean agregarLibro(Libro libro) {
        libro.actualizarDisponibilidad();
        String sql = "INSERT INTO libros (autor, titulo, categoria, paginas, anio, disponibilidad, estante, cantidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.getAutor());
            ps.setString(2, libro.getTitulo());
            ps.setString(3, libro.getCategoria());
            ps.setInt(4, libro.getPaginas());
            ps.setInt(5, libro.getAnio());
            ps.setBoolean(6, libro.isDisponibilidad());
            ps.setString(7, libro.getEstante());
            ps.setInt(8, libro.getCantidad());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al agregar libro: " + e.getMessage());
            return false;
        }
    }

    public List<Libro> listarLibros() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getInt("id_libros"),
                    rs.getString("autor"),
                    rs.getString("titulo"),
                    rs.getString("categoria"),
                    rs.getInt("paginas"),
                    rs.getInt("anio"),
                    rs.getBoolean("disponibilidad"),
                    rs.getString("estante"),
                    rs.getInt("cantidad")
                );
                lista.add(libro);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar libros: " + e.getMessage());
        }
        return lista;
    }

    public List<Libro> listarDisponibles() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE cantidad > 0";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getInt("id_libros"),
                    rs.getString("autor"),
                    rs.getString("titulo"),
                    rs.getString("categoria"),
                    rs.getInt("paginas"),
                    rs.getInt("anio"),
                    rs.getBoolean("disponibilidad"),
                    rs.getString("estante"),
                    rs.getInt("cantidad")
                );
                lista.add(libro);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar libros disponibles: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarLibro(Libro libro) {
        libro.actualizarDisponibilidad();
        String sql = "UPDATE libros SET autor=?, titulo=?, categoria=?, paginas=?, anio=?, disponibilidad=?, estante=?, cantidad=? WHERE id_libros=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.getAutor());
            ps.setString(2, libro.getTitulo());
            ps.setString(3, libro.getCategoria());
            ps.setInt(4, libro.getPaginas());
            ps.setInt(5, libro.getAnio());
            ps.setBoolean(6, libro.isDisponibilidad());
            ps.setString(7, libro.getEstante());
            ps.setInt(8, libro.getCantidad());
            ps.setInt(9, libro.getId_libros());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarLibro(int id) {
        String sql = "DELETE FROM libros WHERE id_libros=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }
}
