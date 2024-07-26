package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Cita;
import logico.Clinica;
import logico.Consulta;
import logico.Doctor;
import logico.Enfermedad;
import sql.DatabaseConnection;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import java.awt.Cursor;
import javax.swing.ListSelectionModel;

public class RegistrarConsulta extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable citas_pendientes_table;
	private Cita selected = null;
	private static int index = -1;
	private JButton btnRegistrar;
	private static DefaultTableModel model;
	private static Object[] row;
	private JTextField txtCodigo;
	private JTextField txtFecha;
	private JTextField txtDoctorLoggeado;
	private JTextArea txtAreaDiagnostico;
	private JComboBox cbxEnfermedades;
	private ArrayList<Enfermedad> enfermedadesLista = new ArrayList<>();
	private JTextArea txtAreaEnfermedadesConsultadas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarConsulta dialog = new RegistrarConsulta();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarConsulta() {
		setTitle("Registrar Consulta");
		setResizable(false);
		setBounds(100, 100, 878, 685);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getSource() != citas_pendientes_table) {
                        citas_pendientes_table.clearSelection();
                    }
				}
			});
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(327, 36, 523, 319);
			panel.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_1.add(scrollPane, BorderLayout.CENTER);

			String[] header = { "Codigo", "Fecha Creacion", "Hora", "Persona"};
	        model = new DefaultTableModel();
	        model.setColumnIdentifiers(header);
	        citas_pendientes_table = new JTable();
	        citas_pendientes_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        citas_pendientes_table.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
					int indice = -1;
					indice = citas_pendientes_table.getSelectedRow();
					if(indice >= 0) {
						btnRegistrar.setEnabled(true);
						String codigoCita = citas_pendientes_table.getValueAt(indice, 0).toString();
			            selected = Clinica.getInstance().buscarCitaById(codigoCita);
			            System.out.println("C�digo de cita seleccionado: " + codigoCita); // Ver funcionamiento
			            loadCampos();
					}
	        	}
	        });
	        citas_pendientes_table.setModel(model);
			scrollPane.setViewportView(citas_pendientes_table);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_2.setBounds(11, 156, 305, 199);
			panel.add(panel_2);
			panel_2.setLayout(new BorderLayout(0, 0));
			
			txtAreaDiagnostico = new JTextArea();
			txtAreaDiagnostico.setLineWrap(true);
			txtAreaDiagnostico.setLocation(15, 2);
			panel_2.add(txtAreaDiagnostico, BorderLayout.CENTER);
			
			JLabel lblNewLabel = new JLabel("Codigo:");
			lblNewLabel.setBounds(15, 20, 46, 14);
			panel.add(lblNewLabel);
			
			txtCodigo = new JTextField();
			txtCodigo.setEditable(false);
			txtCodigo.setBounds(77, 17, 199, 20);
			panel.add(txtCodigo);
			txtCodigo.setColumns(10);
			
			JLabel lblNewLabel_1 = new JLabel("Fecha:");
			lblNewLabel_1.setBounds(15, 54, 46, 14);
			panel.add(lblNewLabel_1);
			
			txtFecha = new JTextField();
			txtFecha.setEditable(false);
			txtFecha.setColumns(10);
			txtFecha.setBounds(77, 51, 199, 20);
			panel.add(txtFecha);
			
			JLabel lblDoctora = new JLabel("Doctor/a:");
			lblDoctora.setBounds(15, 88, 52, 14);
			panel.add(lblDoctora);
			
			JLabel lblNewLabel_2 = new JLabel("Citas pendientes:");
			lblNewLabel_2.setBounds(327, 11, 112, 14);
			panel.add(lblNewLabel_2);
			
			txtDoctorLoggeado = new JTextField();
			txtDoctorLoggeado.setEditable(false);
			txtDoctorLoggeado.setBounds(77, 85, 199, 20);
			panel.add(txtDoctorLoggeado);
			txtDoctorLoggeado.setColumns(10);
			
			JLabel lblDiagnostico = new JLabel("Diagnostico:");
			lblDiagnostico.setBounds(12, 122, 73, 14);
			panel.add(lblDiagnostico);
			
			cbxEnfermedades = new JComboBox();
			cbxEnfermedades.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>"}));
			cbxEnfermedades.setBounds(487, 399, 305, 20);
			panel.add(cbxEnfermedades);
			
			JPanel panel_3 = new JPanel();
			panel_3.setBounds(11, 399, 305, 189);
			panel.add(panel_3);
			panel_3.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_3.add(scrollPane_1, BorderLayout.CENTER);
			
			txtAreaEnfermedadesConsultadas = new JTextArea();
			txtAreaEnfermedadesConsultadas.setLineWrap(true);
			txtAreaEnfermedadesConsultadas.setEditable(false);
			scrollPane_1.setViewportView(txtAreaEnfermedadesConsultadas);
			
			JLabel lblEnfermedadesConsultadas = new JLabel("Enfermedades consultadas:");
			lblEnfermedadesConsultadas.setBounds(11, 365, 189, 14);
			panel.add(lblEnfermedadesConsultadas);
			
			JLabel lblNewLabel_3 = new JLabel("Enfermedad:");
			lblNewLabel_3.setBounds(386, 402, 88, 14);
			panel.add(lblNewLabel_3);
			
			JButton btnAgregarEnfermedad = new JButton("Agregar");
			btnAgregarEnfermedad.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					agregarEnfermedad();
				}
			});
			btnAgregarEnfermedad.setBounds(487, 450, 89, 23);
			panel.add(btnAgregarEnfermedad);
			
			JButton btnQuitarEnfermedad = new JButton("Quitar");
			btnQuitarEnfermedad.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					quitarEnfermedad();
				}
			});
			btnQuitarEnfermedad.setBounds(595, 450, 89, 23);
			panel.add(btnQuitarEnfermedad);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnRegistrar = new JButton("Registrar");
				btnRegistrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					    
						Doctor doctorLogged = (Doctor) Clinica.getInstance().getLoggedUser();
					    int citasPendientes = Clinica.getInstance().getNumCitasPendientesPorDoctor(doctorLogged);

					    if (citasPendientes > 0) {

					        if (selected != null) {
					            String codigoConsulta = txtCodigo.getText().trim();
					            String fechaConsultaStr = txtFecha.getText().trim();
					            String diagnostico = txtAreaDiagnostico.getText().trim();

					            if (!codigoConsulta.isEmpty() && !fechaConsultaStr.isEmpty() && !diagnostico.isEmpty()) {

					                if (!enfermedadesLista.isEmpty()) {

					                    try {
					                    	
					                        Consulta nuevaConsulta = new Consulta(codigoConsulta, selected);
					                        nuevaConsulta.setMisEnfermedades(enfermedadesLista);
					                        nuevaConsulta.setDiagnostico(txtAreaDiagnostico.getText());
					                       
					                        System.out.println(nuevaConsulta.getMisEnfermedades().size());
					                      
					                        
					                        // Hacer que esa persona se convierta en un paciente y a�adir el diagn�stico a consulta
					                        
					                        selected.setRealizada(true); //La cita ahora esta realizada
					                        
					                        Clinica.getInstance().insertarConsulta(nuevaConsulta);
					                        
					                        
					                       // selected.getMiPersona()
					                        
					                        
					                        
					                        System.out.println("\nC�digo: " + nuevaConsulta.getCodigo());
					                        System.out.println("Fecha de Consulta: " + nuevaConsulta.getFechaConsulta());
					                        System.out.println("Diagn�stico: " + nuevaConsulta.getDiagnostico());
					                        System.out.println("Cita: " + nuevaConsulta.getMiCita());

					                        System.out.println("Enfermedades:");
					                        if (nuevaConsulta.getMisEnfermedades().isEmpty()) {
					                            System.out.println("No hay enfermedades.");
					                        } else {
					                            for (Enfermedad enfermedad : nuevaConsulta.getMisEnfermedades()) {
					                                System.out.println(enfermedad); 
					                            }
					                        }
					                        
					                        loadCita();
					                        clear();

					                        JOptionPane.showMessageDialog(null, "Consulta registrada con �xito.", "�xito", JOptionPane.INFORMATION_MESSAGE);

					                    } catch (Exception ex) {
					                        JOptionPane.showMessageDialog(null, "Error al registrar la consulta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					                    }
					                } else {
					                    JOptionPane.showMessageDialog(null, "Debe agregar al menos una enfermedad a la consulta.", "Error", JOptionPane.ERROR_MESSAGE);
					                }
					            } else {
					                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
					            }
					        } else {
					            JOptionPane.showMessageDialog(null, "Por favor, seleccione una cita.", "Error", JOptionPane.ERROR_MESSAGE);
					        }
					    } else {
					        JOptionPane.showMessageDialog(null, "No tiene citas pendientes para registrar una consulta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
					    }
					}
				});
				btnRegistrar.setActionCommand("OK");
				buttonPane.add(btnRegistrar);
				getRootPane().setDefaultButton(btnRegistrar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
		loadCita();
		loadEnfermedades();
	}
	
	private void loadCita() {
		
		if (Clinica.getInstance() != null && Clinica.getInstance().loggedUser != null) {
			model.setRowCount(0);
			row = new Object[model.getColumnCount()];
			for(Cita cita : Clinica.getInstance().getMisCitas()) {
				
				Doctor doctor = (Doctor) Clinica.getInstance().getLoggedUser();
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
				
				if (!cita.isRealizada() && cita.getMiDoctor().equals(doctor)) {
					
					row[0] = cita.getCodigo();
					row[1] = cita.getFechacreacion();
					row[2] = sdf.format(cita.getHoraCita());
					row[3] = cita.getMiPersona().getNombre() + " " + cita.getMiPersona().getApellidos();
					model.addRow(row);
		        }
			}
			txtCodigo.setText(String.valueOf(Clinica.getInstance().getCodConsulta()));
			txtDoctorLoggeado.setText(Clinica.getInstance().loggedUser.getNombre() + " " + Clinica.getInstance().loggedUser.getApellidos());		
		}
	}
	
	private void loadCampos() {
		
		if (Clinica.getInstance() != null && selected != null) {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        SimpleDateFormat shf = new SimpleDateFormat("hh:mm aa");
	        txtCodigo.setText(String.valueOf(Clinica.getInstance().getCodConsulta()));
	        txtFecha.setText(sdf.format(selected.getFechaCita()) + " " + shf.format(selected.getHoraCita()) );
	        System.out.println(txtFecha.getText());
	        txtDoctorLoggeado.setText(Clinica.getInstance().loggedUser.getNombre() + " " + Clinica.getInstance().loggedUser.getApellidos());
	    }
	}
	
	private void loadEnfermedades() {
		
		cbxEnfermedades.removeAllItems();
		cbxEnfermedades.addItem("<Seleccione>");
		
        for (Enfermedad enfermedad : Clinica.getInstance().getMisEnfermedades()) {
        	
        	cbxEnfermedades.addItem("E-" + enfermedad.getCodigo() + " " + enfermedad.getNombre() );
        }
        
	}
	
	private void agregarEnfermedad() {
	    
		String enfermedadSeleccionada = (String) cbxEnfermedades.getSelectedItem();

	    if (enfermedadSeleccionada != null) {
	    	
	        if (enfermedadSeleccionada.equals("<Seleccione>")) {
	        	
	            JOptionPane.showMessageDialog(null, "Seleccione una enfermedad.", "Advertencia", JOptionPane.WARNING_MESSAGE);
	            
	        } else {
	        	
	            String codigo = enfermedadSeleccionada.split(" ")[0].substring(2); // Solo el c�digo
	            Enfermedad enfermedad = Clinica.getInstance().buscarEnfermedadById(codigo);
	            
	            if (enfermedad != null) {
	            	
	                if (!enfermedadesLista.contains(enfermedad)) {
	                	
	                    enfermedadesLista.add(enfermedad);
	                    actualizarEnfermedadesConsultadas();
	                    
	                } else {
	                	
	                    JOptionPane.showMessageDialog(null, "La enfermedad ya est� en la lista.", "Advertencia", JOptionPane.WARNING_MESSAGE);
	                }
	            }
	        }
	        
	    } else {
	    	
	        JOptionPane.showMessageDialog(null, "Seleccione una enfermedad.", "Advertencia", JOptionPane.WARNING_MESSAGE);
	    }
	}
	
	private void quitarEnfermedad() {
	    
		String enfermedadSeleccionada = (String) cbxEnfermedades.getSelectedItem();
	    
	    if (enfermedadSeleccionada != null) {
	    	
	        if (enfermedadSeleccionada.equals("<Seleccione>")) {
	        	
	            JOptionPane.showMessageDialog(null, "Seleccione una enfermedad.", "Advertencia", JOptionPane.WARNING_MESSAGE);
	            
	        } else {
	        	
	            String codigo = enfermedadSeleccionada.split(" ")[0].substring(2); // Solo el c�digo
	            Enfermedad enfermedad = Clinica.getInstance().buscarEnfermedadById(codigo);
	            
	            if (enfermedad != null) {
	            	
	                if (enfermedadesLista.contains(enfermedad)) {
	                	
	                    enfermedadesLista.remove(enfermedad);
	                    actualizarEnfermedadesConsultadas();
	                    
	                } else {
	                	
	                    JOptionPane.showMessageDialog(null, "La enfermedad no est� en la lista.", "Advertencia", JOptionPane.WARNING_MESSAGE);
	                }
	            }
	        }
	        
	    } else {
	    	
	        JOptionPane.showMessageDialog(null, "Seleccione una enfermedad.", "Advertencia", JOptionPane.WARNING_MESSAGE);
	    }
	}


    private void actualizarEnfermedadesConsultadas() {
    	
        StringBuilder enfermedadesText = new StringBuilder();
        
        for (Enfermedad enf : enfermedadesLista) {
        	
            enfermedadesText.append("E-" + enf.getCodigo() + " " + enf.getNombre()).append("\n");
        }
        
        txtAreaEnfermedadesConsultadas.setText(enfermedadesText.toString());
    }
    
    private void clear() {
        txtCodigo.setText(String.valueOf(Clinica.getInstance().getCodConsulta()));
        txtFecha.setText("");
        txtAreaDiagnostico.setText("");
        txtDoctorLoggeado.setText(Clinica.getInstance().loggedUser.getNombre() + " " + Clinica.getInstance().loggedUser.getApellidos());
        cbxEnfermedades.setSelectedIndex(0);
        txtAreaEnfermedadesConsultadas.setText("");
        enfermedadesLista.clear();
        btnRegistrar.setEnabled(false);
    }

    // METODOS SQL:
 
}
