package Jasper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class VentanaBotones extends JFrame{
	private JPanel contentPane;
    private Connection conexion;


    private final Color colorFondoRosa = new Color(255, 204, 229);
    private final Color colorBotonNormal = Color.WHITE;
    private final Color colorBotonHover = new Color(255, 240, 245);
    private final Color colorBordeBoton = new Color(255, 102, 178);
    private final Color colorTitulo = new Color(153, 0, 76);


    private static final String RUTA_INFORMES = "src/informes/";

    public VentanaBotones() {
        setTitle("Flota Espacial - Informes (Jasper + Java)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 550);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(colorFondoRosa);
        contentPane.setBorder(new EmptyBorder(25, 25, 25, 25));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        crearMenu();

        conectarBaseDatos();

        // SOLO variable para el tamaño de los botones
        Dimension tamBoton = new Dimension(520, 36);

        // ===== TÍTULO =====
        JLabel titulo = new JLabel("Informes Flota Espacial", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(colorTitulo);
        titulo.setBounds(0, 18, 820, 40);
        contentPane.add(titulo);

        // ===== LABELS =====
        JLabel  etiqueta1= new JLabel("Listado Naves :");
        etiqueta1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        etiqueta1.setForeground(colorTitulo);
        etiqueta1.setBounds(30, 90, 190, 36);
        contentPane.add(etiqueta1);

        JLabel etiqueta2 = new JLabel("Listado Tripulantes:");
        etiqueta2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        etiqueta2.setForeground(colorTitulo);
        etiqueta2.setBounds(30, 142, 190, 36);
        contentPane.add(etiqueta2);

        JLabel etiqueta3 = new JLabel("Tripulacion por nave:");
        etiqueta3.setFont(new Font("Segoe UI", Font.BOLD, 13));
        etiqueta3.setForeground(colorTitulo);
        etiqueta3.setBounds(30, 194, 190, 36);
        contentPane.add(etiqueta3);

        JLabel etiqueta4 = new JLabel("Misiones por estados:");
        etiqueta4.setFont(new Font("Segoe UI", Font.BOLD, 13));
        etiqueta4.setForeground(colorTitulo);
        etiqueta4.setBounds(30, 246, 190, 36);
        contentPane.add(etiqueta4);

        JLabel etiqueta5 = new JLabel("Misones por Estado:");
        etiqueta5.setFont(new Font("Segoe UI", Font.BOLD, 13));
        etiqueta5.setForeground(colorTitulo);
        etiqueta5.setBounds(30, 298, 220, 36);
        contentPane.add(etiqueta5);

        JLabel etiqueta6 = new JLabel("Tripulacion por Rango:");
        etiqueta6.setFont(new Font("Segoe UI", Font.BOLD, 13));
        etiqueta6.setForeground(colorTitulo);
        etiqueta6.setBounds(30, 350, 220, 36);
        contentPane.add(etiqueta6);

        JLabel etiqueta7 = new JLabel("Grafico barras:");
        etiqueta7.setFont(new Font("Segoe UI", Font.BOLD, 13));
        etiqueta7.setForeground(colorTitulo);
        etiqueta7.setBounds(30, 402, 190, 36);
        contentPane.add(etiqueta7);

        JLabel etiqueta8 = new JLabel("Grafico Quesitos:");
        etiqueta8.setFont(new Font("Segoe UI", Font.BOLD, 13));
        etiqueta8.setForeground(colorTitulo);
        etiqueta8.setBounds(30, 454, 190, 36);
        contentPane.add(etiqueta8);

        // ===== BOTONES =====
        JButton boton1 = new JButton("1");
        boton1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton1.setBackground(colorBotonNormal);
        boton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton1.setFocusPainted(false);
        boton1.setFocusable(false);
        boton1.setRequestFocusEnabled(false);
        boton1.setBorder(new LineBorder(colorBordeBoton, 1, true));
        boton1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent evt) { boton1.setBackground(colorBotonHover); }
            @Override public void mouseExited(java.awt.event.MouseEvent evt) { boton1.setBackground(colorBotonNormal); }
        });
        boton1.setBounds(230, 90, tamBoton.width, tamBoton.height);
        contentPane.add(boton1);

        JButton boton2 = new JButton("2");
        boton2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton2.setBackground(colorBotonNormal);
        boton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton2.setFocusPainted(false);
        boton2.setFocusable(false);
        boton2.setRequestFocusEnabled(false);
        boton2.setBorder(new LineBorder(colorBordeBoton, 1, true));
        boton2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent evt) { boton2.setBackground(colorBotonHover); }
            @Override public void mouseExited(java.awt.event.MouseEvent evt) { boton2.setBackground(colorBotonNormal); }
        });
        boton2.setBounds(230, 142, tamBoton.width, tamBoton.height);
        contentPane.add(boton2);

        JButton boton3 = new JButton("3");
        boton3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton3.setBackground(colorBotonNormal);
        boton3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton3.setFocusPainted(false);
        boton3.setFocusable(false);
        boton3.setRequestFocusEnabled(false);
        boton3.setBorder(new LineBorder(colorBordeBoton, 1, true));
        boton3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent evt) { boton3.setBackground(colorBotonHover); }
            @Override public void mouseExited(java.awt.event.MouseEvent evt) { boton3.setBackground(colorBotonNormal); }
        });
        boton3.setBounds(230, 194, tamBoton.width, tamBoton.height);
        contentPane.add(boton3);

        JButton boton4 = new JButton("4");
        boton4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton4.setBackground(colorBotonNormal);
        boton4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton4.setFocusPainted(false);
        boton4.setFocusable(false);
        boton4.setRequestFocusEnabled(false);
        boton4.setBorder(new LineBorder(colorBordeBoton, 1, true));
        boton4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent evt) { boton4.setBackground(colorBotonHover); }
            @Override public void mouseExited(java.awt.event.MouseEvent evt) { boton4.setBackground(colorBotonNormal); }
        });
        boton4.setBounds(230, 246, tamBoton.width, tamBoton.height);
        contentPane.add(boton4);

        // Filas con input: botón más corto para dejar hueco al campo a la derecha
        JButton boton5 = new JButton("5 Rellena el campo de al lado");
        boton5.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton5.setBackground(colorBotonNormal);
        boton5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton5.setFocusPainted(false);
        boton5.setFocusable(false);
        boton5.setRequestFocusEnabled(false);
        boton5.setBorder(new LineBorder(colorBordeBoton, 1, true));
        boton5.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent evt) { boton5.setBackground(colorBotonHover); }
            @Override public void mouseExited(java.awt.event.MouseEvent evt) { boton5.setBackground(colorBotonNormal); }
        });
        boton5.setBounds(230, 298, 400, tamBoton.height);
        contentPane.add(boton5);

        JTextField txtIdNave = new JTextField();
        txtIdNave.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtIdNave.setColumns(14);
        txtIdNave.setToolTipText("ID nave");
        txtIdNave.setBorder(new LineBorder(colorBordeBoton, 1, true));
        txtIdNave.setBounds(645, 298, 170, 36);
        contentPane.add(txtIdNave);

        JButton boton6 = new JButton("6 Rellena el campo de al lado");
        boton6.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton6.setBackground(colorBotonNormal);
        boton6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton6.setFocusPainted(false);
        boton6.setFocusable(false);
        boton6.setRequestFocusEnabled(false);
        boton6.setBorder(new LineBorder(colorBordeBoton, 1, true));
        boton6.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent evt) { boton6.setBackground(colorBotonHover); }
            @Override public void mouseExited(java.awt.event.MouseEvent evt) { boton6.setBackground(colorBotonNormal); }
        });
        boton6.setBounds(230, 350, 400, tamBoton.height);
        contentPane.add(boton6);

        JTextField txtRango = new JTextField();
        txtRango.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtRango.setColumns(14);
        txtRango.setToolTipText("Capitán/Ingeniero/Piloto");
        txtRango.setBorder(new LineBorder(colorBordeBoton, 1, true));
        txtRango.setBounds(645, 350, 170, 36);
        contentPane.add(txtRango);

        JButton boton7 = new JButton("7");
        boton7.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton7.setBackground(colorBotonNormal);
        boton7.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton7.setFocusPainted(false);
        boton7.setFocusable(false);
        boton7.setRequestFocusEnabled(false);
        boton7.setBorder(new LineBorder(colorBordeBoton, 1, true));
        boton7.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent evt) { boton7.setBackground(colorBotonHover); }
            @Override public void mouseExited(java.awt.event.MouseEvent evt) { boton7.setBackground(colorBotonNormal); }
        });
        boton7.setBounds(230, 402, tamBoton.width, tamBoton.height);
        contentPane.add(boton7);

        JButton boton8 = new JButton("8");
        boton8.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton8.setBackground(colorBotonNormal);
        boton8.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton8.setFocusPainted(false);
        boton8.setFocusable(false);
        boton8.setRequestFocusEnabled(false);
        boton8.setBorder(new LineBorder(colorBordeBoton, 1, true));
        boton8.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent evt) { boton8.setBackground(colorBotonHover); }
            @Override public void mouseExited(java.awt.event.MouseEvent evt) { boton8.setBackground(colorBotonNormal); }
        });
        boton8.setBounds(230, 454, tamBoton.width, tamBoton.height);
        contentPane.add(boton8);

        boton1.addActionListener(e -> lanzarInforme("listado_naves.jrxml", null));
        boton2.addActionListener(e -> lanzarInformeTripulantesDesdeJava());
        boton3.addActionListener(e -> lanzarInforme("tripulacionPorNave.jrxml", null));
        boton4.addActionListener(e -> lanzarInformeMisionesPorEstadoDesdeJava());
        boton7.addActionListener(e -> lanzarInforme("grafico1.jrxml", null));
        boton8.addActionListener(e -> lanzarInforme("graficoQuesitos.jrxml", null));

        boton5.addActionListener(e -> {
            String s = txtIdNave.getText().trim();
            if (s.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce el ID de la nave.");
                return;
            }
            int idNave;
            try { idNave = Integer.parseInt(s); }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID de nave no válido.");
                return;
            }
            Map<String, Object> p = new HashMap<>();
            p.put("p_id_nave", idNave);
            lanzarInforme("misionesDeUnaNave.jrxml", p);
        });

        boton6.addActionListener(e -> {
            String rango = txtRango.getText().trim();
            if (rango.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce un rango (Capitán, Ingeniero, Piloto).");
                return;
            }
            String rangoNormalizado = normalizarRango(rango);
            if (rangoNormalizado == null) {
                JOptionPane.showMessageDialog(this, "Rango no válido. Usa: Capitán, Ingeniero o Piloto.");
                return;
            }
            Map<String, Object> p = new HashMap<>();
            p.put("p_rango", rangoNormalizado);
            lanzarInforme("tripulacionPorRango.jrxml", p);
        });
    }
    
    private String normalizarRango(String rango) {
        String r = rango.trim().toLowerCase();
        if (r.equals("capitán") || r.equals("capitan")) return "Capitán";
        if (r.equals("ingeniero")) return "Ingeniero";
        if (r.equals("piloto")) return "Piloto";
        return null;
    }

    private void conectarBaseDatos() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");


            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/flota_espacial",
                    "root",
                    ""
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error BD: " + e.getMessage());
        }
    }


    private void lanzarInforme(String jrxml, Map<String, Object> paramsExtra) {
        if (conexion == null) {
            JOptionPane.showMessageDialog(this, "No hay conexión a la base de datos.");
            return;
        }

        try {
            String path = RUTA_INFORMES + jrxml;
            System.setProperty("net.sf.jasperreports.compiler.classpath", System.getProperty("java.class.path"));

            Map<String, Object> parametros = new HashMap<>();

            parametros.put("p_fecha", new Date());
            parametros.put("p_registro", "REG-" + System.currentTimeMillis());

            if (paramsExtra != null) parametros.putAll(paramsExtra);

            JasperReport report = JasperCompileManager.compileReport(path);
            JasperPrint print = JasperFillManager.fillReport(report, parametros, conexion);
            System.out.println("Informe: " + jrxml + " pages=" + print.getPages().size());


            JasperViewer.viewReport(print, false);

            // PDF
            String out = RUTA_INFORMES + jrxml.replace(".jrxml", ".pdf");
            JasperExportManager.exportReportToPdfFile(print, out);

            JOptionPane.showMessageDialog(this, "Informe generado:\n" + out);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(this, "Error de Jasper: " + e.getMessage());
        }
    }

    public class TripulanteInfo {
        private final String nombre_tripulante;
        private final String rango;
        private final String nombre_nave;

        public TripulanteInfo(String nombre_tripulante, String rango, String nombre_nave) {
            this.nombre_tripulante = nombre_tripulante;
            this.rango = rango;
            this.nombre_nave = nombre_nave;
        }

        public String getNombre_tripulante() { return nombre_tripulante; }
        public String getRango() { return rango; }
        public String getNombre_nave() { return nombre_nave; }
    }

    
    private JRDataSource cargarTripulantes() throws Exception {
        String sql =
            "SELECT t.nombre_tripulante, t.rango, n.nombre_nave " +
            "FROM tripulantes t " +
            "LEFT JOIN naves_espaciales n ON n.id_nave = t.id_nave " +
            "ORDER BY t.rango, t.nombre_tripulante";

        List<TripulanteInfo> lista = new ArrayList<>();

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre_tripulante");
                String rango  = rs.getString("rango");
                String nave   = rs.getString("nombre_nave");
                if (nave == null) nave = "Sin asignar";

                lista.add(new TripulanteInfo(nombre, rango, nave));
            }
        }


        return new JRBeanCollectionDataSource(lista);
        
    }
    
    private void lanzarInformeTripulantesDesdeJava() {
        if (conexion == null) {
            JOptionPane.showMessageDialog(this, "No hay conexión a la base de datos.");
            return;
        }

        try {
            String jrxml = "listadoTripulantes.jrxml";
            String path = RUTA_INFORMES + jrxml;

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("p_fecha", new Date());
            parametros.put("p_registro", "REG-" + System.currentTimeMillis());

            JasperReport report = JasperCompileManager.compileReport(path);

            JRDataSource ds = cargarTripulantes();

            JasperPrint print = JasperFillManager.fillReport(report, parametros, ds);

            JasperViewer.viewReport(print, false);

            String out = RUTA_INFORMES + jrxml.replace(".jrxml", ".pdf");
            JasperExportManager.exportReportToPdfFile(print, out);

            JOptionPane.showMessageDialog(this, "Informe generado:\n" + out);
            


        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //segundo informe por java:
    public class MisionEstadoInfo {
        private final String estado;
        private final String descripcion;
        private final String nombre_nave;

        public MisionEstadoInfo(String estado, String descripcion, String nombre_nave) {
            this.estado = estado;
            this.descripcion = descripcion;
            this.nombre_nave = nombre_nave;
        }

        public String getEstado() { return estado; }
        public String getDescripcion() { return descripcion; }
        public String getNombre_nave() { return nombre_nave; }
    }
    
    private JRDataSource cargarMisionesPorEstado() throws Exception {
        String sql =
            "SELECT m.estado, m.descripcion, n.nombre_nave " +
            "FROM misiones m " +
            "LEFT JOIN naves_espaciales n ON n.id_nave = m.id_nave " +
            "ORDER BY m.estado, n.nombre_nave, m.descripcion";

        List<MisionEstadoInfo> lista = new ArrayList<>();

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String estado = rs.getString("estado");
                String desc   = rs.getString("descripcion");
                String nave   = rs.getString("nombre_nave");
                if (nave == null) nave = "Sin asignar";
                lista.add(new MisionEstadoInfo(estado, desc, nave));
            }
        }

        System.out.println("Filas datasource misiones: " + lista.size());
        return new JRBeanCollectionDataSource(lista);
    }
    
    private void lanzarInformeMisionesPorEstadoDesdeJava() {
        if (conexion == null) {
            JOptionPane.showMessageDialog(this, "No hay conexión a la base de datos.");
            return;
        }

        try {
            String jrxml = "misionesPorEstado.jrxml";
            String path = RUTA_INFORMES + jrxml;

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("p_fecha", new Date());
            parametros.put("p_registro", "REG-" + System.currentTimeMillis());

            JasperReport report = JasperCompileManager.compileReport(path);

            JRDataSource ds = cargarMisionesPorEstado();

            JasperPrint print = JasperFillManager.fillReport(report, parametros, ds);

            if (print.getPages().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe generado pero sin páginas.");
                return;
            }

            JasperViewer.viewReport(print, false);

            String out = RUTA_INFORMES + jrxml.replace(".jrxml", ".pdf");
            JasperExportManager.exportReportToPdfFile(print, out);

            JOptionPane.showMessageDialog(this, "Informe generado:\n" + out);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void crearMenu(){
    	JMenuBar menuBar = new JMenuBar();

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemVerAyuda = new JMenuItem("Ver ayuda");

        itemVerAyuda.addActionListener(e -> ayuda());

        menuAyuda.add(itemVerAyuda);
        menuBar.add(menuAyuda);

        setJMenuBar(menuBar);
    }
    
    private void ayuda() {
    	 try {
            java.net.URL hsURL = getClass().getResource("/help/help_set.hs");
            if (hsURL == null) {
            JOptionPane.showMessageDialog(this, "No se encontró /help/help_set.hs");
            return;
        }

            javax.help.HelpSet hs = new javax.help.HelpSet(getClass().getClassLoader(), hsURL);
            javax.help.HelpBroker hb = hs.createHelpBroker();

            hb.setDisplayed(true); // Solo se abre al hacer click
        } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error JavaHelp: " + ex.getMessage());
        }
    }




    public static void main(String[] args) {
        VentanaBotones v = new VentanaBotones();
        v.setVisible(true);
    }
}
