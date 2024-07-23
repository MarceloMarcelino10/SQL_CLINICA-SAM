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

public class RegistrarVacuna extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JComboBox<String> cbxEnfermedades;

    public static void main(String[] args) {
        try {
            RegistrarVacuna dialog = new RegistrarVacuna();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RegistrarVacuna() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(RegistrarVacuna.class.getResource("/imagenes/fotoTituloDeVentana.png")));
    	setTitle("Registrar Vacuna");
    	setResizable(false);
        setBounds(100, 100, 412, 227);
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
        lblNewLabel.setBounds(78, 27, 57, 14);
        panel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Nombre: ");
        lblNewLabel_1.setBounds(78, 68, 57, 14);
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Enfermedades:");
        lblNewLabel_2.setBounds(48, 109, 87, 14);
        panel.add(lblNewLabel_2);

        txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
        txtCodigo.setText("Vacu#"+Clinica.getInstance().codVacuna);
        txtCodigo.setBounds(157, 24, 151, 20);
        panel.add(txtCodigo);
        txtCodigo.setColumns(10);

        txtNombre = new JTextField();
        txtNombre.setBounds(157, 65, 151, 20);
        panel.add(txtNombre);
        txtNombre.setColumns(10);

        cbxEnfermedades = new JComboBox<String>();
        cbxEnfermedades.setBounds(157, 105, 151, 22);
        panel.add(cbxEnfermedades);

        // Cargar las enfermedades al iniciar la ventana
        cargarEnfermedades();

        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(new Color(0, 128, 255));
        buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Registrar");
        okButton.setIcon(new ImageIcon(RegistrarVacuna.class.getResource("/imagenes/agregarOcrearboton.png")));
        okButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    String codigo = txtCodigo.getText();
        	    String nombre = txtNombre.getText();
        	    String enfermedadSeleccionada = cbxEnfermedades.getSelectedItem().toString();

        	    if (nombre.isEmpty() || enfermedadSeleccionada.equals("<Seleccionar>")) {
        	        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.ERROR_MESSAGE);
        	    } else {
        	        Clinica clinica = Clinica.getInstance();
        	        ArrayList<Enfermedad> enfermedades = clinica.getInstance().getMisEnfermedades();
        	        Enfermedad enfermedad = null;

        	        for (Enfermedad enf : enfermedades) {
        	            if (enf.getNombre().equals(enfermedadSeleccionada)) {
        	                enfermedad = enf;
        	                break;
        	            }
        	        }

        	        if (enfermedad != null) {
        	            Vacuna vacuna = new Vacuna(codigo, nombre);
        	            vacuna.agregarEnfermedad(enfermedad);
        	            clinica.insertarVacuna(vacuna);
        	            clinica.guardarDatos();
        	            JOptionPane.showMessageDialog(null, "Registrado con Éxito", "Registrar Vacuna", JOptionPane.INFORMATION_MESSAGE);
        	            limpiarCampos();
        	        } else {
        	            JOptionPane.showMessageDialog(null, "Error al obtener la enfermedad seleccionada", "Error", JOptionPane.ERROR_MESSAGE);
        	        }
        	    }
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
    }

    // Metodo para cargar las enfermedades en el JComboBox al iniciar la ventana
    private void cargarEnfermedades() {
        Clinica clinica = Clinica.getInstance();
        ArrayList<Enfermedad> enfermedades = clinica.getEnfermedadesRegistradas();

        cbxEnfermedades.removeAllItems();
        cbxEnfermedades.addItem("<Seleccione>");
        // Agregar las enfermedades al JComboBox
        for (Enfermedad enfermedad : enfermedades) {
            cbxEnfermedades.addItem(enfermedad.getNombre());
        }
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        cbxEnfermedades.setSelectedIndex(0); 
        txtCodigo.setText("Vac-#" + Clinica.getInstance().codVacuna); 
    }
}
