package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Cita;
import logico.Clinica;
import logico.Consulta;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ListarConsulta extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarConsulta dialog = new ListarConsulta();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarConsulta() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrincipalVisual.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		setTitle("Listar Consultas");
		setResizable(false);
		setBounds(100, 100, 640, 350);
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
				JButton btnVerMas = new JButton("VerMas");
				btnVerMas.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						verMasConsulta();
						
					}
				});
				buttonPane.add(btnVerMas);
			}
			{
				JButton btnEliminar = new JButton("Eliminar");
				btnEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						eliminarConsulta();
						
					}
				});
				buttonPane.add(btnEliminar);
			}
			{
				JButton btnModificar = new JButton("Modificar");
				btnModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						modificarConsulta();
						
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
		loadConsultas();
	}
	
	public void loadConsultas() {
		
		model = Clinica.getInstance().cargarDatosConsultasSQL();
	    table.setModel(model);

	}
	
	 private void verMasConsulta() {
	        
		int selectedRow = table.getSelectedRow();

        if (selectedRow >= 0) {
        	
            String diagnostico = (String) model.getValueAt(selectedRow, 3);

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
                    dialog.dispose();
                }
            });
            buttonPanel.add(btnVolver);

            dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            dialog.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione una consulta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	 private void modificarConsulta() {
		 
		int selectedRow = table.getSelectedRow();
         
     	if (selectedRow >= 0) {
            
     		String codigo = (String) model.getValueAt(selectedRow, 0);
     		
     		ArrayList<Object> misDatos = Clinica.getInstance().obtenerConsultaByIdSQL(codigo);
     		
            Consulta consulta = (Consulta) misDatos.get(0);
            ArrayList<String> misVacunasAplicadas = (ArrayList<String>) misDatos.get(1);
             
            ModificarConsulta modConsulta = new ModificarConsulta(consulta, misVacunasAplicadas);         
            modConsulta.setModal(true);
     		modConsulta.setVisible(true);
     		
     		loadConsultas();
     		
         } else {
             
         	JOptionPane.showMessageDialog(null, "Por favor seleccione una consulta", "Error", JOptionPane.ERROR_MESSAGE);
         }

	 }
	
	 private void eliminarConsulta() {
		 
		 int selectedRow = table.getSelectedRow();
         
		 if (selectedRow == -1) {
             
			 JOptionPane.showMessageDialog(null, "Por favor, seleccione una consulta a eliminar.", "Error de eliminación", JOptionPane.ERROR_MESSAGE);
         
		 } else {
             
        	 int dialogResult = JOptionPane.showConfirmDialog(null,"¿Estás seguro que deseas eliminar esta consulta?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
             
             if (dialogResult == JOptionPane.YES_OPTION) {
            	 
                 String id_consulta = (String) table.getValueAt(selectedRow, 0);
                 
                 ArrayList<Object> datosConsulta = Clinica.getInstance().obtenerConsultaByIdSQL(id_consulta);
                 Consulta oldConsulta = (Consulta) datosConsulta.get(0);
                 Cita miCita = Clinica.getInstance().obtenerCitaByIdSQL(oldConsulta.getMiCita().getCodigo());
                 miCita.setRealizada(false);
                 Clinica.getInstance().modificarDatosCitaSQL(miCita);
                 
                 Clinica.getInstance().eliminarDatosConsultaSQL(id_consulta);
                 
                 JOptionPane.showMessageDialog(null, "Consulta eliminada correctamente", "Consulta eliminada", JOptionPane.INFORMATION_MESSAGE);
                 
                 loadConsultas();
             }
        } 
	}
}
