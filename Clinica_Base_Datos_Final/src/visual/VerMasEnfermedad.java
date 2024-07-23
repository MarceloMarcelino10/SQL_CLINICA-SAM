package visual;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerMasEnfermedad extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    public VerMasEnfermedad(String sintomas, String tratamiento) {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(VerMasEnfermedad.class.getResource("/imagenes/fotoTituloDeVentana.png")));
    	setTitle("Detalles sintomas");
        setBounds(100, 100, 511, 385);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(10, 48, 227, 250);
        panel.add(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panel_1.add(scrollPane, BorderLayout.CENTER);

        JTextArea textAreaSintomas = new JTextArea(sintomas);
        textAreaSintomas.setLineWrap(true);
        textAreaSintomas.setWrapStyleWord(true);
        scrollPane.setViewportView(textAreaSintomas);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_2.setBounds(254, 48, 227, 250);
        panel.add(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane_1 = new JScrollPane();
        panel_2.add(scrollPane_1, BorderLayout.CENTER);

        JTextArea textAreaTratamiento = new JTextArea(tratamiento);
        textAreaTratamiento.setLineWrap(true);
        textAreaTratamiento.setWrapStyleWord(true);
        scrollPane_1.setViewportView(textAreaTratamiento);

        JLabel lblNewLabel = new JLabel(">Sintomas<");
        lblNewLabel.setBounds(83, 23, 76, 14);
        panel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel(">Tratamiento<");
        lblNewLabel_1.setBounds(328, 23, 93, 14);
        panel.add(lblNewLabel_1);

        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(new Color(0, 128, 255));
        buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton cancelButton = new JButton("Salir");
        cancelButton.setIcon(new ImageIcon(VerMasEnfermedad.class.getResource("/imagenes/salir16.png")));
        cancelButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();//
        	}
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    public static void main(String[] args) {
        try {
            VerMasEnfermedad dialog = new VerMasEnfermedad("Síntomas aquí", "Tratamiento aquí");
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

