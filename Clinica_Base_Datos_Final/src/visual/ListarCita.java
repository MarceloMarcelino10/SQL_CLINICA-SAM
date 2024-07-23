package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Cita;
import logico.Clinica;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Toolkit;
import java.awt.Color;

public class ListarCita extends JFrame {

	private JPanel contentPane;
	private JTable tableListarCita;
	private static DefaultTableModel model;
	private static Object[] rows;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListarCita frame = new ListarCita();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.u
	 */
	public ListarCita() {
		setTitle("Lista de Citas");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ListarCita.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 765, 541);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelListarCita = new JPanel();
		panelListarCita.setBounds(12, 13, 723, 468);
		contentPane.add(panelListarCita);
		panelListarCita.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneListarCita = new JScrollPane();
		panelListarCita.add(scrollPaneListarCita, BorderLayout.CENTER);
		
		String headers[] = {"Codigo", "Paciente", "Doctor", "Dia"};
		tableListarCita = new JTable();
		model = new DefaultTableModel();
		model.setColumnIdentifiers(headers);
		tableListarCita.setModel(model);
		scrollPaneListarCita.setViewportView(tableListarCita);
		loadCitas();
	}

	private void loadCitas() {
		model.setRowCount(0);
		rows = new Object[model.getColumnCount()];
		for (Cita cita : Clinica.getInstance().getMisCitas()) {
			rows[0] = cita.getCodigo();
			rows[1] = cita.getMiPersona().getNombre()+cita.getMiPersona().getApellidos();
			rows[2] = cita.getMiDoctor().getNombre()+cita.getMiDoctor().getApellidos();
			rows[3] = cita.getFechaCita();
			rows[4] = cita.getHoraCita();
			model.addRow(rows);
		}
	}
}
