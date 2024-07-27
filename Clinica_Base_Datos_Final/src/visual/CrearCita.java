package visual;

import java.awt.BorderLayout;
import java.sql.Time;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import com.toedter.calendar.JDateChooser;
import logico.Cita;
import logico.Clinica;
import logico.Doctor;
import logico.Paciente;
import logico.Persona;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JList;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.DefaultComboBoxModel;

public class CrearCita extends JDialog {

    private final JPanel panelPrincipal = new JPanel();
    private JTextField txtCodigoCita;
    private JComboBox<Doctor> comboBoxDoctor;
    private JComboBox<Persona> comboBoxPersona;
    private Doctor selectedDoctor;
    private Persona selectedPersona;
    private JDateChooser dateChooser;
    private JSpinner spnHoraCita;

    public static void main(String[] args) {
        try {
            CrearCita dialog = new CrearCita();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setModal(true);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CrearCita() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(CrearCita.class.getResource("/imagenes/fotoTituloDeVentana.png")));
        setTitle("Crear cita");
        setBounds(100, 100, 450, 300); // Reduce el tamaño de la ventana
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        panelPrincipal.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);

        JLabel lblCodigoCita = new JLabel("Código de cita:");
        txtCodigoCita = new JTextField();
        txtCodigoCita.setEditable(false);
        txtCodigoCita.setText("" + Clinica.getCodCita());
        txtCodigoCita.setColumns(10);

        JLabel lblFecha = new JLabel("Día de la cita:");
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new java.awt.Dimension(150, 22));

        JLabel lblHoraCita = new JLabel("Hora de la cita:");
        spnHoraCita = new JSpinner();
        spnHoraCita.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
        JSpinner.DateEditor de_spnHoraCita = new JSpinner.DateEditor(spnHoraCita, "HH:mm");
        spnHoraCita.setEditor(de_spnHoraCita);

        JLabel lblDoctor = new JLabel("Doctor:");
        comboBoxDoctor = new JComboBox<>();
        for (Persona persona : Clinica.getInstance().getMisPersonas()) {
            if (persona instanceof Doctor) {
                comboBoxDoctor.addItem((Doctor) persona);
            }
        }
        comboBoxDoctor.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Doctor) {
                    Doctor doctor = (Doctor) value;
                    setText(doctor.getNombre() + " " + doctor.getApellidos());
                }
                return this;
            }
        });
        comboBoxDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDoctor = (Doctor) comboBoxDoctor.getSelectedItem();
            }
        });

        JLabel lblPersona = new JLabel("Persona:");
        comboBoxPersona = new JComboBox<>();
        for (Persona persona : Clinica.getInstance().getMisPersonas()) {
            if (!(persona instanceof Paciente || persona instanceof Doctor)) {
                comboBoxPersona.addItem(persona);
            }
        }
        comboBoxPersona.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Persona) {
                    Persona persona = (Persona) value;
                    setText(persona.getNombre() + " " + persona.getApellidos());
                }
                return this;
            }
        });
        comboBoxPersona.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPersona = (Persona) comboBoxPersona.getSelectedItem();
            }
        });

        GroupLayout layout = new GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblCodigoCita)
                .addComponent(lblFecha)
                .addComponent(lblHoraCita)
                .addComponent(lblDoctor)
                .addComponent(lblPersona))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(txtCodigoCita)
                .addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(spnHoraCita)
                .addComponent(comboBoxDoctor)
                .addComponent(comboBoxPersona))
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblCodigoCita)
                .addComponent(txtCodigoCita))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblFecha)
                .addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblHoraCita)
                .addComponent(spnHoraCita))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblDoctor)
                .addComponent(comboBoxDoctor))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblPersona)
                .addComponent(comboBoxPersona))
        );

        JPanel buttonPane = new JPanel();
        buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton crearCitabtn = new JButton("Crear cita");
        crearCitabtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarCita();
            }
        });
        crearCitabtn.setIcon(new ImageIcon(CrearCita.class.getResource("/imagenes/agregarOcrearboton.png")));
        crearCitabtn.setActionCommand("OK");
        buttonPane.add(crearCitabtn);
        getRootPane().setDefaultButton(crearCitabtn);

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelarBtn.setIcon(new ImageIcon(CrearCita.class.getResource("/imagenes/cancelarboton16x16.png")));
        cancelarBtn.setActionCommand("Cancel");
        buttonPane.add(cancelarBtn);
    }

    private void registrarCita() {
        Date fecha = dateChooser.getDate();
        Date hora = (Date) spnHoraCita.getValue();

        if (selectedPersona != null && selectedDoctor != null && fecha != null && hora != null) {
            String codigo = txtCodigoCita.getText();

            // Convertir Persona a Paciente
            Paciente nuevoPaciente = new Paciente(
                selectedPersona.getCodigo(), 
                selectedPersona.getCedula(), 
                selectedPersona.getNombre(), 
                selectedPersona.getApellidos(), 
                selectedPersona.getFechaNacimiento(), 
                selectedPersona.getGenero(), 
                selectedPersona.getUser(), 
                selectedPersona.getPassword(), 
                1, null, null);

            // Actualizar la lista de personas en Clinica
            Clinica.getInstance().eliminarPersona(selectedPersona);
            Clinica.getInstance().insertarPersona(nuevoPaciente);

            // Combinar fecha y hora
            Calendar fechaHora = Calendar.getInstance();
            fechaHora.setTime(fecha);
            Calendar horaCita = Calendar.getInstance();
            horaCita.setTime(hora);
            fechaHora.set(Calendar.HOUR_OF_DAY, horaCita.get(Calendar.HOUR_OF_DAY));
            fechaHora.set(Calendar.MINUTE, horaCita.get(Calendar.MINUTE));

            Cita cita = new Cita(codigo, nuevoPaciente, selectedDoctor, fechaHora.getTime(), new Time(fechaHora.getTimeInMillis()));
            Clinica.getInstance().insertarCita(cita);
            JOptionPane.showMessageDialog(null, "Operación satisfactoria", "Registro", JOptionPane.INFORMATION_MESSAGE);

            // Eliminar persona del comboBox
            comboBoxPersona.removeItem(selectedPersona);

            // Limpiar campos después de registrar la cita
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtCodigoCita.setText("" + Clinica.getCodCita());
        dateChooser.setDate(null);
        spnHoraCita.setValue(new Date());
        comboBoxDoctor.setSelectedIndex(0);
        comboBoxPersona.setSelectedIndex(0);
        selectedDoctor = null;
        selectedPersona = null;
    }
}
