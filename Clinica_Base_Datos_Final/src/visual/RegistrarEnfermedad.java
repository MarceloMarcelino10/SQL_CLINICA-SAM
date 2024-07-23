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

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Label;
import java.awt.Choice;
import java.awt.TextField;
import java.awt.TextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class RegistrarEnfermedad extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Enfermedad tempenfer;
	private JTextField txtCodigoEnfermedad;
	private JTextField txtNombreEnfermedad;
	private JTextArea txtAreaSintomas;
	private JTextArea txtAreaTratamiento;
	private JComboBox cbxGravedad;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarEnfermedad dialog = new RegistrarEnfermedad();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarEnfermedad() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegistrarEnfermedad.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		setTitle("Registro Enfermedad");
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
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "Datos Generales:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(23, 16, 302, 117);
		panel.add(panel_1);
		
		txtCodigoEnfermedad = new JTextField();
		txtCodigoEnfermedad.setText("Ebv-#"+Clinica.getCodEnfermedad());
		txtCodigoEnfermedad.setEditable(false);
		txtCodigoEnfermedad.setColumns(10);
		txtCodigoEnfermedad.setBounds(82, 26, 172, 20);
		panel_1.add(txtCodigoEnfermedad);
		
		txtNombreEnfermedad = new JTextField();
		txtNombreEnfermedad.setColumns(10);
		txtNombreEnfermedad.setBounds(82, 69, 172, 20);
		panel_1.add(txtNombreEnfermedad);
		
		JLabel label = new JLabel("Codigo:");
		label.setBounds(21, 29, 46, 14);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("Nombre:");
		label_1.setBounds(21, 72, 63, 14);
		panel_1.add(label_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(null);
		panel_2.setBounds(348, 46, 302, 226);
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_2.add(scrollPane_1, BorderLayout.CENTER);
		
		txtAreaTratamiento = new JTextArea();
		txtAreaTratamiento.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txtAreaTratamiento.setWrapStyleWord(true);
		txtAreaTratamiento.setLineWrap(true);
		scrollPane_1.setViewportView(txtAreaTratamiento);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(23, 179, 302, 226);
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_3.add(scrollPane, BorderLayout.CENTER);
		
		txtAreaSintomas = new JTextArea();
		txtAreaSintomas.setBackground(new Color(255, 255, 255));
		txtAreaSintomas.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txtAreaSintomas.setWrapStyleWord(true);
		txtAreaSintomas.setLineWrap(true);
		scrollPane.setViewportView(txtAreaSintomas);
		
		JLabel label_2 = new JLabel("Sintomas:");
		label_2.setBounds(136, 149, 71, 14);
		panel.add(label_2);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 255, 255));
		panel_4.setLayout(null);
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estado:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(348, 288, 302, 117);
		panel.add(panel_4);
		
		cbxGravedad = new JComboBox();
		cbxGravedad.setModel(new DefaultComboBoxModel(new String[] {"<Seleccionar>", "1 - (Sin Urgencia)", "2 - (Emergencia Menor)", "3 - (Urgencia)", "4 - (Emergencia)", "5 - (Resucitacion)"}));
		cbxGravedad.setBounds(106, 47, 179, 22);
		panel_4.add(cbxGravedad);
		
		JLabel label_3 = new JLabel("Gravedad:");
		label_3.setBounds(15, 51, 76, 14);
		panel_4.add(label_3);
		
		JLabel label_4 = new JLabel("Tratamiento:");
		label_4.setBounds(464, 16, 87, 14);
		panel.add(label_4);
		setResizable(false);
		setLocationRelativeTo(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Registrar");
				okButton.setIcon(new ImageIcon(RegistrarEnfermedad.class.getResource("/imagenes/agregarOcrearboton.png")));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
				        String codigo = txtCodigoEnfermedad.getText();
				        String nombre = txtNombreEnfermedad.getText();
				        String sintomas = txtAreaSintomas.getText();
				        String gravedad = cbxGravedad.getSelectedItem().toString();
				        String tratamiento = txtAreaTratamiento.getText();
				        
				        if (nombre.isEmpty() || sintomas.isEmpty() || gravedad.equals("<Seleccionar>") || tratamiento.isEmpty()) {
				            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.ERROR_MESSAGE);
				        } else {
				        	
				            tempenfer = new Enfermedad(codigo, nombre, sintomas, tratamiento, Integer.parseInt(gravedad.substring(0, 1)));
				            Clinica.getInstance().insertarEnfermedad(tempenfer);
				            JOptionPane.showMessageDialog(null, "Operación Satisfactoria", "Registro Enfermedad", JOptionPane.INFORMATION_MESSAGE);
				            clean();
				        }
				        
				     //   tempenfer = new Enfermedad(codigo, nombre, sintomas, tratamiento, Integer.parseInt(gravedad.substring(0, 1)));
				      //  Clinica.getInstance().insertarEnfermedad(tempenfer);
				       // JOptionPane.showMessageDialog(null, "Operaci�n Satisfactoria", "Registro Enfermedad", JOptionPane.INFORMATION_MESSAGE);
				        clean();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setIcon(new ImageIcon(RegistrarEnfermedad.class.getResource("/imagenes/cancelarboton16x16.png")));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void clean() {
	    txtNombreEnfermedad.setText("");
	    txtAreaSintomas.setText("");
	    txtAreaTratamiento.setText("");
	    cbxGravedad.setSelectedIndex(0);
		txtCodigoEnfermedad.setText("Ebv-#"+Clinica.getCodEnfermedad());

	}
}////////
