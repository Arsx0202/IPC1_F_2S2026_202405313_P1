
package com.mycompany.proyecto1s2.views;

import com.mycompany.proyecto1s2.controller.AdoptanteController;
import com.mycompany.proyecto1s2.controller.AnimalController;
import com.mycompany.proyecto1s2.controller.SolicitudController;
import com.mycompany.proyecto1s2.controller.UsuarioController;
import com.mycompany.proyecto1s2.models.Animal;
import com.mycompany.proyecto1s2.models.Adoptante;
import com.mycompany.proyecto1s2.models.Solicitud;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author celad
 */
public class Admin extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Admin.class.getName());
    private final AnimalController controlador = new AnimalController();
    private final UsuarioController controladorUsaurio = new UsuarioController();
    private final AdoptanteController controladorAdoptante = new AdoptanteController();
    private final SolicitudController controladorSolicitud = new SolicitudController();
    /**
     * Creates new form Admin
     */
    public Admin() {
        initComponents();
        componentesPersonalizados();
        componentesAdoptantes();
        cargarCombosSolicitud();
        actualizarTablaSolicitudes();
        controlador.autoAsignarCeldas();
        renderizarMapaCeldas();
        
        jTabbedPane2.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
            int index = jTabbedPane2.getSelectedIndex();

            switch (index) {
                case 0:
                    actualizarTabla();
                    break;
                case 3:
                    renderizarMapaCeldas(); 
                    System.out.println("Ejecutando renderizarMapaCeldas...");
                    break;
                case 4:
                    cargarCombosSolicitud(); 
                    actualizarTablaSolicitudes();
                break;
                default:
                break;
        }
    }
});

    }
    public void renderizarMapaCeldas() {
        pnlMapaCeldas.removeAll();
        int totalCeldas = 20;
    
        pnlMapaCeldas.setLayout(new java.awt.GridLayout(4, 5, 10, 10));

        for (int i = 1; i <= totalCeldas; i++) {
            javax.swing.JButton btnCelda = new javax.swing.JButton();
            Animal animal = controlador.obtenerAnimalEnCelda(i);

            if (animal != null) {
           
                btnCelda.setText("<html><center>Celda " + i + "<br>[" + animal.getNombre() + "]</center></html>");
                btnCelda.setBackground(new java.awt.Color(230, 81, 0));
               btnCelda.setForeground(java.awt.Color.WHITE);
                btnCelda.setToolTipText("Especie: " + animal.getEspecie() + " | Código: " + animal.getCodigo());
            } else {
                btnCelda.setText("<html><center>Celda " + i + "<br>(VACÍA)</center></html>");
                btnCelda.setBackground(new java.awt.Color(76, 175, 80)); 
                btnCelda.setForeground(java.awt.Color.WHITE);
                btnCelda.setToolTipText("Celda disponible para asignación");
            }

            btnCelda.setFocusable(false);
            pnlMapaCeldas.add(btnCelda);
        }

    
        pnlMapaCeldas.revalidate();
        pnlMapaCeldas.repaint();
    }
    
    
    
   public void actualizarTablaSolicitudes(){
    DefaultTableModel modelo = (DefaultTableModel) jTable3.getModel();
    
    modelo.setRowCount(0);

    Solicitud[] lista = controladorSolicitud.getSolicitudes();
    int total = controladorSolicitud.getContador();

    for(int i = 0; i < total; i++){
        Solicitud s = lista[i];
        if(s != null){
            modelo.addRow(new Object[]{
                s.getId(),
                s.getDpiAdoptante(),
                s.getCodigoAnimal(),
                s.getFecha(),
                s.getEstado()
            });
        }
    }
}
    public void cargarCombosSolicitud(){
        cbAdoptanteSoli.removeAllItems();
        cbAnimalSoli.removeAllItems();
        
        Adoptante[] listaAdoptantes = controladorAdoptante.getListaAdoptantes();
        int totalAdoptantes = controladorAdoptante.getContador();
        for (int i = 0; i < totalAdoptantes; i++){
            if (listaAdoptantes[i] != null){
                cbAdoptanteSoli.addItem(listaAdoptantes[i].getDpi() + " - " + listaAdoptantes[i].getNombre());
            }
        }

        Animal[] listaAnimales = controlador.getListaAnimales();
        int totalAnimales = controlador.getContador();
        for(int i = 0; i < totalAnimales; i++){
            if(listaAnimales[i] != null && listaAnimales[i].getEstado().equalsIgnoreCase("Disponible")){
                cbAnimalSoli.addItem(listaAnimales[i].getCodigo() + " - " + listaAnimales[i].getNombre());
            }
        }
    }
    private void limpiarCamposSolicitudes(){
    txtFechaSoli.setText("");
    if(cbAdoptanteSoli.getItemCount() > 0) cbAdoptanteSoli.setSelectedIndex(0);
    if(cbAnimalSoli.getItemCount() > 0) cbAnimalSoli.setSelectedIndex(0);
    jTable3.clearSelection();
}
    
    private void componentesAdoptantes(){
        String[] columnas = {"DPI", "Nombre", "Teléfono", "Dirección"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable2.setModel(modelo);

       
         jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila =  jTable2.getSelectedRow();
                if(fila != -1){
                    txtDPI.setText( jTable2.getValueAt(fila, 0).toString());
                    txtNombreAd.setText( jTable2.getValueAt(fila, 1).toString());
                    txtTelefonoAd.setText( jTable2.getValueAt(fila, 2).toString());
                    txtDireccionAd.setText( jTable2.getValueAt(fila, 3).toString());
                    txtDPI.setEditable(false); 
                }
            }
        });

        actualizarTablaAdoptantes();
    }

    private void actualizarTablaAdoptantes(){
        DefaultTableModel modelo = (DefaultTableModel)  jTable2.getModel();
        modelo.setRowCount(0);

        Adoptante[] arreglo = controladorAdoptante.getListaAdoptantes();
        if (arreglo != null) {
            for (Adoptante a : arreglo) {
                if (a != null && a.isActivo()) {
                    Object[] fila = {
                        a.getDpi(),
                        a.getNombre(),
                        a.getTelefono(),
                        a.getDireccion()
                    };
                    modelo.addRow(fila);
                }
            }
        }
    }

    private void limpiarCamposAdoptante(){
        txtDPI.setText("");
        txtNombreAd.setText("");
        txtTelefonoAd.setText("");
        txtDireccionAd.setText("");
        txtDPI.setEditable(true);
        jTable2.clearSelection();
    }
    
    
    private void componentesPersonalizados(){
       
        cbEspecie.setModel(new DefaultComboBoxModel<>(new String[]{"Perro", "Gato", "Ocelote"}));
        cbEstado.setModel(new DefaultComboBoxModel<>(new String[]{"Disponible", "En Tratamiento", "Adoptado"}));

       
        String[] columnas = {"Código", "Nombre", "Especie", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jTable1.setModel(modelo);

        
        jTable1.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt){
                tblAnimalesMouseClicked(evt);
            }
        });

        actualizarTabla();
    }
    
    private void actualizarTabla(){
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0); 

        Animal[] arreglo = controlador.getListaAnimales();
        for(Animal animal : arreglo){
        
            if(animal != null && animal.isActivo()){
                Object[] fila = {
                    animal.getCodigo(),
                    animal.getNombre(),
                    animal.getEspecie(),
                    animal.getEstado()
                };
                modelo.addRow(fila);
            }
        }
    }
    private void limpiarCampos(){
        txtCodigo.setText("");
        txtNombre.setText("");
        cbEspecie.setSelectedIndex(0);
        cbEstado.setSelectedIndex(0);
        txtCodigo.setEditable(true);
    }
    
    private void tblAnimalesMouseClicked(java.awt.event.MouseEvent evt){
        int fila = jTable1.getSelectedRow();
        if (fila != -1) {
            txtCodigo.setText(jTable1.getValueAt(fila, 0).toString());
            txtNombre.setText(jTable1.getValueAt(fila, 1).toString());
            cbEspecie.setSelectedItem(jTable1.getValueAt(fila, 2).toString());
            cbEstado.setSelectedItem(jTable1.getValueAt(fila, 3).toString());
            txtCodigo.setEditable(false);
        }
    }
