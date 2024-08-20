package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

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
	private ArrayList<String> misVacunas = new ArrayList<>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ModificarConsulta dialog = new ModificarConsulta(null,null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {  
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ModificarConsulta(Consulta modConsulta, ArrayList<String> misVacunasAplicadas) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrincipalVisual.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		miConsulta = modConsulta;
		
		if( misVacunasAplicadas.isEmpty() || misVacunasAplicadas == null) {
			misVacunasAplicadas = new ArrayList<>();
			misVacunas = misVacunasAplicadas;
		} else {
			misVacunas = misVacunasAplicadas;
		}
		
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
				btnAgregarEnfermedad.setBounds(429, 322, 89, 23);
				panel.add(btnAgregarEnfermedad);
			}
			{
				btnQuitarEnferemdad = new JButton("Quitar");
				btnQuitarEnferemdad.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						quitarEnfermedad();
					}
				});
				btnQuitarEnferemdad.setBounds(537, 322, 89, 23);
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
			
			JLabel label = new JLabel("Medicar:");
			label.setBounds(349, 374, 73, 14);
			panel.add(label);
			
			JButton btnVacunas = new JButton("Seleccionar Vacunas");
			btnVacunas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					seleccionarVacunas();

				}
			});
			btnVacunas.setBounds(429, 370, 165, 23);
			panel.add(btnVacunas);
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
					    	
					    	modificarConsulta(); 		      
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
        
    	DefaultTableModel model = Clinica.getInstance().cargarDatosEnfermedadSQL();

        cbxEnfermedades.removeAllItems();
        cbxEnfermedades.addItem("<Seleccione>");
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String codigo = model.getValueAt(i, 0).toString();
            String nombre = model.getValueAt(i, 1).toString();
            cbxEnfermedades.addItem("E-" + codigo + " " + nombre);
        }
    }
	
    private void agregarEnfermedad() {
        String enfermedadSeleccionada = (String) cbxEnfermedades.getSelectedItem();
        if (enfermedadSeleccionada != null && !enfermedadSeleccionada.equals("<Seleccione>")) {
            String codigo = enfermedadSeleccionada.split(" ")[0].substring(2);
            Enfermedad enfermedad = Clinica.getInstance().obtenerEnfermedadByIdSQL(codigo);
            
            if (enfermedad != null) {
                if (gestionarEnfermedadSeleccionada(enfermedad, true)) {
                	actualizarEnfermedadesConsultadas();
                    JOptionPane.showMessageDialog(this, "Enfermedad agregada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "La enfermedad ya está en la lista.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una enfermedad.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void quitarEnfermedad() {
        String enfermedadSeleccionada = (String) cbxEnfermedades.getSelectedItem();
        if (enfermedadSeleccionada != null && !enfermedadSeleccionada.equals("<Seleccione>")) {
            String codigo = enfermedadSeleccionada.split(" ")[0].substring(2);
            Enfermedad enfermedad = Clinica.getInstance().obtenerEnfermedadByIdSQL(codigo);
            
            if (enfermedad != null) {
                if (gestionarEnfermedadSeleccionada(enfermedad, false)) {
                	actualizarEnfermedadesConsultadas();
                    JOptionPane.showMessageDialog(this, "Enfermedad removida con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "La enfermedad no está en la lista.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una enfermedad.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private boolean gestionarEnfermedadSeleccionada(Enfermedad enfermedad, boolean agregar) {
        
    	if (enfermedad == null) {
            return false;
        }

        if (agregar) {
            
            for (Enfermedad e : enfermedadesLista) {
                if (e.getCodigo().equals(enfermedad.getCodigo())) {
                    return false; // Ya esta en la lista?
                }
            }
            
            enfermedadesLista.add(enfermedad);
            
        } else {
        	
            for (Enfermedad e : enfermedadesLista) {
                if (e.getCodigo().equals(enfermedad.getCodigo())) {
                	enfermedadesLista.remove(e);
                    return true; //Eliminar si ya esta
                }
            }
            
            return false;
        }

        actualizarEnfermedadesConsultadas(); 
        return true;
    }
 
    private void actualizarEnfermedadesConsultadas() {
    	StringBuilder enfermedadesText = new StringBuilder();
    	
    	for (Enfermedad enf : enfermedadesLista) {
    		enfermedadesText.append("E-" + enf.getCodigo() + " " + enf.getNombre()).append("\n");
    	}
        txtAreaEnfermedades.setText(enfermedadesText.toString());
        cbxEnfermedades.setSelectedIndex(0);
    }
	 
	 private void modificarConsulta() {
		
		 if (miConsulta != null) {
			 
			if (enfermedadesLista.isEmpty()) {
				 JOptionPane.showMessageDialog(null, "Debe haber al menos una enfermedad en la consulta.", "Error", JOptionPane.ERROR_MESSAGE);
				 return;
			 }
			
		    if (misVacunas.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Por favor, seleccione al menos una vacuna.", "Error", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

			miConsulta.setDiagnostico(txtAreaDiagnostico.getText());
	        miConsulta.setMisEnfermedades(new ArrayList<>(enfermedadesLista));
	
	        Clinica.getInstance().modificarDatosConsultaSQL(miConsulta, misVacunas);
	        
        	JOptionPane.showMessageDialog(null, "Consulta modificada correctamente.");
        	dispose();
		 } 
	 }
	 
	 private void seleccionarVacunas() {
		 
    	if (misVacunas.isEmpty()) {
    		
        	SeleccionarVacunasAplicar seleccion = new SeleccionarVacunasAplicar();
        	seleccion.setModal(true);
        	seleccion.setVisible(true);
        	
        	misVacunas = seleccion.getVacunasSeleccionadas();
    		
    	} else {
    		
        	SeleccionarVacunasAplicar seleccion = new SeleccionarVacunasAplicar();
        	
        	seleccion.cargarVacunasPrevias(misVacunas);
        	
        	seleccion.setModal(true);
        	seleccion.setVisible(true);
        	
        	misVacunas = seleccion.getVacunasSeleccionadas();
    		
    	}
    	
	 }

}
