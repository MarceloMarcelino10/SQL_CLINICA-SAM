package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.HistoriaClinica;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HistorialClinico extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private Object[] row;

    public static void main(String[] args) {
        try {
            HistorialClinico dialog = new HistorialClinico();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setModal(true);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HistorialClinico() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(HistorialClinico.class.getResource("/imagenes/fotoTituloDeVentana.png")));
        setTitle("Historial Clínico");
        setBounds(100, 100, 686, 376);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        String[] header = { "Consultas", "Vacunas Aplicadas" };
        model = new DefaultTableModel();
        model.setColumnIdentifiers(header);
        table = new JTable();
        table.setModel(model);
        scrollPane.setViewportView(table);

        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(new Color(0, 128, 255));
        buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.setIcon(new ImageIcon(HistorialClinico.class.getResource("/imagenes/eliminar16x16.png")));
        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila a eliminar.",
                            "Error de eliminación", JOptionPane.ERROR_MESSAGE);
                } else {
                    int dialogResult = JOptionPane.showConfirmDialog(null,
                            "¿Estás seguro que deseas eliminar esta fila?", "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        model.removeRow(selectedRow);

                        JOptionPane.showMessageDialog(null, "Fila eliminada correctamente",
                                "Fila eliminada", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        JButton btnVerMas = new JButton("Ver Más");
        btnVerMas.setIcon(new ImageIcon(HistorialClinico.class.getResource("/imagenes/vermasBOTON.png")));
        btnVerMas.setForeground(new Color(34, 139, 34));
        btnVerMas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes implementar la lógica para ver más detalles de la consulta o vacuna seleccionada
            }
        });
        buttonPane.add(btnVerMas);

        buttonPane.add(eliminarButton);

        JButton cancelButton = new JButton("Salir");
        cancelButton.setIcon(new ImageIcon(HistorialClinico.class.getResource("/imagenes/salir16.png")));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(cancelButton);

    }

    
    // Esta es una función de ejemplo para obtener la tabla desde otra clase
    public JTable getTable() {
        return table;
    }
}
