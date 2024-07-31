package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import logico.Clinica;
import logico.Enfermedad;

public class RegistrarEnfermedad extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private Enfermedad tempenfer;
    private JTextField txtCodigoEnfermedad;
    private JTextField txtNombreEnfermedad;
    private JTextArea txtAreaSintomas;
    private JTextArea txtAreaTratamiento;
    private JComboBox<String> cbxGravedad;

    // Constructor por defecto
    public RegistrarEnfermedad() {
        this(null);
    }

    // Constructor con Enfermedad
    public RegistrarEnfermedad(Enfermedad enfermedad) {
        setIconImage(Toolkit.getDefaultToolkit().getImage(RegistrarEnfermedad.class.getResource("/imagenes/fotoTituloDeVentana.png")));
        setTitle("Registro Enfermedad");
        setBounds(100, 100, 680, 490);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 128, 255));
        panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(255, 255, 255));
        panel_1.setLayout(null);
        panel_1.setBorder(new TitledBorder(null, "Datos Generales:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(23, 16, 302, 117);
        panel.add(panel_1);

        txtCodigoEnfermedad = new JTextField();
        txtCodigoEnfermedad.setText("" + Clinica.getCodEnfermedad());
        txtCodigoEnfermedad.setEditable(false);
        txtCodigoEnfermedad.setColumns(10);
        txtCodigoEnfermedad.setBounds(82, 26, 172, 20);
        panel_1.add(txtCodigoEnfermedad);

        txtNombreEnfermedad = new JTextField();
        txtNombreEnfermedad.setColumns(10);
        txtNombreEnfermedad.setBounds(82, 69, 172, 20);
        panel_1.add(txtNombreEnfermedad);

        JLabel label = new JLabel("Codigo:");
        label.setBounds(21, 29, 46, 14);
        panel_1.add(label);

        JLabel label_1 = new JLabel("Nombre:");
        label_1.setBounds(21, 72, 63, 14);
        panel_1.add(label_1);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(null);
        panel_2.setBounds(348, 46, 302, 226);
        panel.add(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel_2.add(scrollPane_1, BorderLayout.CENTER);

        txtAreaTratamiento = new JTextArea();
        txtAreaTratamiento.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtAreaTratamiento.setWrapStyleWord(true);
        txtAreaTratamiento.setLineWrap(true);
        scrollPane_1.setViewportView(txtAreaTratamiento);

        JPanel panel_3 = new JPanel();
        panel_3.setBounds(23, 179, 302, 226);
        panel.add(panel_3);
        panel_3.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel_3.add(scrollPane, BorderLayout.CENTER);

        txtAreaSintomas = new JTextArea();
        txtAreaSintomas.setBackground(new Color(255, 255, 255));
        txtAreaSintomas.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtAreaSintomas.setWrapStyleWord(true);
        txtAreaSintomas.setLineWrap(true);
        scrollPane.setViewportView(txtAreaSintomas);

        JLabel label_2 = new JLabel("Sintomas:");
        label_2.setBounds(136, 149, 71, 14);
        panel.add(label_2);

        JPanel panel_4 = new JPanel();
        panel_4.setBackground(new Color(255, 255, 255));
        panel_4.setLayout(null);
        panel_4.setBorder(new TitledBorder(null, "Estado:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_4.setBounds(348, 288, 302, 117);
        panel.add(panel_4);

        cbxGravedad = new JComboBox<>();
        cbxGravedad.setModel(new DefaultComboBoxModel<>(new String[] {"<Seleccionar>", "1 - Leve", "2 - Moderada", "3 - Grave"}));
        cbxGravedad.setBounds(106, 47, 179, 22);
        panel_4.add(cbxGravedad);

        JLabel label_3 = new JLabel("Gravedad:");
        label_3.setBounds(15, 51, 76, 14);
        panel_4.add(label_3);

        JLabel label_4 = new JLabel("Tratamiento:");
        label_4.setBounds(464, 16, 87, 14);
        panel.add(label_4);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel buttonPane = new JPanel();
        buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Registrar");
        okButton.setIcon(new ImageIcon(RegistrarEnfermedad.class.getResource("/imagenes/agregarOcrearboton.png")));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = txtCodigoEnfermedad.getText();
                String nombre = txtNombreEnfermedad.getText();
                String sintomas = txtAreaSintomas.getText();
                String gravedadStr = cbxGravedad.getSelectedItem().toString();
                String tratamiento = txtAreaTratamiento.getText();

                // Validar gravedad
                int gravedad = 0;
                if (!gravedadStr.equals("<Seleccionar>")) {
                    try {
                        gravedad = Integer.parseInt(gravedadStr.split(" - ")[0]);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Error al convertir la gravedad", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (nombre.isEmpty() || sintomas.isEmpty() || gravedadStr.equals("<Seleccionar>") || tratamiento.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (tempenfer == null) {
                        // Crear nueva enfermedad
                        tempenfer = new Enfermedad(
                            codigo,
                            nombre,
                            sintomas,
                            tratamiento,
                            gravedad
                        );
                        JOptionPane.showMessageDialog(null, "Nueva enfermedad registrada con éxito", "Registro Enfermedad", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Actualizar enfermedad existente
                        tempenfer.setNombre(nombre);
                        tempenfer.setSintomas(sintomas);
                        tempenfer.setTratamiento(tratamiento);
                        tempenfer.setGravedad(gravedad);
                        JOptionPane.showMessageDialog(null, "Enfermedad actualizada con éxito", "Actualización Enfermedad", JOptionPane.INFORMATION_MESSAGE);
                    }
                    clean();
                }
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setIcon(new ImageIcon(RegistrarEnfermedad.class.getResource("/imagenes/cancelarboton16x16.png")));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        // Si se pasa una enfermedad al constructor, cargar los datos
        if (enfermedad != null) {
            this.tempenfer = enfermedad;
            txtCodigoEnfermedad.setText(enfermedad.getCodigo());
            txtNombreEnfermedad.setText(enfermedad.getNombre());
            txtAreaSintomas.setText(enfermedad.getSintomas());
            txtAreaTratamiento.setText(enfermedad.getTratamiento());
            cbxGravedad.setSelectedIndex(enfermedad.getGravedad());
        }
    }

    private void clean() {
        txtNombreEnfermedad.setText("");
        txtAreaSintomas.setText("");
        txtAreaTratamiento.setText("");
        cbxGravedad.setSelectedIndex(0);
        txtCodigoEnfermedad.setText("" + Clinica.getCodEnfermedad());
    }
}
