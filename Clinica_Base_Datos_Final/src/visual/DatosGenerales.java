package visual;

import java.awt.BorderLayout
;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;

import logico.Clinica;

public class DatosGenerales extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel panel_resultados;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DatosGenerales dialog = new DatosGenerales();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DatosGenerales() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrincipalVisual.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		setTitle("Reporte Clinico General");
		setResizable(false);
		setBounds(100, 100, 1000, 650);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel_botones = new JPanel();
		panel_botones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_botones.setBounds(14, 52, 163, 512);
		contentPanel.add(panel_botones);
		panel_botones.setLayout(null);
		
		JButton btnVivinda = new JButton("Vivienda");
		btnVivinda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				generarViviendaMasPoblada();
			}
		});
		btnVivinda.setBounds(10, 36, 133, 23);
		panel_botones.add(btnVivinda);
		
		JButton btnEdadPaciente = new JButton("Edad Paciente");
		btnEdadPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				generarEdadPaciente();
			}
		});
		btnEdadPaciente.setBounds(10, 95, 133, 23);
		panel_botones.add(btnEdadPaciente);
		
		JButton btnSexoPaciente = new JButton("Sexo Paciente");
		btnSexoPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				generarSexoPaciente();
			}
		});
		btnSexoPaciente.setBounds(10, 154, 133, 23);
		panel_botones.add(btnSexoPaciente);
		
		JButton btnEnferemedadesRecurentes = new JButton("Enf. Recurrentes");
		btnEnferemedadesRecurentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				generarEnfermedadesRecurrentes();
			}
		});
		btnEnferemedadesRecurentes.setBounds(10, 213, 133, 23);
		panel_botones.add(btnEnferemedadesRecurentes);
		
		JButton btnEnferemdadesGraves = new JButton("Enf. Graves");
		btnEnferemdadesGraves.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				generarEnfermedadesRecurrentesGraves();
			}
		});
		btnEnferemdadesGraves.setBounds(10, 272, 133, 23);
		panel_botones.add(btnEnferemdadesGraves);
		
		JButton btnDoctorMasCitas = new JButton("Doc. Citas");
		btnDoctorMasCitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				generarDoctorMasCitas();
			}
		});
		btnDoctorMasCitas.setBounds(10, 331, 133, 23);
		panel_botones.add(btnDoctorMasCitas);
		
		JButton btnSexoDoctor = new JButton("Sexo Doctor");
		btnSexoDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				generarSexoDoctor();
			}
		});
		btnSexoDoctor.setBounds(10, 390, 133, 23);
		panel_botones.add(btnSexoDoctor);
		
		JButton btnNewButton = new JButton("Vacuna");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				generarVacuna();
			}
		});
		btnNewButton.setBounds(10, 449, 133, 23);
		panel_botones.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Seleccion:");
		lblNewLabel.setBounds(14, 19, 163, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Resultados:");
		lblNewLabel_1.setBounds(191, 19, 95, 14);
		contentPanel.add(lblNewLabel_1);
		
		panel_resultados = new JPanel();
		panel_resultados.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_resultados.setBounds(191, 52, 788, 512);
		contentPanel.add(panel_resultados);
		panel_resultados.setLayout(new BorderLayout(0, 0));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						panel_resultados.removeAll();
						panel_resultados.revalidate();
						panel_resultados.repaint();
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}
	
	private void generarViviendaMasPoblada() {
		
		ChartPanel chartPanel = Clinica.getInstance().crearGraficoViviendasPobladas();
		panel_resultados.removeAll();
		panel_resultados.add(chartPanel, BorderLayout.CENTER);
		panel_resultados.revalidate();
		panel_resultados.repaint();
		
	}
	
	private void generarEdadPaciente() {
		
		ChartPanel chartPanel = Clinica.getInstance().crearGraficoEdadPromedioPaciente();
		panel_resultados.removeAll();
		panel_resultados.add(chartPanel, BorderLayout.CENTER);
		panel_resultados.revalidate();
		panel_resultados.repaint();
		
	}
	
	private void generarSexoPaciente() {
		
		ChartPanel chartPanel = Clinica.getInstance().crearGraficoDistribucionSexoPaciente();
		panel_resultados.removeAll();
		panel_resultados.add(chartPanel, BorderLayout.CENTER);
		panel_resultados.revalidate();
		panel_resultados.repaint();
		
        PiePlot plot = (PiePlot) (chartPanel.getChart()).getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{0}: {1} ({2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
        ));
	}
	
	private void generarEnfermedadesRecurrentes() {
		
		ChartPanel chartPanel = Clinica.getInstance().crearGraficoEnfermedadesRecurrentes();
		panel_resultados.removeAll();
		panel_resultados.add(chartPanel, BorderLayout.CENTER);
		panel_resultados.revalidate();
		panel_resultados.repaint();
	}
	
	private void generarEnfermedadesRecurrentesGraves() {
		
		ChartPanel chartPanel = Clinica.getInstance().crearGraficoEnfermedadesMasGraves();
		panel_resultados.removeAll();
		panel_resultados.add(chartPanel, BorderLayout.CENTER);
		panel_resultados.revalidate();
		panel_resultados.repaint();
	}
	
	private void generarDoctorMasCitas() {
		
		ChartPanel chartPanel = Clinica.getInstance().crearGraficoDoctoresConMasCitas();
		panel_resultados.removeAll();
		panel_resultados.add(chartPanel, BorderLayout.CENTER);
		panel_resultados.revalidate();
		panel_resultados.repaint();		
	}
	
	private void generarSexoDoctor() {
		
		ChartPanel chartPanel = Clinica.getInstance().crearGraficoDistribucionSexoDoctor();
		panel_resultados.removeAll();
		panel_resultados.add(chartPanel, BorderLayout.CENTER);
		panel_resultados.revalidate();
		panel_resultados.repaint();
		
        PiePlot plot = (PiePlot) (chartPanel.getChart()).getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{0}: {1} ({2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
        ));
	}
	
	private void generarVacuna() {
		
		ChartPanel chartPanel = Clinica.getInstance().crearGraficoVacunaMasCurativa();
		panel_resultados.removeAll();
		panel_resultados.add(chartPanel, BorderLayout.CENTER);
		panel_resultados.revalidate();
		panel_resultados.repaint();
	}
	
}
