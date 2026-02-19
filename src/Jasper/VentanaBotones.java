package Jasper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class VentanaBotones extends JFrame{
	private JPanel contentPane;
    private Connection conexion;

    // Estilo (lo mantengo como el tuyo)
    private final Color colorFondoRosa = new Color(255, 204, 229);
    private final Color colorBotonNormal = Color.WHITE;
    private final Color colorBotonHover = new Color(255, 240, 245);
    private final Color colorBordeBoton = new Color(255, 102, 178);
    private final Color colorTitulo = new Color(153, 0, 76);

    // Carpeta donde guardas tus JRXML
    private static final String RUTA_INFORMES = "src/informes/";

    public VentanaBotones() {
        setTitle("Flota Espacial - Informes (Jasper + Java)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 520);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(colorFondoRosa);
        contentPane.setBorder(new EmptyBorder(25, 25, 25, 25));
        contentPane.setLayout(new BorderLayout(0, 15));
        setContentPane(contentPane);

        JLabel titulo = new JLabel("Informes Flota Espacial", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(colorTitulo);
        contentPane.add(titulo, BorderLayout.NORTH);

        // Panel central con 8 informes (6 normales + 2 dinámicos con input al lado)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        conectarBaseDatos();

        // --- Botones (8 informes) ---
        JButton b1 = crearBotonEstilizado("1) Listado de Naves");
        JButton b2 = crearBotonEstilizado("2) Listado de Tripulantes");
        JButton b3 = crearBotonEstilizado("3) Tripulación por Nave (Agrupado)");
        JButton b4 = crearBotonEstilizado("4) Misiones por Estado (Agrupado)");
        JButton b5 = crearBotonEstilizado("5) Misiones de una Nave (Dinámico)");
        JButton b6 = crearBotonEstilizado("6) Tripulación por Rango (Dinámico)");
        JButton b7 = crearBotonEstilizado("7) Gráfico: Misiones por Estado");
        JButton b8 = crearBotonEstilizado("8) Gráfico: Tripulantes por Rango");

        // --- Inputs dinámicos al lado (como pides) ---
        JTextField txtIdNave = crearCampoInput("ID nave");
        JTextField txtRango = crearCampoInput("Capitán/Ingeniero/Piloto");

        // Layout filas
        int fila = 0;
        agregarFila(panel, "Informe 1 (JasperReport):", b1, null, fila++, gbc);
        agregarFila(panel, "Informe 2 (JAVA):", b2, null, fila++, gbc);
        agregarFila(panel, "Informe 3 (JasperReport):", b3, null, fila++, gbc);
        agregarFila(panel, "Informe 4 (JAVA):", b4, null, fila++, gbc);
        agregarFila(panel, "Informe 5 (Java + Jasper)  ID nave:", b5, txtIdNave, fila++, gbc);
        agregarFila(panel, "Informe 6 (Java + Jasper)  Rango:", b6, txtRango, fila++, gbc);
        agregarFila(panel, "Informe 7 (Gráfico):", b7, null, fila++, gbc);
        agregarFila(panel, "Informe 8 (Gráfico):", b8, null, fila++, gbc);

        contentPane.add(panel, BorderLayout.CENTER);

        // --- Eventos ---
        // Nombres de JRXML esperados en src/informes/
        // (Cámbialos si tus archivos se llaman distinto)
        b1.addActionListener(e -> lanzarInforme("listado_naves.jrxml", null));
        b2.addActionListener(e -> lanzarInforme("listado_tripulantes.jrxml", null));
        b3.addActionListener(e -> lanzarInforme("tripulacionPorNave.jrxml", null));
        b4.addActionListener(e -> lanzarInforme("misiones_por_estado.jrxml", null));
        b7.addActionListener(e -> lanzarInforme("graf_misiones_estado.jrxml", null));
        b8.addActionListener(e -> lanzarInforme("graf_tripulantes_rango.jrxml", null));

        // Dinámico 5: Misiones de una nave
        b5.addActionListener(e -> {
            String s = txtIdNave.getText().trim();
            if (s.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce el ID de la nave.");
                return;
            }
            int idNave;
            try {
                idNave = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID de nave no válido.");
                return;
            }

            Map<String, Object> p = new HashMap<>();
            p.put("p_id_nave", idNave);
            lanzarInforme("misiones_de_una_nave.jrxml", p);
        });

        // Dinámico 6: Tripulación por rango
        b6.addActionListener(e -> {
            String rango = txtRango.getText().trim();
            if (rango.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce un rango (Capitán, Ingeniero, Piloto).");
                return;
            }

            // Opcional: validar valores permitidos
            if (!rango.equalsIgnoreCase("Capitán") &&
                !rango.equalsIgnoreCase("Ingeniero") &&
                !rango.equalsIgnoreCase("Piloto")) {
                JOptionPane.showMessageDialog(this, "Rango no válido. Usa: Capitán, Ingeniero o Piloto.");
                return;
            }

            Map<String, Object> p = new HashMap<>();
            // Respeta la acentuación tal cual la tengas en BBDD
            // (si en BBDD guardas "Capitan" sin tilde, ajusta aquí)
            p.put("p_rango", normalizarRango(rango));
            lanzarInforme("tripulacion_por_rango.jrxml", p);
        });
    }

    private String normalizarRango(String rango) {
        // Si en la BBDD guardas con mayúscula inicial:
        String r = rango.trim().toLowerCase();
        if (r.equals("capitán") || r.equals("capitan")) return "Capitán";
        if (r.equals("ingeniero")) return "Ingeniero";
        if (r.equals("piloto")) return "Piloto";
        return rango;
    }

    private JTextField crearCampoInput(String placeholder) {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txt.setColumns(14);
        txt.setToolTipText(placeholder);
        txt.setBorder(new LineBorder(colorBordeBoton, 1, true));
        return txt;
    }

    private JButton crearBotonEstilizado(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(colorBotonNormal);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setFocusable(false);
        btn.setRequestFocusEnabled(false);
        btn.setBorder(new LineBorder(colorBordeBoton, 1, true));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(colorBotonHover); }
            @Override public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(colorBotonNormal); }
        });
        return btn;
    }

    /**
     * Fila con:
     *  Col 0: JLabel
     *  Col 1: JButton
     *  Col 2: (opcional) JTextField para parámetros dinámicos
     */
    private void agregarFila(JPanel panel, String texto, JButton boton, JComponent extra, int fila, GridBagConstraints gbc) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(colorTitulo);

        gbc.gridy = fila;

        gbc.gridx = 0; gbc.weightx = 0.55;
        panel.add(label, gbc);

        gbc.gridx = 1; gbc.weightx = 0.30;
        panel.add(boton, gbc);

        gbc.gridx = 2; gbc.weightx = 0.15;
        if (extra != null) {
            panel.add(extra, gbc);
        } else {
            panel.add(Box.createHorizontalStrut(10), gbc);
        }
    }

    private void conectarBaseDatos() {
        try {
            // Driver MySQL 8+
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Cambia "flota" por el nombre real de tu base de datos
            // y user/pass según tu entorno
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/flota_espacial",
                    "root",
                    ""
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error BD: " + e.getMessage());
        }
    }

    /**
     * Lanza un informe JRXML:
     * - compila el jrxml
     * - rellena con parámetros + conexión
     * - muestra JasperViewer
     * - exporta a PDF automáticamente en src/informes/ con nombre basado en el jrxml
     *
     * Nota: si quieres separar "ver" y "pdf", lo adapto.
     */
    private void lanzarInforme(String jrxml, Map<String, Object> paramsExtra) {
        if (conexion == null) {
            JOptionPane.showMessageDialog(this, "No hay conexión a la base de datos.");
            return;
        }

        try {
            String path = RUTA_INFORMES + jrxml;
            System.setProperty("net.sf.jasperreports.compiler.classpath", System.getProperty("java.class.path"));

            Map<String, Object> parametros = new HashMap<>();
            // Comunes obligatorios en todos los informes (los añades en JRXML)
            parametros.put("p_fecha", new Date());
            parametros.put("p_registro", "REG-" + System.currentTimeMillis());

            if (paramsExtra != null) parametros.putAll(paramsExtra);

            JasperReport report = JasperCompileManager.compileReport(path);
            JasperPrint print = JasperFillManager.fillReport(report, parametros, conexion);

            // Ver
            JasperViewer.viewReport(print, false);

            // PDF
            String out = RUTA_INFORMES + jrxml.replace(".jrxml", ".pdf");
            JasperExportManager.exportReportToPdfFile(print, out);

            JOptionPane.showMessageDialog(this, "Informe generado:\n" + out);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(this, "Error de Jasper: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new VentanaBotones().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
