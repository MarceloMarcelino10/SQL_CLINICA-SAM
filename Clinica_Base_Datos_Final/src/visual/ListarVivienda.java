package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import logico.Clinica;
import logico.Vivienda;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Toolkit;

public class ListarVivienda extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private Object[] rows;

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
