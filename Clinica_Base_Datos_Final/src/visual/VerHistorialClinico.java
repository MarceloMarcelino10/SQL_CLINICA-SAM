package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Clinica;
import logico.Paciente;
import logico.Persona;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;
import javax.swing.JLabel;

public class VerHistorialClinico extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
    private DefaultTableModel model;
    private JButton btnVerMas;
    private Paciente selected = null;
    private JTextField txtCantidadConsulta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VerHistorialClinico dialog = new VerHistorialClinico();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VerHistorialClinico() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrincipalVisual.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		setResizable(false);
		setTitle("Historial Clinico");
		setBounds(100, 100, 770, 520);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						
						actualizarDatos();
						
					}
				});
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(table);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnVerMas = new JButton("Ver Mas");
				btnVerMas.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						verMas();
						
					}
				});
				{
					JLabel lblNewLabel = new JLabel("Cantidad de Consultas:");
					buttonPane.add(lblNewLabel);
				}
				{
					txtCantidadConsulta = new JTextField();
					txtCantidadConsulta.setText("0");
					buttonPane.add(txtCantidadConsulta);
					txtCantidadConsulta.setColumns(10);
				}
				btnVerMas.setActionCommand("OK"); 
				buttonPane.add(btnVerMas);
				getRootPane().setDefaultButton(btnVerMas);
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
		
		loadHistorialClinico();
	}
	
	private void loadHistorialClinico() {
		
		Persona conectado = Clinica.getInstance().getLoggedUser();
		
		if (conectado == null ) { 
			
			System.out.println("Hay problemas");
			return;
			
		}
		
		if (conectado.getRangoUser() == 4 || conectado instanceof Paciente) { //Paciente
			
			if (conectado != null) {
				
				selected = (Paciente) conectado;
				
				VerHistorialPaciente verHistorial = new VerHistorialPaciente(selected);
				verHistorial.setModal(true);
				
				verHistorial.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        dispose();
                    }
                });
				verHistorial.setVisible(true);
			}
			
			btnVerMas.setEnabled(false);
			
		} else { //Doctor
			
			model = Clinica.getInstance().cargarDatosPacienteSQL();
			table.setModel(model);
			btnVerMas.setEnabled(true);
		}
	} 
	
	
	private void actualizarDatos() {
		
		int index = -1;
		
		index = table.getSelectedRow();
		
		if (index >= 0) {
			
			String id_persona = table.getValueAt(index, 0).toString();
			selected = (Paciente) Clinica.getInstance().buscarPersonaByIdSQL(id_persona);
			
			if (selected != null) {
				
				DefaultTableModel model_consultas = new DefaultTableModel();
				model_consultas = Clinica.getInstance().cargarCantidadConsultasPacienteSQL(selected.getCodigo());
				String cantidad = (String) model_consultas.getValueAt(0, 0);
				txtCantidadConsulta.setText(cantidad);
			}
			
			
		} else {
			
			txtCantidadConsulta.setText("0");
		}
		
	}
	
	private void verMas() {
		
		if (selected != null) {
			
			VerHistorialPaciente verHistorial = new VerHistorialPaciente(selected);
			verHistorial.setModal(true);
			verHistorial.setVisible(true);
		}
		
	}

}
