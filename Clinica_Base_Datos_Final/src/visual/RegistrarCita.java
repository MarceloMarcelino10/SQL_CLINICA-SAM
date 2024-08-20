package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import logico.Cita;
import logico.Clinica;
import logico.Doctor;
import logico.Persona;

import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import com.toedter.components.JSpinField;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.JRadioButton;

public class RegistrarCita extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel panel = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtHora;
	private JSpinner spnMinuto;
	private JSpinner spnHora;
	private boolean formato12 = false;
	private JRadioButton rdbtnHoraFormato;
	private JComboBox <String> cbxDoctor;
	private JComboBox <String> cbxPersona;
	private JDateChooser dateChooser;
	private Cita miCita = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarCita dialog = new RegistrarCita(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarCita(Cita cita) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrincipalVisual.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		miCita = cita;
		
		if (miCita != null) {
			setTitle("Modificar Cita");
		} else {
			setTitle("Registrar Cita");
		}

		setResizable(false);
        setBounds(100, 100, 350, 285);
        setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Codigo:");
		lblNewLabel.setBounds(27, 24, 46, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Fecha:");
		lblNewLabel_1.setBounds(27, 62, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Hora:");
		lblNewLabel_2.setBounds(27, 100, 46, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Doctor:");
		lblNewLabel_3.setBounds(27, 138, 46, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Solicitante:");
		lblNewLabel_4.setBounds(27, 176, 71, 14);
		panel.add(lblNewLabel_4);
		
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(100, 21, 200, 20);
		txtCodigo.setText("" + Clinica.getInstance().obtenerMaximoIdCita());
		panel.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		dateChooser = new JDateChooser();
		dateChooser.setDate(new Date());
	    dateChooser.setMinSelectableDate(new Date()); // Deshabilitar fechas anteriores a hoy
		dateChooser.setBounds(100, 59, 200, 20);
		panel.add(dateChooser);

		cbxDoctor = new JComboBox<String>();
		cbxDoctor.setModel(new DefaultComboBoxModel<String>(new String[] {"<Seleccione>"}));
		cbxDoctor.setBounds(100, 135, 200, 20);
		panel.add(cbxDoctor);
		
		cbxPersona = new JComboBox<String>();
		cbxPersona.setModel(new DefaultComboBoxModel<String>(new String[] {"<Seleccione>"}));
		cbxPersona.setBounds(100, 173, 200, 20);
		panel.add(cbxPersona);
		
		txtHora = new JTextField();
		txtHora.setText("00:00");
		txtHora.setEditable(false);
		txtHora.setBounds(100, 97, 120, 20);
		panel.add(txtHora);
		txtHora.setColumns(10);
		
		spnHora = new JSpinner();
		spnHora.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
		        actualizarHora();
			}
		});
		spnHora.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		spnHora.setBounds(221, 97, 15, 20);
		panel.add(spnHora);
		
		spnMinuto = new JSpinner();
		spnMinuto.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
		        actualizarHora();
			}
		});
		spnMinuto.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		spnMinuto.setBounds(239, 97, 15, 20);
		panel.add(spnMinuto);
		
		rdbtnHoraFormato = new JRadioButton("AM/PM");
		rdbtnHoraFormato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formato12 = rdbtnHoraFormato.isSelected();
				actualizarHora();
			}
		});
		rdbtnHoraFormato.setBounds(260, 97, 65, 20);
		panel.add(rdbtnHoraFormato);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton();
				
				if (miCita != null) {
					btnRegistrar = new JButton("Modificar");
				} else {
					btnRegistrar = new JButton("Registrar");
				}
				
				btnRegistrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if (validarFechaHora(dateChooser, txtHora, formato12)) {
							
							if (miCita != null) {
								
								modificarCita();

							} else {
								
								registrarCita();
							}
							
						} else {
							
							JOptionPane.showMessageDialog(null, "La fecha u hora seleccionadas no son válidas. Deben ser posteriores a la fecha y hora actuales.", "Error", JOptionPane.ERROR_MESSAGE);

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
		
		loadSolicitante();
		loadDoctor();
		loadDatosCita();
	}
	
	
	private void actualizarHora() {
	    int hora = (int) spnHora.getValue();
	    int minuto = (int) spnMinuto.getValue();
	    
	    String horaFormateada;
	    
	    if (formato12) { // Convertir a formato 12 horas
	        String periodo = "AM";
	        
	        if (hora == 0) {
	            hora = 12; // Medianoche
	        } else if (hora == 12) {
	            periodo = "PM"; // Mediodía
	        } else if (hora > 12) {
	            hora -= 12;
	            periodo = "PM";
	        }
	        
	        horaFormateada = String.format("%02d:%02d %s", hora, minuto, periodo);
	    
	    } else { // Formato 24 horas
	        horaFormateada = String.format("%02d:%02d", hora, minuto);
	    }
	    
	    txtHora.setText(horaFormateada);
	}

	private boolean validarFechaHora(JDateChooser dateChooser, JTextField txtHora, boolean formato12) {

		Calendar ahora = Calendar.getInstance();
	    int horaActual = ahora.get(Calendar.HOUR_OF_DAY);
	    int minutoActual = ahora.get(Calendar.MINUTE);
	    
	    Calendar fechaSeleccionada = Calendar.getInstance();
	    fechaSeleccionada.setTime(dateChooser.getDate());
	    
	    int horaSeleccionada = 0;
	    int minutoSeleccionado = 0;
	    
	    String horaTexto = txtHora.getText().trim();
	    
	    if (formato12) {
	    	
	        String[] partes = horaTexto.split(" ");
	        String[] horaMinuto = partes[0].split(":");
	        
	        horaSeleccionada = Integer.parseInt(horaMinuto[0]);
	        minutoSeleccionado = Integer.parseInt(horaMinuto[1]);
	        
	        if (partes[1].equalsIgnoreCase("PM") && horaSeleccionada != 12) {
	            horaSeleccionada += 12;
	        } else if (partes[1].equalsIgnoreCase("AM") && horaSeleccionada == 12) {
	            horaSeleccionada = 0;
	        }
	        
	    } else {

	    	String[] horaMinuto = horaTexto.split(":");
	        horaSeleccionada = Integer.parseInt(horaMinuto[0]);
	        minutoSeleccionado = Integer.parseInt(horaMinuto[1]);
	    }

	    fechaSeleccionada.set(Calendar.HOUR_OF_DAY, horaSeleccionada);
	    fechaSeleccionada.set(Calendar.MINUTE, minutoSeleccionado);
	    fechaSeleccionada.set(Calendar.SECOND, 0);
	    fechaSeleccionada.set(Calendar.MILLISECOND, 0);
	    
	    Calendar ahoraCompleto = Calendar.getInstance();
	    ahoraCompleto.set(Calendar.SECOND, 0);
	    ahoraCompleto.set(Calendar.MILLISECOND, 0);
	    
	    if (fechaSeleccionada.before(ahoraCompleto)) {
	        return false;
	    }
	    
	    boolean esHoy = fechaSeleccionada.get(Calendar.YEAR) == ahora.get(Calendar.YEAR) &&
	                     fechaSeleccionada.get(Calendar.MONTH) == ahora.get(Calendar.MONTH) &&
	                     fechaSeleccionada.get(Calendar.DAY_OF_MONTH) == ahora.get(Calendar.DAY_OF_MONTH);
	    
	    if (esHoy) {
	        if (horaSeleccionada < horaActual || (horaSeleccionada == horaActual && minutoSeleccionado < minutoActual)) {
	            return false;
	        }
	    }
	    
	    return true;
	}
	/*
	private void loadSolicitante() {
	    
		DefaultTableModel model = Clinica.getInstance().cargarDatosPersonaSQL(5); 
	    
	    cbxPersona.removeAllItems();
	    cbxPersona.addItem("<Seleccione>");
	    
	    for (int i = 0; i < model.getRowCount(); i++) {
	        String codigo = model.getValueAt(i, 0).toString();
	        String nombre = model.getValueAt(i, 1).toString();
	        String apellido = model.getValueAt(i, 2).toString();
	        cbxPersona.addItem("P-" + codigo + " " + nombre + " " + apellido);
	    }
	}
	*/
	private void loadSolicitante() { // Personas y pacientes pueden hacer citas

	    DefaultTableModel modelPersona = Clinica.getInstance().cargarDatosPersonaSQL(5); // Persona
	    DefaultTableModel modelPaciente = Clinica.getInstance().cargarDatosPacienteSQL(); // Paciente

	    cbxPersona.removeAllItems();
	    cbxPersona.addItem("<Seleccione>");

	    ArrayList<String> codigosPacientes = new ArrayList<>();

	    for (int i = 0; i < modelPaciente.getRowCount(); i++) {
	        String codigo = modelPaciente.getValueAt(i, 0).toString();
	        String nombre = modelPaciente.getValueAt(i, 1).toString();
	        String apellido = modelPaciente.getValueAt(i, 2).toString();

	        codigosPacientes.add(codigo);
	        cbxPersona.addItem("P-" + codigo + " " + nombre + " " + apellido);
	    }

	    for (int i = 0; i < modelPersona.getRowCount(); i++) {
	        String codigo = modelPersona.getValueAt(i, 0).toString();
	        String nombre = modelPersona.getValueAt(i, 1).toString();
	        String apellido = modelPersona.getValueAt(i, 2).toString();
	        
	        if (!codigosPacientes.contains(codigo)) {
	            cbxPersona.addItem("S-" + codigo + " " + nombre + " " + apellido);
	        }
	    }
	}

	
	private void loadDoctor() {
		
		DefaultTableModel model = Clinica.getInstance().cargarDatosDoctorSQL();
        
        cbxDoctor.removeAllItems();
        cbxDoctor.addItem("<Seleccione>");
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String codigo = model.getValueAt(i, 0).toString();
            String nombre = model.getValueAt(i, 1).toString();
            String apellido = model.getValueAt(i, 2).toString();
            cbxDoctor.addItem("D-" + codigo + " " + nombre + " " + apellido);
        }
	}
	
	private void limpiarCampos() {
	    txtCodigo.setText("" + Clinica.getInstance().obtenerMaximoIdCita());
	    dateChooser.setDate(new Date());
	    spnHora.setValue(0);
	    spnMinuto.setValue(0);
	    rdbtnHoraFormato.setSelected(false);
	    cbxDoctor.setSelectedIndex(0);
	    cbxPersona.setSelectedIndex(0);
	    txtHora.setText("00:00");
	}
	
	private void registrarCita() {
		
		if (cbxDoctor.getSelectedIndex() == 0 || cbxPersona.getSelectedIndex() == 0) {
	        JOptionPane.showMessageDialog(null, "Por favor, seleccione un doctor y un solicitante.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
		
		int id_cita = Integer.parseInt(txtCodigo.getText());
        
		Date fecha_cita = dateChooser.getDate();
        Time hora_cita = convertirHora(txtHora.getText().trim(),formato12);
        
        String id_Doctor = cbxDoctor.getSelectedItem().toString().split(" ")[0].substring(2);
        String id_Persona = cbxPersona.getSelectedItem().toString().split(" ")[0].substring(2);
        
        Persona miDoctor = Clinica.getInstance().buscarPersonaByIdSQL(id_Doctor);
        Persona miPersona = Clinica.getInstance().buscarPersonaByIdSQL(id_Persona);
        //En consulta se decide cuando esta realizada por defecto es false (no realizada)
        
        Cita cita = new Cita(""+ id_cita, miPersona, (Doctor) miDoctor, fecha_cita, hora_cita);
		Clinica.getInstance().insertarDatosCitaSQL(cita);
		
		JOptionPane.showMessageDialog(null, "Cita registrada exitosamente.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);

	    limpiarCampos();	
	    
	}
	
	private Time convertirHora(String horaTexto, boolean formato12) {
	    
		SimpleDateFormat formato;
	    
	    if (formato12) {
	        formato = new SimpleDateFormat("hh:mm a");
	    } else {
	        formato = new SimpleDateFormat("HH:mm");
	    }
	    
	    try {
	        Date fechaHora = formato.parse(horaTexto);
	        return new Time(fechaHora.getTime());
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public void modificarCita() {
		
		if (cbxDoctor.getSelectedIndex() == 0 || cbxPersona.getSelectedIndex() == 0) {
	        JOptionPane.showMessageDialog(null, "Por favor, seleccione un doctor y un solicitante.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
		
		Date fecha_cita = dateChooser.getDate();
		Time hora_cita = convertirHora(txtHora.getText().trim(),formato12);
		
		miCita.setFechaCita(fecha_cita);
		miCita.setHoraCita(hora_cita);
		
		String id_Doctor = cbxDoctor.getSelectedItem().toString().split(" ")[0].substring(2);
        String id_Persona = cbxPersona.getSelectedItem().toString().split(" ")[0].substring(2);
		
        Persona miDoctor = Clinica.getInstance().buscarPersonaByIdSQL(id_Doctor);
        Persona miPersona = Clinica.getInstance().buscarPersonaByIdSQL(id_Persona);
        
		miCita.setMiDoctor((Doctor) miDoctor);
		miCita.setMiPersona(miPersona);
		
		Clinica.getInstance().modificarDatosCitaSQL(miCita);
		
		ListarCita.loadCitas();
		
		JOptionPane.showMessageDialog(null, "Cita actualizada correctamente", "Modificación Cita", JOptionPane.INFORMATION_MESSAGE);
        dispose();
		
	}
	
	public void loadDatosCita() {
		
		if (miCita != null) {
			
			txtCodigo.setText(miCita.getCodigo());
		    dateChooser.setDate(miCita.getFechaCita());
		    Time horaCita = miCita.getHoraCita();
		    
		    spnHora.setValue(horaCita.toLocalTime().getHour());
		    spnMinuto.setValue(horaCita.toLocalTime().getMinute());
		    
		    actualizarHora();
		    
	        seleccionarItemPorId(cbxDoctor, miCita.getMiDoctor().getCodigo());

	        seleccionarItemPorId(cbxPersona, miCita.getMiPersona().getCodigo());
		    
	        System.out.println(miCita.getMiDoctor().getCodigo());
		    
		}
	}
	
	
	private void seleccionarItemPorId(JComboBox<String> comboBox, String id) {
	    
		for (int i = 0; i < comboBox.getItemCount(); i++) {
	       
			String item = comboBox.getItemAt(i);
	        String itemId = item.split(" ")[0].substring(2);
	        
	        if (itemId.equals(id)) {
	            comboBox.setSelectedIndex(i);
	            break;
	        }
	        
	    }
		
	}
	
	
}
