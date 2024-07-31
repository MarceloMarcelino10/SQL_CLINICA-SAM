package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import logico.Clinica;
import logico.Consulta;
import logico.Doctor;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListarConsulta extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private Object[] row;
    private JButton btnEliminar;
    private JButton btnVerMas;
    private JButton btnModificar;

    public static void main(String[] args) {
        try {
            ListarConsulta dialog = new ListarConsulta();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ListarConsulta() {
        setTitle("Listar Consultas\r\n");
        setBounds(100, 100, 600, 400);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            contentPanel.add(panel, gbc);
            panel.setLayout(new BorderLayout(0, 0));
            {
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                panel.add(scrollPane, BorderLayout.CENTER);
                {
                    String[] header = {"Codigo", "Fecha Consulta", "Consultado por", "Diagnostico"};
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
            buttonPane.setBackground(SystemColor.textHighlight);
            buttonPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                btnVerMas = new JButton("Ver mas");
                btnVerMas.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        verMas();
                    }
                });
                buttonPane.add(btnVerMas);
            }
            {
                btnEliminar = new JButton("Eliminar");
                btnEliminar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        eliminarConsulta();
                    }
                });
                buttonPane.add(btnEliminar);
            }
            {
                btnModificar = new JButton("Modificar");
                btnModificar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	
                    	int selectedRow = table.getSelectedRow();
                        
                    	if (selectedRow >= 0) {
                           
                    		String codigo = (String) model.getValueAt(selectedRow, 0);
                            Consulta consulta = Clinica.getInstance().buscarConsultaById(codigo);
                            
                            ModificarConsulta modConsulta = new ModificarConsulta(consulta);
                            modConsulta.setModal(true);
                    		modConsulta.setVisible(true);
                    		
                        } else {
                            JOptionPane.showMessageDialog(null, "Por favor seleccione una consulta", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                buttonPane.add(btnModificar);
            }
            {
                JButton cancelButton = new JButton("Cancelar");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                buttonPane.add(cancelButton);
            }
        }
        loadTableData();
    }

    private void loadTableData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat shf = new SimpleDateFormat("hh:mm aa");
        row = new Object[4];
        for (Consulta consulta : Clinica.getInstance().getMisConsultas()) {
            row[0] = consulta.getCodigo();
            row[1] = sdf.format(consulta.getFechaConsulta()) + " " + shf.format(consulta.getMiCita().getHoraCita());
            if (consulta.getMiCita() != null && consulta.getMiCita().getMiDoctor() != null) {
                Doctor doctor = consulta.getMiCita().getMiDoctor();
                row[2] = doctor.getNombre() + " " + doctor.getApellidos();
            } else {
                row[2] = "No asignado";
            }
            row[3] = consulta.getDiagnostico();
            model.addRow(row);
        }
    }

    private void eliminarConsulta() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String codigo = (String) model.getValueAt(selectedRow, 0);
            Consulta consulta = Clinica.getInstance().buscarConsultaById(codigo);
            if (consulta != null) {
                Clinica.getInstance().eliminarConsulta(consulta);
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(null, "Consulta eliminada satisfactoriamente", "Eliminar Consulta", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione una consulta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verMas() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String diagnostico = (String) model.getValueAt(selectedRow, 3);

            JDialog dialog = new JDialog(this, "Diagnóstico", true); 
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
}