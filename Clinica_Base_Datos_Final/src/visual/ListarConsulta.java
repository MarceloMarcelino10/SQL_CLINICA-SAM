package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import logico.Clinica;
import logico.Consulta;
import logico.Doctor;
import java.awt.SystemColor;

public class ListarConsulta extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private Object[] row;
    private JButton btnEliminar;
    private JButton btnVerMas;

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
        setBounds(100, 100, 600, 400);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
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
                panel.add(scrollPane, BorderLayout.CENTER);
                {
                    String[] header = {"Codigo", "Fecha", "Consultado", "Diagnostico"};
                    model = new DefaultTableModel();
                    model.setColumnIdentifiers(header);
                    table = new JTable();
                    table.setModel(model);
                    scrollPane.setViewportView(table);
                }
            }
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBackground(SystemColor.textHighlight);
            buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                btnVerMas = new JButton("Ver Más");
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
        row = new Object[4];
        for (Consulta consulta : Clinica.getInstance().getMisConsultas()) {
            row[0] = consulta.getCodigo();
            row[1] = sdf.format(consulta.getFechaConsulta());
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
            Consulta consulta = buscarConsultaPorCodigo(codigo);
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
            JTextArea textArea = new JTextArea(diagnostico);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(300, 200));
            JOptionPane.showMessageDialog(null, scrollPane, "Diagnostico", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione una consulta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Consulta buscarConsultaPorCodigo(String codigo) {
        for (Consulta consulta : Clinica.getInstance().getMisConsultas()) {
            if (consulta.getCodigo().equals(codigo)) {
                return consulta;
            }
        }
        return null;
    }
}
