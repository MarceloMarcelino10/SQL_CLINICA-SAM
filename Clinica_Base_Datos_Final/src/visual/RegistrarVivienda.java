package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import logico.Clinica;
import logico.Vivienda;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RegistrarVivienda extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
    private JTextField txtCodigo;
    private JTextField txtDireccion;
    private Vivienda v = null;
    private JButton btnRegistrar;
    private JButton btnCancelar;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        RegistrarVivienda dialog = new RegistrarVivienda(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    /**
     * Create the dialog.
     */
    public RegistrarVivienda(Vivienda vivienda) {
        v = vivienda;
        setTitle(v != null ? "Modificar Vivienda" : "Registrar Vivienda");
        setIconImage(Toolkit.getDefaultToolkit().getImage(RegistrarVivienda.class.getResource("/imagenes/fotoTituloDeVentana.png")));
        setResizable(false);
        setBounds(100, 100, 450, 225);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel lblCodigo = new JLabel("Codigo:");
        lblCodigo.setBounds(66, 43, 46, 14);
        panel.add(lblCodigo);

        JLabel lblDireccion = new JLabel("Direccion:");
        lblDireccion.setBounds(48, 97, 64, 14);
        panel.add(lblDireccion);

        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBounds(122, 40, 154, 20);
        panel.add(txtCodigo);
        txtCodigo.setColumns(10);

        txtDireccion = new JTextField();
        txtDireccion.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		if (txtDireccion.getText().length() >= 30) {
                    e.consume();
                }
        	}
        });
        txtDireccion.setBounds(122, 94, 230, 20);
        panel.add(txtDireccion);
        txtDireccion.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(new Color(0, 128, 255));
        buttonPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        btnRegistrar = new JButton(v != null ? "Modificar" : "Registrar");
        btnRegistrar.setIcon(new ImageIcon(RegistrarVivienda.class.getResource("/imagenes/agregarOcrearboton.png")));
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            	if(v == null) {
                    String codigo = txtCodigo.getText();
                    String direccion = txtDireccion.getText();

                    Vivienda vivienda = new Vivienda(codigo, direccion);
                    Clinica.getInstance().insertarDatosViviendaSQL(vivienda);
                    JOptionPane.showMessageDialog(null, "Vivienda creada exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    clear();
                    
                } else {
                	
                	String direccion = txtDireccion.getText();
                	v.setDireccion(direccion);
                    Clinica.getInstance().modificarDatosViviendaSQL(v);
                    dispose();
                    
                }
            }
        });
        buttonPane.add(btnRegistrar);
        getRootPane().setDefaultButton(btnRegistrar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setIcon(new ImageIcon(RegistrarVivienda.class.getResource("/imagenes/cancelarboton16x16.png")));
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(btnCancelar);
        
        loadVivienda();
    }

    private void loadVivienda() {
    	
    	if (v != null) {
        
    		txtCodigo.setText(v.getCodigo());
            txtDireccion.setText(v.getDireccion());
    	
    	} else {
    		
    		 txtCodigo.setText("" + Clinica.getInstance().obtenerMaximoIdVivienda());
    	}
    }

    private void clear() {
        txtCodigo.setText("" + Clinica.getInstance().obtenerMaximoIdVivienda());
        txtDireccion.setText("");
    }

}
