package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Clinica;
import logico.Paciente;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class VerHistorialPaciente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Paciente miPaciente = null;
    private DefaultTableModel model;
    private JTable table;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VerHistorialPaciente dialog = new VerHistorialPaciente(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VerHistorialPaciente(Paciente paciente) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrincipalVisual.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		setResizable(false);
		miPaciente = paciente;
		
		if (miPaciente == null) {
			
			setTitle("Historial del paciente ");
			
		} else {
			
			setTitle("Historial del paciente " + miPaciente.getNombre() + " " + miPaciente.getApellidos());
			
		}
		
		setBounds(100, 100, 705, 430);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				scrollPane.setViewportView(table);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnVerMas = new JButton("VerMas");
				btnVerMas.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						verMas();
						
					}
				});
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
		
		loadConsultasPaciente();
	}

	private void loadConsultasPaciente() {
		
		if (miPaciente != null) {
			model = Clinica.getInstance().cargarDatosConsultaPacienteSQL(miPaciente.getCodigo());
			table.setModel(model);
		}
	}
	
	private void verMas() {
		
		int selectedRow = table.getSelectedRow();

        if (selectedRow >= 0) {
        	
            String diagnostico = (String) model.getValueAt(selectedRow, 4);

            JDialog dialog = new JDialog(this, "Diagnostico", true); 
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(this);
            dialog.getContentPane().setLayout(new BorderLayout());

            JTextArea textArea = new JTextArea(diagnostico);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnVolver = new JButton("Volver");
            btnVolver.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                	dispose();
                }
            });
            
            buttonPanel.add(btnVolver);

            dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            dialog.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione una consulta", "Error", JOptionPane.ERROR_MESSAGE);
        }
		
		
	}
	
}
