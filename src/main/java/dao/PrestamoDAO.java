package dao;

import Conexion.Conexion;
import modelo.Prestamo;
import modelo.Libro;
import modelo.Vinilo;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class PrestamoDAO {
    private final Connection con;
    private final LibroDAO libroDAO;
    private final ViniloDAO viniloDAO;

    public PrestamoDAO() {
        con = Conexion.getConnection();
        libroDAO = new LibroDAO();
        viniloDAO = new ViniloDAO();
    }

    public boolean agregarPrestamo(Prestamo p) {
        try {
            int activos = contarPrestamosActivos(p.getId_socios());
            if (activos >= 3) {
                System.out.println("⚠️ El socio ya tiene 3 préstamos activos.");
                return false;
            }

            String sql = """
                INSERT INTO prestamo (id_socios, id_libros, id_vinilos, fecha_inicio, fecha_fin, estado)
                VALUES (?, ?, ?, ?, ?, 'activo')
            """;

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, p.getId_socios());

                if (p.getId_libro() == 0) ps.setNull(2, Types.INTEGER);
                else {
                    ps.setInt(2, p.getId_libro());
                    ajustarStockLibro(p.getId_libro(), -1);
                }

                if (p.getId_vinilo() == 0) ps.setNull(3, Types.INTEGER);
                else {
                    ps.setInt(3, p.getId_vinilo());
                    ajustarStockVinilo(p.getId_vinilo(), -1);
                }

                ps.setDate(4, Date.valueOf(p.getFecha_inicio()));
                ps.setDate(5, Date.valueOf(p.getFecha_fin()));
                ps.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al agregar préstamo: " + e.getMessage());
            return false;
        }
    }

    public List<Map<String, Object>> listarPrestamosDetallado() {
        actualizarPrestamosVencidos();

        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = """
            SELECT p.id_prestamo,
                   s.nombre AS socio_nombre,
                   s.dni AS socio_dni,
                   l.titulo AS libro_titulo,
                   v.nombre AS vinilo_nombre,
                   p.fecha_inicio,
                   p.fecha_fin,
                   p.estado
            FROM prestamo p
            LEFT JOIN socios s ON p.id_socios = s.id_socios
            LEFT JOIN libros l ON p.id_libros = l.id_libros
            LEFT JOIN vinilos v ON p.id_vinilos = v.id_vinilos
            ORDER BY p.id_prestamo DESC;
        """;

        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id", rs.getInt("id_prestamo"));
                fila.put("socio", rs.getString("socio_nombre") + " (DNI: " + rs.getString("socio_dni") + ")");
                fila.put("libro", rs.getString("libro_titulo") != null ? rs.getString("libro_titulo") : "-");
                fila.put("vinilo", rs.getString("vinilo_nombre") != null ? rs.getString("vinilo_nombre") : "-");
                fila.put("fecha_inicio", rs.getDate("fecha_inicio").toString());
                fila.put("fecha_fin", rs.getDate("fecha_fin").toString());
                fila.put("estado", rs.getString("estado"));
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar préstamos: " + e.getMessage());
        }
        return lista;
    }

    private void actualizarPrestamosVencidos() {
        String sql = "UPDATE prestamo SET estado='vencido' WHERE estado='activo' AND fecha_fin < CURDATE()";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("⚠️ Error al actualizar préstamos vencidos: " + e.getMessage());
        }
    }

    public List<Map<String, Object>> buscarPrestamos(String criterio) {
        actualizarPrestamosVencidos();
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = """
            SELECT p.id_prestamo, s.nombre AS socio, l.titulo AS libro, v.nombre AS vinilo,
                   p.fecha_inicio, p.fecha_fin, p.estado
            FROM prestamo p
            LEFT JOIN socios s ON p.id_socios = s.id_socios
            LEFT JOIN libros l ON p.id_libros = l.id_libros
            LEFT JOIN vinilos v ON p.id_vinilos = v.id_vinilos
            WHERE s.nombre LIKE ? OR l.titulo LIKE ? OR v.nombre LIKE ? OR p.estado LIKE ?
            ORDER BY p.id_prestamo DESC;
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            String like = "%" + criterio + "%";
            for (int i = 1; i <= 4; i++) ps.setString(i, like);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id", rs.getInt("id_prestamo"));
                fila.put("socio", rs.getString("socio"));
                fila.put("libro", rs.getString("libro") != null ? rs.getString("libro") : "-");
                fila.put("vinilo", rs.getString("vinilo") != null ? rs.getString("vinilo") : "-");
                fila.put("fecha_inicio", rs.getDate("fecha_inicio").toString());
                fila.put("fecha_fin", rs.getDate("fecha_fin").toString());
                fila.put("estado", rs.getString("estado"));
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error en búsqueda: " + e.getMessage());
        }
        return lista;
    }

    public boolean registrarDevolucion(int idPrestamo) {
        String sql = "UPDATE prestamo SET estado='devuelto' WHERE id_prestamo=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            PreparedStatement ps2 = con.prepareStatement("SELECT id_libros, id_vinilos FROM prestamo WHERE id_prestamo=?");
            ps2.setInt(1, idPrestamo);
            ResultSet rs = ps2.executeQuery();

            if (rs.next()) {
                int idLibro = rs.getInt("id_libros");
                int idVinilo = rs.getInt("id_vinilos");
                if (idLibro > 0) ajustarStockLibro(idLibro, +1);
                if (idVinilo > 0) ajustarStockVinilo(idVinilo, +1);
            }

            ps.setInt(1, idPrestamo);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al registrar devolución: " + e.getMessage());
            return false;
        }
    }

    public int contarPrestamosActivos(int idSocio) {
        String sql = "SELECT COUNT(*) FROM prestamo WHERE id_socios=? AND estado='activo'";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("⚠️ Error al contar préstamos activos: " + e.getMessage());
        }
        return 0;
    }

    private void ajustarStockLibro(int idLibro, int cambio) {
        try {
            for (Libro l : libroDAO.listarLibros()) {
                if (l.getId_libros() == idLibro) {
                    l.setCantidad(l.getCantidad() + cambio);
                    libroDAO.actualizarLibro(l);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al ajustar stock de libro: " + e.getMessage());
        }
    }

    private void ajustarStockVinilo(int idVinilo, int cambio) {
        try {
            for (Vinilo v : viniloDAO.listarVinilos()) {
                if (v.getId_vinilos() == idVinilo) {
                    v.setCantidad(v.getCantidad() + cambio);
                    viniloDAO.actualizarVinilo(v);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al ajustar stock de vinilo: " + e.getMessage());
        }
    }
}
