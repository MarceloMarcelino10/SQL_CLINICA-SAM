package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import logico.Clinica;
import logico.Enfermedad;
import logico.Vacuna;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ScrollPaneConstants;

public class RegistrarVacuna extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JComboBox<String> cbxEnfermedades;
    private ArrayList<Enfermedad> enfermedadesSeleccionadas = new ArrayList<>();
    private JTextArea txtAreaEnfermedades;
    private Vacuna miVacuna = null;

    public static void main(String[] args) {
        try {
            RegistrarVacuna dialog = new RegistrarVacuna(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RegistrarVacuna(Vacuna modVacuna) {
    	miVacuna = modVacuna;
        setTitle(miVacuna == null ? "Registrar Vacuna" : "Modificar Vacuna");
    	setIconImage(Toolkit.getDefaultToolkit().getImage(RegistrarVacuna.class.getResource("/imagenes/fotoTituloDeVentana.png")));
    	setResizable(false);
        setBounds(100, 100, 424, 545);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Codigo:");
        lblNewLabel.setBounds(40, 19, 57, 14);
        panel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Nombre: ");
        lblNewLabel_1.setBounds(40, 52, 57, 14);
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Enfermedades:");
        lblNewLabel_2.setBounds(58, 341, 87, 14);
        panel.add(lblNewLabel_2);

        txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
        txtCodigo.setText(""+Clinica.getInstance().getCodVivienda());
        txtCodigo.setBounds(125, 16, 151, 20);
        panel.add(txtCodigo);
        txtCodigo.setColumns(10);

        txtNombre = new JTextField();
        txtNombre.setBounds(125, 49, 151, 20);
        panel.add(txtNombre);
        txtNombre.setColumns(10);

        cbxEnfermedades = new JComboBox<String>();
        cbxEnfermedades.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>"}));
        cbxEnfermedades.setBounds(157, 337, 151, 22);
        panel.add(cbxEnfermedades);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(40, 118, 327, 204);
        panel.add(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        panel_1.add(scrollPane, BorderLayout.CENTER);
        
        txtAreaEnfermedades = new JTextArea();
        txtAreaEnfermedades.setEditable(false);
        txtAreaEnfermedades.setLineWrap(true);
        scrollPane.setViewportView(txtAreaEnfermedades);
        
        JLabel lblEnfermedadesQueCura = new JLabel("Enfermedades que cura:");
        lblEnfermedadesQueCura.setBounds(40, 85, 167, 14);
        panel.add(lblEnfermedadesQueCura);
        
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		agregarEnfermedad();
        	}
        });
        btnAgregar.setBounds(76, 405, 89, 23);
        panel.add(btnAgregar);
        
        JButton btnQuitar = new JButton("Quitar");
        btnQuitar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		quitarEnfermedad();
        	}
        });
        btnQuitar.setBounds(241, 405, 89, 23);
        panel.add(btnQuitar);

        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(new Color(0, 128, 255));
        buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton();
        if(miVacuna == null) {
        	okButton.setText("Registrar");
        } else {
        	okButton.setText("Modificar");

        }
        okButton.setIcon(new ImageIcon(RegistrarVacuna.class.getResource("/imagenes/agregarOcrearboton.png")));
        okButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	   
        		registrarVacuna();
        	}
        });

        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setIcon(new ImageIcon(RegistrarVacuna.class.getResource("/imagenes/cancelarboton16x16.png")));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPane.add(cancelButton);
        
        loadCampos();
        loadEnfermedades();
    }
 
    
    private void loadEnfermedades() {
    	Clinica.getInstance().cargarDatosEnfermedadSQL();
        ArrayList<Enfermedad> todasEnfermedades = Clinica.getInstance().getMisEnfermedades();

        cbxEnfermedades.removeAllItems();
        cbxEnfermedades.addItem("<Seleccione>");
        for (Enfermedad enf : todasEnfermedades) {
            cbxEnfermedades.addItem("E-" + enf.getCodigo() + " " + enf.getNombre());
        }
    }
    

    
 
    private void agregarEnfermedad() {
        String enfermedadSeleccionada = (String) cbxEnfermedades.getSelectedItem();
        if (enfermedadSeleccionada != null && !enfermedadSeleccionada.equals("<Seleccione>")) {
            String codigo = enfermedadSeleccionada.split(" ")[0].substring(2);
            Enfermedad enfermedad = Clinica.getInstance().buscarEnfermedadById(codigo);
            if (enfermedad != null && !enfermedadesSeleccionadas.contains(enfermedad)) {
                enfermedadesSeleccionadas.add(enfermedad);
                actualizarTextoEnfermedades();
            } else if (enfermedadesSeleccionadas.contains(enfermedad)) {
                JOptionPane.showMessageDialog(this, "La enfermedad ya está en la lista.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una enfermedad.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void quitarEnfermedad() {
        String enfermedadSeleccionada = (String) cbxEnfermedades.getSelectedItem();
        if (enfermedadSeleccionada != null && !enfermedadSeleccionada.equals("<Seleccione>")) {
            String codigo = enfermedadSeleccionada.split(" ")[0].substring(2);
            Enfermedad enfermedad = Clinica.getInstance().buscarEnfermedadById(codigo);
            if (enfermedad != null) {
                // Buscar y eliminar la enfermedad de la lista
                for (Enfermedad e : enfermedadesSeleccionadas) {
                    if (e.getCodigo().equals(codigo)) {
                        enfermedadesSeleccionadas.remove(e);
                        actualizarTextoEnfermedades();
                        JOptionPane.showMessageDialog(this, "Enfermedad removida con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "La enfermedad no está en la lista.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una enfermedad.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    
    private void limpiarCampos() {
        txtNombre.setText("");
        cbxEnfermedades.setSelectedIndex(0);
        txtCodigo.setText("" +  Clinica.getInstance().getCodVivienda());
        enfermedadesSeleccionadas.clear();
        actualizarTextoEnfermedades();
    }

    
    private void actualizarTextoEnfermedades() {
        StringBuilder sb = new StringBuilder();
        for (Enfermedad enfermedad : enfermedadesSeleccionadas) {
            sb.append("E-" + enfermedad.getCodigo() + " " + enfermedad.getNombre()).append("\n");
        }
        txtAreaEnfermedades.setText(sb.toString());
    }
    
    private void loadCampos() {
        if (miVacuna != null) {
            txtCodigo.setText(miVacuna.getCodigo());
            txtNombre.setText(miVacuna.getNombre());
            enfermedadesSeleccionadas = new ArrayList<>(miVacuna.getMisEnfermedades());
            actualizarTextoEnfermedades();
        } else {
            txtCodigo.setText("" + Clinica.getInstance().getCodVivienda());
        }
    }
    
    
    private void registrarVacuna() {
        String codigo = txtCodigo.getText(); 
        String nombre = txtNombre.getText();

        if (nombre.isEmpty() || enfermedadesSeleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos y seleccione al menos una enfermedad.", "Campos vacíos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (miVacuna == null) {

            Vacuna nuevaVacuna = new Vacuna(codigo, nombre);
            nuevaVacuna.setMisEnfermedades(new ArrayList<>(enfermedadesSeleccionadas));
            Clinica.getInstance().insertarDatosVacunaSQL(nuevaVacuna);
            JOptionPane.showMessageDialog(null, "Vacuna registrada con éxito", "Registrar Vacuna", JOptionPane.INFORMATION_MESSAGE);
            
        } else {

            miVacuna.setNombre(nombre);
            miVacuna.setMisEnfermedades(new ArrayList<>(enfermedadesSeleccionadas));
            Clinica.getInstance().actualizarDatosVacunaSQL(miVacuna);
            JOptionPane.showMessageDialog(null, "Vacuna modificada con éxito", "Modificar Vacuna", JOptionPane.INFORMATION_MESSAGE);
    		ListarVacuna.cargarVacuna();
            dispose();
        }
        
        limpiarCampos();
    }

}
