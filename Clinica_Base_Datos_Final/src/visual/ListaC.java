package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import logico.Cita;
import logico.Clinica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;

public class ListaC extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private JButton btnModificar;
    private JButton btnEliminar;
    private Object[] row;

    public static void main(String[] args) {
        try {
            ListaC dialog = new ListaC();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ListaC() {
    	setResizable(false);
        setBounds(100, 100, 970, 400);
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
                String[] header = {"Codigo", "Paciente", "Doctor", "Fecha/Hora", "Atendido por", "Completado"};
                model = new DefaultTableModel();
                model.setColumnIdentifiers(header);
                table = new JTable();
                table.setModel(model);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.getColumnModel().getColumn(0).setPreferredWidth(100);
                table.getColumnModel().getColumn(1).setPreferredWidth(150);
                table.getColumnModel().getColumn(2).setPreferredWidth(150);
                table.getColumnModel().getColumn(3).setPreferredWidth(200);
                table.getColumnModel().getColumn(4).setPreferredWidth(150);
                table.getColumnModel().getColumn(5).setPreferredWidth(100);
                scrollPane.setViewportView(table);
            }
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                btnEliminar = new JButton("Eliminar");
                btnEliminar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = table.getSelectedRow();
                        if (selectedRow >= 0) {
                            String codigoCita = (String) table.getValueAt(selectedRow, 0);
                            int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar esta cita?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                Cita citaAEliminar = Clinica.getInstance().buscarCitaById(codigoCita);
                                Clinica.getInstance().eliminarCita(citaAEliminar);
                                loadCitas();
                                JOptionPane.showMessageDialog(null, "Cita eliminada exitosamente", "Eliminar Cita", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Por favor, seleccione una cita para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
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
                            Cita cita = Clinica.getInstance().buscarCitaById(codigo);
                            if (cita != null) {
                                CrearCita dialog = new CrearCita(cita);
                                dialog.setModal(true);
                                dialog.setVisible(true);
                                loadCitas(); // Refresh the table after modification
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Por favor seleccione una cita para modificar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                buttonPane.add(btnModificar);
                getRootPane().setDefaultButton(btnModificar);
            }
            {
                JButton cancelButton = new JButton("Cancelar");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
        loadCitas();
    }

    private void loadCitas() {
        Clinica clinica = Clinica.getInstance();
        clinica.cargarDatosCitaSQL(); // Asegúrate de que esta llamada solo se haga una vez
        clinica.cargarDatosDoctorSQL();
        clinica.cargarDatosPacienteSQL();

        if (clinica == null) {
            JOptionPane.showMessageDialog(null, "La instancia de Clinica es null.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Cita> citas = clinica.getMisCitas();
        if (citas == null) {
            JOptionPane.showMessageDialog(null, "La lista de citas es null.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.setRowCount(0); // Limpia la tabla antes de recargar los datos

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        ArrayList<String> seenCitas = new ArrayList<>(); // Usar ArrayList para verificar duplicados

        for (Cita cita : citas) {
            if (cita == null) {
                continue; // Salta las citas que son null
            }

            String codigoCita = cita.getCodigo();
            if (seenCitas.contains(codigoCita)) {
                continue; // Salta las citas duplicadas
            }

            row = new Object[6];

            String nombrePaciente = "Desconocido";
            if (cita.getMiPersona() != null) {
                nombrePaciente = cita.getMiPersona().getNombre() + " " + cita.getMiPersona().getApellidos();
            }

            String nombreDoctor = "Desconocido";
            if (cita.getMiDoctor() != null) {
                nombreDoctor = cita.getMiDoctor().getNombre() + " " + cita.getMiDoctor().getApellidos();
            }

            row[0] = cita.getCodigo();
            row[1] = nombrePaciente;
            row[2] = nombreDoctor;
            row[3] = (cita.getFechaCita() != null) ? dateFormat.format(cita.getFechaCita()) + " " + new SimpleDateFormat("hh:mm a").format(cita.getHoraCita()) : "Desconocida";
            row[4] = cita.isRealizada() ? "Sí" : "No";
            row[5] = cita.isRealizada() ? "Sí" : "No"; // Asegúrate de que `isCompletada` existe en la clase Cita

            model.addRow(row);
            seenCitas.add(codigoCita); // Añade el código de la cita a la lista de códigos ya vistos
        }
    }
}
