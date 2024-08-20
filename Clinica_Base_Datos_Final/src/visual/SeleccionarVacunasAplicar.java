package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Clinica;
import logico.Vacuna;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;

public class SeleccionarVacunasAplicar extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private ArrayList<String> misVacunasAplicar = new ArrayList<>();
	private DefaultTableModel model;
	private JPanel panel;
	private ArrayList<JCheckBox> checkBoxList = new ArrayList<>();
	private ArrayList<String> vacunasSeleccionadas = new ArrayList<>(); 
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SeleccionarVacunasAplicar dialog = new SeleccionarVacunasAplicar();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SeleccionarVacunasAplicar() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrincipalVisual.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		setResizable(false);
		setTitle("Aplicar vacunas");
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				scrollPane.setViewportView(panel);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton("Aplicar");
				btnRegistrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						vacunasSeleccionadas = obtenerVacunasSeleccionadas();
						dispose(); 
						
					}
				});
				btnRegistrar.setActionCommand("OK");
				buttonPane.add(btnRegistrar);
				getRootPane().setDefaultButton(btnRegistrar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						dispose();
						
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
		loadVacunasAplicar();
		loadCheckBoxVacunas();
	}
	
	private void loadVacunasAplicar() {
		
		model = Clinica.getInstance().cargarDatosVacunaSQL();
		
		misVacunasAplicar.clear();
		
		for (int i = 0; i < model.getRowCount(); i++) {
            String codigo = model.getValueAt(i, 0).toString();
            String nombre = model.getValueAt(i, 1).toString(); 
            String vacuna = "V-" + codigo + " " + nombre;
            System.out.println(vacuna);
            misVacunasAplicar.add(vacuna);
        }
	}

	private void loadCheckBoxVacunas() {
		
		checkBoxList.clear();
		panel.removeAll();
		
		for (int i = 0; i < misVacunasAplicar.size(); i++) {
			
			JCheckBox checkBox = new JCheckBox(misVacunasAplicar.get(i));
			checkBoxList.add(checkBox);
			panel.add(checkBox);
		}
		
		panel.revalidate();
        panel.repaint();
	}
	
	private ArrayList<String> obtenerVacunasSeleccionadas() {
		
		ArrayList<String> seleccionadas = new ArrayList<>();
		
		for (JCheckBox checkBox : checkBoxList) {
			
			if (checkBox.isSelected()) {
			
				seleccionadas.add(checkBox.getText());
			}
		}
		
		return seleccionadas;
	}

	public ArrayList<String> getVacunasSeleccionadas() {
		return vacunasSeleccionadas;
	}
	
	
	public void cargarVacunasPrevias(ArrayList<String> vacunasPrevias) {
        
		for (JCheckBox checkBox : checkBoxList) {
           
			if (vacunasPrevias.contains(checkBox.getText())) {
                
				checkBox.setSelected(true);
            }
        }
		
    }
	
}
