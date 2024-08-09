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
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import logico.Clinica;
import logico.Vivienda;

public class ListarVivienda extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private static JTable table;
    private static DefaultTableModel model;
    private Object[] rows;
    private JButton btnModificar;
    private JButton btnCancelar;
    private JButton btnEliminar;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            ListarVivienda dialog = new ListarVivienda();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public ListarVivienda() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(ListarVivienda.class.getResource("/imagenes/fotoTituloDeVentana.png")));
        setTitle("Lista de Viviendas");
        setBounds(100, 100, 600, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPanel.add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        model = new DefaultTableModel();
        String[] headers = {"Codigo", "Direccion"};
        model.setColumnIdentifiers(headers);
        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(model);
        scrollPane.setViewportView(table);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	int selectedRow = table.getSelectedRow();
                
                if (selectedRow >= 0) {

                	String id_vivienda = (String) model.getValueAt(selectedRow, 0);
                    Vivienda vivienda = Clinica.getInstance().buscarViviendaByIdSQL(id_vivienda);
                    
                    if (vivienda != null) {

                    	RegistrarVivienda dialog = new RegistrarVivienda(vivienda);
                        dialog.setModal(true); 
                        dialog.setVisible(true);
                        
                        JOptionPane.showMessageDialog(null, "Vivienda modificada exitosamente");
                        
                        loadVivienda();
                        
                    } else {
                    	
                        JOptionPane.showMessageDialog(null, "No se encontró la vivienda para modificar.");
                    }
                    
                } else {
                	
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona una vivienda para modificar.");
                }
                
            }
        });
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		 int selectedRow = table.getSelectedRow();
        	        
        		 if (selectedRow >= 0) {
        			 
        			 String id_vivienda = (String) model.getValueAt(selectedRow, 0);

        			 int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar esta vivienda?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        	            
	    	             if (confirm == JOptionPane.YES_OPTION) {

	    	            	 Clinica.getInstance().eliminarDatosViviendaSQL(id_vivienda);
	    	            	 
	    	            	 JOptionPane.showMessageDialog(null, "Vivienda eliminada exitosamente");
	    	            	 
	    	            	 loadVivienda();
	    	             }
	    	             
        	         } else {
        	        	 
        	            JOptionPane.showMessageDialog(null, "Por favor, selecciona una vivienda para eliminar.");
        	         }
        		
        	}
        });
        buttonPane.add(btnEliminar);
        buttonPane.add(btnModificar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        buttonPane.add(btnCancelar);
        
        loadVivienda();
    }
    
    public static void loadVivienda() {
    	
    	model = Clinica.getInstance().cargarDatosViviendaSQL();
        table.setModel(model);	
    }
    
    
    public JTable getTable() {
        return table;
    }
}
