package dao;

import Conexion.Conexion;
import modelo.Socio;
import java.sql.*;
import java.util.*;

public class SocioDAO {
    private final Connection con;

    public SocioDAO() {
        con = Conexion.getConnection();
    }

    public boolean agregarSocio(Socio socio) {
        String sql = "INSERT INTO socios (nombre, dni, email, numero) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getDni());
            ps.setString(3, socio.getEmail());
            ps.setString(4, socio.getNumero());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al agregar socio: " + e.getMessage());
            return false;
        }
    }

    public List<Socio> listarSocios() {
        List<Socio> lista = new ArrayList<>();
        String sql = "SELECT * FROM socios";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Socio(
                    rs.getInt("id_socios"),
                    rs.getString("nombre"),
                    rs.getString("dni"),
                    rs.getString("email"),
                    rs.getString("numero")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar socios: " + e.getMessage());
        }
        return lista;
    }

    public List<Socio> listarSociosCombo() {
        return listarSocios();
    }

    public boolean actualizarSocio(Socio socio) {
        String sql = "UPDATE socios SET nombre=?, dni=?, email=?, numero=? WHERE id_socios=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getDni());
            ps.setString(3, socio.getEmail());
            ps.setString(4, socio.getNumero());
            ps.setInt(5, socio.getId_socios());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar socio: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarSocio(int id) {
        String sql = "DELETE FROM socios WHERE id_socios=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar socio: " + e.getMessage());
            return false;
        }
    }
}
