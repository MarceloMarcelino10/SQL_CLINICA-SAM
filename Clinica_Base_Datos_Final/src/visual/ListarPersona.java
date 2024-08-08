package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import logico.Clinica;
import logico.Doctor;
import logico.Paciente;
import logico.Persona;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;

public class ListarPersona extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
    private static DefaultTableModel model;
    private static Object[] rows;
    private static JTable table;
    private static JComboBox<String> cbxListar;
    public static int index = -1;
    private JButton btnModificar;
    private JButton btnEliminar;
    private Persona selected;
    public static String rangoSelected = "";

    public static void main(String[] args) {
        try {
            ListarPersona dialog = new ListarPersona();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setModal(true);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ListarPersona() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(ListarPersona.class.getResource("/imagenes/fotoTituloDeVentana.png")));
        setResizable(false);
        setTitle("Listar Persona");
        setBounds(100, 100, 690, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Seleccion:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setBounds(22, 351, 629, 59);
        contentPanel.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Listar:");
        lblNewLabel.setBounds(60, 22, 46, 14);
        panel.add(lblNewLabel);

        cbxListar = new JComboBox<>();
        cbxListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                index = cbxListar.getSelectedIndex();
                rangoSelected = cbxListar.getSelectedItem().toString();
                if (index != -1) {
                    loadPersona(cbxListar.getSelectedItem().toString());
                }
            }
        });
        cbxListar.setModel(new DefaultComboBoxModel<>(new String[] {"<Seleccione>", "Administrador", "Secretario", "Doctor", "Persona", "Paciente", "Todos"}));
        cbxListar.setBounds(116, 19, 125, 20);
        panel.add(cbxListar);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(22, 13, 629, 325);
        contentPanel.add(panel_1);
        panel_1.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 629, 325);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel_1.add(scrollPane);

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = table.getSelectedRow();
                if (index >= 0) {
                    btnEliminar.setEnabled(true);
                    btnModificar.setEnabled(true);
                    selected = Clinica.getInstance().buscarPersonaByIdSQL(table.getValueAt(index, 0).toString());
                }
            }
        });
        model = new DefaultTableModel();
        scrollPane.setViewportView(table);

        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBackground(new Color(0, 128, 255));
            buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);

            btnModificar = new JButton("Modificar");
            btnModificar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	
                	if (table.getSelectedRow() >= 0) {
                        RegistrarNewUser regNue = new RegistrarNewUser(selected);
                        regNue.setModal(true);
                        regNue.setVisible(true);
                	}
                	
                }
            });
            btnModificar.setEnabled(false);
            buttonPane.add(btnModificar);

            btnEliminar = new JButton("Eliminar");
            btnEliminar.setIcon(new ImageIcon(ListarPersona.class.getResource("/imagenes/eliminar16x16.png")));
            btnEliminar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                	int option = JOptionPane.showConfirmDialog(null,"Seguro desea eliminar la persona con código: " + selected.getCodigo(),"Eliminar Persona", JOptionPane.OK_CANCEL_OPTION);
                    
                    if (option == JOptionPane.OK_OPTION && selected != null) {
                        
                    	Clinica.getInstance().eliminarDatosPersonaSQL(table.getValueAt(index, 0).toString());
                        btnEliminar.setEnabled(false);
                        btnModificar.setEnabled(false);
                    }
                    
                    loadPersona(cbxListar.getSelectedItem().toString());
                }
            });
            btnEliminar.setEnabled(false);
            btnEliminar.setActionCommand("OK");
            buttonPane.add(btnEliminar);
            getRootPane().setDefaultButton(btnEliminar);

            JButton btnCancelar = new JButton("Cancelar");
            btnCancelar.setIcon(new ImageIcon(ListarPersona.class.getResource("/imagenes/cancelarboton16x16.png")));
            btnCancelar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            btnCancelar.setActionCommand("Cancel");
            buttonPane.add(btnCancelar);
        }

        usuarioLogged();
    }

    private void usuarioLogged() {
        
    	if (Clinica.getInstance().loggedUser != null) {
            
    		int rango = Clinica.getInstance().loggedUser.getRangoUser();
            
    		switch (rango) {
                case 1: // Administrador
                    cbxListar.setModel(new DefaultComboBoxModel<>(new String[] {"<Seleccione>", "Administrador", "Secretario", "Doctor", "Paciente", "Persona", "Todos"}));
                    btnModificar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                    break;
                case 2: // Secretario
                    cbxListar.setModel(new DefaultComboBoxModel<>(new String[] {"<Seleccione>", "Doctor", "Persona"}));
                    btnModificar.setEnabled(false);
                    btnEliminar.setEnabled(false);
                    break;
                case 3: // Doctor
                    cbxListar.setModel(new DefaultComboBoxModel<>(new String[] {"<Seleccione>", "Doctor", "Persona", "Paciente"}));
                    btnModificar.setEnabled(false);
                    btnEliminar.setEnabled(false);
                    break;
                case 4: // Paciente
                    cbxListar.setModel(new DefaultComboBoxModel<>(new String[] {"<Seleccione>", "Doctor", "Paciente"}));
                    btnModificar.setEnabled(false);
                    btnEliminar.setEnabled(false);
                    break;
                case 5: // Persona
                    cbxListar.setModel(new DefaultComboBoxModel<>(new String[] {"<Seleccione>", "Doctor", "Paciente"}));
                    btnModificar.setEnabled(false);
                    btnEliminar.setEnabled(false);
                    break;
                default:
                    break;
            }
        }
    }
    
    public static void loadPersona(String rangoCbx) {
        
    	int rango = Clinica.getInstance().obtenerRango(rangoCbx);
        model.setRowCount(0);

        if (rango == 3) { // Doctor
            model = Clinica.getInstance().cargarDatosDoctorSQL();
        } else if (rango == 4) { // Paciente
            model = Clinica.getInstance().cargarDatosPacienteSQL();
        } else {
            model = Clinica.getInstance().cargarDatosPersonaSQL(rango);
        }
        
        table.setModel(model);
    }

    public JTable getTable(String modo) {
        loadPersona(modo);
        return table;
    }  
}
