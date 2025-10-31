package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    
    private static final Color BG_PRIMARY = new Color(250, 250, 252);
    private static final Color BG_CARD = Color.WHITE;
    private static final Color ACCENT_BLUE = new Color(99, 102, 241);
    private static final Color ACCENT_PURPLE = new Color(139, 92, 246);
    private static final Color ACCENT_GREEN = new Color(34, 197, 94);
    private static final Color ACCENT_ORANGE = new Color(251, 146, 60);
    private static final Color TEXT_PRIMARY = new Color(30, 30, 30);
    private static final Color TEXT_SECONDARY = new Color(115, 115, 115);
    
    public VentanaPrincipal() {
        setTitle("Biblioteca");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(BG_PRIMARY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BG_PRIMARY);
        
        JLabel titleLabel = new JLabel("Biblioteca");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Sistema de gestión");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);
        headerPanel.add(Box.createVerticalStrut(30));
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 1, 0, 15));
        buttonsPanel.setBackground(BG_PRIMARY);
        
        JButton btnLibros = createStyledButton("Libros", "Gestionar catálogo de libros", ACCENT_BLUE);
        JButton btnVinilos = createStyledButton("Vinilos", "Gestionar colección de vinilos", ACCENT_PURPLE);
        JButton btnSocios = createStyledButton("Socios", "Administrar miembros", ACCENT_GREEN);
        JButton btnPrestamos = createStyledButton("Préstamos", "Registrar y consultar préstamos", ACCENT_ORANGE);
        JButton btnSalir = createExitButton();
        
        buttonsPanel.add(btnLibros);
        buttonsPanel.add(btnVinilos);
        buttonsPanel.add(btnSocios);
        buttonsPanel.add(btnPrestamos);
        buttonsPanel.add(btnSalir);
        
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        
        btnLibros.addActionListener(e -> new VentanaLibros());
        btnVinilos.addActionListener(e -> new VentanaVinilos());
        btnSocios.addActionListener(e -> new VentanaSocios());
        btnPrestamos.addActionListener(e -> new VentanaPrestamos());
        btnSalir.addActionListener(e -> System.exit(0));
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JButton createStyledButton(String title, String description, Color accentColor) {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(new Color(0, 0, 0, 15));
                g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 12, 12);
                
                if (getModel().isPressed()) {
                    g2.setColor(new Color(245, 245, 247));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(249, 249, 251));
                } else {
                    g2.setColor(BG_CARD);
                }
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 12, 12);
                
                g2.setColor(accentColor);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(15, 15, 15, getHeight() - 19);
                
                g2.dispose();
            }
        };
        
        button.setLayout(new BorderLayout(15, 0));
        button.setPreferredSize(new Dimension(420, 85));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 20));
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(TEXT_SECONDARY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(descLabel);
        
        button.add(textPanel, BorderLayout.CENTER);
        
        return button;
    }
    
    private JButton createExitButton() {
        JButton button = new JButton("Salir") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(new Color(220, 220, 220));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(235, 235, 235));
                } else {
                    g2.setColor(new Color(245, 245, 245));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setForeground(TEXT_SECONDARY);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(420, 45));
        
        return button;
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(VentanaPrincipal::new);
    }
}