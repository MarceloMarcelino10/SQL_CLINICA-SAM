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
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class ListarCita extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private Object[] row;
    private JButton btnModificar;
    private JButton btnEliminar;

    public static void main(String[] args) {
        try {
            ListarCita dialog = new ListarCita();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ListarCita() {
        setBounds(100, 100, 800, 400); // Tamaño adecuado para que todo se vea correctamente
        setResizable(false); // No permitir redimensionar
        setLocationRelativeTo(null); // Centrar el diálogo en la pantalla
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        {
            JScrollPane scrollPane = new JScrollPane();
            contentPanel.add(scrollPane, BorderLayout.CENTER);
            {
                String[] header = {"Codigo", "Paciente", "Doctor", "Fecha/Hora", "Atendido por", "Completado"};
                model = new DefaultTableModel();
                model.setColumnIdentifiers(header);
                table = new JTable();
                table.setModel(model);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Desactivar el ajuste automático de tamaño
                // Establecer tamaños de columnas
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
                btnModificar.setActionCommand("OK");
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
        model.setRowCount(0);
        List<Cita> citas = Clinica.getInstance().getMisCitas();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

        for (Cita cita : citas) {
            row = new Object[6];
            row[0] = cita.getCodigo();
            row[1] = cita.getMiPersona().getNombre() + " " + cita.getMiPersona().getApellidos();
            row[2] = cita.getMiDoctor().getNombre() + " " + cita.getMiDoctor().getApellidos();
            row[3] = dateFormat.format(cita.getFechaCita()) + " " + new SimpleDateFormat("hh:mm a").format(cita.getHoraCita());
            row[4] = cita.isRealizada() ? "Sí" : "No"; // Método para obtener el nombre de la persona que agendó la cita
            row[5] = cita.isRealizada() ? "Sí" : "No";
            model.addRow(row);
        }
    }
}
