package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Enfermedad;
import logico.Vacuna;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class VerMasEnfermedadesVacuna extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
    private DefaultTableModel model;
    private Object[] row;
    private Vacuna miVacuna = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VerMasEnfermedadesVacuna dialog = new VerMasEnfermedadesVacuna(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VerMasEnfermedadesVacuna(Vacuna vacuna) {
		miVacuna = vacuna;
        setIconImage(Toolkit.getDefaultToolkit().getImage(VerMasEnfermedadesVacuna.class.getResource("/imagenes/fotoTituloDeVentana.png")));
        
        if (miVacuna != null) {
            setTitle("Enfermedades que cura la vacuna " + vacuna.getNombre());
        } else {
        	setTitle("MiVacuna");
        }

		setBounds(100, 100, 800, 475);
        setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					table = new JTable();
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					
				    model = new DefaultTableModel();
				    String[] header = { "Nombre", "Sintomas", "Tratamiento", "Gravedad" }; 
				    model.setColumnIdentifiers(header);
				    table.setModel(model);
				    scrollPane.setViewportView(table);
	
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Volver");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				{
					JButton btnDetalles = new JButton("Mas Detalles");
					btnDetalles.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int selectedRow = table.getSelectedRow();
			                if (selectedRow != -1) {
			                    String sintomas = (String) table.getValueAt(selectedRow, 1);
			                    String tratamiento = (String) table.getValueAt(selectedRow, 2);
			                    VerMasEnfermedad verEnfermedad = new VerMasEnfermedad(sintomas, tratamiento);
			                    verEnfermedad.setModal(true);
			                    verEnfermedad.setVisible(true);
			                } else {
			                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una enfermedad.", "Error", JOptionPane.ERROR_MESSAGE);
			                }
						}
					});
					buttonPane.add(btnDetalles);
				}
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		if (miVacuna != null) {
			loadEnfermedadesVacunas();			
		}

	}

	public void loadEnfermedadesVacunas(){
		
		model.setRowCount(0);
        ArrayList<Enfermedad> enfermedades = miVacuna.getMisEnfermedades();
        row = new Object[4];

        for (Enfermedad enfermedad : enfermedades) {
            row[0] = enfermedad.getNombre();
            row[1] = enfermedad.getSintomas();
            row[2] = enfermedad.getTratamiento();
            row[3] = obtenerGravedad(enfermedad.getGravedad());
            model.addRow(row);
        }
	}
	
	private String obtenerGravedad(int gravedad) {
	    switch (gravedad) {
	        case 1:
	            return "Leve";
	        case 2:
	            return "Moderada";
	        default:
	            return "Grave";
	    }
	}

	
}
