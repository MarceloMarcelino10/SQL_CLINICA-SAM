package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import logico.Clinica;
import logico.Consulta;
import logico.Enfermedad;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModificarConsulta extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Consulta miConsulta = null;
	private JButton btnModificar;
	private JButton btnCancelar;
	private JButton btnAgregarEnfermedad;
	private JButton btnQuitarEnferemdad;
	private JComboBox cbxEnfermedades;
	private JTextArea txtAreaDiagnostico;
	private JTextArea txtAreaEnfermedades;
	private ArrayList<Enfermedad> enfermedadesLista = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ModificarConsulta dialog = new ModificarConsulta(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {  
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ModificarConsulta(Consulta modConsulta) {
		miConsulta = modConsulta;
		setTitle("Modificar Diagnostico");
		setResizable(false);
		setBounds(100, 100, 695, 495);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JLabel label = new JLabel("Enfermedad:");
				label.setBounds(349, 294, 88, 14);
				panel.add(label);
			}
			{
				cbxEnfermedades = new JComboBox();
				cbxEnfermedades.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>"}));
				cbxEnfermedades.setBounds(429, 291, 197, 20);
				panel.add(cbxEnfermedades);
			}
			{
				btnAgregarEnfermedad = new JButton("Agregar");
				btnAgregarEnfermedad.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						agregarEnfermedad();
					}
				});
				btnAgregarEnfermedad.setBounds(429, 342, 89, 23);
				panel.add(btnAgregarEnfermedad);
			}
			{
				btnQuitarEnferemdad = new JButton("Quitar");
				btnQuitarEnferemdad.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						quitarEnfermedad();
					}
				});
				btnQuitarEnferemdad.setBounds(537, 342, 89, 23);
				panel.add(btnQuitarEnferemdad);
			}
			{
				JLabel label = new JLabel("Diagnostico:");
				label.setBounds(24, 22, 73, 14);
				panel.add(label);
			}
			{
				JLabel label = new JLabel("Enfermedades consultadas:");
				label.setBounds(349, 19, 189, 14);
				panel.add(label);
			}
			
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(349, 47, 301, 194);
			panel.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_1.add(scrollPane, BorderLayout.CENTER);
			
			txtAreaEnfermedades = new JTextArea();
			txtAreaEnfermedades.setLineWrap(true);
			txtAreaEnfermedades.setEditable(false);
			scrollPane.setViewportView(txtAreaEnfermedades);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_2.setBounds(24, 47, 301, 358);
			panel.add(panel_2);
			panel_2.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane_1 = new JScrollPane();
				scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
				panel_2.add(scrollPane_1);
				{
					txtAreaDiagnostico = new JTextArea();
					txtAreaDiagnostico.setLineWrap(true);
					scrollPane_1.setViewportView(txtAreaDiagnostico);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnModificar = new JButton("Modificar");
				btnModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if (enfermedadesLista.isEmpty()) {
					        JOptionPane.showMessageDialog(null, "Debe haber al menos una enfermedad en la consulta.", "Error", JOptionPane.ERROR_MESSAGE);
					    } else {
					    	
					    	modificarConsulta(); //en el metodo lo de sql y vaina llamado desde clinica
					        // Aquí puedes realizar las operaciones adicionales que necesites, como guardar la consulta en la base de datos.
					        
					        
					        dispose();
					    }
						
					}
				});
				btnModificar.setActionCommand("OK");
				buttonPane.add(btnModificar);
				getRootPane().setDefaultButton(btnModificar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
		
		loadCampos();
		loadEnfermedades();
	}
	
	private void loadCampos() {
        if (miConsulta != null) {
            txtAreaDiagnostico.setText(miConsulta.getDiagnostico());
            enfermedadesLista = new ArrayList<>(miConsulta.getMisEnfermedades());
            actualizarEnfermedadesConsultadas();
        }
    }
	
	private void loadEnfermedades() {
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
            if (enfermedad != null && !enfermedadesLista.contains(enfermedad)) {
                enfermedadesLista.add(enfermedad);
                actualizarEnfermedadesConsultadas();
            } else if (enfermedadesLista.contains(enfermedad)) {
                JOptionPane.showMessageDialog(null, "La enfermedad ya está en la lista.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una enfermedad válida.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
	
	 private void quitarEnfermedad() {
        String enfermedadSeleccionada = (String) cbxEnfermedades.getSelectedItem();
        if (enfermedadSeleccionada != null && !enfermedadSeleccionada.equals("<Seleccione>")) {
            String codigo = enfermedadSeleccionada.split(" ")[0].substring(2);
            Enfermedad enfermedad = Clinica.getInstance().buscarEnfermedadById(codigo);
            if (enfermedad != null && enfermedadesLista.remove(enfermedad)) {
                actualizarEnfermedadesConsultadas();
            } else {
                JOptionPane.showMessageDialog(null, "La enfermedad no está en la lista.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una enfermedad válida.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
	 
	 private void actualizarEnfermedadesConsultadas() {
        StringBuilder enfermedadesText = new StringBuilder();
        for (Enfermedad enf : enfermedadesLista) {
            enfermedadesText.append("E-").append(enf.getCodigo()).append(" ").append(enf.getNombre()).append("\n");
        }
        txtAreaEnfermedades.setText(enfermedadesText.toString());
    }
	 
	 private void modificarConsulta() {
		if (miConsulta != null) {
			 if (enfermedadesLista.isEmpty()) {
				 JOptionPane.showMessageDialog(null, "Debe haber al menos una enfermedad en la consulta.", "Error", JOptionPane.ERROR_MESSAGE);
				 return;
			 }

		miConsulta.setDiagnostico(txtAreaDiagnostico.getText());
        miConsulta.setMisEnfermedades(new ArrayList<>(enfermedadesLista));

        //Imprementar db
        Clinica.getInstance().actualizarConsulta(miConsulta);

        JOptionPane.showMessageDialog(null, "Consulta modificada correctamente.");
        dispose();
    }
}
	 
}
