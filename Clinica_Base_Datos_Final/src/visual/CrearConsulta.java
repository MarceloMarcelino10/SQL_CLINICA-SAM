package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Cita;
import logico.Clinica;
import logico.Consulta;
import logico.Doctor;
import logico.Enfermedad;
import logico.Persona;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Button;
import java.awt.Panel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

public class CrearConsulta extends JDialog {

	private final JPanel panelPrincipal = new JPanel();
	private JTextField txtCodigoConsulta;
	private JTextField txtDoctorConsulta;
	private JTable tableListarPendCita;
	private JTable tableListarEnf;
	private Enfermedad selectedEnfermedad;
	private Cita selectedCita;
	private Doctor doctorLogged;
	private Enfermedad selected;
	private static DefaultTableModel model;
	private static Object[] rows;
	/**
	 * Launch the application.su
	 */
	public static void main(String[] args) {
		try {
			CrearConsulta dialog = new CrearConsulta();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CrearConsulta() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CrearConsulta.class.getResource("/imagenes/fotoTituloDeVentana.png")));//
		setTitle("Crear una consulta");
		setBounds(100, 100, 816, 544);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		panelPrincipal.setBackground(new Color(0, 128, 255));
		panelPrincipal.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(null);
		{
			JPanel panelDatosConsulta = new JPanel();
			panelDatosConsulta.setBackground(new Color(255, 255, 255));
			panelDatosConsulta.setBorder(new TitledBorder(null, "Datos Generales: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelDatosConsulta.setBounds(12, 13, 364, 213);
			panelPrincipal.add(panelDatosConsulta);
			panelDatosConsulta.setLayout(null);
			
			JLabel lblCodigoConsulta = new JLabel("C\u00F3digo de consulta: ");
			lblCodigoConsulta.setBounds(12, 60, 123, 16);
			panelDatosConsulta.add(lblCodigoConsulta);
			
			txtCodigoConsulta = new JTextField();
			txtCodigoConsulta.setEditable(false);
			txtCodigoConsulta.setBounds(135, 56, 199, 22);
			txtCodigoConsulta.setText("Csta-" +Clinica.getInstance().codConsulta);
			panelDatosConsulta.add(txtCodigoConsulta);
			txtCodigoConsulta.setColumns(10);
			
			JLabel lblDoctorConsulta = new JLabel("Doctor: ");
			lblDoctorConsulta.setBounds(12, 136, 56, 16);
			panelDatosConsulta.add(lblDoctorConsulta);
			
			
			txtDoctorConsulta = new JTextField();
			txtDoctorConsulta.setEditable(false);
			txtDoctorConsulta.setBounds(135, 134, 199, 22);
			txtDoctorConsulta.setText(Clinica.getInstance().getLoggedUser().getNombre()+Clinica.getInstance().getLoggedUser().getApellidos());
			panelDatosConsulta.add(txtDoctorConsulta);
			txtDoctorConsulta.setColumns(10);
		}
		{
			JPanel panelDiagnostico = new JPanel();
			panelDiagnostico.setBackground(new Color(255, 255, 255));
			panelDiagnostico.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Diagn\u00F3stico:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelDiagnostico.setBounds(408, 13, 364, 411);
			panelPrincipal.add(panelDiagnostico);
			panelDiagnostico.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPaneListEnf = new JScrollPane();
			panelDiagnostico.add(scrollPaneListEnf, BorderLayout.CENTER);
			
			
			tableListarEnf = new JTable();
			tableListarEnf.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) {
					int enfSelectedColum= tableListarEnf.getSelectedRow();
			        if (enfSelectedColum >= 0) {
			           selectedEnfermedad = Clinica.getInstance().buscarEnfermedadById(tableListarEnf.getValueAt(enfSelectedColum, 0).toString());
			        } else {
			        	JOptionPane.showMessageDialog(null, "Seleccion Invalida", "Error", JOptionPane.ERROR_MESSAGE);
			        }
			    }
			});
			ListarEnfermedades listarDiagnostico  = new ListarEnfermedades();
			tableListarEnf = listarDiagnostico.getTable();
			//scrollPaneListEnf.setViewportView(tableListarEnf);
		}
		
		JPanel panelCitasPendientes = new JPanel();
		panelCitasPendientes.setBackground(new Color(255, 255, 255));
		panelCitasPendientes.setBorder(new TitledBorder(null, "Citas pendientes: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCitasPendientes.setBounds(12, 239, 364, 185);
		panelPrincipal.add(panelCitasPendientes);
		panelCitasPendientes.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPanePendCita = new JScrollPane();
		panelCitasPendientes.add(scrollPanePendCita, BorderLayout.CENTER);
		
		tableListarPendCita = new JTable();
		tableListarEnf.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
				int CitaSelectedColum= tableListarPendCita.getSelectedRow();
		        if (CitaSelectedColum >= 0) {
		           selectedCita = Clinica.getInstance().buscarCitaById(tableListarPendCita.getValueAt(CitaSelectedColum, 0).toString());
		        } else {
		        	JOptionPane.showMessageDialog(null, "Seleccion Invalida", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		model = new DefaultTableModel();
		scrollPanePendCita.setViewportView(tableListarPendCita);
		
		Button btnAgregarEnfermedad = new Button("Agregar enfermedad");
		btnAgregarEnfermedad.setBounds(525, 430, 130, 24);
		panelPrincipal.add(btnAgregarEnfermedad);
		btnAgregarEnfermedad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarEnfermedad aux = new RegistrarEnfermedad();
				dispose();
				aux.setVisible(true);
				DefaultListModel<String> listModel = new DefaultListModel<>();
		        for (Enfermedad enfermedades : Clinica.getInstance().getMisEnfermedades()) {
		            listModel.addElement(enfermedades.getCodigo() + ":" + enfermedades.getNombre() + enfermedades.getGravedad());
		        }
		        loadEnfermedades();
			}
		});
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(255, 255, 255));
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCrearConsulta = new JButton("Generar consulta");
				btnCrearConsulta.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Consulta consulta = new Consulta(txtCodigoConsulta.getText(), selectedCita);
						Clinica.getInstance().insertarConsulta(consulta);	
						JOptionPane.showMessageDialog(null, "Operacion satisfactoria", "Registro", JOptionPane.INFORMATION_MESSAGE);
					}
				});
				btnCrearConsulta.setIcon(new ImageIcon(CrearConsulta.class.getResource("/imagenes/agregarOcrearboton.png")));
				btnCrearConsulta.setActionCommand("OK");
				buttonPane.add(btnCrearConsulta);
				getRootPane().setDefaultButton(btnCrearConsulta);
			}
			{
				JButton btnCancelarConsulta = new JButton("Cancelar");
				btnCancelarConsulta.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelarConsulta.setIcon(new ImageIcon(CrearConsulta.class.getResource("/imagenes/cancelarboton16x16.png")));
				btnCancelarConsulta.setActionCommand("Cancel");
				buttonPane.add(btnCancelarConsulta);
			}
		}
	}

	protected void loadEnfermedades() {
		model.setRowCount(0);
		rows = new Object[model.getColumnCount()];
		for (Enfermedad enfermedad : Clinica.getInstance().getMisEnfermedades()) {
			rows[0] = enfermedad.getCodigo();
			rows[1] = enfermedad.getNombre();
			rows[2] = enfermedad.getGravedad();
			model.addRow(rows);
		}
		
	}
}
