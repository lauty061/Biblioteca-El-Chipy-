package vista;

import dao.SocioDAO;
import modelo.Socio;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class VentanaSocios extends JFrame {
    private JTextField txtNombre, txtDni, txtTelefono, txtEmail;
    private JTable tabla;
    private DefaultTableModel modelo;
    private SocioDAO dao = new SocioDAO();
    
    private static final Color BG_PRIMARY = new Color(250, 250, 252);
    private static final Color BG_CARD = Color.WHITE;
    private static final Color ACCENT_COLOR = new Color(34, 197, 94);
    private static final Color TEXT_PRIMARY = new Color(30, 30, 30);
    private static final Color TEXT_SECONDARY = new Color(115, 115, 115);
    private static final Color DANGER = new Color(239, 68, 68);

    public VentanaSocios() {
        setTitle("Gestión de Socios");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(BG_PRIMARY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_PRIMARY);
        JLabel titleLabel = new JLabel("Socios");
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
        
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 15, 12));
        formPanel.setBackground(BG_CARD);
        
        txtNombre = createStyledTextField();
        txtDni = createStyledTextField();
        txtTelefono = createStyledTextField();
        txtEmail = createStyledTextField();
        
        formPanel.add(createLabel("Nombre"));
        formPanel.add(txtNombre);
        formPanel.add(createLabel("DNI"));
        formPanel.add(txtDni);
        formPanel.add(createLabel("Teléfono"));
        formPanel.add(txtTelefono);
        formPanel.add(createLabel("Email"));
        formPanel.add(txtEmail);
        
        formCard.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(formCard, BorderLayout.NORTH);
        
        modelo = new DefaultTableModel(
            new String[]{"ID", "Nombre", "DNI", "Teléfono", "Email"}, 0
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
        tabla.setSelectionBackground(new Color(34, 197, 94, 30));
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
        
        btnAgregar.addActionListener(e -> agregar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
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
        List<Socio> lista = dao.listarSocios();
        for (Socio s : lista) {
            modelo.addRow(new Object[]{
                s.getId_socios(), s.getNombre(), s.getDni(), s.getNumero(), s.getEmail()
            });
        }
    }
    
    private void agregar() {
        try {
            if (txtNombre.getText().trim().isEmpty() ||
                txtDni.getText().trim().isEmpty() ||
                txtTelefono.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty()) {
                showMessage("Complete todos los campos antes de guardar", false);
                return;
            }
            
            if (!txtDni.getText().matches("\\d+")) {
                showMessage("El DNI debe contener solo números", false);
                return;
            }
            
            if (!txtTelefono.getText().matches("\\d+")) {
                showMessage("El teléfono debe contener solo números", false);
                return;
            }
            
            Socio s = new Socio();
            s.setNombre(txtNombre.getText().trim());
            s.setDni(txtDni.getText().trim());
            s.setNumero(txtTelefono.getText().trim());
            s.setEmail(txtEmail.getText().trim());
            
            if (dao.agregarSocio(s)) {
                showMessage("Socio agregado correctamente", true);
                limpiarCampos();
                cargarTabla();
            }
        } catch (Exception e) {
            showMessage("Error al agregar: " + e.getMessage(), false);
        }
    }
    
    private void actualizar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            showMessage("Seleccione un socio para actualizar", false);
            return;
        }
        
        try {
            if (txtNombre.getText().trim().isEmpty() ||
                txtDni.getText().trim().isEmpty() ||
                txtTelefono.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty()) {
                showMessage("Complete todos los campos antes de actualizar", false);
                return;
            }
            
            Socio s = new Socio();
            s.setId_socios((int) tabla.getValueAt(fila, 0));
            s.setNombre(txtNombre.getText().trim());
            s.setDni(txtDni.getText().trim());
            s.setNumero(txtTelefono.getText().trim());
            s.setEmail(txtEmail.getText().trim());
            
            if (dao.actualizarSocio(s)) {
                showMessage("Socio actualizado correctamente", true);
                limpiarCampos();
                cargarTabla();
            }
        } catch (Exception e) {
            showMessage("Error al actualizar: " + e.getMessage(), false);
        }
    }
    
    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            showMessage("Seleccione un socio para eliminar", false);
            return;
        }
        
        int id = (int) tabla.getValueAt(fila, 0);
        if (dao.eliminarSocio(id)) {
            showMessage("Socio eliminado correctamente", true);
            limpiarCampos();
            cargarTabla();
        }
    }
    
    private void llenarCampos() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;
        
        txtNombre.setText(tabla.getValueAt(fila, 1).toString());
        txtDni.setText(tabla.getValueAt(fila, 2).toString());
        txtTelefono.setText(tabla.getValueAt(fila, 3).toString());
        txtEmail.setText(tabla.getValueAt(fila, 4).toString());
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        txtDni.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }
    
    private void showMessage(String message, boolean success) {
        JOptionPane.showMessageDialog(this, message, 
            success ? "Éxito" : "Error", 
            success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
}