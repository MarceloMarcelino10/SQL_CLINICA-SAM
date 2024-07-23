package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Cita;
import logico.Clinica;
import logico.Consulta;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.Color;

public class ListarConsulta extends JFrame {

	private JPanel contentPane;
	private JTable tableListarConsulta;
	private static DefaultTableModel model;
	private static Object[] rows;

	/**
	 * Launch the application.u
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListarConsulta frame = new ListarConsulta();
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
	public ListarConsulta() {
		setTitle("Lista de Consultas");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ListarConsulta.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 863, 591);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelListarDoctor = new JPanel();
		panelListarDoctor.setBounds(12, 13, 821, 518);
		contentPane.add(panelListarDoctor);
		panelListarDoctor.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneListarConsulta = new JScrollPane();
		panelListarDoctor.add(scrollPaneListarConsulta, BorderLayout.CENTER);
		
		String headers[] = {"Codigo", "Paciente", "Doctor", "Dia"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(headers); 
		tableListarConsulta = new JTable();
		tableListarConsulta.setModel(model);
		scrollPaneListarConsulta.setViewportView(tableListarConsulta);
		loadConsulta();
		
		scrollPaneListarConsulta.setViewportView(tableListarConsulta);
	}
	private void loadConsulta() {
		model.setRowCount(0);
		rows = new Object[model.getColumnCount()];
		for (Consulta consulta : Clinica.getInstance().getMisConsultas()) {
			rows[0] = consulta.getCodigo();
			rows[1] = consulta.getMiCita().getMiPersona().getNombre()+consulta.getMiCita().getMiPersona().getApellidos();
			rows[2] = consulta.getMiCita().getMiDoctor().getNombre()+consulta.getMiCita().getMiDoctor().getApellidos();
			rows[3] = consulta.getFechaConsulta();
			rows[4] = consulta.getMiCita().getHoraCita();
			model.addRow(rows);
		}
	}
}

