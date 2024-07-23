package visual;
//d
import java.awt.BorderLayout;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import com.toedter.calendar.JDateChooser;

import logico.Cita;
import logico.Clinica;
import logico.Doctor;
import logico.Persona;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
//hola:..
public class CrearCita extends JDialog {

	private final JPanel panelPrincipal = new JPanel();
	private JTextField txtCodigoCita;
	private JTable tableDocDispo;
	private JTable tablePacienteReg;
	private Doctor selectedDoctor;
	private Persona selectedPaciente;
 

	/**
	 * Launch the application.u
	 */
	public static void main(String[] args) {
		try {
			CrearCita dialog = new CrearCita();
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
	public CrearCita() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CrearCita.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		setTitle("Crear cita");
		setBounds(100, 100, 999, 686);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		panelPrincipal.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(null);
		
		JPanel panelInfoGeneral = new JPanel();
		panelInfoGeneral.setBorder(new TitledBorder(null, "Informacion general: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelInfoGeneral.setBounds(12, 13, 431, 225);
		panelPrincipal.add(panelInfoGeneral);
		panelInfoGeneral.setLayout(null);
		
		JLabel lblCodigoCita = new JLabel("C\u00F3digo de cita:");
		lblCodigoCita.setBounds(12, 44, 93, 16);
		panelInfoGeneral.add(lblCodigoCita);
		
		txtCodigoCita = new JTextField();
		txtCodigoCita.setEditable(false);
		txtCodigoCita.setBounds(117, 39, 116, 22);
		txtCodigoCita.setText("Cta-" + Clinica.getCodCita());
		panelInfoGeneral.add(txtCodigoCita);
		txtCodigoCita.setColumns(10);
		
		JLabel lblFecha = new JLabel("D\u00EDa de la cita:");
		lblFecha.setBounds(12, 104, 93, 16);
		panelInfoGeneral.add(lblFecha);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(117, 100, 116, 22);
		panelInfoGeneral.add(dateChooser);
		
		JLabel lblHoraCita = new JLabel("Hora de la cita:");
		lblHoraCita.setBounds(12, 164, 93, 16);
		panelInfoGeneral.add(lblHoraCita);
		
		JSpinner spnHoraCita = new JSpinner();
		spnHoraCita.setModel(new SpinnerDateModel(new Date(1701748800000L), null, null, Calendar.HOUR));
		JSpinner.DateEditor de_spnHoraCita = new JSpinner.DateEditor(spnHoraCita, "HH:mm");
        spnHoraCita.setEditor(de_spnHoraCita);
		spnHoraCita.setBounds(117, 161, 116, 22);
		panelInfoGeneral.add(spnHoraCita);
		
		JPanel panelListarDoctores = new JPanel();
		panelListarDoctores.setBorder(new TitledBorder(null, "Doctores disponibles: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelListarDoctores.setBounds(460, 13, 509, 578);
		panelPrincipal.add(panelListarDoctores);
		panelListarDoctores.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneDispDoc = new JScrollPane();
		panelListarDoctores.add(scrollPaneDispDoc);
		
		tableDocDispo = new JTable();
		tableDocDispo.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
				int DocSelectedColum= tableDocDispo.getSelectedRow();
		        if (DocSelectedColum >= 0) {
		           selectedDoctor = (Doctor) Clinica.getInstance().buscarPersonaById(tableDocDispo.getValueAt(DocSelectedColum, 0).toString());
		        } else {
		        	JOptionPane.showMessageDialog(null, "Seleccion Invalida", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		tableDocDispo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		ListarPersona listarDocDispo = new ListarPersona();
		String modo  = "Doctor";
		JTable tableDefault  = listarDocDispo.getTable(modo);
		tableDocDispo = tableDefault;
		panelListarDoctores.add(scrollPaneDispDoc, BorderLayout.CENTER);
		
		scrollPaneDispDoc.setViewportView(tableDocDispo);
		JPanel panelListarPersona = new JPanel();
		panelListarPersona.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Personas registradas:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelListarPersona.setBounds(12, 251, 431, 340);
		panelPrincipal.add(panelListarPersona);
		panelListarPersona.setLayout(new BorderLayout(0,0));
		
		JScrollPane scrollPanePaciReg = new JScrollPane();
		scrollPanePaciReg.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelListarPersona.add(scrollPanePaciReg, BorderLayout.CENTER);
		
		tablePacienteReg = new JTable();
		tablePacienteReg.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
				int PacienteSelectedColum= tablePacienteReg.getSelectedRow();
		        if (PacienteSelectedColum >= 0) {
		           selectedPaciente = Clinica.getInstance().buscarPersonaById(tablePacienteReg.getValueAt(PacienteSelectedColum, 0).toString());
		           dispose();
		        } else {
		        	JOptionPane.showMessageDialog(null, "Seleccion Invalida", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		ListarPersona listarPacReg = new ListarPersona();
		String paciente = "Persona";
		JTable tablaPacienteDefault = listarPacReg.getTable(paciente);
		tablePacienteReg = tablaPacienteDefault;
		scrollPanePaciReg.setViewportView(tablePacienteReg);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton crearCitabtn = new JButton("Crear cita");
				crearCitabtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (selectedPaciente != null && selectedDoctor != null ) {
							String codigo = txtCodigoCita.getText();
							Cita cita = new Cita(codigo, selectedPaciente, selectedDoctor, dateChooser.getDate(), (Time) spnHoraCita.getValue());
							Clinica.getInstance().insertarCita(cita);
							JOptionPane.showMessageDialog(null, "Operacion satisfactoria", "Registro", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				crearCitabtn.setIcon(new ImageIcon(CrearCita.class.getResource("/imagenes/agregarOcrearboton.png")));
				crearCitabtn.setActionCommand("OK");
				buttonPane.add(crearCitabtn);
				getRootPane().setDefaultButton(crearCitabtn);
			}
			{
				JButton cancelarBtn = new JButton("Cancelar");
				cancelarBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelarBtn.setIcon(new ImageIcon(CrearCita.class.getResource("/imagenes/cancelarboton16x16.png")));
				cancelarBtn.setActionCommand("Cancel");
				buttonPane.add(cancelarBtn);
			}
		}
	}
}