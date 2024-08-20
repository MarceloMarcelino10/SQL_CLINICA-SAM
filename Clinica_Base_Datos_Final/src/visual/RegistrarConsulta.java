package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

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
import logico.Paciente;
import logico.Persona;
import logico.Vacuna;
import logico.Vivienda;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import java.awt.Cursor;
import javax.swing.ListSelectionModel;

public class RegistrarConsulta extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable citas_pendientes_table;
	private Cita selected = null;
	private JButton btnRegistrar;
	private static DefaultTableModel model;
	private JTextField txtCodigo;
	private JTextField txtFecha;
	private JTextField txtDoctorLoggeado;
	private JTextArea txtAreaDiagnostico;
	private JComboBox<String> cbxEnfermedades;
	private ArrayList<Enfermedad> enfermedadesLista = new ArrayList<>();
	private JTextArea txtAreaEnfermedadesConsultadas;
	private JPanel panel;
	private JComboBox<String> cbxVivienda;
	private JComboBox<?> cbxTipoSangre;
	private JButton btnAgregarEnfermedad;
	private JButton btnQuitarEnfermedad;
	private ArrayList<String> misVacunas = new ArrayList<>();
	private Paciente paciente = null;
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
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrincipalVisual.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		setTitle("Registrar Consulta");
		setResizable(false);
		setBounds(100, 100, 878, 685);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			panel = new JPanel();
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

			String[] header = { "Codigo", "Fecha creacion", "Hora", "Persona"};
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
						selected = Clinica.getInstance().obtenerCitaByIdSQL(codigoCita);
						
						Persona persona = selected.getMiPersona();
						
						int codigo = Integer.parseInt(persona.getCodigo());
			            Vivienda vivienda = Clinica.getInstance().buscarViviendaByIdPersonaSQL(codigo);
			            String tipo_sangre = Clinica.getInstance().buscarTipoSangrePorIdPersonaSQL(codigo);
 
			            paciente = copiarDePersona(persona); 
			            paciente.setMiVivienda(vivienda);
			            paciente.setTipoSangre(tipo_sangre);
			            
			          //  System.out.println(codigo + " " + vivienda + " " + tipo_sangre);
			            
			            if (paciente == null) {
			            	
			            	paciente = (Paciente) selected.getMiPersona();
			            }
			            
			           // System.out.println(paciente.getNombre() + " " + paciente.getMiVivienda());

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
			
			cbxEnfermedades = new JComboBox<String>();
			cbxEnfermedades.setModel(new DefaultComboBoxModel<String>(new String[] {"<Seleccione>"}));
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
			
			btnAgregarEnfermedad = new JButton("Agregar");
			btnAgregarEnfermedad.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					agregarEnfermedad();
				}
			});
			btnAgregarEnfermedad.setBounds(487, 450, 89, 23);
			panel.add(btnAgregarEnfermedad);
			
			btnQuitarEnfermedad = new JButton("Quitar");
			btnQuitarEnfermedad.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					quitarEnfermedad();
				}
			});
			btnQuitarEnfermedad.setBounds(595, 450, 89, 23);
			panel.add(btnQuitarEnfermedad);
			
			JLabel lblNewLabel_4 = new JLabel("Tipo Sangre:");
			lblNewLabel_4.setBounds(386, 498, 90, 18);
			panel.add(lblNewLabel_4);
			
			cbxTipoSangre = new JComboBox();
			cbxTipoSangre.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "+A", "+B", "+AB", "-A", "-B", "-AB", "-O", "+O"}));
			cbxTipoSangre.setBounds(466, 496, 110, 22);
			panel.add(cbxTipoSangre);
			
			JLabel lblNewLabel_5 = new JLabel("Vivienda:");
			lblNewLabel_5.setBounds(595, 500, 60, 14);
			panel.add(lblNewLabel_5);
			
			cbxVivienda = new JComboBox();
			cbxVivienda.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>"}));
			cbxVivienda.setBounds(651, 496, 180, 22);
			panel.add(cbxVivienda);
			
			JLabel lblNewLabel_6 = new JLabel("Medicar:");
			lblNewLabel_6.setBounds(386, 561, 73, 14);
			panel.add(lblNewLabel_6);
			
			JButton btnNewButton = new JButton("Seleccionar Vacunas");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					seleccionarVacunas();
					
				}
			});
			btnNewButton.setBounds(466, 557, 165, 23);
			panel.add(btnNewButton);
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
				    	registrarConsulta();
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
		loadViviendas();

	}
	
	private void registrarConsulta() {

	    Doctor doctorLogged = (Doctor) Clinica.getInstance().getLoggedUser();
	    
	    if (selected == null) {
	        JOptionPane.showMessageDialog(null, "Por favor, seleccione una cita.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    String codigoConsulta = txtCodigo.getText().trim();
	    String fechaConsultaStr = txtFecha.getText().trim();
	    String diagnostico = txtAreaDiagnostico.getText().trim();
	    String tipoSangre = (String) cbxTipoSangre.getSelectedItem();
	    String viviendaSeleccionada = (String) cbxVivienda.getSelectedItem();
	    
	    if (codigoConsulta.isEmpty() || fechaConsultaStr.isEmpty() || diagnostico.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (enfermedadesLista.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Debe agregar al menos una enfermedad a la consulta.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (tipoSangre == null || tipoSangre.equals("<Seleccione>")) {
	        JOptionPane.showMessageDialog(null, "Por favor, seleccione un tipo de sangre.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (viviendaSeleccionada == null || viviendaSeleccionada.equals("<Seleccione>")) {
	        JOptionPane.showMessageDialog(null, "Por favor, seleccione una vivienda.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    
	    if (misVacunas.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Por favor, seleccione al menos una vacuna.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    
	    try {

	        String id_vivienda = viviendaSeleccionada.split(" ")[0].substring(2);
	        Vivienda miVivienda = Clinica.getInstance().buscarViviendaByIdSQL(id_vivienda);
	        
	        String id_persona = selected.getMiPersona().getCodigo();
	        Persona miPersona = Clinica.getInstance().buscarPersonaByIdSQL(id_persona);
	        
	        if (!Clinica.getInstance().esUnPacienteSQL(id_persona) && miPersona != null) { //Si no es un paciente:
	            
	        	//System.out.println(miVivienda.getCodigo());
	        	
	        	Clinica.getInstance().insertarNuevaPersonaEnPacienteSQL(id_persona, tipoSangre, Integer.parseInt(miVivienda.getCodigo()));
	            
	        } else {
	        	
	        	paciente.setMiVivienda(miVivienda);
	        	Clinica.getInstance().modificarDatosPacienteSQL(paciente);
	        	
	        }
	        
	        Consulta consulta = new Consulta(codigoConsulta, selected);
	        consulta.setMisEnfermedades(enfermedadesLista);
	        consulta.setDiagnostico(diagnostico);
	        
	        Clinica.getInstance().insertarDatosConsultasSQL(consulta, misVacunas);

	        selected.setRealizada(true);
		    Clinica.getInstance().modificarDatosCitaSQL(selected);
	        
	        JOptionPane.showMessageDialog(null, "Consulta registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	        
	        loadCita();
	        
	        clear();

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar la consulta.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void loadCita() {
		
		Persona doc = Clinica.getInstance().getLoggedUser();
		
		if (doc != null) {
			
			model = Clinica.getInstance().cargarDatosCitasPorDoctorSQL(doc.getCodigo());
			citas_pendientes_table.setModel(model);
			
			txtCodigo.setText(String.valueOf(Clinica.getInstance().obtenerMaximoIdConsulta()));
			txtDoctorLoggeado.setText(doc.getNombre() + " " + doc.getApellidos());
		}
	}
	
	private void loadCampos() {
	    
		if (Clinica.getInstance() != null && selected != null) {
	        
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        SimpleDateFormat shf = new SimpleDateFormat("hh:mm aa");
	        
	        txtCodigo.setText("" + Clinica.getInstance().obtenerMaximoIdConsulta());
	        txtFecha.setText(sdf.format(selected.getFechaCita()) + " " + shf.format(selected.getHoraCita()));
	        txtDoctorLoggeado.setText(Clinica.getInstance().loggedUser.getNombre() + " " + Clinica.getInstance().loggedUser.getApellidos());        
	        
	        if (selected.getMiPersona().getRangoUser() == 4) { // Es un paciente
	        	
	            //Paciente paciente = (Paciente) selected.getMiPersona();
	            
	            if (paciente.getMiVivienda() != null) {// Paciente con vivienda
	                
	                cbxTipoSangre.setSelectedItem(paciente.getTipoSangre());

	                String codigo = paciente.getMiVivienda().getCodigo();
	                String direccion = paciente.getMiVivienda().getDireccion();

	                String seleccionVivienda = "V-" + codigo + " " + direccion;
	                System.out.println(seleccionVivienda);
	                
	                cbxVivienda.setSelectedItem(seleccionVivienda);

	                cbxTipoSangre.setEnabled(false);
	                cbxVivienda.setEnabled(false);

	            } else { // Paciente sin vivienda
	                
	                cbxTipoSangre.setSelectedItem(paciente.getTipoSangre());
	                cbxTipoSangre.setEnabled(false);

	                cbxVivienda.setSelectedIndex(0);
	                cbxVivienda.setEnabled(true);
	            }

	        } else { // No es un paciente
	            
	            cbxTipoSangre.setSelectedIndex(0);
	            cbxTipoSangre.setEnabled(true);

	            cbxVivienda.setSelectedIndex(0);
	            cbxVivienda.setEnabled(true);
	        } 
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
    
    private void loadViviendas() {
        DefaultTableModel model = Clinica.getInstance().cargarDatosViviendaSQL();
        
        cbxVivienda.removeAllItems();
        cbxVivienda.addItem("<Seleccione>");
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String codigo = model.getValueAt(i, 0).toString();
            String direccion = model.getValueAt(i, 1).toString();

            cbxVivienda.addItem("V-" + codigo + " " + direccion);
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
        txtAreaEnfermedadesConsultadas.setText(enfermedadesText.toString());
        cbxEnfermedades.setSelectedIndex(0);
    }
    
    private void clear() {
       
        txtFecha.setText("");
        txtAreaDiagnostico.setText("");
        cbxEnfermedades.setSelectedIndex(0);
        cbxVivienda.setSelectedIndex(0);
        cbxTipoSangre.setSelectedIndex(0);
        txtAreaEnfermedadesConsultadas.setText("");
        enfermedadesLista.clear();
        btnRegistrar.setEnabled(false);
        txtCodigo.setText(""+Clinica.getInstance().obtenerMaximoIdConsulta());
    	txtDoctorLoggeado.setText(Clinica.getInstance().loggedUser.getNombre() + " " + Clinica.getInstance().loggedUser.getApellidos());
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
    
    public Paciente copiarDePersona(Persona persona) {

        return new Paciente(persona.getCodigo(), persona.getCedula(), persona.getNombre(), persona.getApellidos(), persona.getFechaNacimiento(), persona.getGenero(), 
        					persona.getUser(), persona.getPassword(), persona.getRangoUser(), null, null);
    }



    
}