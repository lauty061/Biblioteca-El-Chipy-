package vista;

import dao.*;
import modelo.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaPrestamos extends JFrame {
    private JComboBox<Socio> comboSocios;
    private JComboBox<String> comboCategoriaLibro;
    private JComboBox<String> comboAutorVinilo;
    private JComboBox<Object> comboLibros;
    private JComboBox<Object> comboVinilos;
    private JTextField txtFechaInicio, txtFechaFin;
    private JTable tabla;
    private DefaultTableModel modelo;
    
    private PrestamoDAO prestamoDAO = new PrestamoDAO();
    private SocioDAO socioDAO = new SocioDAO();
    private LibroDAO libroDAO = new LibroDAO();
    private ViniloDAO viniloDAO = new ViniloDAO();
    
    private List<Libro> librosDisponibles;
    private List<Vinilo> vinilosDisponibles;
    private List<Libro> librosFiltrados;
    private List<Vinilo> vinilosFiltrados;
    
    private static final Color BG_PRIMARY = new Color(250, 250, 252);
    private static final Color BG_CARD = Color.WHITE;
    private static final Color ACCENT_COLOR = new Color(251, 146, 60);
    private static final Color TEXT_PRIMARY = new Color(30, 30, 30);
    private static final Color TEXT_SECONDARY = new Color(115, 115, 115);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color WARNING = new Color(234, 179, 8);
    private static final Color DANGER = new Color(239, 68, 68);

    public VentanaPrestamos() {
        setTitle("Gestión de Préstamos");
        setSize(1150, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(BG_PRIMARY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_PRIMARY);
        JLabel titleLabel = new JLabel("Préstamos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        JPanel formCard = new JPanel(new BorderLayout());
        formCard.setBackground(BG_CARD);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 235), 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BG_CARD);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        
        comboSocios = createStyledCombo();
        comboCategoriaLibro = createStyledCombo();
        comboLibros = createStyledCombo();
        comboAutorVinilo = createStyledCombo();
        comboVinilos = createStyledCombo();
        txtFechaInicio = createStyledTextField();
        txtFechaFin = createStyledTextField();
        
        LocalDate hoy = LocalDate.now();
        txtFechaInicio.setText(hoy.toString());
        txtFechaFin.setText(hoy.plusDays(7).toString());
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Socio"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        formPanel.add(comboSocios, gbc);
        gbc.gridwidth = 1;
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel("Categoría Libro"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        formPanel.add(comboCategoriaLibro, gbc);
        gbc.gridwidth = 1;
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabel("Libro"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        formPanel.add(comboLibros, gbc);
        gbc.gridwidth = 1;
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createLabel("Autor Vinilo"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        formPanel.add(comboAutorVinilo, gbc);
        gbc.gridwidth = 1;
        
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(createLabel("Vinilo"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        formPanel.add(comboVinilos, gbc);
        gbc.gridwidth = 1;
        
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(createLabel("Fecha inicio (YYYY-MM-DD)"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtFechaInicio, gbc);
        gbc.gridx = 2;
        formPanel.add(createLabel("Fecha fin"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtFechaFin, gbc);
        
        formCard.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(formCard, BorderLayout.NORTH);
        
        modelo = new DefaultTableModel(
            new String[]{"ID", "Socio", "Libro", "Vinilo", "Fecha Inicio", "Fecha Fin", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modelo);
        tabla.setRowHeight(45);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setForeground(TEXT_PRIMARY);
        tabla.setSelectionBackground(new Color(251, 146, 60, 30));
        tabla.setSelectionForeground(TEXT_PRIMARY);
        tabla.setShowVerticalLines(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setDefaultRenderer(Object.class, new PrestamoRenderer());
        
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(248, 248, 250));
        header.setForeground(TEXT_SECONDARY);
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 230, 235)));
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 235), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setBackground(BG_PRIMARY);
        
        JButton btnAgregar = createStyledButton("Agregar", ACCENT_COLOR);
        JButton btnDevolver = createStyledButton("Registrar Devolución", SUCCESS);
        JButton btnRefrescar = createStyledButton("Refrescar", TEXT_SECONDARY);
        
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnDevolver);
        buttonPanel.add(btnRefrescar);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        btnAgregar.addActionListener(e -> agregarPrestamo());
        btnDevolver.addActionListener(e -> registrarDevolucion());
        btnRefrescar.addActionListener(e -> { cargarCombos(); cargarTabla(); });
        
        comboCategoriaLibro.addActionListener(e -> filtrarLibrosPorCategoria());
        comboAutorVinilo.addActionListener(e -> filtrarVinilosPorAutor());
        
        add(mainPanel);
        cargarCombos();
        cargarTabla();
        setVisible(true);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(TEXT_SECONDARY);
        return label;
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 225), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }
    
    private <T> JComboBox<T> createStyledCombo() {
        JComboBox<T> combo = new JComboBox<>();
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setForeground(TEXT_PRIMARY);
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 225), 1));
        return combo;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(text.length() > 10 ? 180 : 120, 38));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    private void cargarCombos() {
        comboSocios.removeAllItems();
        comboCategoriaLibro.removeAllItems();
        comboAutorVinilo.removeAllItems();
        comboLibros.removeAllItems();
        comboVinilos.removeAllItems();
        
        for (Socio s : socioDAO.listarSocios()) comboSocios.addItem(s);
        
        librosDisponibles = libroDAO.listarDisponibles();
        vinilosDisponibles = viniloDAO.listarDisponibles();
        librosFiltrados = new ArrayList<>(librosDisponibles);
        vinilosFiltrados = new ArrayList<>(vinilosDisponibles);
        
        comboCategoriaLibro.addItem("Todas");
        Set<String> categorias = librosDisponibles.stream()
                .map(Libro::getCategoria)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(TreeSet::new));
        for (String c : categorias) comboCategoriaLibro.addItem(c);
        
        comboAutorVinilo.addItem("Todos");
        Set<String> autores = vinilosDisponibles.stream()
                .map(Vinilo::getAutor)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(TreeSet::new));
        for (String a : autores) comboAutorVinilo.addItem(a);
        
        actualizarComboLibros(librosDisponibles);
        actualizarComboVinilos(vinilosDisponibles);
    }
    
    private void filtrarLibrosPorCategoria() {
        String categoria = (String) comboCategoriaLibro.getSelectedItem();
        if (categoria == null || categoria.equals("Todas")) {
            librosFiltrados = new ArrayList<>(librosDisponibles);
        } else {
            librosFiltrados = librosDisponibles.stream()
                    .filter(l -> l.getCategoria().equalsIgnoreCase(categoria))
                    .collect(Collectors.toList());
        }
        actualizarComboLibros(librosFiltrados);
    }
    
    private void filtrarVinilosPorAutor() {
        String autor = (String) comboAutorVinilo.getSelectedItem();
        if (autor == null || autor.equals("Todos")) {
            vinilosFiltrados = new ArrayList<>(vinilosDisponibles);
        } else {
            vinilosFiltrados = vinilosDisponibles.stream()
                    .filter(v -> v.getAutor().equalsIgnoreCase(autor))
                    .collect(Collectors.toList());
        }
        actualizarComboVinilos(vinilosFiltrados);
    }
    
    private void actualizarComboLibros(List<Libro> lista) {
        comboLibros.removeAllItems();
        comboLibros.addItem("Ninguno");
        for (Libro l : lista)
            comboLibros.addItem(l.getTitulo() + " (" + l.getAutor() + ") - " + l.getCategoria());
    }
    
    private void actualizarComboVinilos(List<Vinilo> lista) {
        comboVinilos.removeAllItems();
        comboVinilos.addItem("Ninguno");
        for (Vinilo v : lista)
            comboVinilos.addItem(v.getNombre() + " (" + v.getAutor() + ") - " + v.getCanciones());
    }
    
    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Map<String, Object>> lista = prestamoDAO.listarPrestamosDetallado();
        for (Map<String, Object> fila : lista) {
            modelo.addRow(new Object[]{
                fila.get("id"),
                fila.get("socio"),
                fila.get("libro"),
                fila.get("vinilo"),
                fila.get("fecha_inicio"),
                fila.get("fecha_fin"),
                fila.get("estado")
            });
        }
    }
    
    private void agregarPrestamo() {
        try {
            Socio socio = (Socio) comboSocios.getSelectedItem();
            if (socio == null) {
                showMessage("Seleccione un socio", false);
                return;
            }
            
            int libroIndex = comboLibros.getSelectedIndex();
            int viniloIndex = comboVinilos.getSelectedIndex();
            Libro libro = (libroIndex > 0 && libroIndex <= librosFiltrados.size()) ? librosFiltrados.get(libroIndex - 1) : null;
            Vinilo vinilo = (viniloIndex > 0 && viniloIndex <= vinilosFiltrados.size()) ? vinilosFiltrados.get(viniloIndex - 1) : null;
            
            if (libro == null && vinilo == null) {
                showMessage("Seleccione al menos un libro o un vinilo", false);
                return;
            }
            
            if (libro != null && libro.getCantidad() <= 0) {
                showMessage("No hay stock disponible de este libro", false);
                return;
            }
            if (vinilo != null && vinilo.getCantidad() <= 0) {
                showMessage("No hay stock disponible de este vinilo", false);
                return;
            }
            
            Prestamo p = new Prestamo();
            p.setId_socios(socio.getId_socios());
            p.setId_libro(libro != null ? libro.getId_libros() : 0);
            p.setId_vinilo(vinilo != null ? vinilo.getId_vinilos() : 0);
            p.setFecha_inicio(LocalDate.parse(txtFechaInicio.getText()));
            p.setFecha_fin(LocalDate.parse(txtFechaFin.getText()));
            
            if (prestamoDAO.agregarPrestamo(p)) {
                showMessage("Préstamo agregado correctamente", true);
                cargarCombos();
                cargarTabla();
            } else {
                showMessage("No se pudo agregar el préstamo (posible límite de préstamos activos)", false);
            }
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), false);
        }
    }
    
    private void registrarDevolucion() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            showMessage("Seleccione un préstamo para registrar devolución", false);
            return;
        }
        
        int id = (int) tabla.getValueAt(fila, 0);
        String estado = (String) tabla.getValueAt(fila, 6);
        
        if ("devuelto".equalsIgnoreCase(estado)) {
            showMessage("Este préstamo ya fue devuelto", false);
            return;
        }
        
        if (prestamoDAO.registrarDevolucion(id)) {
            showMessage("Devolución registrada correctamente", true);
            cargarTabla();
        }
    }
    
    private void showMessage(String message, boolean success) {
        JOptionPane.showMessageDialog(this, message, 
            success ? "Éxito" : "Error", 
            success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
    
    private class PrestamoRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            String estado = table.getValueAt(row, 6).toString();
            LocalDate hoy = LocalDate.now();
            LocalDate fechaFin = LocalDate.parse(table.getValueAt(row, 5).toString());
            
            if (!isSelected) {
                if ("activo".equalsIgnoreCase(estado)) {
                    if (fechaFin.isBefore(hoy)) {
                        c.setBackground(new Color(254, 226, 226));
                    } else {
                        c.setBackground(new Color(220, 252, 231));
                    }
                } else {
                    c.setBackground(new Color(243, 244, 246));
                }
                
                if (row % 2 != 0) {
                    Color bg = c.getBackground();
                    c.setBackground(new Color(
                        Math.max(0, bg.getRed() - 5),
                        Math.max(0, bg.getGreen() - 5),
                        Math.max(0, bg.getBlue() - 5)
                    ));
                }
            }
            
            setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            
            return c;
        }
    }
}