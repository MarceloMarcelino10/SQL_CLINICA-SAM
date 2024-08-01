package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Clinica;
import logico.Vacuna;

import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;

public class ListarVacuna extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private static JTable table;
    private static DefaultTableModel model;
    private static Object[] row;

 
    public static void main(String[] args) {
        try {
        	ListarVacuna dialog = new ListarVacuna();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 
    public ListarVacuna() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(ListarVacuna.class.getResource("/imagenes/fotoTituloDeVentana.png")));
        setTitle("Lista de Vacunas");
        setBounds(100, 100, 686, 376);
		setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
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
                panel.add(scrollPane, BorderLayout.CENTER);
                {
                    String[] header = { "Codigo", "Vacuna", "Enfermedades que cura (Cantidad)" };
                    model = new DefaultTableModel();
                    model.setColumnIdentifiers(header);
                    table = new JTable();
                    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    table.setModel(model);
                    scrollPane.setViewportView(table);
                }
            }
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBackground(new Color(0, 128, 255));
            buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);

            JButton eliminarButton = new JButton("Eliminar");
            eliminarButton.setIcon(new ImageIcon(ListarVacuna.class.getResource("/imagenes/eliminar16x16.png")));
            eliminarButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                	int selectedRow = table.getSelectedRow();
                
                	if (selectedRow != -1) {
                   
                		String id_vacuna = (String) table.getValueAt(selectedRow, 0);
                	
                		int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres eliminar esta vacuna?", "Confirmar eliminación",  JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)	;

                    	if (response == JOptionPane.YES_OPTION) {
                        	Clinica.getInstance().eliminarDatosVacunaSQL(id_vacuna);
                        	cargarVacuna();
                    	}
                    
                	} else {
                    	JOptionPane.showMessageDialog( null, "Por favor, seleccione una enfermedad.", "Error", JOptionPane.ERROR_MESSAGE);
                	}
                
                }
   
            });
            buttonPane.add(eliminarButton);

            JButton cancelButton = new JButton("Salir");
            cancelButton.setIcon(new ImageIcon(ListarVacuna.class.getResource("/imagenes/salir16.png")));
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            {
            	JButton btnModificar = new JButton("Modificar");
            	btnModificar.addActionListener(new ActionListener() {
            		public void actionPerformed(ActionEvent e) {
            			
            			int selectedRow = table.getSelectedRow();
                        
            			if (selectedRow != -1) {
                            String id_vacuna = (String) table.getValueAt(selectedRow, 0);
                            Vacuna vacuna = Clinica.getInstance().buscarVacunaById(id_vacuna);
                            RegistrarVacuna v = new RegistrarVacuna(vacuna);
                            v.setModal(true);
                            v.setVisible(true);
                            
                        } else {
                            JOptionPane.showMessageDialog(null, "Por favor, seleccione una vacuna.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
            		}
            	});
            	buttonPane.add(btnModificar);
            }
            buttonPane.add(cancelButton);
        }

        cargarVacuna();
    }

    public static void cargarVacuna() {	
    	
    	Clinica.getInstance().cargarDatosEnfermedadSQL(); // Vacuna necesita las enfermedades.
    	Clinica.getInstance().cargarDatosVacunaSQL();
    	
    	if(model == null) {
    		model = new DefaultTableModel();
    	}
    	
        model.setRowCount(0);
        row = new Object[model.getColumnCount()];
        for (Vacuna vacu : Clinica.getInstance().getMisVacunas()) {
            row[0] = vacu.getCodigo();
            row[1] = vacu.getNombre();
            row[2] = vacu.getMisEnfermedades().size();
            model.addRow(row);

        }
    }
}
