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
import logico.Enfermedad;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;

public class ListarEnfermedades extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private JButton btnModificar;

    public static void main(String[] args) {
        try {    		
            ListarEnfermedades dialog = new ListarEnfermedades();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setModal(true);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    public ListarEnfermedades() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(ListarEnfermedades.class.getResource("/imagenes/fotoTituloDeVentana.png")));
        setTitle("Lista de Enfermedades");
        setBounds(100, 100, 686, 376);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        String[] header = { "Codigo", "Nombre", "Sintomas", "Tratamiento", "Gravedad" };
        model = new DefaultTableModel();
        model.setColumnIdentifiers(header);
        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(model);
        scrollPane.setViewportView(table);

        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(new Color(0, 128, 255));
        buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.setIcon(new ImageIcon(ListarEnfermedades.class.getResource("/imagenes/eliminar16x16.png")));
        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una enfermedad a eliminar.", "Error de eliminación", JOptionPane.ERROR_MESSAGE);
                } else {
                    int dialogResult = JOptionPane.showConfirmDialog(null,"¿Estás seguro que deseas eliminar esta enfermedad?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        String id_enfermedad = (String) table.getValueAt(selectedRow, 0);
                        Clinica.getInstance().eliminarDatosEnfermedadSQL(id_enfermedad);
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(null, "Enfermedad eliminada correctamente", "Enfermedad eliminada", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        buttonPane.add(eliminarButton);

        JButton btnVerMas = new JButton("Ver Mas");
        btnVerMas.setIcon(new ImageIcon(ListarEnfermedades.class.getResource("/imagenes/vermasBOTON.png")));
        btnVerMas.setForeground(new Color(34, 139, 34));
        btnVerMas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String sintomas = (String) table.getValueAt(selectedRow, 2);
                    String tratamiento = (String) table.getValueAt(selectedRow, 3);
                    VerMasEnfermedad verEnfermedad = new VerMasEnfermedad(sintomas, tratamiento);
                    verEnfermedad.setModal(true);
                    verEnfermedad.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una enfermedad.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPane.add(btnVerMas);

        JButton btnNuevaEnfermedad = new JButton("Nueva Enfermedad");
        btnNuevaEnfermedad.setIcon(new ImageIcon(ListarEnfermedades.class.getResource("/imagenes/agregarOcrearboton.png")));
        btnNuevaEnfermedad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegistrarEnfermedad regNewEnfe = new RegistrarEnfermedad(null);
                regNewEnfe.setModal(true);
                regNewEnfe.setVisible(true);
            }
        });
        buttonPane.add(btnNuevaEnfermedad);

        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            	int selectedRow = table.getSelectedRow();
                
            	if (selectedRow != -1) {
                	
                    String codigo = (String) table.getValueAt(selectedRow, 0);
                    Enfermedad enf = Clinica.getInstance().obtenerEnfermedadByIdSQL(codigo);
                   
                    
                    RegistrarEnfermedad regEnfermedad = new RegistrarEnfermedad(enf);
                    regEnfermedad.setModal(true);
                    regEnfermedad.setVisible(true);
                    
                    loadEnfermedades();                    
                    
                } else {
                	
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una enfermedad para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPane.add(btnModificar);

        JButton cancelButton = new JButton("Salir");
        cancelButton.setIcon(new ImageIcon(ListarEnfermedades.class.getResource("/imagenes/salir16.png"))); 
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(cancelButton);

        loadEnfermedades();                    
    }
 
    private void loadEnfermedades() {
    	model = Clinica.getInstance().cargarDatosEnfermedadSQL();
        table.setModel(model);
    }

	public JTable getTable() {
		// TODO Auto-generated method stub
		return table;
	} 
}
