package visual;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import logico.Clinica;
import logico.Persona;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Toolkit;

public class Login_o_Registro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUser;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
/*
	public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            FileInputStream clinica;
	            FileOutputStream tempClinica;
	            ObjectInputStream clinicaRead;
	            ObjectOutputStream clinicaWrite;
	            
	            try {
	                clinica = new FileInputStream("clinica.dat");
	                clinicaRead = new ObjectInputStream(clinica);
	                Clinica temp = (Clinica) clinicaRead.readObject();
	                Clinica.setClinica(temp);
	                clinica.close();
	                clinicaRead.close();
	                Clinica.getInstance().cargarValoresEstaticos();
	                System.out.println(Clinica.getInstance().getMisCitas().size());
	            } catch (FileNotFoundException e) {
	            	try {
	            		tempClinica = new FileOutputStream("clinica.dat");
	            		clinicaWrite =  new ObjectOutputStream(tempClinica);
	            		Persona aux = new Persona("Admin","Admin","Admin","",null,"","Admin","Admin",4);
	            		Clinica.getInstance().insertarPersona(aux);
	            		clinicaWrite.writeObject(Clinica.getInstance());
	            		tempClinica.close();
	            		clinicaWrite.close();
	            	} catch (FileNotFoundException e1) {
					} catch (IOException e1) {
						// TODO Auto-generated catch block
					}
	            } catch (IOException e) {
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            try {
	                Login_o_Registro frame = new Login_o_Registro();
	                frame.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
*/
	
	public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                // Inicializa la instancia de Clinica y carga los datos
	                Clinica.getInstance().cargarDatosDesdeSQL();
	                
	                // Crea y muestra la ventana de login
	                Login_o_Registro frame = new Login_o_Registro();
	                frame.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(null, "Error al iniciar la aplicación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	}
	

	/** 
	 * Create the frame.
	 */
	public Login_o_Registro() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login_o_Registro.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Clinica.getInstance().guardarDatos();
				JOptionPane.showMessageDialog(null, "Informacion guardada correctamente\nGracias por preferirnos", "Hasta la Proxima!", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(45, 93, 56, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(14, 118, 87, 14);
		contentPane.add(lblNewLabel_1);
		
		txtUser = new JTextField();
		txtUser.setBounds(115, 91, 175, 20);
		contentPane.add(txtUser);
		txtUser.setColumns(10);
		
		JButton btnInicioSesion = new JButton("Iniciar Sesi\u00F3n");
		btnInicioSesion.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnInicioSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] temp = txtPassword.getPassword();
                String password = new String(temp);
				if (Clinica.getInstance().confirmLogin(txtUser.getText(),password)) {
					PrincipalVisual visual = new PrincipalVisual();
					visual.setVisible(true);
					dispose();
				} else {
		            JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error de inicio de sesi\u00F3n", JOptionPane.ERROR_MESSAGE);
				}
			}
		});//
		btnInicioSesion.setBounds(304, 101, 123, 23);
		contentPane.add(btnInicioSesion);
		
		JLabel lblNewLabel_2 = new JLabel("No estas registrado? ");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(87, 159, 139, 23);
		contentPane.add(lblNewLabel_2);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(115, 115, 175, 20);
		contentPane.add(txtPassword);
		
		JButton btnRegistrarNuevoUser = new JButton("Registrate");
		btnRegistrarNuevoUser.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRegistrarNuevoUser.setForeground(SystemColor.textHighlight);
		btnRegistrarNuevoUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarNewUser newUser = new RegistrarNewUser(null);
				newUser.setModal(true);
				newUser.setVisible(true);
			}
		});
		btnRegistrarNuevoUser.setBounds(239, 159, 110, 23);
		contentPane.add(btnRegistrarNuevoUser);
		
		JLabel lblNewLabel_3 = new JLabel("Bienvenido");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_3.setBounds(134, 11, 175, 33);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Clinica SAM");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(187, 46, 69, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel Fondo = new JLabel("");
		Fondo.setHorizontalAlignment(SwingConstants.CENTER);
		Fondo.setIcon(new ImageIcon(Login_o_Registro.class.getResource("/imagenes/Fondo Login 2.jpg")));
		Fondo.setBounds(0, 0, 444, 271);
		contentPane.add(Fondo);
	}
}

