package vista;

import dao.LibroDAO;
import modelo.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class VentanaLibros extends JFrame {
    private JTextField txtAutor, txtTitulo, txtCategoria, txtPaginas, txtAnio, txtEstante, txtCantidad;
    private JTable tabla;
    private DefaultTableModel modelo;
    private LibroDAO dao = new LibroDAO();
    
    private static final Color BG_PRIMARY = new Color(250, 250, 252);
    private static final Color BG_CARD = Color.WHITE;
    private static final Color ACCENT_COLOR = new Color(99, 102, 241);
    private static final Color TEXT_PRIMARY = new Color(30, 30, 30);
    private static final Color TEXT_SECONDARY = new Color(115, 115, 115);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color DANGER = new Color(239, 68, 68);

    public VentanaLibros() {
        setTitle("Gestión de Libros");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(BG_PRIMARY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_PRIMARY);
        JLabel titleLabel = new JLabel("Libros");
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
        
        txtAutor = createStyledTextField();
        txtTitulo = createStyledTextField();
        txtCategoria = createStyledTextField();
        txtPaginas = createStyledTextField();
        txtAnio = createStyledTextField();
        txtEstante = createStyledTextField();
        txtCantidad = createStyledTextField();
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Autor"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtAutor, gbc);
        gbc.gridx = 2;
        formPanel.add(createLabel("Título"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtTitulo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel("Categoría"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtCategoria, gbc);
        gbc.gridx = 2;
        formPanel.add(createLabel("Páginas"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtPaginas, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabel("Año"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtAnio, gbc);
        gbc.gridx = 2;
        formPanel.add(createLabel("Estante"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtEstante, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createLabel("Cantidad"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtCantidad, gbc);
        
        formCard.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(formCard, BorderLayout.NORTH);
        
        modelo = new DefaultTableModel(
            new String[]{"ID", "Autor", "Título", "Categoría", "Páginas", "Año", "Estante", "Cantidad", "Disponible"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modelo);
        tabla.setRowHeight(40);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setForeground(TEXT_PRIMARY);
        tabla.setSelectionBackground(new Color(99, 102, 241, 30));
        tabla.setSelectionForeground(TEXT_PRIMARY);
        tabla.setShowVerticalLines(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(248, 248, 250));
        header.setForeground(TEXT_SECONDARY);
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 230, 235)));
        
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(249, 249, 251));
                }
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                return c;
            }
        };
        
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 235), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setBackground(BG_PRIMARY);
        
        JButton btnAgregar = createStyledButton("Agregar", ACCENT_COLOR);
        JButton btnActualizar = createStyledButton("Actualizar", new Color(59, 130, 246));
        JButton btnEliminar = createStyledButton("Eliminar", DANGER);
        JButton btnRefrescar = createStyledButton("Refrescar", TEXT_SECONDARY);
        
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnRefrescar);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        btnAgregar.addActionListener(e -> agregarLibro());
        btnActualizar.addActionListener(e -> actualizarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnRefrescar.addActionListener(e -> cargarTabla());
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) llenarCampos();
        });
        
        add(mainPanel);
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
        button.setPreferredSize(new Dimension(120, 38));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Libro> lista = dao.listarLibros();
        for (Libro l : lista) {
            modelo.addRow(new Object[]{
                l.getId_libros(),
                l.getAutor(),
                l.getTitulo(),
                l.getCategoria(),
                l.getPaginas(),
                l.getAnio(),
                l.getEstante(),
                l.getCantidad(),
                l.isDisponibilidad() ? "✓ Sí" : "✗ No"
            });
        }
    }
    
    private void agregarLibro() {
        try {
            Libro libro = new Libro();
            libro.setAutor(txtAutor.getText());
            libro.setTitulo(txtTitulo.getText());
            libro.setCategoria(txtCategoria.getText());
            libro.setPaginas(Integer.parseInt(txtPaginas.getText()));
            libro.setAnio(Integer.parseInt(txtAnio.getText()));
            libro.setEstante(txtEstante.getText());
            libro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            
            dao.agregarLibro(libro);
            cargarTabla();
            limpiarCampos();
            showMessage("Libro agregado correctamente", true);
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), false);
        }
    }
    
    private void actualizarLibro() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            showMessage("Seleccione un libro para actualizar", false);
            return;
        }
        
        try {
            Libro libro = new Libro();
            libro.setId_libros((int) tabla.getValueAt(fila, 0));
            libro.setAutor(txtAutor.getText());
            libro.setTitulo(txtTitulo.getText());
            libro.setCategoria(txtCategoria.getText());
            libro.setPaginas(Integer.parseInt(txtPaginas.getText()));
            libro.setAnio(Integer.parseInt(txtAnio.getText()));
            libro.setEstante(txtEstante.getText());
            libro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            
            dao.actualizarLibro(libro);
            cargarTabla();
            limpiarCampos();
            showMessage("Libro actualizado correctamente", true);
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), false);
        }
    }
    
    private void eliminarLibro() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            showMessage("Seleccione un libro para eliminar", false);
            return;
        }
        
        int id = (int) tabla.getValueAt(fila, 0);
        dao.eliminarLibro(id);
        cargarTabla();
        limpiarCampos();
        showMessage("Libro eliminado correctamente", true);
    }
    
    private void llenarCampos() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;
        
        txtAutor.setText(tabla.getValueAt(fila, 1).toString());
        txtTitulo.setText(tabla.getValueAt(fila, 2).toString());
        txtCategoria.setText(tabla.getValueAt(fila, 3).toString());
        txtPaginas.setText(tabla.getValueAt(fila, 4).toString());
        txtAnio.setText(tabla.getValueAt(fila, 5).toString());
        txtEstante.setText(tabla.getValueAt(fila, 6).toString());
        txtCantidad.setText(tabla.getValueAt(fila, 7).toString());
    }
    
    private void limpiarCampos() {
        txtAutor.setText("");
        txtTitulo.setText("");
        txtCategoria.setText("");
        txtPaginas.setText("");
        txtAnio.setText("");
        txtEstante.setText("");
        txtCantidad.setText("");
    }
    
    private void showMessage(String message, boolean success) {
        JOptionPane.showMessageDialog(this, message, 
            success ? "Éxito" : "Error", 
            success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
}