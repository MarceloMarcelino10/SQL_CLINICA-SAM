package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import logico.Clinica;
import logico.Enfermedad;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrarEnfermedad extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txtCodigoEnfermedad;
    private JTextField txtNombreEnfermedad;
    private JTextArea txtAreaSintomas;
    private JTextArea txtAreaTratamiento;
    private JComboBox<String> cbxGravedad;
    private Enfermedad miEnfermedad;

    public static void main(String[] args) {
        try {
            RegistrarEnfermedad dialog = new RegistrarEnfermedad(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RegistrarEnfermedad(Enfermedad enfermedad) {
        miEnfermedad = enfermedad;
        setIconImage(Toolkit.getDefaultToolkit().getImage(RegistrarEnfermedad.class.getResource("/imagenes/fotoTituloDeVentana.png")));
        if (miEnfermedad != null) {
            setTitle("Modificar Enfermedad");
        } else {
            setTitle("Registrar Enfermedad");
        }

        setBounds(100, 100, 680, 490);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 128, 255));
        panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        panel_1.setLayout(null);
        panel_1.setBorder(new TitledBorder(null, "Datos Generales:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(23, 16, 302, 117);
        panel.add(panel_1);

        JLabel labelCodigo = new JLabel("Codigo:");
        labelCodigo.setBounds(21, 29, 46, 14);
        panel_1.add(labelCodigo);

        txtCodigoEnfermedad = new JTextField();
        txtCodigoEnfermedad.setEditable(false);
        txtCodigoEnfermedad.setBounds(82, 26, 172, 20);
        panel_1.add(txtCodigoEnfermedad);
        txtCodigoEnfermedad.setColumns(10);

        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setBounds(21, 72, 63, 14);
        panel_1.add(labelNombre);

        txtNombreEnfermedad = new JTextField();
        txtNombreEnfermedad.setBounds(82, 69, 172, 20);
        panel_1.add(txtNombreEnfermedad);
        txtNombreEnfermedad.setColumns(10);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(348, 46, 302, 226);
        panel.add(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));

        txtAreaTratamiento = new JTextArea();
        txtAreaTratamiento.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtAreaTratamiento.setWrapStyleWord(true);
        txtAreaTratamiento.setLineWrap(true);
        JScrollPane scrollPaneTratamiento = new JScrollPane(txtAreaTratamiento);
        scrollPaneTratamiento.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel_2.add(scrollPaneTratamiento, BorderLayout.CENTER);

        JPanel panel_3 = new JPanel();
        panel_3.setBounds(23, 179, 302, 226);
        panel.add(panel_3);
        panel_3.setLayout(new BorderLayout(0, 0));

        txtAreaSintomas = new JTextArea();
        txtAreaSintomas.setBackground(Color.WHITE);
        txtAreaSintomas.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtAreaSintomas.setWrapStyleWord(true);
        txtAreaSintomas.setLineWrap(true);
        JScrollPane scrollPaneSintomas = new JScrollPane(txtAreaSintomas);
        scrollPaneSintomas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel_3.add(scrollPaneSintomas, BorderLayout.CENTER);

        JLabel labelSintomas = new JLabel("Sintomas:");
        labelSintomas.setBounds(136, 149, 71, 14);
        panel.add(labelSintomas);

        JPanel panel_4 = new JPanel();
        panel_4.setBackground(Color.WHITE);
        panel_4.setLayout(null);
        panel_4.setBorder(new TitledBorder(null, "Estado:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_4.setBounds(348, 288, 302, 117);
        panel.add(panel_4);

        cbxGravedad = new JComboBox<>();
        cbxGravedad.setModel(new DefaultComboBoxModel<>(new String[] {"<Seleccionar>", "1 - Leve", "2 - Moderada", "3 - Grave"}));
        cbxGravedad.setBounds(106, 47, 179, 22);
        panel_4.add(cbxGravedad);

        JLabel labelGravedad = new JLabel("Gravedad:");
        labelGravedad.setBounds(15, 51, 76, 14);
        panel_4.add(labelGravedad);

        JLabel labelTratamiento = new JLabel("Tratamiento:");
        labelTratamiento.setBounds(464, 16, 87, 14);
        panel.add(labelTratamiento);

        setResizable(false);
        setLocationRelativeTo(null);

        JPanel buttonPane = new JPanel();
        buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton();
        okButton.setIcon(new ImageIcon(RegistrarEnfermedad.class.getResource("/imagenes/agregarOcrearboton.png")));
        
        if (miEnfermedad != null) {
            okButton.setText("Modificar");
        } else {
            okButton.setText("Registrar");
        }
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	String nombre = txtNombreEnfermedad.getText();
                String sintomas = txtAreaSintomas.getText();
                String gravedad = cbxGravedad.getSelectedItem().toString();
                String tratamiento = txtAreaTratamiento.getText();

                if (nombre.isEmpty() || sintomas.isEmpty() || gravedad.equals("<Seleccionar>") || tratamiento.isEmpty()) {
                    
                	JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.ERROR_MESSAGE);
                    
                } else {
                	
                    int gravedadValue = Integer.parseInt(gravedad.substring(0, 1));
                    
                    if (miEnfermedad != null) {
                    	
                        miEnfermedad.setNombre(nombre);
                        miEnfermedad.setSintomas(sintomas);
                        miEnfermedad.setTratamiento(tratamiento);
                        miEnfermedad.setGravedad(gravedadValue);
                        
                        Clinica.getInstance().actualizarDatosEnfermedadSQL(miEnfermedad);
                        
                        JOptionPane.showMessageDialog(null, "Enfermedad actualizada correctamente", "Modificación Enfermedad", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    
                    } else {
                    	
                        miEnfermedad = new Enfermedad(String.valueOf(Clinica.getInstance().obtenerMaximoIdEnfermedad()), nombre, sintomas, tratamiento, gravedadValue);
                        
                        Clinica.getInstance().insertarDatosEnfermedadSQL(miEnfermedad);
                        
                        JOptionPane.showMessageDialog(null, "Enfermedad registrada correctamente", "Registro Enfermedad", JOptionPane.INFORMATION_MESSAGE);
                        
                    }
                    clean();
                }
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setIcon(new ImageIcon(RegistrarEnfermedad.class.getResource("/imagenes/cancelarboton16x16.png")));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }); 
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        loadEnfermedad();
    }

    private void loadEnfermedad() {
    	
    	if (miEnfermedad != null) {
	       
    		txtNombreEnfermedad.setText(miEnfermedad.getNombre());
	        txtAreaSintomas.setText(miEnfermedad.getSintomas());
	        txtAreaTratamiento.setText(miEnfermedad.getTratamiento());
	        cbxGravedad.setSelectedIndex(miEnfermedad.getGravedad());

        	txtCodigoEnfermedad.setText(miEnfermedad.getCodigo());
        } else {
        	
            txtCodigoEnfermedad.setText(String.valueOf(Clinica.getInstance().obtenerMaximoIdEnfermedad()));
        }        
    }

    private void clean() {
        txtNombreEnfermedad.setText("");
        txtAreaSintomas.setText("");
        txtAreaTratamiento.setText("");
        cbxGravedad.setSelectedIndex(0);
        txtCodigoEnfermedad.setText(String.valueOf(Clinica.getInstance().obtenerMaximoIdEnfermedad()));
    }
  
}

