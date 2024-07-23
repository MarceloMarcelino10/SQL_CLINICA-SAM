package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import logico.Clinica;
import logico.Paciente;
import logico.Persona;
import logico.Vivienda;

import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class RegistrarVivienda extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4309671989948148858L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtAfiliado;
	private JTextField txtCodigo;
	private JTextField txtDireccion;
	private JTable table;
	private JButton btnBuscar;
	private Vivienda selected;
	private JButton btnRegistrar;
	private JPanel panelFamiliares;
	private JPanel panelListarVivienda;
	private Persona miPersona;
	private int index = -1;
	private JList<String> listFamiliares;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarVivienda dialog = new RegistrarVivienda(null);
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
	public RegistrarVivienda(Persona p) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegistrarVivienda.class.getResource("/imagenes/fotoTituloDeVentana.png")));
		miPersona = p;
		setTitle("Registar Vivienda");
		setResizable(false);
		setBounds(100, 100, 745, 580);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(0, 128, 255));
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			 panel.addMouseListener(new MouseAdapter() {
			        @Override
			        public void mouseClicked(MouseEvent e) {
			            if (e.getComponent() != table) {
			                table.clearSelection();
			            }
			        }
			    });
			
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JPanel panelRegistrar = new JPanel();
				panelRegistrar.setBackground(new Color(255, 255, 255));
				panelRegistrar.setBorder(new TitledBorder(null, "Registrar:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panelRegistrar.setBounds(34, 20, 342, 221);
				panel.add(panelRegistrar);
				panelRegistrar.setLayout(null);
				{
					JLabel lblNewLabel = new JLabel("Afiliado/Familiar:");
					lblNewLabel.setBounds(12, 44, 106, 14);
					panelRegistrar.add(lblNewLabel);
				}
				{
					JLabel lblNewLabel_1 = new JLabel("C\u00F3digo:");
					lblNewLabel_1.setBounds(10, 102, 46, 14);
					panelRegistrar.add(lblNewLabel_1);
				}
				{
					JLabel lblNewLabel_2 = new JLabel("Direcci\u00F3n:");
					lblNewLabel_2.setBounds(10, 160, 62, 14);
					panelRegistrar.add(lblNewLabel_2);
				}
				{
					txtAfiliado = new JTextField();
					txtAfiliado.setBounds(111, 41, 117, 23);
					panelRegistrar.add(txtAfiliado);
					txtAfiliado.setColumns(10);
				}
				{
					txtCodigo = new JTextField();
					txtCodigo.setEditable(false);
					txtCodigo.setBounds(78, 99, 117, 23);
					txtCodigo.setText("Vda-" + Clinica.getInstance().codVivienda);
					panelRegistrar.add(txtCodigo);
					txtCodigo.setColumns(10);
				}
				{
					txtDireccion = new JTextField();
					txtDireccion.setEditable(false);
					txtDireccion.setBounds(78, 157, 233, 23);
					panelRegistrar.add(txtDireccion);
					txtDireccion.setColumns(10);
				}
				
				btnBuscar = new JButton("Buscar");
				btnBuscar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {				
						String afiliado = txtAfiliado.getText();
				        if (selected != null && selected.buscarAfiliado(afiliado)) {
				            btnRegistrar.setText("A�adir");
				         	cargarDatos();
				        } else {
				            btnRegistrar.setText("Registrar");
				            JOptionPane.showMessageDialog(null, "Afiliado no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
				            txtDireccion.setEditable(true);
				            txtAfiliado.setText("");
				            usuarioLogged();
				        }
				    }
				});
				btnBuscar.setBounds(240, 40, 89, 23);
				panelRegistrar.add(btnBuscar);
			}
			{
				panelListarVivienda = new JPanel();
				panelListarVivienda.setBackground(Color.WHITE);
				panelListarVivienda.setEnabled(true);
				panelListarVivienda.setBorder(new TitledBorder(null, "Viviendas:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panelListarVivienda.setBounds(410, 20, 283, 462);
				panel.add(panelListarVivienda);
				panelListarVivienda.setLayout(new BorderLayout(0, 0));
				
				ListarVivienda listarVivienda = new ListarVivienda();
				JTable tableFromListarVivienda = listarVivienda.getTable();

				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panelListarVivienda.add(scrollPane, BorderLayout.CENTER);

				table = tableFromListarVivienda;
				table.addMouseListener(new MouseAdapter() {
				    @Override
				    public void mouseClicked(MouseEvent e) {
						index = table.getSelectedRow();
				        if (index >= 0) {
				           btnBuscar.setEnabled(true);
				           txtAfiliado.setEnabled(true);
				           selected = Clinica.getInstance().buscarViviendaById(table.getValueAt(index, 0).toString());
				           System.out.println("Persona: " + selected.getCodigo() + " Personas en la lista: "+ selected.getMisPersonas().size());
				        } else {
				        	JOptionPane.showMessageDialog(null, "Selecci�n Inv�lida", "Error", JOptionPane.ERROR_MESSAGE);
				        }
				    }
				});
			}
			{
				panelFamiliares = new JPanel();
				panelFamiliares.setBackground(Color.WHITE);
				panelFamiliares.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Afiliados:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				panelFamiliares.setBounds(34, 261, 342, 221);
				panel.add(panelFamiliares);
				panelFamiliares.setLayout(new BorderLayout(0, 0));
				{
					listFamiliares = new JList<>();
					listFamiliares.setBackground(new Color(255, 255, 255));
					listFamiliares.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					panelFamiliares.add(new JScrollPane(listFamiliares), BorderLayout.CENTER);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(255, 255, 255));
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnRegistrar = new JButton("Registrar");
				btnRegistrar.setIcon(new ImageIcon(RegistrarVivienda.class.getResource("/imagenes/agregarOcrearboton.png")));
				btnRegistrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String afiliado = txtAfiliado.getText();
						
						if (miPersona != null) {
							
							index = table.getSelectedRow();
							
							if (index >= 0) {
								
					            if (selected != null && selected.buscarAfiliado(afiliado)) {
					            	
					                if (Clinica.getInstance().existePersonaEnVivienda(miPersona)) {

					                    String[] opciones = {"S�", "No"};
					                    int respuesta = JOptionPane.showOptionDialog(
					                            null, "La persona ya est� registrada en una vivienda. �Desea moverse a otra?",
					                            "Confirmar", JOptionPane.YES_NO_OPTION,
					                            JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

					                    if (respuesta == JOptionPane.YES_OPTION) {

					                        Clinica.getInstance().eliminarPersonaDeVivienda(miPersona);

					                        selected.insertarPersona(miPersona);
					                        ((Paciente) miPersona).setMiVivienda(selected);
					                        Clinica.getInstance().actualizarVivienda(selected);
					                        Clinica.getInstance().actualizarPersona(((Paciente)miPersona));
					                    }
					                    
					                } else {
					                	
					                    selected.insertarPersona(miPersona);
					                    ((Paciente) miPersona).setMiVivienda(selected);
					                    Clinica.getInstance().actualizarVivienda(selected);
					                    Clinica.getInstance().actualizarPersona(((Paciente)miPersona)); 
					                }			                
					            }
					            cargarDatos();
					            JOptionPane.showMessageDialog(null, "Paciente a�adido correctamente", "A�adir Persona a Vivienda", JOptionPane.INFORMATION_MESSAGE);  
					        } 
					    } else {

				            String codigo = txtCodigo.getText();
				            String direccion = txtDireccion.getText();
				            Vivienda newVivienda = new Vivienda(codigo, direccion);
				            Clinica.getInstance().insertarVivienda(newVivienda);
				            JOptionPane.showMessageDialog(null, "Vivienda a�adida correctamente", "Registrar Vivienda", JOptionPane.INFORMATION_MESSAGE);
							System.out.println(newVivienda.getCodigo());
				        }//
				        ListarVivienda.loadViviendas();
				        clean();
					}
				});
				btnRegistrar.setActionCommand("OK");
				buttonPane.add(btnRegistrar);
				getRootPane().setDefaultButton(btnRegistrar);
			}
			{
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
		}
		ListarVivienda.loadViviendas();
		usuarioLogged();
	}
	

	private void usuarioLogged() {
		 if (Clinica.getInstance().loggedUser != null && Clinica.getInstance().loggedUser.getRangoUser() != 4) {
		        txtDireccion.setEditable(false);   
		 } else {
			 	txtDireccion.setEditable(true);   
		 }
	}

	public Vivienda getViviendaSeleccionada() {
		return selected;
	}
	
	private void cargarDatos() {
        txtCodigo.setText(selected.getCodigo());
        txtDireccion.setText(selected.getDireccion());
        txtDireccion.setEditable(false);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Persona persona : selected.getMisPersonas()) {
            listModel.addElement(persona.getCedula() + ":" + persona.getNombre() + ":" + persona.getApellidos());
        }
        listFamiliares.setModel(listModel);
	}
	
	private void clean() {
        txtAfiliado.setText("");
        txtCodigo.setText("Vda-" + Clinica.getInstance().codVivienda);
        txtDireccion.setText("");
        btnRegistrar.setText("Registrar");
        txtDireccion.setEditable(false);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listFamiliares.setModel(listModel);
    }
	
	
	
}
