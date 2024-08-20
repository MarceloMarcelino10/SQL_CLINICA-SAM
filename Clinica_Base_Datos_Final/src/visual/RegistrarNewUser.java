package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import com.toedter.calendar.JDateChooser;

import logico.Clinica;
import logico.Doctor;
import logico.Paciente;
import logico.Persona;
import logico.Vivienda;
import seguridad.GestorUsuario;

import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
	private JButton btnCambiarPassword;
	private String tempPassword = "";
	private JButton btnGenerarFecha;
	private JPanel panelRegistro0123;

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
		txtCodigo.setText("" + Clinica.getInstance().obtenerMaximoIdPersona());
		panelDatosGenerales.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtCedula = new JTextField();
		txtCedula.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				formatCedulaField(txtCedula, e);
			}
		});
		txtCedula.setBounds(64, 57, 270, 20);
		panelDatosGenerales.add(txtCedula);
		txtCedula.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				
				limitTextFieldLength(txtNombre, e, 30);
			}
		});
		txtNombre.setBounds(64, 94, 270, 20);
		panelDatosGenerales.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellido = new JTextField();
		txtApellido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				
				limitTextFieldLength(txtApellido, e, 30);
			}
		});
		txtApellido.setBounds(64, 131, 270, 20);
		panelDatosGenerales.add(txtApellido); 
		txtApellido.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Fecha de Nacimiento:");
		lblNewLabel_4.setBounds(384, 20, 148, 20);
		panelDatosGenerales.add(lblNewLabel_4);
		
		dateChooser = new JDateChooser();
		dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) { 
				
				Date selectedDate = dateChooser.getDate();
		        if (selectedDate != null && selectedDate.after(new Date())) {
		            JOptionPane.showMessageDialog(null, "La fecha seleccionada no puede ser mayor que la fecha actual.", "Error", JOptionPane.ERROR_MESSAGE);
		            //SetRandomDate(dateChooser,18);
		        }
				
			}
		});
		dateChooser.setBounds(384, 57, 148, 20);
		dateChooser.setMaxSelectableDate(new Date()); // Deshabilitar fechas posteriores a hoy
		panelDatosGenerales.add(dateChooser);
		
		JLabel lblNewLabel_8 = new JLabel("Sexo:");
		lblNewLabel_8.setBounds(384, 97, 46, 14);
		panelDatosGenerales.add(lblNewLabel_8);
		
		cbxSexo = new JComboBox();
		cbxSexo.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Femenino", "Masculino"}));
		cbxSexo.setBounds(384, 131, 148, 20);
		panelDatosGenerales.add(cbxSexo);
		
		btnGenerarFecha = new JButton("Random");
		btnGenerarFecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showAgeInputDialog(dateChooser);

			}
		});
		btnGenerarFecha.setBounds(542, 57, 89, 20);
		panelDatosGenerales.add(btnGenerarFecha);
		
		panelRegistro0123 = new JPanel();
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
		txtUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				
				limitTextFieldLength(txtUser, e, 30);
			}
		});
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
		txtEspecialidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				
				limitTextFieldLength(txtUser, e, 64);
			}
		});
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
		cbxTipoSangre.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "+A", "+B", "+AB", "+O", "-A", "-B", "-AB", "-O"}));
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
				    	
				        if (!rdbtnAdministrador.isSelected() && !rdbtnSecretaria.isSelected() && !rdbtnDoctor.isSelected() && !rdbtnPaciente.isSelected() && !rdbtnPersona.isSelected()) {
				            JOptionPane.showMessageDialog(null, "Debe seleccionar un tipo de usuario.", "Error", JOptionPane.ERROR_MESSAGE);
				            return;
				        }
				        
				        String codigo = txtCodigo.getText();
				        String cedula = txtCedula.getText();
				        String nombre = txtNombre.getText();
				        String apellidos = txtApellido.getText();
				        Date fecNacim = dateChooser.getDate();
				        String sexo = cbxSexo.getSelectedItem().toString();
				        String userName = txtUser.getText();  
				        String password = txtpassword.getText();

				        if (codigo.isEmpty() || cedula.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || fecNacim == null || sexo.equals("<Seleccione>") || userName.isEmpty() || password.isEmpty()) {
				            JOptionPane.showMessageDialog(null, "Todos los campos obligatorios deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
				            return;
				        }

				        if (rdbtnDoctor.isSelected()) {
				            String especialidad = txtEspecialidad.getText();
				            if (especialidad.isEmpty()) {
				                JOptionPane.showMessageDialog(null, "Debe ingresar la especialidad del doctor.", "Error", JOptionPane.ERROR_MESSAGE);
				                return; 
				            }
				        }

				        if (rdbtnPaciente.isSelected()) {
				            String tipoSangre = cbxTipoSangre.getSelectedItem().toString();
				            if (tipoSangre.equals("<Seleccione>")) {
				                JOptionPane.showMessageDialog(null, "Debe seleccionar el tipo de sangre.", "Error", JOptionPane.ERROR_MESSAGE);
				                return;
				            }
				        }

				        if (GestorUsuario.existUserNameSQL(userName) && p == null) {
				            JOptionPane.showMessageDialog(null, "El nombre de usuario ya está en uso.", "Error", JOptionPane.ERROR_MESSAGE);
				            return;
				        }

				        Persona aux = null;
				        if (rdbtnAdministrador.isSelected()) {
				            aux = new Persona(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, 1);
				        } else if (rdbtnSecretaria.isSelected()) {
				            aux = new Persona(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, 2);
				        } else if (rdbtnDoctor.isSelected()) {
				            String especialidad = txtEspecialidad.getText();
				            boolean disponible = rdbtnSi.isSelected();
				            aux = new Doctor(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, 3, especialidad, disponible);
				        } else if (rdbtnPersona.isSelected()) {
				            aux = new Persona(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, 5);
				        } else if (rdbtnPaciente.isSelected()) {
				            String tipoSangre = cbxTipoSangre.getSelectedItem().toString();
				            aux = new Paciente(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, 4, null, tipoSangre);
				        }
				        

				        if (p == null) {
				            
				            Clinica.getInstance().insertarDatosPersonaSQL(aux);
				            
				            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito", "Registrar Persona", JOptionPane.INFORMATION_MESSAGE);
				            
				            clean();
				            
				        } else {
				        	
				        	if (!tempPassword.isEmpty()) { //Cambiamos la password
					        	password = tempPassword;
					        }
				        	
				            if (p instanceof Doctor) {
				                ((Doctor)p).setEspecialidad(txtEspecialidad.getText());
				                ((Doctor)p).setEnServicio(rdbtnSi.isSelected());
				            } else if (p instanceof Paciente) {
				                ((Paciente)p).setTipoSangre(cbxTipoSangre.getSelectedItem().toString());
				            }

				            if (rdbtnAdministrador.isSelected()) {
				                aux = new Persona(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, 1);
				            } else if (rdbtnSecretaria.isSelected()) {
				                aux = new Persona(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, 2);
				            } else if (rdbtnDoctor.isSelected()) {
				                String especialidad = txtEspecialidad.getText();
				                boolean disponible = rdbtnSi.isSelected();
				                aux = new Doctor(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, 3, especialidad, disponible);
				            } else if (rdbtnPersona.isSelected()) {
				                aux = new Persona(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, 5);
				            } else if (rdbtnPaciente.isSelected()) {
				                Vivienda v = ((Paciente)p).getMiVivienda();
				                String tipoSangre = cbxTipoSangre.getSelectedItem().toString();
				                aux = new Paciente(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, 4, v, tipoSangre);
				            }

				            JOptionPane.showMessageDialog(null, "Usuario modificado con éxito", "Modificar Persona", JOptionPane.INFORMATION_MESSAGE);
				            Clinica.getInstance().modificarDatosPersonaSQL(aux);
				            String load = ListarPersona.cbxListar.getSelectedItem().toString();
				            ListarPersona.loadPersona(load);
				            dispose(); 
				        }
				    }

				});
				
				btnCambiarPassword = new JButton("Nueva Contrase\u00F1a ");
				btnCambiarPassword.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						
						JPasswordField newPasswordField = new JPasswordField();
						//JTextField newPasswordField = new JTextField();
		                int action = JOptionPane.showConfirmDialog(null, newPasswordField, "Ingrese la nueva contraseña", JOptionPane.OK_CANCEL_OPTION);
		                if (action == JOptionPane.OK_OPTION) {
		                    String newPassword = new String(newPasswordField.getPassword());
		                	///String newPassword = new String(newPasswordField.getText());
		                    
		                    String temp = "";
		                    
		                    for (int i = 0; i < newPassword.length(); i++) {
		                    	temp += "•";
		                    }
		                    txtpassword.setText(temp); //Visual
		                    tempPassword = newPassword;
		                }

					}
				});
				buttonPane.add(btnCambiarPassword);
				
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
		loadCredenciales();
		esUpdate();
	}

	private void esUpdate() {
	
		if (p != null) {
			
		deshabilitarComponentes(panelRegistro0123);
		
			if (p.getRangoUser() == 1) {
	            rdbtnAdministrador.setSelected(true);
	        } else if (p.getRangoUser() == 2) {
	            rdbtnSecretaria.setSelected(true);
	        } else if (p.getRangoUser() == 3) {
	            rdbtnDoctor.setSelected(true);
	            panelDoctor.setVisible(true);
	        } else if (p.getRangoUser() == 4) {
	            rdbtnPaciente.setSelected(true);
	            panelPaciente.setVisible(true);
	        } else if (p.getRangoUser() == 5) {
	            rdbtnPersona.setSelected(true);
	        }
			
	        activarPanel();
		}
	} 	

	public void deshabilitarComponentes(JPanel panel) {
	    for (java.awt.Component comp : panel.getComponents()) {
	        if (comp instanceof JComponent) {
	            comp.setEnabled(false);
	            ;
	        }
	        if (comp instanceof JPanel) {
	            deshabilitarComponentes((JPanel) comp);
	        }
	    }
	}
	
	
	private void loadCredenciales() {
		
		if (p == null) {
			panelCredenciales.setEnabled(true);
			txtUser.setEditable(true);
			txtpassword.setEditable(true);
			btnCambiarPassword.setEnabled(false);
			
		} else {
			txtUser.setEditable(false);
			txtpassword.setEditable(false);
			txtpassword.setText("•••••••••••••••••••••••••••••");
			btnCambiarPassword.setEnabled(true);
		}
	
	}
	
	
	private void usuarioLogged() { //MODIFICAR ESTO 
	    if (Clinica.getInstance().loggedUser != null) {
	        if (Clinica.getInstance().loggedUser.getRangoUser() == 1) {
	            rdbtnPaciente.setEnabled(false);
	        } 
	        if (Clinica.getInstance().loggedUser.getRangoUser() == 2) {
	            rdbtnAdministrador.setEnabled(false);
	            rdbtnPaciente.setEnabled(false);
	            rdbtnSecretaria.setEnabled(false);
	            rdbtnDoctor.setEnabled(false);
	            rdbtnPersona.setSelected(true);
	            rdbtnAdministrador.setSelected(false);
	        }
	        if (Clinica.getInstance().loggedUser.getRangoUser() == 3) {
	            rdbtnAdministrador.setEnabled(false);
	            rdbtnSecretaria.setEnabled(false);
	            rdbtnDoctor.setEnabled(false);
	            rdbtnPersona.setSelected(true);
	            rdbtnAdministrador.setSelected(false);
	        }
	        if (Clinica.getInstance().loggedUser.getRangoUser() == 4) {
	            rdbtnAdministrador.setEnabled(false);
	            rdbtnSecretaria.setEnabled(false);
	            rdbtnDoctor.setEnabled(false);
	            rdbtnPersona.setEnabled(false);
	            rdbtnPersona.setSelected(true);
	            rdbtnAdministrador.setSelected(false);
	        }
	    }
	}
	
	private void clean() {
	    txtCodigo.setText("" + Clinica.getInstance().obtenerMaximoIdPersona());
	    txtCedula.setText("");
	    txtNombre.setText("");
	    txtApellido.setText("");
	    txtUser.setText("");
	    txtpassword.setText("");
	    txtEspecialidad.setText("");
	    dateChooser.setDate(null);
	    cbxTipoSangre.setSelectedItem("<Seleccione>");    
	    cbxSexo.setSelectedItem("<Seleccione>");
	    tempPassword = "";
	}

	private void loadPersonas() {
	    
		if (p != null) {
	        txtCodigo.setText(p.getCodigo());
	        txtCedula.setText(p.getCedula());
	        txtNombre.setText(p.getNombre());
	        txtApellido.setText(p.getApellidos());
	        dateChooser.setDate(p.getFechaNacimiento());
	        txtUser.setText(p.getUser());
	        txtpassword.setText(p.getPassword());
	        
	        if(p.getGenero().equalsIgnoreCase("M")) {
	        	
	        	cbxSexo.setSelectedItem("Masculino");
	        	
	        } else {
	        	
	        	cbxSexo.setSelectedItem("Femenino");
	        }
	        
	        activarPanel();
	    }
	}
	
	private void activarPanel() {
		
		if (p != null) {
			
			rdbtnAdministrador.setSelected(false);
			rdbtnSecretaria.setSelected(false);
			rdbtnPersona.setSelected(false);
			rdbtnPaciente.setSelected(false);
			rdbtnDoctor.setSelected(false);
			panelPaciente.setVisible(false);
			panelDoctor.setVisible(false);

			if (p.getRangoUser() == 3) { 
				
				txtEspecialidad.setText(((Doctor)p).getEspecialidad());
			    rdbtnSi.setSelected(((Doctor)p).isEnServicio());
			    rdbtnNo.setSelected(!((Doctor)p).isEnServicio());
			
			    rdbtnDoctor.setSelected(true);
			    panelDoctor.setVisible(true);
			    
			} else if (p.getRangoUser() == 4) { 
			    
				cbxTipoSangre.setSelectedItem(((Paciente)p).getTipoSangre());
			    
			    rdbtnPaciente.setSelected(true);
			    panelPaciente.setVisible(true);
			    
			} else if (p.getRangoUser() == 5) { 
				
			    rdbtnPersona.setSelected(true);
			    
			} else if (p.getRangoUser() == 1) { 
				
			    rdbtnAdministrador.setSelected(true);
			    
			} else if (p.getRangoUser() == 2) { 
				
			    rdbtnSecretaria.setSelected(true);
			}
		}
	}
	
	private void SetRandomDate(JDateChooser dateChooser, int minAge, int maxAge) {
	    try {
	        if (minAge > maxAge) {
	            throw new IllegalArgumentException("La edad mínima no puede ser mayor que la edad máxima.");
	        }

	        int FechaDefecto = 1900;
	        Random random = new Random();
	        Calendar calendar = Calendar.getInstance();
	        
	        int currentYear = calendar.get(Calendar.YEAR);
	        int minYear = currentYear - maxAge;
	        int maxYear = currentYear - minAge;

	        if (minYear <= FechaDefecto) {
	            throw new IllegalArgumentException("El rango de años es demasiado grande. Asegúrate de que 'maxAge' sea razonable.");
	        }

	        int randomYear = minYear + random.nextInt(maxYear - minYear + 1);
	        
	        int randomMonth = random.nextInt(12);
	        
	        calendar.set(randomYear, randomMonth, 1);
	        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	        
	        int randomDay = 1 + random.nextInt(maxDay);
	        
	        calendar.set(randomYear, randomMonth, randomDay);   
	        dateChooser.setDate(calendar.getTime());
	        
	    } catch (IllegalArgumentException e) {
	        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void showAgeInputDialog(JDateChooser dateChooser) {
	    JTextField minAgeField = new JTextField(5);
	    JTextField maxAgeField = new JTextField(5);
	    
	    JPanel panel = new JPanel();
	    panel.add(new JLabel("Edad mínima:"));
	    panel.add(minAgeField);
	    panel.add(Box.createHorizontalStrut(15)); // Espacio entre campos
	    panel.add(new JLabel("Edad máxima:"));
	    panel.add(maxAgeField);
	    
	    int option = JOptionPane.showConfirmDialog(null, panel, "Ingrese las edades mínima y máxima:", JOptionPane.OK_CANCEL_OPTION);
	    if (option == JOptionPane.OK_OPTION) {
	        try {
	            int minAge = Integer.parseInt(minAgeField.getText());
	            int maxAge = Integer.parseInt(maxAgeField.getText());
	            SetRandomDate(dateChooser, minAge, maxAge);
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(null, "Por favor, ingrese números válidos para las edades.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	
	
	private void limitTextFieldLength(JTextField textField, KeyEvent e, int maxLength) {
        if (textField.getText().length() >= maxLength) {
            e.consume();
        }
    }
	
    private void formatCedulaField(JTextField textField, KeyEvent e) {
        String text = textField.getText();
        int length = text.length();

        if (Character.isDigit(e.getKeyChar())) {
            if (length == 3 || length == 11) {
                textField.setText(text + "-");
            } else if (length >= 13) {
                e.consume();
            }
        } else if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE && e.getKeyChar() != KeyEvent.VK_DELETE) {
            e.consume();
        }
    }
	
}