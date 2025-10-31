package dao;

import Conexion.Conexion;
import modelo.Vinilo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViniloDAO {
    private final Connection con;

    public ViniloDAO() {
        con = Conexion.getConnection();
    }

    public boolean agregarVinilo(Vinilo v) {
        v.actualizarDisponibilidad();
        String sql = "INSERT INTO vinilos (autor, nombre, canciones, anio, disponibilidad, cantidad) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getAutor());
            ps.setString(2, v.getNombre());
            ps.setString(3, v.getCanciones());
            ps.setInt(4, v.getAnio());
            ps.setBoolean(5, v.isDisponibilidad());
            ps.setInt(6, v.getCantidad());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al agregar vinilo: " + e.getMessage());
            return false;
        }
    }

    public List<Vinilo> listarVinilos() {
        List<Vinilo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vinilos";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Vinilo v = new Vinilo(
                        rs.getInt("id_vinilos"),
                        rs.getString("autor"),
                        rs.getString("nombre"),
                        rs.getString("canciones"),
                        rs.getInt("anio"),
                        rs.getBoolean("disponibilidad"),
                        rs.getInt("cantidad")
                );
                lista.add(v);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar vinilos: " + e.getMessage());
        }
        return lista;
    }

    public List<Vinilo> listarDisponibles() {
        List<Vinilo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vinilos WHERE cantidad > 0";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Vinilo v = new Vinilo(
                    rs.getInt("id_vinilos"),
                    rs.getString("autor"),
                    rs.getString("nombre"),
                    rs.getString("canciones"),
                    rs.getInt("anio"),
                    rs.getBoolean("disponibilidad"),
                    rs.getInt("cantidad")
                );
                lista.add(v);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar vinilos disponibles: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarVinilo(Vinilo v) {
        v.actualizarDisponibilidad();
        String sql = "UPDATE vinilos SET autor=?, nombre=?, canciones=?, anio=?, disponibilidad=?, cantidad=? WHERE id_vinilos=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getAutor());
            ps.setString(2, v.getNombre());
            ps.setString(3, v.getCanciones());
            ps.setInt(4, v.getAnio());
            ps.setBoolean(5, v.isDisponibilidad());
            ps.setInt(6, v.getCantidad());
            ps.setInt(7, v.getId_vinilos());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar vinilo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarVinilo(int id) {
        String sql = "DELETE FROM vinilos WHERE id_vinilos=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar vinilo: " + e.getMessage());
            return false;
        }
    }
}
