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
    private JTable table;
    private DefaultTableModel model;
    private Object[] rows;
    private JButton btnModificar;
    private JButton btnCancelar;

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
        String[] headers = {"Código", "Dirección"};
        model.setColumnIdentifiers(headers);
        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(model);
        scrollPane.setViewportView(table);

        loadViviendas();
        
        JPanel buttonPane = new JPanel();
        buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String codigo = (String) model.getValueAt(selectedRow, 0);
                    Vivienda vivienda = Clinica.getInstance().buscarViviendaById(codigo);
                    if (vivienda != null) {
                        // Crear y mostrar el diálogo de modificación como modal
                        CrearVivienda dialog = new CrearVivienda(vivienda);
                        dialog.setModal(true); // Establecer como modal
                        dialog.setVisible(true);

                        if (dialog.isConfirmed()) {
                            // Guardar los cambios en la base de datos
                            Clinica.getInstance().actualizarViviendaSQL(vivienda);
                            JOptionPane.showMessageDialog(null, "Vivienda modificada exitosamente");
                            loadViviendas(); // Recargar los datos después de modificar
                        }
                    }
                }
            }
        });
        buttonPane.add(btnModificar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(btnCancelar);
    }

    public void loadViviendas() {
        Clinica.getInstance().cargarDatosViviendaSQL();
        model.setRowCount(0);
        rows = new Object[model.getColumnCount()];
        for (Vivienda vivienda : Clinica.getInstance().getMisViviendas()) {
            rows[0] = vivienda.getCodigo();
            rows[1] = vivienda.getDireccion();
            model.addRow(rows);
        }
    }

    public JTable getTable() {
        return table;
    }
}
