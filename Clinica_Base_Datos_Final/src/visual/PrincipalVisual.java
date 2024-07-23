package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import logico.Clinica;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class PrincipalVisual extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1797972503436833192L;
	private JPanel contentPane;
	private JPanel panel_Vivienda;
	private JPanel panel_Persona;
	private JPanel panel_Cita;
	private JPanel panel_Consulta;
	private JPanel panel_Enfermedad;
	private JPanel panel_Vacuna;
	private JPanel panel_Historia;
	private JPanel panel_Reporte;
	private int indexPanel = 0;
    private JPanel[] panels;
    private JButton btnRegVivienda;
    private JButton btnListVievienda;
    private JButton btnRegistrarPersona;
    private JButton btnListarPersona;
    private JButton btnRegistrarCita;
    private JButton btnRegistrarConsulta;
    private JButton btnListarCitas;
    private JButton btnListarConsulta;
    private JButton btnRegistrarEnfermedad;
    private JButton btnRegistrarVacuna;
    private JButton btnListarHistoriaClinica;
    private JButton btnListarEnfermedades;
    private JButton btnListarVacuna;
    private JButton btnGenerarReporte;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;
    private JLabel lblNewLabel_4;
    private JLabel lblNewLabel_5;
    private JLabel lblNewLabel_6;
    private JLabel lblNewLabel_7;
    private JLabel lblNewLabel_8;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalVisual frame = new PrincipalVisual();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PrincipalVisual() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrincipalVisual.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		addWindowListener(new WindowAdapter() {//
			@Override
			public void windowClosed(WindowEvent e) {
				Login_o_Registro loginDialog = new Login_o_Registro();
		        loginDialog.setVisible(true);
			}
		});
		setTitle("Ventana Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setBounds(100, 100, 830, 525);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(30, 144, 255));
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		panel_Vivienda = new JPanel();
		panel_Vivienda.setBackground(new Color(255, 255, 255));
		panel_Vivienda.setForeground(Color.WHITE);
		panel_Vivienda.setBounds(74, 107, 290, 282);
		panel.add(panel_Vivienda);
		panel_Vivienda.setLayout(null);
		
		btnRegVivienda = new JButton("Registar");
		btnRegVivienda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarVivienda regVivienda = new RegistrarVivienda(null);
				regVivienda.setModal(true);
				regVivienda.setVisible(true);
			}
		});
		btnRegVivienda.setBounds(100, 104, 89, 23);
		panel_Vivienda.add(btnRegVivienda);
		
		btnListVievienda = new JButton("Listar");
		btnListVievienda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarVivienda listVivienda = new ListarVivienda();
				listVivienda.setModal(true);
				listVivienda.setVisible(true);
			}
		});
		btnListVievienda.setBounds(100, 169, 89, 23);
		panel_Vivienda.add(btnListVievienda);
		
		JLabel lblNewLabel_1 = new JLabel("VIVIENDAS");
		lblNewLabel_1.setFont(new Font("Georgia", Font.PLAIN, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(58, 11, 173, 53);
		panel_Vivienda.add(lblNewLabel_1);
		
		panel_Persona = new JPanel();
		panel_Persona.setBackground(new Color(255, 255, 255));
		panel_Persona.setBounds(438, 107, 290, 282);
		panel.add(panel_Persona);
		panel_Persona.setLayout(null);
		
		btnRegistrarPersona = new JButton("Registrar");
		btnRegistrarPersona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarNewUser regPersona = new RegistrarNewUser(null);
				regPersona.setModal(true);
				regPersona.setVisible(true);
			}
		});
		btnRegistrarPersona.setBounds(100, 104, 89, 23);
		panel_Persona.add(btnRegistrarPersona);
		
		btnListarPersona = new JButton("Listar");
		btnListarPersona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarPersona listPersona = new ListarPersona();
				listPersona.setModal(true);
				listPersona.setVisible(true);
			}
		});
		btnListarPersona.setBounds(100, 169, 89, 23);
		panel_Persona.add(btnListarPersona);
		
		lblNewLabel_2 = new JLabel("USUARIOS");
		lblNewLabel_2.setFont(new Font("Georgia", Font.PLAIN, 20));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(58, 11, 173, 53);
		panel_Persona.add(lblNewLabel_2);
		
		JButton btnCancelar = new JButton("Cerrar Sesi\u00F3n");
		btnCancelar.setBackground(new Color(192, 192, 192));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(335, 421, 132, 23);
		panel.add(btnCancelar);
		
		JButton btnIzquierda = new JButton("");
		btnIzquierda.setBackground(Color.WHITE);
		btnIzquierda.setIcon(new ImageIcon(PrincipalVisual.class.getResource("/imagenes/Flecha Izquierda.png")));
		btnIzquierda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moverIzquierda();
			}
		});
		btnIzquierda.setBounds(123, 412, 103, 41);
		panel.add(btnIzquierda);
		
		JButton btnDerecha = new JButton("");
		btnDerecha.setBackground(Color.WHITE);
		btnDerecha.setIcon(new ImageIcon(PrincipalVisual.class.getResource("/imagenes/Flecha Derecha.png")));
		btnDerecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moverDerecha();
			}
		});
		btnDerecha.setBounds(590, 412, 89, 41);
		panel.add(btnDerecha);
		
		panel_Cita = new JPanel();
		panel_Cita.setBackground(new Color(255, 255, 255));
		panel_Cita.setVisible(false);
		panel_Cita.setBounds(74, 107, 290, 282);
		panel.add(panel_Cita);
		panel_Cita.setLayout(null);
		
		btnRegistrarCita = new JButton("Registrar");
		btnRegistrarCita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CrearCita creaci = new CrearCita();
				creaci.setModal(true);
				creaci.setVisible(true);
				
			}
		});
		btnRegistrarCita.setBounds(100, 104, 89, 23);
		panel_Cita.add(btnRegistrarCita);
		
		btnListarCitas = new JButton("Listar");
		btnListarCitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarCita listci = new ListarCita();
				listci.setVisible(true);
			}
		});
		btnListarCitas.setBounds(100, 169, 89, 23);
		panel_Cita.add(btnListarCitas);
		
		lblNewLabel_3 = new JLabel("CITAS");
		lblNewLabel_3.setFont(new Font("Georgia", Font.PLAIN, 20));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(58, 11, 173, 53);
		panel_Cita.add(lblNewLabel_3);
		
		panel_Consulta = new JPanel();
		panel_Consulta.setBackground(new Color(255, 255, 255));
		panel_Consulta.setVisible(false);
		panel_Consulta.setBounds(438, 107, 290, 282);
		panel.add(panel_Consulta);
		panel_Consulta.setLayout(null);
		
		btnRegistrarConsulta = new JButton("Registrar");
		btnRegistrarConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CrearConsulta creaconsu = new CrearConsulta();
				creaconsu.setModal(true);
				creaconsu.setVisible(true);
			}
		});
		btnRegistrarConsulta.setBounds(100, 104, 89, 23);
		panel_Consulta.add(btnRegistrarConsulta);
		
		btnListarConsulta = new JButton("Listar");
		btnListarConsulta.setBounds(100, 169, 89, 23);
		panel_Consulta.add(btnListarConsulta);
		
		lblNewLabel_4 = new JLabel("CONSULTAS");
		lblNewLabel_4.setFont(new Font("Georgia", Font.PLAIN, 20));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(58, 11, 173, 53);
		panel_Consulta.add(lblNewLabel_4);
		
		panel_Enfermedad = new JPanel();
		panel_Enfermedad.setBackground(new Color(255, 255, 255));
		panel_Enfermedad.setVisible(false);
		panel_Enfermedad.setBounds(74, 107, 290, 282);
		panel.add(panel_Enfermedad);
		panel_Enfermedad.setLayout(null);
		
		btnRegistrarEnfermedad = new JButton("Registrar");
		btnRegistrarEnfermedad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarEnfermedad regenfe = new RegistrarEnfermedad();
				regenfe.setModal(true);
				regenfe.setVisible(true);
				
				
			}
		});
		btnRegistrarEnfermedad.setBounds(100, 104, 89, 23);
		panel_Enfermedad.add(btnRegistrarEnfermedad);
		
		btnListarEnfermedades = new JButton("Listar");
		btnListarEnfermedades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarEnfermedades listenfe = new ListarEnfermedades();
				listenfe.setModal(true);
				listenfe.setVisible(true);
			}
		});
		btnListarEnfermedades.setBounds(100, 169, 89, 23);
		panel_Enfermedad.add(btnListarEnfermedades);
		
		lblNewLabel_5 = new JLabel("ENFERMEDADES");
		lblNewLabel_5.setFont(new Font("Georgia", Font.PLAIN, 20));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(58, 11, 173, 53);
		panel_Enfermedad.add(lblNewLabel_5);
		
		panel_Vacuna = new JPanel();
		panel_Vacuna.setBackground(new Color(255, 255, 255));
		panel_Vacuna.setVisible(false);
		panel_Vacuna.setBounds(438, 107, 290, 282);
		panel.add(panel_Vacuna);
		panel_Vacuna.setLayout(null);
		
		btnRegistrarVacuna = new JButton("Registrar");
		btnRegistrarVacuna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarVacuna regvacu = new RegistrarVacuna();
				regvacu.setModal(true);
				regvacu.setVisible(true);
			}
		});
		btnRegistrarVacuna.setBounds(100, 104, 89, 23);
		panel_Vacuna.add(btnRegistrarVacuna);
		
		btnListarVacuna = new JButton("Listar");
		btnListarVacuna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarVacuna listvacu = new ListarVacuna();
				listvacu.setModal(true);
				listvacu.setVisible(true);
			}
		});
		btnListarVacuna.setBounds(100, 169, 89, 23);
		panel_Vacuna.add(btnListarVacuna);
		
		lblNewLabel_6 = new JLabel("VACUNAS");
		lblNewLabel_6.setFont(new Font("Georgia", Font.PLAIN, 20));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBounds(58, 11, 173, 53);
		panel_Vacuna.add(lblNewLabel_6);
		
		panel_Historia = new JPanel();
		panel_Historia.setBackground(new Color(255, 255, 255));
		panel_Historia.setVisible(false);
		panel_Historia.setBounds(74, 107, 290, 282);
		panel.add(panel_Historia);
		panel_Historia.setLayout(null);
		
		btnListarHistoriaClinica = new JButton("Listar");
		btnListarHistoriaClinica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HistorialClinico histcli = new HistorialClinico();
				histcli.setModal(true);
				histcli.setVisible(true);
			}
		});
		btnListarHistoriaClinica.setBounds(100, 129, 89, 23);
		panel_Historia.add(btnListarHistoriaClinica);
		
		lblNewLabel_8 = new JLabel("HISTORIAL CLINICO");
		lblNewLabel_8.setFont(new Font("Georgia", Font.PLAIN, 20));
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8.setBounds(34, 11, 222, 53);
		panel_Historia.add(lblNewLabel_8);
		
		panel_Reporte = new JPanel();
		panel_Reporte.setBackground(new Color(255, 255, 255));
		panel_Reporte.setVisible(false);
		panel_Reporte.setBounds(438, 107, 290, 282);
		panel.add(panel_Reporte);
		panel_Reporte.setLayout(null);
		
		btnGenerarReporte = new JButton("Generar");
		btnGenerarReporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VerReporte reporte = new VerReporte();
				reporte.setModal(true);
				reporte.setVisible(true);
			}
		});
		btnGenerarReporte.setBounds(100, 129, 89, 23);
		panel_Reporte.add(btnGenerarReporte);
		
		lblNewLabel_7 = new JLabel("DATOS GENERALES");
		lblNewLabel_7.setFont(new Font("Georgia", Font.PLAIN, 20));
		lblNewLabel_7.setBounds(34, 11, 222, 53);
		panel_Reporte.add(lblNewLabel_7);
		
		JLabel lblNewLabel = new JLabel("Clinica SAM");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 50));
		lblNewLabel.setBounds(74, 23, 300, 61);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setIcon(new ImageIcon(PrincipalVisual.class.getResource("/imagenes/Icon Clinica 2.png")));
		lblNewLabel_9.setBounds(346, 18, 64, 71);
		panel.add(lblNewLabel_9);
		
		vistaPaneles();
	}
	
	private void moverDerecha() {
		  JPanel[] panels = {panel_Vivienda, panel_Persona, panel_Cita, panel_Consulta, panel_Enfermedad, panel_Vacuna, panel_Historia, panel_Reporte};
		  int actual = indexPanel;
		  int siguiente = (indexPanel + 1) % panels.length;
		  panels[siguiente].setVisible(true);
		  panels[siguiente].setBounds(74, 107, 290, 282);
		  panels[(siguiente + 1) % panels.length].setBounds(438, 107, 290, 282);
		  panels[actual].setVisible(false);
		  panels[(siguiente + 1) % panels.length].setVisible(true);
		  indexPanel = (indexPanel + 1) % panels.length;
	}
	
	private void moverIzquierda() {
		  JPanel[] panels = {panel_Vivienda, panel_Persona, panel_Cita, panel_Consulta, panel_Enfermedad, panel_Vacuna, panel_Historia, panel_Reporte};
		  int actual = indexPanel;
		  int anterior = (indexPanel - 1 + panels.length) % panels.length;
		  panels[actual].setBounds(438, 107, 290, 282);
		  panels[(actual + 1) % panels.length].setVisible(false);
		  panels[anterior].setBounds(74, 107, 290, 282);
		  panels[anterior].setVisible(true);
		  indexPanel = (indexPanel - 1 + panels.length) % panels.length;
	}
	
	private void vistaPaneles() {
	    JPanel[] panels = {panel_Vivienda, panel_Persona, panel_Cita, panel_Consulta, panel_Enfermedad, panel_Vacuna, panel_Historia, panel_Reporte};
	    for (JPanel panel : panels) {
	        panel.setEnabled(false);
	        for (Component component : panel.getComponents()) {
	            if (component instanceof JButton) {
	                component.setEnabled(false);
	            }
	        }
	    }
	    	    
	    try {
	        if (Clinica.getInstance().loggedUser != null && Clinica.getInstance().loggedUser.getRangoUser() != 1) { // Persona
	            if (Clinica.getInstance().loggedUser.getRangoUser() == 4) { // Admin
	                enablePanelAndButtons(panel_Vivienda);
	                enablePanelAndButtons(panel_Persona);
	                enablePanelAndButtons(panel_Enfermedad);
	                enablePanelAndButtons(panel_Vacuna);
	                enablePanelAndButtons(panel_Reporte);
	            } else if (Clinica.getInstance().loggedUser.getRangoUser() == 3) { // Secretario
	                enablePanelAndButtons(panel_Persona);
	                enablePanelAndButtons(panel_Cita);
	                enablePanelAndButtons(panel_Historia);
	                enablePanelAndButtons(panel_Reporte);
	            } else if (Clinica.getInstance().loggedUser.getRangoUser() == 2) { // Doctor
	                enablePanelAndButtons(panel_Persona);
	                enablePanelAndButtons(panel_Cita);
	                enablePanelAndButtons(panel_Consulta);
	                enablePanelAndButtons(panel_Historia);
	            } else { // Paciente
	                enablePanelAndButtons(panel_Historia);
	            }
	        }
	    } catch (NullPointerException e) {
	    }
	}
	
	private void enablePanelAndButtons(JPanel panel) {
	    panel.setEnabled(true);
	    for (Component component : panel.getComponents()) {
	        if (component instanceof JButton) {
	            component.setEnabled(true);
	        }
	    }
	}
}
