package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logico.Clinica;
import logico.Paciente;
import logico.Persona;
import logico.Vivienda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.SystemColor;

public class VerPacientesVivienda extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private Object[] row;
    private Vivienda vivienda;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            VerPacientesVivienda dialog = new VerPacientesVivienda(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public VerPacientesVivienda(Vivienda vivienda) {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(PrincipalVisual.class.getResource("/imagenes/fotoTituloDeVentana.png")));
    	this.vivienda = vivienda;
        setTitle("Lista de Pacientes en la Vivienda");
        setSize(600, 400); 
        setResizable(false); 
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            contentPanel.add(panel, BorderLayout.CENTER);
            panel.setLayout(new BorderLayout(0, 0));
            {
                JScrollPane scrollPane = new JScrollPane();
                panel.add(scrollPane, BorderLayout.CENTER);
                {
                    String[] header = {"Código", "Nombre", "Apellido", "Edad"};
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
            buttonPane.setForeground(new Color(0, 128, 255));
            buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
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

        loadPacientes();
    }

    private void loadPacientes() {
        model.setRowCount(0);
        row = new Object[model.getColumnCount()];
        for (Persona persona : Clinica.getInstance().getMisPersonas()) {
            if (persona instanceof Paciente && ((Paciente) persona).getMiVivienda().equals(vivienda)) {
                row[0] = persona.getCodigo();
                row[1] = persona.getNombre();
                row[2] = persona.getApellidos();
                row[3] = calculateAge(persona.getFechaNacimiento());
                model.addRow(row);
            }
        }
    }

    private int calculateAge(Date fechaNacimiento) {
        if (fechaNacimiento == null) {
            return 0;
        }
        LocalDate birthDate = fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
