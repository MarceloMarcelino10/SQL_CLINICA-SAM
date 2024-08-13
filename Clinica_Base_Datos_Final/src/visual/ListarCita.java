package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import logico.Cita;
import logico.Clinica;
import logico.Enfermedad;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.ListSelectionModel;

public class ListarCita extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static JTable table;
    private static DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarCita dialog = new ListarCita();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarCita() {
		setTitle("Listar citas");
		setResizable(false);
		setBounds(100, 100, 950, 400);
        setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
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
				JButton btnEliminar = new JButton("Eliminar");
				btnEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						eliminarCita();
						
					}
				});
				buttonPane.add(btnEliminar);
			}
			{
				JButton btnModificar = new JButton("Modificar");
				btnModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						modificarCita();
						
					}
				});
				btnModificar.setActionCommand("OK");
				buttonPane.add(btnModificar);
				getRootPane().setDefaultButton(btnModificar);
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
		
		loadCitas();
	}
	
	public static void loadCitas() {
		model = Clinica.getInstance().cargarDatosCitaSQL();
        table.setModel(model);
	}
	
	
	private void modificarCita() {
		
		int selectedRow = table.getSelectedRow();
        
    	if (selectedRow != -1) {
        	
            String id_cita = (String) table.getValueAt(selectedRow, 0);
            Cita cita = Clinica.getInstance().obtenerCitaByIdSQL(id_cita);
           
            if (cita.isRealizada()) {
            	JOptionPane.showMessageDialog(null, "Por favor, seleccione una cita que no este realizada.", "Error", JOptionPane.ERROR_MESSAGE);
            	return;
            }
            
            RegistrarCita modCita = new RegistrarCita(cita);
            modCita.setModal(true);
            modCita.setVisible(true);
            
            loadCitas();                    
            
        } else {
        	
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una cita para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
		
		
	}
	
	private void eliminarCita() { 

		int selectedRow = table.getSelectedRow();
		
        if (selectedRow == -1) {
        	
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una cita a eliminar.", "Error de eliminación", JOptionPane.ERROR_MESSAGE);
            
        } else {
        	
            int dialogResult = JOptionPane.showConfirmDialog(null,"¿Estás seguro que deseas eliminar esta cita?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
            if (dialogResult == JOptionPane.YES_OPTION) {
            	
                String id_cita = (String) table.getValueAt(selectedRow, 0);
                
                Clinica.getInstance().eliminarDatosCitaSQL(id_cita);
                
                loadCitas();
                
                JOptionPane.showMessageDialog(null, "Cita eliminada correctamente", "Cita eliminada", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }
	}
	
}
