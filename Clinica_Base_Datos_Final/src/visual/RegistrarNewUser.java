package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Objects;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import com.toedter.calendar.JDateChooser;

import logico.Clinica;
import logico.Doctor;
import logico.Paciente;
import logico.Persona;
import logico.Vivienda;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class RegistrarNewUser extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtCedula;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtUserNew;
	private JTextField txtUser;
	private JTextField txtpassword;
	private JRadioButton rdbtnAdministrador;
	private JRadioButton rdbtnSecretaria;
	private JRadioButton rdbtnPersona;
	private JRadioButton rdbtnPaciente;
	private JPanel panelCredenciales;
	private JRadioButton rdbtnDoctor;
	private JTextField txtEspecialidad;
	private JPanel panelDoctor;
	private JPanel panelPaciente;
	private JRadioButton rdbtnSi;
	private JRadioButton rdbtnNo;
	private JDateChooser dateChooser;
	private JComboBox cbxSexo;
	private JComboBox cbxTipoSangre;
	private Persona p = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarNewUser dialog = new RegistrarNewUser(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModal(true);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarNewUser(Persona persona) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegistrarNewUser.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		setTitle("Registrar Persona");
		p = persona;
		if(p == null) {
				setTitle("Registrar Persona");
			} else {
				setTitle("Modificar Persona");
			}
		setResizable(false);
		setBounds(100, 100, 755, 575);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panelDatosGenerales = new JPanel();
		panelDatosGenerales.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos Generales:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDatosGenerales.setBounds(25, 22, 699, 172);
		contentPanel.add(panelDatosGenerales);
		panelDatosGenerales.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("C\u00F3digo:");
			lblNewLabel.setBounds(10, 23, 66, 14);
			panelDatosGenerales.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("C\u00E9dula:");
			lblNewLabel_1.setBounds(10, 60, 66, 14);
			panelDatosGenerales.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Nombre:");
			lblNewLabel_2.setBounds(10, 97, 66, 14);
			panelDatosGenerales.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Apellido:");
			lblNewLabel_3.setBounds(10, 134, 66, 14);
			panelDatosGenerales.add(lblNewLabel_3);
		}
		
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(64, 20, 142, 20);
		txtCodigo.setText("Pers-" + Clinica.getInstance().codPersona);
		panelDatosGenerales.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtCedula = new JTextField();
		txtCedula.setBounds(64, 57, 270, 20);
		panelDatosGenerales.add(txtCedula);
		txtCedula.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(64, 94, 270, 20);
		panelDatosGenerales.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(64, 131, 270, 20);
		panelDatosGenerales.add(txtApellido);
		txtApellido.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Fecha de Nacimiento:");
		lblNewLabel_4.setBounds(384, 20, 148, 20);
		panelDatosGenerales.add(lblNewLabel_4);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(384, 57, 148, 20);
		panelDatosGenerales.add(dateChooser);
		
		JLabel lblNewLabel_8 = new JLabel("Sexo:");
		lblNewLabel_8.setBounds(384, 97, 46, 14);
		panelDatosGenerales.add(lblNewLabel_8);
		
		cbxSexo = new JComboBox();
		cbxSexo.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Femenino", "Masculino"}));
		cbxSexo.setBounds(384, 131, 148, 20);
		panelDatosGenerales.add(cbxSexo);
		
		JPanel panelRegistro0123 = new JPanel();
		panelRegistro0123.setBorder(new TitledBorder(null, "Registrar:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelRegistro0123.setBounds(25, 205, 699, 68);
		contentPanel.add(panelRegistro0123);
		panelRegistro0123.setLayout(null);
		
		rdbtnAdministrador = new JRadioButton("Administrador");
		rdbtnAdministrador.setSelected(true);
		rdbtnAdministrador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnAdministrador.setSelected(true);
		        rdbtnSecretaria.setSelected(false);
		        rdbtnPersona.setSelected(false);
		        rdbtnPaciente.setSelected(false);
		        rdbtnDoctor.setSelected(false);
		        panelPaciente.setVisible(false);
		        panelDoctor.setVisible(false);
			}
		});
		rdbtnAdministrador.setBounds(25, 25, 109, 23);
		panelRegistro0123.add(rdbtnAdministrador);
		
		rdbtnSecretaria = new JRadioButton("Secretaria");
		rdbtnSecretaria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnAdministrador.setSelected(false);
		        rdbtnPersona.setSelected(false);
		        rdbtnPaciente.setSelected(false);
		        rdbtnDoctor.setSelected(false);
		        panelPaciente.setVisible(false);
		        panelDoctor.setVisible(false);
			}
		});
		rdbtnSecretaria.setBounds(159, 25, 109, 23);
		panelRegistro0123.add(rdbtnSecretaria);
		
		rdbtnPersona = new JRadioButton("Persona ");
		rdbtnPersona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnPaciente.setSelected(false);
		        rdbtnAdministrador.setSelected(false);
		        rdbtnSecretaria.setSelected(false);
		        rdbtnDoctor.setSelected(false);
		        panelPaciente.setVisible(false);
		        panelDoctor.setVisible(false);
			}
		});
		rdbtnPersona.setBounds(427, 25, 109, 23);
		panelRegistro0123.add(rdbtnPersona);
		
		rdbtnPaciente = new JRadioButton("Paciente");
		rdbtnPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnAdministrador.setSelected(false);
		        rdbtnSecretaria.setSelected(false);
		        rdbtnPersona.setSelected(false);
		        rdbtnDoctor.setSelected(false);
		        panelCredenciales.setVisible(true);
		        panelPaciente.setVisible(true);
		        panelDoctor.setVisible(false);
			}
		});
		rdbtnPaciente.setBounds(561, 25, 109, 23);
		panelRegistro0123.add(rdbtnPaciente);
		
		rdbtnDoctor = new JRadioButton("Doctor");
		rdbtnDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnAdministrador.setSelected(false);
		        rdbtnSecretaria.setSelected(false);
		        rdbtnPersona.setSelected(false);
		        rdbtnPaciente.setSelected(false);
		        panelCredenciales.setVisible(true);
		        panelPaciente.setVisible(false);
		        panelDoctor.setVisible(true);
			}
		});
		rdbtnDoctor.setBounds(293, 25, 109, 23);
		panelRegistro0123.add(rdbtnDoctor);
		
		panelCredenciales = new JPanel();
		panelCredenciales.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Credenciales:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCredenciales.setBounds(25, 295, 331, 161);
		contentPanel.add(panelCredenciales);
		panelCredenciales.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("Usuario:");
		lblNewLabel_5.setBounds(137, 18, 57, 14);
		panelCredenciales.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Contrase\u00F1a:");
		lblNewLabel_6.setBounds(126, 88, 79, 14);
		panelCredenciales.add(lblNewLabel_6);
		
		txtUser = new JTextField();
		txtUser.setBounds(60, 50, 210, 20);
		panelCredenciales.add(txtUser);
		txtUser.setColumns(10);

		txtpassword = new JTextField();
		txtpassword.setBounds(60, 120, 210, 20);
		panelCredenciales.add(txtpassword);
		txtpassword.setColumns(10);

		
		panelDoctor = new JPanel();
		panelDoctor.setVisible(false);
		panelDoctor.setBorder(new TitledBorder(null, "Datos Doctor:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDoctor.setBounds(393, 295, 331, 161);
		contentPanel.add(panelDoctor);
		panelDoctor.setLayout(null);
		
		JLabel lblNewLabel_9 = new JLabel("Especialidad:");
		lblNewLabel_9.setBounds(28, 40, 89, 14);
		panelDoctor.add(lblNewLabel_9);
		
		txtEspecialidad = new JTextField();
		txtEspecialidad.setBounds(111, 37, 190, 20);
		panelDoctor.add(txtEspecialidad);
		txtEspecialidad.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("Disponibilidad:");
		lblNewLabel_10.setBounds(124, 79, 82, 14);
		panelDoctor.add(lblNewLabel_10);
		
		rdbtnSi = new JRadioButton("S\u00ED");
		rdbtnSi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnSi.setSelected(true);
				rdbtnNo.setSelected(false);
			}
		});
		rdbtnSi.setSelected(true);
		rdbtnSi.setBounds(74, 110, 54, 23);
		panelDoctor.add(rdbtnSi);
		
		rdbtnNo = new JRadioButton("No");
		rdbtnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnNo.setSelected(true);
				rdbtnSi.setSelected(false);
			}
		});
		rdbtnNo.setBounds(202, 110, 54, 23);
		panelDoctor.add(rdbtnNo);
		
		panelPaciente = new JPanel();
		panelPaciente.setVisible(false);
		panelPaciente.setBorder(new TitledBorder(null, "Datos Paciente:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelPaciente.setDoubleBuffered(false);
		panelPaciente.setBounds(393, 295, 331, 161);
		contentPanel.add(panelPaciente);
		panelPaciente.setLayout(null);
		
		JLabel label = new JLabel("Tipo de Sangre:");
		label.setBounds(115, 42, 101, 14);
		panelPaciente.add(label);
		
		cbxTipoSangre = new JComboBox();
		cbxTipoSangre.setBounds(102, 67, 126, 20);
		cbxTipoSangre.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"}));
		panelPaciente.add(cbxTipoSangre);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistar = new JButton("Registrar");
				btnRegistar.setIcon(new ImageIcon(RegistrarNewUser.class.getResource("/imagenes/agregarOcrearboton.png")));
				if (p != null) {
					btnRegistar.setText("Modifcar");
				}
				
				btnRegistar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(p == null) {
							Persona aux = null;
							String codigo = txtCodigo.getText();
							String cedula = txtCedula.getText();
							String nombre = txtNombre.getText();
							String apellidos = txtApellido.getText();
							Date fecNacim = dateChooser.getDate();
							String sexo = cbxSexo.getSelectedItem().toString();
							String userName = txtUser.getText();
							String password = txtpassword.getText();
							
							if (Clinica.getInstance().existUserName(userName)) {
			                    
								JOptionPane.showMessageDialog(null, "�Error! El nombre de usuario ya est� en uso.", "Error", JOptionPane.ERROR_MESSAGE);
								
			                } else {
								
			                	if(rdbtnAdministrador.isSelected()) {
									aux = new Persona(codigo,cedula,nombre,apellidos,fecNacim,sexo,userName,password,4);
								} 
								if (rdbtnSecretaria.isSelected()) {
									aux = new Persona(codigo,cedula,nombre,apellidos,fecNacim,sexo,userName,password,3);
								}
								if (rdbtnDoctor.isSelected()) {
									String especialidad = txtEspecialidad.getText();
									boolean disponible = rdbtnSi.isSelected() ? true : false; 
									aux = new Doctor(codigo,cedula,nombre,apellidos,fecNacim,sexo,userName,password,2,especialidad,disponible);
								}
								if (rdbtnPersona.isSelected()) {
									aux = new Persona(codigo,cedula,nombre,apellidos,fecNacim,sexo,userName,password,1);
								}
								if (rdbtnPaciente.isSelected()) {
									String tipoSangre = cbxTipoSangre.getSelectedItem().toString();
									aux = new Paciente(codigo,cedula,nombre,apellidos,fecNacim,sexo,userName,password,0,null,tipoSangre);
									
									UIManager.put("OptionPane.okButtonText", "Continuar");				   
							        JOptionPane.showMessageDialog(null, "Necesitas agragar una vivienda!", "Agragar Vivienda", JOptionPane.INFORMATION_MESSAGE);
							        
									RegistrarVivienda regVivienda = new RegistrarVivienda(aux);
									regVivienda.setModal(true);
									regVivienda.setVisible(true);
									((Paciente)aux).setMiVivienda(regVivienda.getViviendaSeleccionada());
								}
								JOptionPane.showMessageDialog(null, "Usuario registrado con �xito", "Registrar Persona", JOptionPane.INFORMATION_MESSAGE);
								Clinica.getInstance().insertarPersona(aux);
								clean();
			                }
							
						} else {
							Persona aux = null;
							String codigo = txtCodigo.getText();
							String cedula = txtCedula.getText();
							String nombre = txtNombre.getText();
							String apellidos = txtApellido.getText();
							Date fecNacim = dateChooser.getDate();
							String sexo = cbxSexo.getSelectedItem().toString();
							String userName = txtUser.getText();
							String password = txtpassword.getText();
							
							String userNameAntiguo = (p != null) ? p.getUser() : null;
							
					       if (!Objects.equals(userName, userNameAntiguo) && Clinica.getInstance().existUserName(userName)) {
					    	   
					    	   JOptionPane.showMessageDialog(null, "�Error! El nombre de usuario ya est� en uso.", "Error", JOptionPane.ERROR_MESSAGE);
					    	   
					       } else {
					    	   
								if(p instanceof Doctor) {
									((Doctor)p).setEspecialidad(txtEspecialidad.getText());
								    ((Doctor)p).setEnServicio(rdbtnSi.isSelected());
								} else if (p instanceof Paciente) {
									((Paciente)p).setTipoSangre(cbxTipoSangre.getSelectedItem().toString());
								}
								  
								if(rdbtnAdministrador.isSelected()) {
									aux = new Persona(codigo,cedula,nombre,apellidos,fecNacim,sexo,userName,password,4);
								} 
								if (rdbtnSecretaria.isSelected()) {
									aux = new Persona(codigo,cedula,nombre,apellidos,fecNacim,sexo,userName,password,3);
								}
								if (rdbtnDoctor.isSelected()) {
									String especialidad = txtEspecialidad.getText();
									boolean disponible = rdbtnSi.isSelected() ? true : false; 
									aux = new Doctor(codigo,cedula,nombre,apellidos,fecNacim,sexo,userName,password,2,especialidad,disponible);
								}
								if (rdbtnPersona.isSelected()) {
									aux = new Persona(codigo,cedula,nombre,apellidos,fecNacim,sexo,userName,password,1);
								}
								if (rdbtnPaciente.isSelected()) {
									Vivienda v = ((Paciente)p).getMiVivienda();
									String tipoSangre = cbxTipoSangre.getSelectedItem().toString();
									aux = new Paciente(codigo,cedula,nombre,apellidos,fecNacim,sexo,userName,password,0,v,tipoSangre);
									
									String[] opciones = {"Si", "No"};
									int respuesta = JOptionPane.showOptionDialog(null, "Necesitas cambiar de vivienda?", "Modificar Vivienda", JOptionPane.YES_NO_OPTION, 
													JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
							       
							        if(respuesta == JOptionPane.YES_NO_OPTION) {
							        	RegistrarVivienda regVivienda = new RegistrarVivienda(((Paciente)aux));
										regVivienda.setModal(true);
										regVivienda.setVisible(true);
										((Paciente)aux).setMiVivienda(regVivienda.getViviendaSeleccionada());
							        }	
								}
							    JOptionPane.showMessageDialog(null, "Usuario modificado con exito", "Modificar Persona", JOptionPane.INFORMATION_MESSAGE);
							    ListarPersona.loadPersona(ListarPersona.index);
							    Clinica.getInstance().actualizarPersona(aux);
							    dispose();
			               }
						}	
					}
				});
				btnRegistar.setActionCommand("OK");
				buttonPane.add(btnRegistar);
				getRootPane().setDefaultButton(btnRegistar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.setIcon(new ImageIcon(RegistrarNewUser.class.getResource("/imagenes/cancelarboton16x16.png")));
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
		usuarioLogged();
		loadPersonas();
	}
	
	private void usuarioLogged() {
		 if (Clinica.getInstance().loggedUser != null) {
			 if(Clinica.getInstance().loggedUser.getRangoUser() == 4) {
				 rdbtnPaciente.setEnabled(false);
			 } 
			 if (Clinica.getInstance().loggedUser.getRangoUser() == 3) {
				 rdbtnAdministrador.setEnabled(false);
				 rdbtnPaciente.setEnabled(false);
				 rdbtnSecretaria.setEnabled(false);
				 rdbtnDoctor.setEnabled(false);
				 rdbtnPersona.setSelected(true);
				 rdbtnAdministrador.setSelected(false);
			 }
			 if (Clinica.getInstance().loggedUser.getRangoUser() == 2) {
				 rdbtnAdministrador.setEnabled(false);
				 rdbtnSecretaria.setEnabled(false);
				 rdbtnDoctor.setEnabled(false);
				 rdbtnPersona.setSelected(true);
				 rdbtnAdministrador.setSelected(false);
			 }
		 }
	}
	
	private void clean() {
		txtCodigo.setText("Pers-" + Clinica.getInstance().codPersona);
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtUser.setText("");
        txtpassword.setText("");
        txtEspecialidad.setText("");
        dateChooser.setDate(null);
        cbxTipoSangre.setSelectedItem("<Seleccione>");
        cbxSexo.setSelectedItem("<Seleccione>");
        dateChooser.setDate(null);
	}
	
	private void loadPersonas() {
		if(p != null) {
			txtCodigo.setText(p.getCodigo());
	        txtCedula.setText(p.getCedula());
	        txtNombre.setText(p.getNombre());
	        txtApellido.setText(p.getApellidos());
	        dateChooser.setDate(p.getFechaNacimiento());
	        txtUser.setText(p.getUser());
	        txtpassword.setText(p.getPassword());
	        cbxSexo.setSelectedItem(p.getGenero());
			
	        if(p instanceof Doctor) { //Doctor
	        	txtEspecialidad.setText(((Doctor)p).getEspecialidad());
	        	rdbtnSi.setSelected(((Doctor) p).isEnServicio());
	        	rdbtnNo.setSelected(!((Doctor) p).isEnServicio());
	        } else if (p instanceof Paciente) { //Paciente
	        	cbxTipoSangre.setSelectedItem(((Paciente) p).getTipoSangre());
	        }
	        
	        if (p.getRangoUser() == 4) { //Admin
	        	rdbtnAdministrador.setSelected(true);
		        rdbtnSecretaria.setSelected(false);
		        rdbtnPersona.setSelected(false);
		        rdbtnPaciente.setSelected(false);
		        rdbtnDoctor.setSelected(false);
		        panelPaciente.setVisible(false);
		        panelDoctor.setVisible(false);
	        } else if (p.getRangoUser() == 3) { //Secretario
	        	rdbtnSecretaria.setSelected(true);
	        	rdbtnAdministrador.setSelected(false);
		        rdbtnPersona.setSelected(false);
		        rdbtnPaciente.setSelected(false);
		        rdbtnDoctor.setSelected(false);
		        panelPaciente.setVisible(false);
		        panelDoctor.setVisible(false);
	        } else if (p.getRangoUser() == 2) { //Doctor
	        	rdbtnDoctor.setSelected(true);
				rdbtnAdministrador.setSelected(false);
		        rdbtnSecretaria.setSelected(false);
		        rdbtnPersona.setSelected(false);
		        rdbtnPaciente.setSelected(false);
		        panelCredenciales.setVisible(true);
		        panelPaciente.setVisible(false);
		        panelDoctor.setVisible(true);
	        } else if (p.getRangoUser() == 1) { //Persona
	        	rdbtnPersona.setSelected(true);
				rdbtnPaciente.setSelected(false);
		        rdbtnAdministrador.setSelected(false);
		        rdbtnSecretaria.setSelected(false);
		        rdbtnDoctor.setSelected(false);
		        panelPaciente.setVisible(false);
		        panelDoctor.setVisible(false);
	        } else if (p.getRangoUser() == 0) { //Paciente
		        rdbtnPaciente.setSelected(true);
				rdbtnAdministrador.setSelected(false);
		        rdbtnSecretaria.setSelected(false);
		        rdbtnPersona.setSelected(false);
		        rdbtnDoctor.setSelected(false);
		        panelCredenciales.setVisible(true);
		        panelPaciente.setVisible(true);
		        panelDoctor.setVisible(false);
	        }   
		}
	}
}