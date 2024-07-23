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
import javax.swing.border.EtchedBorder;

public class CrearVivienda extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1190338930400665614L;
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
		try {
			CrearVivienda dialog = new CrearVivienda(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CrearVivienda(Vivienda vivienda) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CrearVivienda.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		v = vivienda;
		if (v != null) {
			setTitle("Modificar Vivienda");
		} else {
			setTitle("Crear Vivienda");
		}

		setResizable(false);
		setBounds(100, 100, 450, 225);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("C\u00F3digo:");
			lblNewLabel.setBounds(66, 40, 46, 14);
			panel.add(lblNewLabel);
			
			JLabel lblNewLabel_1 = new JLabel("Direcci\u00F3n:");
			lblNewLabel_1.setBounds(45, 94, 67, 14);
			panel.add(lblNewLabel_1);
			
			txtCodigo = new JTextField();
			txtCodigo.setEditable(false);
			txtCodigo.setBounds(122, 40, 154, 20);
			panel.add(txtCodigo);
			txtCodigo.setColumns(10);
			
			txtDireccion = new JTextField();
			txtDireccion.setBounds(122, 94, 230, 20);
			panel.add(txtDireccion);
			txtDireccion.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(0, 128, 255));
			buttonPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnRegistrar = new JButton("Registar");
				btnRegistrar.setIcon(new ImageIcon(CrearVivienda.class.getResource("/imagenes/agregarOcrearboton.png")));
				if (v != null) {
					btnRegistrar.setText("Modificar");
				}
				btnRegistrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(v == null) {
							String codigo = txtCodigo.getText();
							String direccion = txtDireccion.getText();
							Vivienda aux = new Vivienda(codigo, direccion);
							Clinica.getInstance().insertarVivienda(aux);
						} else {
							String codigo = txtCodigo.getText();
							String direccion = txtDireccion.getText();
							v.setCodigo(codigo);
							v.setDireccion(direccion);
							Clinica.getInstance().actualizarVivienda(v);
						}
						ListarVivienda.loadViviendas();
						dispose();
					}
				});
				btnRegistrar.setActionCommand("OK");
				buttonPane.add(btnRegistrar);
				getRootPane().setDefaultButton(btnRegistrar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.setIcon(new ImageIcon(CrearVivienda.class.getResource("/imagenes/cancelarboton16x16.png")));
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
		cargarDatos();
		usuarioLogged();
		ListarVivienda.loadViviendas();
	}
	
	private void cargarDatos() {
		if(v != null) {
			txtCodigo.setText(v.getCodigo());
			txtDireccion.setText(v.getDireccion());
		}
	}
	
	private void usuarioLogged() {
		if(Clinica.getInstance().loggedUser.getRangoUser() != 4) {
			btnRegistrar.setEnabled(false);
		} else {
			btnRegistrar.setEnabled(true);
		}
	}
}
