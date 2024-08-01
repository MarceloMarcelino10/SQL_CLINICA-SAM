package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import logico.Clinica;
import logico.Vivienda;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrarVivienda extends JDialog {

    private static final long serialVersionUID = 4309671989948148858L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txtCodigo;
    private JTextField txtDireccion;
    private JButton btnRegistrar;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            RegistrarVivienda dialog = new RegistrarVivienda();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setModal(true);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public RegistrarVivienda() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(RegistrarVivienda.class.getResource("/imagenes/fotoTituloDeVentana.png")));
        setTitle("Registrar Vivienda");
        setResizable(false);
        setBounds(100, 100, 400, 250);
        setLocationRelativeTo(null); // Centrar en la pantalla
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JPanel panelRegistrar = new JPanel();
        panelRegistrar.setBackground(new Color(255, 255, 255));
        panelRegistrar.setBorder(new TitledBorder(null, "Registrar:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelRegistrar.setBounds(34, 20, 320, 130);
        contentPanel.add(panelRegistrar);
        panelRegistrar.setLayout(null);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(12, 30, 46, 14);
        panelRegistrar.add(lblCodigo);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(12, 70, 62, 14);
        panelRegistrar.add(lblDireccion);

        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBounds(100, 27, 200, 20);
        txtCodigo.setText("" + Clinica.getInstance().codVivienda);
        panelRegistrar.add(txtCodigo);
        txtCodigo.setColumns(10);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(100, 67, 200, 20);
        panelRegistrar.add(txtDireccion);
        txtDireccion.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(new Color(255, 255, 255));
        buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setIcon(new ImageIcon(RegistrarVivienda.class.getResource("/imagenes/agregarOcrearboton.png")));
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = txtCodigo.getText();
                String direccion = txtDireccion.getText();

                if (direccion.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Vivienda newVivienda = new Vivienda(codigo, direccion);
                Clinica.getInstance().insertarVivienda(newVivienda);
                JOptionPane.showMessageDialog(null, "Vivienda añadida correctamente", "Registrar Vivienda", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(newVivienda.getCodigo());

                ListarVivienda listarVivienda = new ListarVivienda();
                listarVivienda.loadViviendas();
                clean();
            }
        });
        btnRegistrar.setActionCommand("OK");
        buttonPane.add(btnRegistrar);
        getRootPane().setDefaultButton(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setIcon(new ImageIcon(RegistrarVivienda.class.getResource("/imagenes/cancelarboton16x16.png")));
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnCancelar.setActionCommand("Cancel");
        buttonPane.add(btnCancelar);
    }

    private void clean() {
        txtCodigo.setText("" + Clinica.getInstance().codVivienda);
        txtDireccion.setText("");
    }
}