public void generarReporteUsuariosHTML() {
    StringBuilder html = new StringBuilder();
    
    java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    String fechaHoraActual = java.time.LocalDateTime.now().format(dtf);

    html.append("<!DOCTYPE html>\n")
        .append("<html lang=\"es\">\n")
        .append("<head>\n")
        .append("    <meta charset=\"UTF-8\">\n")
        .append("    <title>Reporte de usuarios</title>\n")
        .append("    <style>\n")
        .append("        body { font-family: Arial, sans-serif; margin: 40px; color: #000; background-color: #fff; }\n")
        .append("        h1 { font-size: 28px; margin-bottom: 5px; }\n")
        .append("        p { font-size: 16px; margin-top: 5px; margin-bottom: 20px; }\n")
        .append("        table { width: 40%; border-collapse: collapse; margin-bottom: 20px; }\n")
        .append("        th, td { border: 1px solid #ccc; padding: 8px 12px; text-align: left; font-size: 15px; }\n")
        .append("        th { background-color: #f9f9f9; }\n")
        .append("        .total { font-size: 16px; font-weight: normal; margin-top: 15px; }\n")
        .append("    </style>\n")
        .append("</head>\n")
        .append("<body>\n")
        .append("    <h1>Reporte de usuarios</h1>\n")
        .append("    <p>Generado: " + fechaHoraActual + "</p>\n")
        .append("    <table>\n")
        .append("        <tr>\n")
        .append("            <th>Codigo</th>\n")
        .append("            <th>Usuario</th>\n")
        .append("            <th>Rol</th>\n")
        .append("        </tr>\n");

    com.mycompany.proyecto1s2.models.Usuario[] listaUsuarios = controladorUsaurio.getUsuarios();
    int totalUsuarios = 0;

    for (com.mycompany.proyecto1s2.models.Usuario u : listaUsuarios) {
        if (u != null) {
            totalUsuarios++;
            html.append("        <tr>\n")
                .append("            <td>").append(u.getCodigo()).append("</td>\n")
                .append("            <td>").append(u.getUsuario()).append("</td>\n")
                .append("            <td>").append(u.getRol()).append("</td>\n")
                .append("        </tr>\n");
        }
    }

    html.append("    </table>\n")
        .append("    <div class=\"total\">Total de usuarios: " + totalUsuarios + "</div>\n")
        .append("</body>\n")
        .append("</html>");

    String nombreArchivo = "reporte_usuarios.html";
    try (java.io.FileWriter writer = new java.io.FileWriter(nombreArchivo)) {
        writer.write(html.toString());
        
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop.getDesktop().browse(new java.io.File(nombreArchivo).toURI());
        }
        
    } catch (java.io.IOException e) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Error al generar el reporte: " + e.getMessage(), 
            "Error", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbEstado = new javax.swing.JComboBox<>();
        cbEspecie = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        BtnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtDPI = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNombreAd = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTelefonoAd = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDireccionAd = new javax.swing.JTextField();
        btnAgregar2 = new javax.swing.JButton();
        btnBuscar2 = new javax.swing.JButton();
        btnModificar2 = new javax.swing.JButton();
        btnEliminar2 = new javax.swing.JButton();
        btnLimpiar2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cbAdoptanteSoli = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cbAnimalSoli = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtFechaSoli = new javax.swing.JTextField();
        btnRegistrarAdoptante = new javax.swing.JButton();
        btnAprobarAdoptante = new javax.swing.JButton();
        btnRechazarAdoptante = new javax.swing.JButton();
        btnLimpiarAdoptante = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        pnlMapaCeldas = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        GenerarReporet = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane2.addChangeListener(this::jTabbedPane2StateChanged);

        jLabel1.setText("Codigo");

        jLabel2.setText("Nombre");

        jLabel3.setText("Especie");

        jLabel4.setText("Estado");

        cbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbEstado.addActionListener(this::cbEstadoActionPerformed);

        cbEspecie.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbEspecie.addActionListener(this::cbEspecieActionPerformed);

        jTable1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        BtnModificar.setText("Modificar");
        BtnModificar.addActionListener(this::BtnModificarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                        .addComponent(txtNombre)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnModificar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpiar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnBuscar)
                    .addComponent(BtnModificar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar))
                .addGap(46, 46, 46))
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Animales", jPanel1);

        jLabel5.setText("DPI");

        txtDPI.addActionListener(this::txtDPIActionPerformed);

        jLabel6.setText("Nombre");

        jLabel7.setText("Teléfono");

        jLabel8.setText("Dirección");

        btnAgregar2.setText("Agregar");
        btnAgregar2.addActionListener(this::btnAgregar2ActionPerformed);

        btnBuscar2.setText("Buscar");
        btnBuscar2.addActionListener(this::btnBuscar2ActionPerformed);

        btnModificar2.setText("Modificar");
        btnModificar2.addActionListener(this::btnModificar2ActionPerformed);

        btnEliminar2.setText("Eliminar");
        btnEliminar2.addActionListener(this::btnEliminar2ActionPerformed);

        btnLimpiar2.setText("Limpiar");
        btnLimpiar2.addActionListener(this::btnLimpiar2ActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btnAgregar2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnModificar2))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtDPI, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                                .addComponent(txtNombreAd, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTelefonoAd, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDireccionAd, javax.swing.GroupLayout.Alignment.LEADING))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnEliminar2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar2)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDPI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombreAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTelefonoAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDireccionAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar2)
                    .addComponent(btnBuscar2)
                    .addComponent(btnModificar2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiar2)
                    .addComponent(btnEliminar2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Adoptantes", jPanel2);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 787, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Rescate", jPanel8);

        jLabel9.setText("Adoptante");

        cbAdoptanteSoli.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Animal");

        cbAnimalSoli.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setText("Fecha");

        btnRegistrarAdoptante.setText("Registrar");
        btnRegistrarAdoptante.addActionListener(this::btnRegistrarAdoptanteActionPerformed);

        btnAprobarAdoptante.setText("Aprobar");
        btnAprobarAdoptante.addActionListener(this::btnAprobarAdoptanteActionPerformed);

        btnRechazarAdoptante.setText("Rechazar");
        btnRechazarAdoptante.addActionListener(this::btnRechazarAdoptanteActionPerformed);

        btnLimpiarAdoptante.setText("Limpiar");
        btnLimpiarAdoptante.addActionListener(this::btnLimpiarAdoptanteActionPerformed);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(cbAdoptanteSoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbAnimalSoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaSoli, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnRegistrarAdoptante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAprobarAdoptante))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnRechazarAdoptante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpiarAdoptante)))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbAdoptanteSoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbAnimalSoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFechaSoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrarAdoptante)
                    .addComponent(btnAprobarAdoptante))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRechazarAdoptante)
                    .addComponent(btnLimpiarAdoptante))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No. Solicitud", "Solicitante", "Animal", "Fecha"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jTabbedPane2.addTab("Solicitudes", jPanel3);

        javax.swing.GroupLayout pnlMapaCeldasLayout = new javax.swing.GroupLayout(pnlMapaCeldas);
        pnlMapaCeldas.setLayout(pnlMapaCeldasLayout);
        pnlMapaCeldasLayout.setHorizontalGroup(
            pnlMapaCeldasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 781, Short.MAX_VALUE)
        );
        pnlMapaCeldasLayout.setVerticalGroup(
            pnlMapaCeldasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 442, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMapaCeldas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMapaCeldas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Mapa", jPanel4);

        GenerarReporet.setText("Generar Reporte");
        GenerarReporet.addActionListener(this::GenerarReporetActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(335, 335, 335)
                .addComponent(GenerarReporet)
                .addContainerGap(337, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(204, 204, 204)
                .addComponent(GenerarReporet)
                .addContainerGap(227, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Reporte", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbEspecieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEspecieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEspecieActionPerformed

    private void cbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEstadoActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        try{
            
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            if(codigo < 0){
                JOptionPane.showMessageDialog(this, "El código debe ser un numero entero positivo");
                return;
            }

            String nombre = txtNombre.getText().trim();
            String especie = cbEspecie.getSelectedItem().toString();
            String estado = cbEstado.getSelectedItem().toString();

            if(nombre.isEmpty()){
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
                return;
            }
            
        boolean exito = controlador.agregar(codigo, nombre, especie, estado);
        if(exito){
            JOptionPane.showMessageDialog(this, "Animal agregado con éxito.");
            actualizarTabla();
            limpiarCampos();
        } else{
            JOptionPane.showMessageDialog(this, "No se pudo agregar (Código existente o lista llena).");
        }
    } catch(NumberFormatException e){
        JOptionPane.showMessageDialog(this, "El código debe ser un número entero válido.", "Error de tipo", JOptionPane.ERROR_MESSAGE);
    }
      
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
   String textoCodigo = txtCodigo.getText().trim();
    
    if(textoCodigo.isEmpty()){
        JOptionPane.showMessageDialog(this, "Ingrese el Código para buscar.", "Atención", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    try{
        int codigo = Integer.parseInt(textoCodigo);
        
        Animal a = controlador.buscar(codigo);
        if(a != null){
            txtNombre.setText(a.getNombre());
            cbEspecie.setSelectedItem(a.getEspecie());
            cbEstado.setSelectedItem(a.getEstado());
            txtCodigo.setEditable(false);
            
        }else{
            JOptionPane.showMessageDialog(this, "Animal no encontrado o dado de baja.", "Búsqueda",JOptionPane.WARNING_MESSAGE);
        }
    } catch(NumberFormatException e){
        JOptionPane.showMessageDialog(this, "El código ingresado debe ser un número entero válido.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
 String textoCodigo = txtCodigo.getText().trim();
    
    if(textoCodigo.isEmpty()){
        JOptionPane.showMessageDialog(this, "Ingrese o seleccione el Código a eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try{
        int codigo = Integer.parseInt(textoCodigo);

        int op = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de dar de baja el código: " + codigo + "?", 
            "Baja Lógica", 
            JOptionPane.YES_NO_OPTION
        );

        if(op == JOptionPane.YES_OPTION){
            boolean exito = controlador.eliminarLogico(codigo);
            if(exito) {
                JOptionPane.showMessageDialog(this, "Registro eliminado lógicamente con éxito.");
                actualizarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ningún registro activo con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch(NumberFormatException e){
        JOptionPane.showMessageDialog(this, "El código debe ser un número entero válido.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void BtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModificarActionPerformed
        // TODO add your handling code here:
String textoCodigo = txtCodigo.getText().trim();
    String nombre = txtNombre.getText().trim();

    if(textoCodigo.isEmpty() || nombre.isEmpty()){
        JOptionPane.showMessageDialog(this, "Debe ingresar el Código y el Nombre para modificar.", "Atención", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try{
        
        int codigo = Integer.parseInt(textoCodigo);
        
        String especie = cbEspecie.getSelectedItem().toString();
        String estado = cbEstado.getSelectedItem().toString();


        boolean exito = controlador.modificar(codigo, nombre, especie, estado);

        if(exito) {
            JOptionPane.showMessageDialog(this, "Registro actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();           
            limpiarCampos();            
            txtCodigo.setEditable(true);
        }else {
            JOptionPane.showMessageDialog(this, "No se encontró ningún animal activo con el código " + codigo + ".", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }catch (NumberFormatException e){
        JOptionPane.showMessageDialog(this, "El código debe ser un número entero válido.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_BtnModificarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtDPIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDPIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDPIActionPerformed

    private void btnAgregar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar2ActionPerformed
        // TODO add your handling code here:
    String dpiTxt = txtDPI.getText().trim();
    String nombre = txtNombreAd.getText().trim();
    String telTxt = txtTelefonoAd.getText().trim();
    String direccion = txtDireccionAd.getText().trim();

    if(dpiTxt.isEmpty() || nombre.isEmpty() || telTxt.isEmpty() || direccion.isEmpty()){
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Atención", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try{
        long dpi = Long.parseLong(dpiTxt);
        int telefono = Integer.parseInt(telTxt);

        if (dpi <= 0 || telefono <= 0){
            JOptionPane.showMessageDialog(this, "El DPI y teléfono deben ser valores positivos.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito = controladorAdoptante.agregar(dpi, nombre, telefono, direccion);
        if(exito){
            JOptionPane.showMessageDialog(this, "Adoptante registrado correctamente.");
            actualizarTablaAdoptantes();
            limpiarCamposAdoptante();
        } else{
            JOptionPane.showMessageDialog(this, "El DPI ya se encuentra registrado o la lista está llena.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El DPI y el teléfono deben ser valores numéricos válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnAgregar2ActionPerformed

    private void btnBuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar2ActionPerformed
        // TODO add your handling code here:
        String dpiTxt = txtDPI.getText().trim();
        if(dpiTxt.isEmpty()){
            JOptionPane.showMessageDialog(this, "Ingrese el DPI para buscar.", "Atención", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try{
            long dpi = Long.parseLong(dpiTxt);
            Adoptante a = controladorAdoptante.buscar(dpi);
            if (a != null){
                txtNombreAd.setText(a.getNombre());
                txtTelefonoAd.setText(String.valueOf(a.getTelefono()));
                txtDireccionAd.setText(a.getDireccion());
                txtDPI.setEditable(false);
            }else{
                JOptionPane.showMessageDialog(this, "Adoptante no encontrado o inactivo.", "Búsqueda", JOptionPane.WARNING_MESSAGE);
            }
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "El DPI debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscar2ActionPerformed

    private void btnModificar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificar2ActionPerformed
        // TODO add your handling code here:
    String dpiTxt = txtDPI.getText().trim();
    String nombre = txtNombreAd.getText().trim();
    String telTxt = txtTelefonoAd.getText().trim();
    String direccion = txtDireccionAd.getText().trim();

    if(dpiTxt.isEmpty() || nombre.isEmpty() || telTxt.isEmpty() || direccion.isEmpty()){
        JOptionPane.showMessageDialog(this, "Debe seleccionar un registro y completar todos los campos.", "Atención", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try{
        long dpi = Long.parseLong(dpiTxt);
        int telefono = Integer.parseInt(telTxt);

        boolean exito = controladorAdoptante.modificar(dpi, nombre, telefono, direccion);
        if (exito){
            JOptionPane.showMessageDialog(this, "Adoptante actualizado con éxito.");
            actualizarTablaAdoptantes();
            limpiarCamposAdoptante();
        } else{
            JOptionPane.showMessageDialog(this, "No se encontró un adoptante activo con ese DPI.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch(NumberFormatException e){
        JOptionPane.showMessageDialog(this, "Verifique los datos numéricos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnModificar2ActionPerformed

    private void btnEliminar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar2ActionPerformed
        // TODO add your handling code here:
        String dpiTxt = txtDPI.getText().trim();
        if(dpiTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione o ingrese el DPI a eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            long dpi = Long.parseLong(dpiTxt);
            int op = JOptionPane.showConfirmDialog(this, "¿Desea dar de baja al adoptante con DPI: " + dpi + "?", "Baja Lógica", JOptionPane.YES_NO_OPTION);

            if(op == JOptionPane.YES_OPTION) {
                boolean exito = controladorAdoptante.eliminarLogico(dpi);
                if (exito){
                    JOptionPane.showMessageDialog(this, "Adoptante dado de baja exitosamente.");
                    actualizarTablaAdoptantes();
                    limpiarCamposAdoptante();
                } else{
                    JOptionPane.showMessageDialog(this, "No se encontró un adoptante activo con ese DPI.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El DPI debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminar2ActionPerformed

    private void btnLimpiar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiar2ActionPerformed
        // TODO add your handling code here:
        limpiarCamposAdoptante();
    }//GEN-LAST:event_btnLimpiar2ActionPerformed

    private void btnRegistrarAdoptanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarAdoptanteActionPerformed
        // TODO add your handling code here:
        if(cbAdoptanteSoli.getItemCount() == 0 || cbAnimalSoli.getItemCount() == 0){
        JOptionPane.showMessageDialog(this, "Debe haber adoptantes y animales disponibles.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }
        
    String dpiAdoptante = cbAdoptanteSoli.getSelectedItem().toString();
    String codigoAnimal = cbAnimalSoli.getSelectedItem().toString();
    String fecha = txtFechaSoli.getText().trim();

    if(fecha.isEmpty()){
        JOptionPane.showMessageDialog(this, "Debe ingresar una fecha.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String id = "SOL-" + (controladorSolicitud.getContador() + 1);

    Solicitud nueva = new Solicitud(id, dpiAdoptante, codigoAnimal, fecha, "PENDIENTE");

    if(controladorSolicitud.agregarSolicitud(nueva)){
        JOptionPane.showMessageDialog(this, "Solicitud " + id + " registrada con éxito.");
        actualizarTablaSolicitudes();
        limpiarCamposSolicitudes();
    } else{
        JOptionPane.showMessageDialog(this, "No se pudo registrar la solicitud. El arreglo está lleno.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnRegistrarAdoptanteActionPerformed

    private void btnAprobarAdoptanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAprobarAdoptanteActionPerformed
        // TODO add your handling code here:
        
        int fila = jTable3.getSelectedRow();

        if(fila >= 0){
        String idSolicitud = jTable3.getValueAt(fila, 0).toString();
        Solicitud solicitud = controladorSolicitud.buscarPorId(idSolicitud);

            if(solicitud != null){
                boolean solicitudAprobada = controladorSolicitud.cambiarEstado(idSolicitud, "APROBADA");
                boolean animalActualizado = controlador.cambiarEstadoAnimal(solicitud.getCodigoAnimal(), "Adoptado");
                if(solicitudAprobada && animalActualizado){
                    controladorSolicitud.rechazarOtrasSolicitudes(solicitud.getCodigoAnimal(), idSolicitud);
                    JOptionPane.showMessageDialog(this, "Solicitud " + idSolicitud + " aprobada.\n" +
                    "El animal ha sido Adoptado.");

                    actualizarTablaSolicitudes();
                    cargarCombosSolicitud();
                    actualizarTabla();
                }else{
                    JOptionPane.showMessageDialog(this, "Se aprobó la solicitud, pero hubo un problema al actualizar los datos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        }else{
        JOptionPane.showMessageDialog(this, "Seleccione una fila de la tabla de solicitudes.", "Atención", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_btnAprobarAdoptanteActionPerformed

    private void btnRechazarAdoptanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRechazarAdoptanteActionPerformed
        // TODO add your handling code here:
        int fila = jTable3.getSelectedRow();

    if (fila >= 0){
        String id = jTable3.getValueAt(fila, 0).toString();
        if (controladorSolicitud.cambiarEstado(id, "RECHAZADA")){
            JOptionPane.showMessageDialog(this, "Solicitud " + id + " rechazada.");
            actualizarTablaSolicitudes();
        } else{
            JOptionPane.showMessageDialog(this, "No se pudo cambiar el estado de la solicitud.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else{
        JOptionPane.showMessageDialog(this, "Seleccione una fila de la tabla de solicitudes.", "Atención", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btnRechazarAdoptanteActionPerformed

    private void btnLimpiarAdoptanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarAdoptanteActionPerformed
        // TODO add your handling code here:]\
         limpiarCamposSolicitudes();
    }//GEN-LAST:event_btnLimpiarAdoptanteActionPerformed

    private void jTabbedPane2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane2StateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTabbedPane2StateChanged

    private void GenerarReporetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarReporetActionPerformed
        // TODO add your handling code here:
        generarReporteUsuariosHTML();
    }//GEN-LAST:event_GenerarReporetActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Admin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnModificar;
    private javax.swing.JButton GenerarReporet;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregar2;
    private javax.swing.JButton btnAprobarAdoptante;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscar2;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminar2;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnLimpiar2;
    private javax.swing.JButton btnLimpiarAdoptante;
    private javax.swing.JButton btnModificar2;
    private javax.swing.JButton btnRechazarAdoptante;
    private javax.swing.JButton btnRegistrarAdoptante;
    private javax.swing.JComboBox<String> cbAdoptanteSoli;
    private javax.swing.JComboBox<String> cbAnimalSoli;
    private javax.swing.JComboBox<String> cbEspecie;
    private javax.swing.JComboBox<String> cbEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JPanel pnlMapaCeldas;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDPI;
    private javax.swing.JTextField txtDireccionAd;
    private javax.swing.JTextField txtFechaSoli;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreAd;
    private javax.swing.JTextField txtTelefonoAd;
    // End of variables declaration//GEN-END:variables
}
