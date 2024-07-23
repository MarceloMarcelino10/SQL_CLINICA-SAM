package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.ReporteClinico;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import java.awt.Color;

public class VerReporte extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1816665915810368021L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea txtAreaReporte;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VerReporte dialog = new VerReporte();
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
	public VerReporte() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VerReporte.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		setResizable(false);
		setTitle("Reporte General");
		setBounds(100, 100, 710, 475);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				txtAreaReporte = new JTextArea();
				txtAreaReporte.setEditable(false);
				txtAreaReporte.setFont(new Font("Monospaced", Font.PLAIN, 13));
				txtAreaReporte.setWrapStyleWord(true);
				txtAreaReporte.setLineWrap(true);
				scrollPane.setViewportView(txtAreaReporte);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(0, 128, 255));
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnGuardar = new JButton("Guardar");
				btnGuardar.setIcon(new ImageIcon(VerReporte.class.getResource("/imagenes/listatBOTON.png")));
				btnGuardar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						guardarReporte();
						JOptionPane.showMessageDialog(null, "Informaci�n guardada correctamente", "�xito", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
				});
				btnGuardar.setActionCommand("OK");
				buttonPane.add(btnGuardar);
				getRootPane().setDefaultButton(btnGuardar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.setIcon(new ImageIcon(VerReporte.class.getResource("/imagenes/cancelarboton16x16.png")));
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
	}
	
	private void cargarDatos() {
		if (txtAreaReporte == null) {
			txtAreaReporte = new JTextArea();
		}
		ReporteClinico reporte = new ReporteClinico();
        StringBuilder contenido = reporte.getContenido();
        txtAreaReporte.setText(contenido.toString());
	}
	
	private void guardarReporte() {
		ReporteClinico reporte = new ReporteClinico();
		String path = System.getProperty("user.dir")  + "/reporte_clinico.txt";
		reporte.guardarReporteEnArchivo(path);
	}
}
