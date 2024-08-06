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

public class ListarPersona extends JDialog {

    private static final long serialVersionUID = -3536538460360698934L;
    private final JPanel contentPanel = new JPanel();
    private static DefaultTableModel model;
    private static Object[] rows;
    private JTable table;
    private static JComboBox cbxListar;
    public static int index = -1;
    private JButton btnModificar;
    private JButton btnEliminar;
    private String[] headers;
    private Persona selected;

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
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Selección:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setBounds(22, 351, 629, 59);
        contentPanel.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Listar:");
        lblNewLabel.setBounds(60, 22, 46, 14);
        panel.add(lblNewLabel);

        cbxListar = new JComboBox();
        cbxListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarTitulo();
                index = cbxListar.getSelectedIndex();
                if (index != -1) {
                    loadPersona(index);
                }
            }
        });
        cbxListar.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Administrador", "Secretario", "Doctor", "Persona", "Paciente", "Todos"}));
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
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = -1;
                index = table.getSelectedRow();
                if (index >= 0) {
                    btnEliminar.setEnabled(true);
                    btnModificar.setEnabled(true);
                    selected = Clinica.getInstance().buscarPersonaById(table.getValueAt(index, 0).toString());
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
                    RegistrarNewUser regNue = new RegistrarNewUser(selected);
                    regNue.setModal(true);
                    regNue.setVisible(true);
                    dispose();
                }
            });
            btnModificar.setEnabled(false);
            buttonPane.add(btnModificar);
            {
                btnEliminar = new JButton("Eliminar");
                btnEliminar.setIcon(new ImageIcon(ListarPersona.class.getResource("/imagenes/eliminar16x16.png")));
                btnEliminar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int Option = JOptionPane.showConfirmDialog(null,"Seguro desea eliminar la persona con código: " + selected.getCodigo(),"Eliminar Persona", JOptionPane.OK_CANCEL_OPTION);
                        if (Option == JOptionPane.OK_OPTION && selected != null) {
                            Clinica.getInstance().eliminarPersona(selected);
                            btnEliminar.setEnabled(false);
                            btnModificar.setEnabled(false);
                        }
                        loadPersona(index);
                    }
                });
                btnEliminar.setEnabled(false);
                btnEliminar.setActionCommand("OK");
                buttonPane.add(btnEliminar);
                getRootPane().setDefaultButton(btnEliminar);
            }
            {
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
        }

        usuarioLogged();
    }

    private void actualizarTitulo() {
    	
    	
        String selectedItem = cbxListar.getSelectedItem().toString();
        switch (selectedItem) {
            case "Administrador":
                headers = new String[]{"Codigo", "Nombre", "Apellidos", "Edad"};
                Clinica.getInstance().cargarDatosPersonaSQL();
                
                break;
            case "Secretario":
            	
            	Clinica.getInstance().cargarDatosPersonaSQL();
                headers = new String[]{"Codigo", "Nombre", "Apellidos", "Edad"};
                break;
            case "Doctor":
            	
            	Clinica.getInstance().cargarDatosDoctorSQL();
                headers = new String[]{"Codigo", "Nombre", "Apellidos", "Edad", "Especialidad", "Disponibilidad"};
                break;
            case "Persona":
            	
            	Clinica.getInstance().cargarDatosPersonaSQL();
                headers = new String[]{"Codigo", "Nombre", "Apellidos", "Edad"};
                break;
            case "Paciente":
            	
            	Clinica.getInstance().cargarDatosPacienteSQL();
                headers = new String[]{"Codigo", "Nombre", "Apellidos", "Edad", "Tipo de Sangre"};
                
                break;               
            case "Todos":
            	Clinica.getInstance().cargarDatosDoctorSQL();
            	Clinica.getInstance().cargarDatosPacienteSQL();
                Clinica.getInstance().cargarDatosPersonaSQL();
            	
            	
                headers = new String[]{"Codigo", "Nombre", "Apellidos", "Edad"};        
                
                break;
            default:
            	
                Clinica.getInstance().cargarDatosPersonaSQL();
            	Clinica.getInstance().cargarDatosDoctorSQL();
            	Clinica.getInstance().cargarDatosPacienteSQL();
            	
                break; 
        }

        model.setColumnIdentifiers(headers);
        table.setModel(model);
    }

    public static void loadPersona(int index) {
        model.setRowCount(0);
        rows = new Object[model.getColumnCount()];
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String option = cbxListar.getModel().getElementAt(index).toString(); 

        switch (option) {
            case "Administrador":
                for (Persona persona : Clinica.getInstance().getMisPersonas()) {
                    if (persona.getRangoUser() == 1) {
                        rows[0] = persona.getCodigo();
                        rows[1] = persona.getNombre();
                        rows[2] = persona.getApellidos();
                        rows[3] = (persona.getFechaNacimiento() != null) ? dateFormatter.format(persona.getFechaNacimiento()) : "";
                        model.addRow(rows);
                    }
                }
                break;
            case "Secretario":
                for (Persona persona : Clinica.getInstance().getMisPersonas()) {
                    if (persona.getRangoUser() == 2) {
                        rows[0] = persona.getCodigo();
                        rows[1] = persona.getNombre();
                        rows[2] = persona.getApellidos();
                        rows[3] = (persona.getFechaNacimiento() != null) ? dateFormatter.format(persona.getFechaNacimiento()) : "";
                        model.addRow(rows);
                    }
                }
                break;
            case "Doctor":
                for (Persona persona : Clinica.getInstance().getMisPersonas()) {
                    if (persona instanceof Doctor) {
                        rows[0] = persona.getCodigo();
                        rows[1] = persona.getNombre();
                        rows[2] = persona.getApellidos();
                        rows[3] = (persona.getFechaNacimiento() != null) ? dateFormatter.format(persona.getFechaNacimiento()) : "";
                        rows[4] = ((Doctor) persona).getEspecialidad();
                        rows[5] = ((Doctor) persona).isEnServicio() ? "S�" : "No";
                        model.addRow(rows);
                    }
                }
                break;
            case "Persona":
                for (Persona persona : Clinica.getInstance().getMisPersonas()) {
                    if (persona.getRangoUser() == 5 && !(persona instanceof Paciente)) {
                        rows[0] = persona.getCodigo();
                        rows[1] = persona.getNombre();
                        rows[2] = persona.getApellidos();
                        rows[3] = (persona.getFechaNacimiento() != null) ? dateFormatter.format(persona.getFechaNacimiento()) : "";
                        model.addRow(rows);
                    }
                }
                break;
            case "Paciente":
                for (Persona persona : Clinica.getInstance().getMisPersonas()) {
                    if (persona instanceof Paciente) {
                        rows[0] = persona.getCodigo();
                        rows[1] = persona.getNombre();
                        rows[2] = persona.getApellidos();
                        rows[3] = (persona.getFechaNacimiento() != null) ? dateFormatter.format(persona.getFechaNacimiento()) : "";
                        rows[4] = ((Paciente) persona).getTipoSangre();
                        model.addRow(rows);
                    }
                }
                break;
            case "Todos":
                for (Persona persona : Clinica.getInstance().getMisPersonas()) {
                    rows[0] = persona.getCodigo();
                    rows[1] = persona.getNombre();
                    rows[2] = persona.getApellidos();
                    rows[3] = (persona.getFechaNacimiento() != null) ? dateFormatter.format(persona.getFechaNacimiento()) : "";
                    model.addRow(rows);
                }
                break;
            default:
                System.out.println("Opci�n no reconocida: " + option);
                break;
        }
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


    public JTable getTable(String modo) {
        int temp = buscarIndexByTexto(modo);
        cbxListar.setSelectedIndex(temp);
        actualizarTitulo();
        loadPersona(temp);
        return table;
    }
    
    private static int buscarIndexByTexto(String modo) {
        int indice = 0;
        int i = 0;
        boolean  encontrado = false;
        while(!encontrado&& i  <cbxListar.getItemCount()) {
            if(cbxListar.getItemAt(i).toString().equalsIgnoreCase(modo)) {
                indice = i;
                encontrado =  true;
            }
            i++;
        }
        return indice;
    }
}
