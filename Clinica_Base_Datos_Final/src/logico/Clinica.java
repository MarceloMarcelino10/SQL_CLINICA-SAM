package logico;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import seguridad.GestorUsuario;
import sql.DatabaseConnection;

public class Clinica implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Vivienda> misViviendas;
	private ArrayList<Persona> misPersonas;
	private ArrayList<Enfermedad> misEnfermedades;
	private ArrayList<Cita> misCitas;
	private ArrayList<Vacuna> misVacunas;
	private ArrayList<Consulta> misConsultas;
	private ArrayList<HistoriaClinica> misHistoriasClinicas;
	private static Clinica clinica = null;
	public static int codAdministrador = 1;
	public static int codSecretario = 1;
	public static int codDoctor = 1;
	public static int codPaciente = 1;
	public static int codPersona = 1;
	public static int codCredencial = 1;
	public static int codCita= 1;
	public static int codVacuna = 1;
	public static int codEnfermedad = 1;
	public static int codConsulta = 1;
	public static int codVivienda = 1;
	public static int codHistoriaClinica = 1;
	public static Persona loggedUser;
	private ArrayList<Integer> valoresEstaticos = new ArrayList<>();
	
	public Clinica() {
		super();
		this.misViviendas = new ArrayList<Vivienda>();
		this.misPersonas =  new ArrayList<Persona>();
		this.misEnfermedades =  new ArrayList<Enfermedad>();
		this.misCitas =  new ArrayList<Cita>();
		this.misVacunas =  new ArrayList<Vacuna>();
		this.misConsultas =  new ArrayList<Consulta>();
		this.misHistoriasClinicas = new ArrayList<HistoriaClinica>();
	}
	
	public static Clinica getInstance() {
		if (clinica == null) {
			clinica = new Clinica();
		}
		return clinica;
	}

	public ArrayList<Vivienda> getMisViviendas() {
		return misViviendas;
	}

	public void setMisViviendas(ArrayList<Vivienda> misViviendas) {
		this.misViviendas = misViviendas;
	}

	public ArrayList<Persona> getMisPersonas() {
		return misPersonas;
	}

	public void setMisPersonas(ArrayList<Persona> misPersonas) {
		this.misPersonas = misPersonas;
	}

	public ArrayList<Enfermedad> getMisEnfermedades() {
		return misEnfermedades;
	}

	public void setMisEnfermedades(ArrayList<Enfermedad> misEnfermedades) {
		this.misEnfermedades = misEnfermedades;
	}

	public ArrayList<Cita> getMisCitas() {
		return misCitas;
	}

	public void setMisCitas(ArrayList<Cita> misCitas) {
		this.misCitas = misCitas;
	}

	public ArrayList<Vacuna> getMisVacunas() {
		return misVacunas;
	}

	public void setMisVacunas(ArrayList<Vacuna> misVacunas) {
		this.misVacunas = misVacunas;
	}

	public ArrayList<Consulta> getMisConsultas() {
		return misConsultas;
	}

	public void setMisConsultas(ArrayList<Consulta> misConsultas) {
		this.misConsultas = misConsultas;
	}
	
	public ArrayList<HistoriaClinica> getMisHistoriasClinicas() {
		return misHistoriasClinicas;
	}

	public void setMisHistoriasClinicas(ArrayList<HistoriaClinica> misHistoriasClinicas) {
		this.misHistoriasClinicas = misHistoriasClinicas;
	}
	
	public static Persona getLoggedUser() {
		return Clinica.loggedUser;
	}
	
	/*** IMPLEMENTACIONES SQL ***/
	
	//METODOS PARA EL LOGIN Y REGISTRO SQL:

	public boolean existUserNameSQL(String userName) {
	    String query = "SELECT COUNT(*) " +
	    			   "FROM CREDENCIAL " + 
	    			   "WHERE userName = ? ";
	    
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, userName);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return false;
	}
	
    //MANEJO DE INDICES BASE DE DATOS:
    
    public int obtenerMaximoIdAdministrador() {
        
    	String query = "SELECT MAX(id_administrador) AS max_administrador_id " +
    				   "FROM ADMINISTRADOR";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
            	codAdministrador = rs.getInt("max_administrador_id") + 1;
                return codAdministrador; 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codAdministrador; // Si no hay datos, empezar en 1
    }
    
    public int obtenerMaximoIdSecretario() {
        
    	String query = "SELECT MAX(id_secretario) AS max_secretario_id " +
    				   "FROM SECRETARIO";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                codSecretario = rs.getInt("max_secretario_id") + 1;
                return codSecretario;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codSecretario;
    }
    
   public int obtenerMaximoIdDoctor() {
        
    	String query = "SELECT MAX(id_doctor) AS max_doctor_id " +
    			       "FROM DOCTOR";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                codDoctor = rs.getInt("max_doctor_id") + 1;
                return codDoctor;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codDoctor;
    }
    
    public int obtenerMaximoIdPaciente() {
        
    	String query = "SELECT MAX(id_paciente) AS max_paciente_id " +
    				   "FROM PACIENTE";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                codPaciente = rs.getInt("max_paciente_id") + 1;
                return codPaciente;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codPaciente;
    }
    
    public int obtenerMaximoIdPersona() {
        
    	String query = "SELECT MAX(id_persona) AS max_persona_id " +
    				   "FROM PERSONA";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                codPersona = rs.getInt("max_persona_id") + 1;
                return codPersona;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codPersona;
    }

    
    public int obtenerMaximoIdEnfermedad() {
        
    	String query = "SELECT MAX(id_enfermedad) AS max_enfermedad_id " + 
    				   "FROM ENFERMEDAD";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                codEnfermedad = rs.getInt("max_enfermedad_id") + 1;
                return codEnfermedad;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codEnfermedad;
    }
    
    public int obtenerMaximoIdConsulta() {
        String query = "SELECT MAX(id_consulta) AS max_consulta_id " +
        			   "FROM CONSULTA";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                codConsulta = rs.getInt("max_consulta_id") + 1;
                return codConsulta;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codConsulta;
    }
    
    public int obtenerMaximoIdVivienda() {
        String query = "SELECT MAX(id_vivienda) AS max_vivienda_id " + 
        			   "FROM VIVIENDA";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                codVivienda = rs.getInt("max_vivienda_id") + 1;
                return codVivienda;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codVivienda;
    }
    
    public int obtenerMaximoIdHistoriaClinica() {
        String query = "SELECT MAX(id_historial_clinico) AS max_historia_consulta_id " + 
        			   "FROM HISTORIAL_CLINICO";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) { 
                codHistoriaClinica = rs.getInt("max_historia_consulta_id") + 1;
                return codHistoriaClinica;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codHistoriaClinica;
    }

    public int obtenerMaximoIdVacuna() {
        String query = "SELECT MAX(id_vacuna) AS max_vacuna_id " +
        			   "FROM VACUNA";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                codVacuna = rs.getInt("max_vacuna_id") + 1;
                return codVacuna;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codVacuna;
    }
    
    public int obtenerMaximoIdCita() {
        String query = "SELECT MAX(id_cita) AS max_cita_id " + 
        			   "FROM CITA";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                codCita = rs.getInt("max_cita_id") + 1;
                return codCita;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codCita;
    }
    
    public int obtenerMaximoIdCredencial() {
        String query = "SELECT MAX(id_credencial) AS max_credencial_id " +
        			   "FROM CREDENCIAL";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
            	codCredencial = rs.getInt("max_credencial_id") + 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return codCredencial;
    }

    //ENFERMEDADES SQL:
    
    public DefaultTableModel cargarDatosEnfermedadSQL() {
        String query = "SELECT e.id_enfermedad, e.nombre, e.sintomas, e.tratamiento, e.id_gravedad_enfermedad " +
                       "FROM ENFERMEDAD AS e";

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Codigo", "Nombre", "Sintomas", "Tratamiento", "Gravedad"});

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getString("id_enfermedad");
                row[1] = rs.getString("nombre");
                row[2] = rs.getString("sintomas");
                row[3] = rs.getString("tratamiento");
                row[4] = rs.getInt("id_gravedad_enfermedad");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public void insertarDatosEnfermedadSQL(Enfermedad enfermedad) {
    	
    	Clinica.getInstance().obtenerMaximoIdEnfermedad();

    	String query = "INSERT INTO ENFERMEDAD (id_enfermedad, nombre, sintomas, tratamiento, id_gravedad_enfermedad) VALUES (?, ?, ?, ?, ?)";
    	int filas = 0;
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(enfermedad.getCodigo()));
            stmt.setString(2, enfermedad.getNombre());
            stmt.setString(3, enfermedad.getSintomas());
            stmt.setString(4, enfermedad.getTratamiento());
            stmt.setInt(5, enfermedad.getGravedad());
            filas =+ stmt.executeUpdate();
            System.out.println("Enfermedades agregadas: " + filas);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Enfermedad obtenerEnfermedadByIdSQL(String codigo) {
        String query = "SELECT e.id_enfermedad, e.nombre, e.sintomas, e.tratamiento, e.id_gravedad_enfermedad " +
                       "FROM ENFERMEDAD AS e WHERE e.id_enfermedad = ?";
        
        Enfermedad enfermedad = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                
            	if (rs.next()) {
            		
                    String nombre = rs.getString("nombre");
                    String sintomas = rs.getString("sintomas");
                    String tratamiento = rs.getString("tratamiento");
                    int gravedad = rs.getInt("id_gravedad_enfermedad");

                    enfermedad = new Enfermedad(codigo, nombre, sintomas, tratamiento, gravedad);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enfermedad;
    }

    public void actualizarDatosEnfermedadSQL(Enfermedad enfermedad) {
        String query = "UPDATE ENFERMEDAD SET nombre = ?, sintomas = ?, tratamiento = ?, id_gravedad_enfermedad = ? " +
        		 	   "WHERE id_enfermedad = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
   
            stmt.setString(1, enfermedad.getNombre());
            stmt.setString(2, enfermedad.getSintomas());
            stmt.setString(3, enfermedad.getTratamiento());
            stmt.setInt(4, enfermedad.getGravedad());
            stmt.setString(5, enfermedad.getCodigo());
            int filas = stmt.executeUpdate();
            
            System.out.println("Enfermedades actualizadas: " + filas);

        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
    
    public void eliminarDatosEnfermedadSQL(String id_enfermedad) {
    	
    	String query = "DELETE FROM ENFERMEDAD " +
    				   "WHERE id_enfermedad = ? ";
    	
    	try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    		 
    		stmt.setString(1, id_enfermedad);
    		stmt.executeUpdate();
    		System.out.println("Enfermedad con codigo " + id_enfermedad + " eliminada.");
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    //VACUNAS SQL:
    
    public DefaultTableModel cargarDatosVacunaSQL() {
        String query = "SELECT v.id_vacuna, v.nombre, COUNT(ev.id_enfermedad) AS Cantidades_Enfermedades_Curadas " +
        			   "FROM VACUNA AS v " +
        			   "LEFT JOIN ENFERMEDAD_VACUNA AS ev ON v.id_vacuna = ev.id_vacuna " +
        		       "GROUP BY v.id_vacuna, v.nombre ";

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Codigo", "Nombre Vacuna", "Enfermedades que cura (Cantidad)"});

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[3];
                row[0] = rs.getString("id_vacuna");
                row[1] = rs.getString("nombre");
                row[2] = rs.getString("Cantidades_Enfermedades_Curadas");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
    
    public Vacuna obtenerVacunaByIdSQL(String codigo) {
        String queryVacuna = "SELECT v.id_vacuna, v.nombre " +
                             "FROM VACUNA AS v WHERE v.id_vacuna = ?";

        String queryEnfermedades = "SELECT e.id_enfermedad, e.nombre, e.sintomas, e.tratamiento, e.id_gravedad_enfermedad " +
                                   "FROM ENFERMEDAD AS e " +
                                   "INNER JOIN ENFERMEDAD_VACUNA AS ev ON e.id_enfermedad = ev.id_enfermedad " +
                                   "WHERE ev.id_vacuna = ?";

        Vacuna vacuna = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtVacuna = conn.prepareStatement(queryVacuna);
             PreparedStatement stmtEnfermedades = conn.prepareStatement(queryEnfermedades)) {

            stmtVacuna.setString(1, codigo);
            
            try (ResultSet rsVacuna = stmtVacuna.executeQuery()) {
            	
                if (rsVacuna.next()) {
                	
                    String nombreVacuna = rsVacuna.getString("nombre");
                    vacuna = new Vacuna(codigo, nombreVacuna); 
                }
            }

            if (vacuna != null) {
                
            	stmtEnfermedades.setString(1, codigo);
                
                try (ResultSet rsEnfermedades = stmtEnfermedades.executeQuery()) {
                    
                	while (rsEnfermedades.next()) {
                        
                    	String idEnfermedad = rsEnfermedades.getString("id_enfermedad");
                        String nombreEnfermedad = rsEnfermedades.getString("nombre");
                        String sintomas = rsEnfermedades.getString("sintomas");
                        String tratamiento = rsEnfermedades.getString("tratamiento");
                        int gravedad = rsEnfermedades.getInt("id_gravedad_enfermedad");

                        Enfermedad enfermedad = new Enfermedad(idEnfermedad, nombreEnfermedad, sintomas, tratamiento, gravedad);
                        vacuna.agregarEnfermedad(enfermedad);
                    }
                	
                }
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vacuna;
    }
    
    public void insertarDatosVacunaSQL(Vacuna vacuna) {
    	
        String queryVacuna = "INSERT INTO VACUNA (id_vacuna, nombre) VALUES (?, ?)";
        String queryEnferemedad = "INSERT INTO ENFERMEDAD_VACUNA (id_vacuna, id_enfermedad) VALUES (?, ?)";
        int filas = 0;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement insertVacunaStmt = conn.prepareStatement(queryVacuna)) {

            insertVacunaStmt.setString(1, vacuna.getCodigo());
            insertVacunaStmt.setString(2, vacuna.getNombre());

            filas = insertVacunaStmt.executeUpdate();
            System.out.println("Vacunas agregadas: " + filas);

            try (PreparedStatement insertEnfermedadesStmt = conn.prepareStatement(queryEnferemedad)) {
                for (Enfermedad enfermedad : vacuna.getMisEnfermedades()) {
                    insertEnfermedadesStmt.setString(1, vacuna.getCodigo());
                    insertEnfermedadesStmt.setString(2, enfermedad.getCodigo());
                    insertEnfermedadesStmt.addBatch(); // Agrupamos todas las llamadas
                }
                insertEnfermedadesStmt.executeBatch(); // Ejecutamos todas las llamadas en batch
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarDatosVacunaSQL(Vacuna vacuna) {

        String query = "UPDATE VACUNA SET nombre = ? " +
        			   "WHERE id_vacuna = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vacuna.getNombre());
            stmt.setString(2, vacuna.getCodigo());

            int filas = stmt.executeUpdate();
            System.out.println("Vacunas actualizadas: " + filas);
            
            String deleteEnfermedadesQuery = "DELETE FROM ENFERMEDAD_VACUNA " +
            								 "WHERE id_vacuna = ?";
            
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteEnfermedadesQuery)) {
                deleteStmt.setString(1, vacuna.getCodigo());
                deleteStmt.executeUpdate();
            }

            String insertEnfermedadesQuery = "INSERT INTO ENFERMEDAD_VACUNA (id_vacuna, id_enfermedad) " +
            								 "VALUES (?, ?)";
            
            try (PreparedStatement insertStmt = conn.prepareStatement(insertEnfermedadesQuery)) {
                for (Enfermedad enfermedad : vacuna.getMisEnfermedades()) {
                    insertStmt.setString(1, vacuna.getCodigo());
                    insertStmt.setString(2, enfermedad.getCodigo());
                    insertStmt.addBatch(); //Agrupamos todas las llamadas.
                }
                insertStmt.executeBatch(); //Ejecutamos todas las llamadas.
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarDatosVacunaSQL(String id_vacuna) {

        String queryEnferemedadVacuna = "DELETE FROM ENFERMEDAD_VACUNA " +
                                        "WHERE id_vacuna = ?";
        String queryConsultaVacuna = "DELETE FROM CONSULTA_VACUNA " +
                                     "WHERE id_vacuna_aplicada = ?";
        String queryVacuna = "DELETE FROM VACUNA " +
                             "WHERE id_vacuna = ?";
        int filas = 0;
        int filas_elim_enfermedades = 0;
        int filas_elim_consultas = 0;

        try (Connection conn = DatabaseConnection.getConnection()) {
            
            try (PreparedStatement deleteEnfermedadesStmt = conn.prepareStatement(queryEnferemedadVacuna)) {
                deleteEnfermedadesStmt.setInt(1, Integer.parseInt(id_vacuna));
                filas_elim_enfermedades = deleteEnfermedadesStmt.executeUpdate();
            }

            try (PreparedStatement deleteConsultaStmt = conn.prepareStatement(queryConsultaVacuna)) {
                deleteConsultaStmt.setInt(1, Integer.parseInt(id_vacuna));
                filas_elim_consultas = deleteConsultaStmt.executeUpdate();
            }

            try (PreparedStatement deleteVacunaStmt = conn.prepareStatement(queryVacuna)) {
                deleteVacunaStmt.setInt(1, Integer.parseInt(id_vacuna));
                filas = deleteVacunaStmt.executeUpdate();
                System.out.println(filas + " Vacuna eliminada con: " + filas_elim_enfermedades + " enfermedades eliminadas y " + filas_elim_consultas + " consultas eliminadas.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 

    //PERSONAS SQL:
    
    public DefaultTableModel cargarDatosPersonaSQL(int byRango) {
        String query = "SELECT p.id_persona, p.nombre, p.apellido, DATEDIFF(YEAR,p.fecha_nacimiento,GETDATE()) AS edad, c.id_rango_persona " +
        			   "FROM PERSONA AS p " +
        			   "INNER JOIN CREDENCIAL AS c ON p.id_persona = c.id_persona ";
        
        if (byRango == 0) {
            query = "SELECT p.id_persona, p.nombre, p.apellido, DATEDIFF(YEAR, p.fecha_nacimiento, GETDATE()) AS edad " +
                    "FROM PERSONA AS p";
        } else {  // si se ha especificado un rango [1 = adm, 2 = sec, 5 = pers, 0 = todos]
            query = "SELECT p.id_persona, p.nombre, p.apellido, DATEDIFF(YEAR, p.fecha_nacimiento, GETDATE()) AS edad, c.id_rango_persona " +
                    "FROM PERSONA AS p " +
                    "INNER JOIN CREDENCIAL AS c ON p.id_persona = c.id_persona " +
                    "WHERE c.id_rango_persona = ?";
        }

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Codigo", "Nombre", "Apellidos", "Edad"});

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            if (byRango != 0) {
                stmt.setInt(1, byRango);
            }

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Object[] row = new Object[4];
                    row[0] = rs.getString("id_persona");
                    row[1] = rs.getString("nombre");
                    row[2] = rs.getString("apellido");
                    row[3] = rs.getInt("edad");
                    model.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
 
    public DefaultTableModel cargarDatosDoctorSQL() {
        String query = "SELECT d.id_persona, p.nombre, p.apellido, DATEDIFF(YEAR,p.fecha_nacimiento,GETDATE()) AS edad, d.especialidad, d.enServicio " +
        			   "FROM DOCTOR AS d " +
        			   "INNER JOIN PERSONA AS p ON p.id_persona = d.id_persona ";

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Codigo", "Nombre", "Apellidos", "Edad", "Especialidad", "Disponibilidad"});

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getString("id_persona");
                row[1] = rs.getString("nombre");
                row[2] = rs.getString("apellido");
                row[3] = rs.getInt("edad");
                row[4] = rs.getString("especialidad");
                row[5] = rs.getBoolean("enServicio") ? "Sí" : "No";
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public DefaultTableModel cargarDatosPacienteSQL() {
        String query = "SELECT pa.id_persona, p.nombre, p.apellido, DATEDIFF(YEAR,p.fecha_nacimiento,GETDATE()) AS edad, pa.tipoSangre " +
        			   "FROM PACIENTE AS pa " +
        		       "INNER JOIN PERSONA AS p ON p.id_persona = pa.id_persona ";

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Codigo", "Nombre", "Apellidos", "Edad", "Tipo de Sangre"});

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getString("id_persona");
                row[1] = rs.getString("nombre");
                row[2] = rs.getString("apellido");
                row[3] = rs.getInt("edad");
                row[4] = rs.getString("tipoSangre");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
    
    
    public void insertarDatosPersonaSQL(Persona persona) {
    	
        String queryPersona = "INSERT INTO PERSONA (id_persona, cedula, nombre, apellido, sexo, fecha_nacimiento) " +
        					  "VALUES (?, ?, ?, ?, ?, ?)";
        int filas = 0;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtPersona = conn.prepareStatement(queryPersona)) {

            stmtPersona.setInt(1, Clinica.getInstance().obtenerMaximoIdPersona());
            stmtPersona.setString(2, persona.getCedula());
            stmtPersona.setString(3, persona.getNombre());
            stmtPersona.setString(4, persona.getApellidos());
            stmtPersona.setString(5, obtenerSexoCorto(persona.getGenero()));
            stmtPersona.setDate(6, new java.sql.Date(persona.getFechaNacimiento().getTime()));

            filas = stmtPersona.executeUpdate();
                        
            if (persona instanceof Paciente || persona.getRangoUser() == 4) {
            	
            	insertarDatosCredencialSQL((Paciente) persona, Integer.parseInt(persona.getCodigo()));
                insertarDatosPacienteSQL((Paciente) persona, Integer.parseInt(persona.getCodigo()));
                
            } else if (persona instanceof Doctor || persona.getRangoUser() == 3) {
            	
                insertarDatosCredencialSQL((Doctor) persona, Integer.parseInt(persona.getCodigo()));
                insertarDatosDoctorSQL((Doctor) persona, Integer.parseInt(persona.getCodigo()));
            
            } else {
                insertarDatosCredencialSQL(persona, Integer.parseInt(persona.getCodigo()));
            }

            System.out.println("Persona agregada: " + filas);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insertarDatosCredencialSQL(Persona persona, int id_persona) {
    	
    	int rango_persona = persona.getRangoUser();
    	
    	if (rango_persona != 1 && rango_persona != 2 && rango_persona != 3 && rango_persona != 4 && rango_persona != 5) {
    		rango_persona = 1;
    	}
    	
    	int fila = 0;
 
    	String hashedPassword = GestorUsuario.hashPassword(persona.getPassword());
    	
        String query = "INSERT INTO CREDENCIAL (id_credencial, userName, passwordUser, id_persona, id_rango_persona) " +
        			   "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
        	
            stmt.setInt(1, Clinica.getInstance().obtenerMaximoIdCredencial());
            stmt.setString(2, persona.getUser());
            stmt.setBytes(3, hashedPassword.getBytes());
            stmt.setInt(4, id_persona);
            stmt.setInt(5, rango_persona);

            fila = stmt.executeUpdate();
            System.out.println("Credenciales agregadas: " + fila);
            System.out.println("Verificacion del rango: " + rango_persona);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insertarDatosPacienteSQL(Persona persona, int id_persona) {
    	
    	Paciente paciente = ((Paciente) persona);
    	int fila = 0;
    	
        String query = "INSERT INTO PACIENTE (id_paciente, id_persona, tipoSangre, id_vivienda) " +
        			   "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Clinica.getInstance().obtenerMaximoIdPaciente());
            stmt.setInt(2, id_persona);
            stmt.setString(3, paciente.getTipoSangre());
            stmt.setObject(4, paciente.getMiVivienda() != null ? paciente.getMiVivienda().getCodigo() : null);

            fila = stmt.executeUpdate();
            System.out.println("Pacientes agregados: " + fila);
            System.out.println("Paciente sangre: " + paciente.getTipoSangre());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    }
    
    public void insertarDatosDoctorSQL(Persona persona, int id_persona) {
        
    	Doctor doctor = ((Doctor) persona);
        int fila = 0;
        
        String query = "INSERT INTO DOCTOR (id_doctor, id_persona, especialidad, enServicio) " +
                       "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Clinica.getInstance().obtenerMaximoIdDoctor());
            stmt.setInt(2, id_persona);
            stmt.setString(3, doctor.getEspecialidad());
            stmt.setBoolean(4, doctor.isEnServicio());

            fila = stmt.executeUpdate();
            System.out.println("Doctores agregados: " + fila);
            System.out.println("Doctor especialidad: " + doctor.getEspecialidad());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String obtenerSexoCorto(String sexo) {
        
    	String sexoLowerCase = sexo.toLowerCase();

        switch (sexoLowerCase) {
            case "masculino":
                return "M";
            default:
                return "F";
        }
        
    }
    
    public int obtenerRango(String rango) { // Corresponde al valor en la base de datos(nuevo) // EN EL PROGRMA DE JAVA: 4 ADMIN, 3 SECRETARIO, 2 DOCTOR, 1 PACIENTE, 0 PERSONA(viejo)
    	
    	switch (rango) {
        	case "Administrador":
                return 1; 
            case "Secretario":
                return 2;
            case "Doctor":
                return 3;
            case "Paciente":
                return 4;
            case "Todos":
            	return 0;
            default: // Persona
                return 5;
        }
    }

    public Persona buscarPersonaByIdSQL(String id_persona) {
        Persona persona = null;

        String query = "SELECT p.id_persona, p.cedula, p.nombre, p.apellido, p.sexo, p.fecha_nacimiento, " +
                       "pa.tipoSangre, d.especialidad, d.enServicio, c.id_rango_persona, c.userName, c.passwordUser " +
                       "FROM PERSONA AS p " +
                       "LEFT JOIN PACIENTE AS pa ON p.id_persona = pa.id_persona " +
                       "LEFT JOIN DOCTOR AS d ON p.id_persona = d.id_persona " +
                       "LEFT JOIN CREDENCIAL AS c ON p.id_persona = c.id_persona " +
                       "WHERE p.id_persona = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id_persona);
            
            try (ResultSet rs = stmt.executeQuery()) {
            	
                if (rs.next()) {
                	
                    String codigo = rs.getString("id_persona");
                    String cedula = rs.getString("cedula");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellido");
                    Date fechaNacimiento = rs.getDate("fecha_nacimiento");
                    String sexo = rs.getString("sexo");
                    String userName = rs.getString("userName");
                    String password = "";
                    
                    if (rs.getBytes("passwordUser") == null) {
                    	
                    	password = "123";
                    
                    } else {
                        
                    	password = new String(rs.getBytes("passwordUser"), StandardCharsets.UTF_8);
                    
                    }                    
                    
                    int rangoUser = rs.getInt("id_rango_persona");

                    switch (rangoUser) {
                        case 3: // Doctor
                            String especialidad = rs.getString("especialidad");
                            boolean enServicio = rs.getBoolean("enServicio");
                            persona = new Doctor(codigo, cedula, nombre, apellidos, fechaNacimiento, sexo, userName, password, rangoUser, especialidad, enServicio);
                            break;
                        case 4: // Paciente
                            String tipoSangre = rs.getString("tipoSangre");
                            persona = new Paciente(codigo, cedula, nombre, apellidos, fechaNacimiento, sexo, userName, password, rangoUser, null, tipoSangre);
                            break;
                        default: //[Admin, Secretario, Persona]
                        	 persona = new Persona(codigo, cedula, nombre, apellidos, fechaNacimiento, sexo, userName, password, rangoUser);
                            break;
                    }
                    
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persona;
    }

    public void eliminarDatosPacienteSQL(String id_persona) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Eliminación en cadena para evitar errores con las claves foráneas

            String queryConsultaVacuna = "DELETE FROM CONSULTA_VACUNA WHERE id_consulta IN " +
                                         "(SELECT id_consulta FROM CONSULTA WHERE id_historial_clinico IN " +
                                         "(SELECT id_historial_clinico FROM HISTORIAL_CLINICO " +
                                         "WHERE id_paciente = (SELECT id_paciente FROM PACIENTE WHERE id_persona = ?)))";
            try (PreparedStatement stmtConsultaVacuna = conn.prepareStatement(queryConsultaVacuna)) {
                stmtConsultaVacuna.setString(1, id_persona);
                stmtConsultaVacuna.executeUpdate();
            }

            String queryEnfermedadConsulta = "DELETE FROM ENFERMEDAD_CONSULTA WHERE id_consulta IN " +
                                             "(SELECT id_consulta FROM CONSULTA WHERE id_historial_clinico IN " +
                                             "(SELECT id_historial_clinico FROM HISTORIAL_CLINICO " +
                                             "WHERE id_paciente = (SELECT id_paciente FROM PACIENTE WHERE id_persona = ?)))";
            try (PreparedStatement stmtEnfermedadConsulta = conn.prepareStatement(queryEnfermedadConsulta)) {
                stmtEnfermedadConsulta.setString(1, id_persona);
                stmtEnfermedadConsulta.executeUpdate();
            }

            String queryConsulta = "DELETE FROM CONSULTA WHERE id_historial_clinico IN " +
                                   "(SELECT id_historial_clinico FROM HISTORIAL_CLINICO " +
                                   "WHERE id_paciente = (SELECT id_paciente FROM PACIENTE WHERE id_persona = ?))";
            try (PreparedStatement stmtConsulta = conn.prepareStatement(queryConsulta)) {
                stmtConsulta.setString(1, id_persona);
                stmtConsulta.executeUpdate();
            }

            String queryCita = "DELETE FROM CITA " +
                               "WHERE id_persona = ? OR id_creador_cita = ?";
            try (PreparedStatement stmtCita = conn.prepareStatement(queryCita)) {
                stmtCita.setString(1, id_persona);
                stmtCita.setString(2, id_persona);
                stmtCita.executeUpdate();
            }

            String queryHistorialClinico = "DELETE FROM HISTORIAL_CLINICO " +
                                           "WHERE id_paciente = (SELECT id_paciente FROM PACIENTE WHERE id_persona = ?)";
            try (PreparedStatement stmtHistorialClinico = conn.prepareStatement(queryHistorialClinico)) {
                stmtHistorialClinico.setString(1, id_persona);
                stmtHistorialClinico.executeUpdate();
            }

            String queryCredencial = "DELETE FROM CREDENCIAL " +
                                     "WHERE id_persona = ?";
            try (PreparedStatement stmtCredencial = conn.prepareStatement(queryCredencial)) {
                stmtCredencial.setString(1, id_persona);
                stmtCredencial.executeUpdate();
            }

            String queryPaciente = "DELETE FROM PACIENTE " + 
                                   "WHERE id_persona = ?";
            try (PreparedStatement stmtPaciente = conn.prepareStatement(queryPaciente)) {
                stmtPaciente.setString(1, id_persona);
                stmtPaciente.executeUpdate();
            }

            String queryPersona = "DELETE FROM PERSONA " +
                                  "WHERE id_persona = ?";
            try (PreparedStatement stmtPersona = conn.prepareStatement(queryPersona)) {
                stmtPersona.setString(1, id_persona);
                stmtPersona.executeUpdate();
            }

            conn.commit();
            System.out.println("Datos del paciente con código " + id_persona + " eliminados.");
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void eliminarDatosDoctorSQL(String id_persona) {

        Connection conn = null;
        
        try {
            
        	conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar la transacción

            // Eliminacion en cadena para evitar errores con las claves foráneas
            
            String queryEnfermedadConsulta = "DELETE FROM ENFERMEDAD_CONSULTA WHERE id_consulta IN " +
                                             "(SELECT id_consulta FROM CONSULTA WHERE id_doctor IN " +
                                             "(SELECT id_doctor FROM DOCTOR WHERE id_persona = ?))";
            try (PreparedStatement stmtEnfermedadConsulta = conn.prepareStatement(queryEnfermedadConsulta)) {
                stmtEnfermedadConsulta.setString(1, id_persona);
                stmtEnfermedadConsulta.executeUpdate();
            }

            String queryConsultaVacuna = "DELETE FROM CONSULTA_VACUNA WHERE id_consulta IN " +
                                         "(SELECT id_consulta FROM CONSULTA WHERE id_doctor IN " +
                                         "(SELECT id_doctor FROM DOCTOR WHERE id_persona = ?))";
            try (PreparedStatement stmtConsultaVacuna = conn.prepareStatement(queryConsultaVacuna)) {
                stmtConsultaVacuna.setString(1, id_persona);
                stmtConsultaVacuna.executeUpdate();
            }

            String queryConsulta = "DELETE FROM CONSULTA WHERE id_doctor IN " +
                                   "(SELECT id_doctor FROM DOCTOR WHERE id_persona = ?)";
            try (PreparedStatement stmtConsulta = conn.prepareStatement(queryConsulta)) {
                stmtConsulta.setString(1, id_persona);
                stmtConsulta.executeUpdate();
            }

            String queryCita = "DELETE FROM CITA WHERE id_doctor IN " +
                               "(SELECT id_doctor FROM DOCTOR WHERE id_persona = ?)";
            try (PreparedStatement stmtCita = conn.prepareStatement(queryCita)) {
                stmtCita.setString(1, id_persona);
                stmtCita.executeUpdate();
            }

            String queryCredencial = "DELETE FROM CREDENCIAL WHERE id_persona = ?";
            try (PreparedStatement stmtCredencial = conn.prepareStatement(queryCredencial)) {
                stmtCredencial.setString(1, id_persona);
                stmtCredencial.executeUpdate();
            }

            String queryDoctor = "DELETE FROM DOCTOR WHERE id_persona = ?";
            try (PreparedStatement stmtDoctor = conn.prepareStatement(queryDoctor)) {
                stmtDoctor.setString(1, id_persona);
                stmtDoctor.executeUpdate();
            }

            String queryPersona = "DELETE FROM PERSONA WHERE id_persona = ?";
            try (PreparedStatement stmtPersona = conn.prepareStatement(queryPersona)) {
                stmtPersona.setString(1, id_persona);
                stmtPersona.executeUpdate();
            }

            conn.commit(); // Confirmar la transacción
            System.out.println("Datos del doctor con código " + id_persona + " eliminados.");

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    public void eliminarDatosPersonaSQL(String id_persona) {
        
    	Connection conn = null;
        
        try {
           
        	conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Eliminacion en cadena para evitar errores con las claves foráneas
            
            String queryEnfermedadConsulta = "DELETE FROM ENFERMEDAD_CONSULTA " +
                                             "WHERE id_consulta IN (SELECT id_consulta FROM CONSULTA WHERE id_cita_solicitada IN " + 
                                             "(SELECT id_cita FROM CITA WHERE id_persona = ? OR id_creador_cita = ?))";
            try (PreparedStatement stmtEnfermedadConsulta = conn.prepareStatement(queryEnfermedadConsulta)) {
                stmtEnfermedadConsulta.setString(1, id_persona);
                stmtEnfermedadConsulta.setString(2, id_persona);
                stmtEnfermedadConsulta.executeUpdate();
            }

            String queryConsultaVacuna = "DELETE FROM CONSULTA_VACUNA WHERE id_consulta IN " + 
            							 "(SELECT id_consulta FROM CONSULTA WHERE id_cita_solicitada IN " + 
            							 "(SELECT id_cita FROM CITA WHERE id_persona = ? OR id_creador_cita = ?))";
            try (PreparedStatement stmtConsultaVacuna = conn.prepareStatement(queryConsultaVacuna)) {
                stmtConsultaVacuna.setString(1, id_persona);
                stmtConsultaVacuna.setString(2, id_persona);
                stmtConsultaVacuna.executeUpdate();
            }

            String queryConsulta = "DELETE FROM CONSULTA WHERE id_cita_solicitada IN " + 
            					   "(SELECT id_cita FROM CITA WHERE id_persona = ? OR id_creador_cita = ?)";
            try (PreparedStatement stmtConsulta = conn.prepareStatement(queryConsulta)) {
                stmtConsulta.setString(1, id_persona);
                stmtConsulta.setString(2, id_persona);
                stmtConsulta.executeUpdate();
            }

            String queryCita = "DELETE FROM CITA " + 
            				   "WHERE id_persona = ? OR id_creador_cita = ?";
            try (PreparedStatement stmtCita = conn.prepareStatement(queryCita)) {
                stmtCita.setString(1, id_persona);
                stmtCita.setString(2, id_persona);
                stmtCita.executeUpdate();
            }

            if (esUnPacienteSQL(id_persona)) {
                eliminarDatosPacienteSQL(id_persona);
            }

            String queryAdmin = "DELETE FROM ADMINISTRADOR " + 
            					"WHERE id_persona = ?";
            try (PreparedStatement stmtAdmin = conn.prepareStatement(queryAdmin)) {
                stmtAdmin.setString(1, id_persona);
                stmtAdmin.executeUpdate();
            }

            String querySecretario = "DELETE FROM SECRETARIO " + 
            						 "WHERE id_persona = ?";
            try (PreparedStatement stmtSecretario = conn.prepareStatement(querySecretario)) {
                stmtSecretario.setString(1, id_persona);
                stmtSecretario.executeUpdate();
            }

            String queryDoctor = "DELETE FROM DOCTOR " + 
            					 "WHERE id_persona = ?";
            try (PreparedStatement stmtDoctor = conn.prepareStatement(queryDoctor)) {
                stmtDoctor.setString(1, id_persona);
                stmtDoctor.executeUpdate();
            }

            String queryCredencial = "DELETE FROM CREDENCIAL " +
            						 "WHERE id_persona = ?";
            try (PreparedStatement stmtCredencial = conn.prepareStatement(queryCredencial)) {
                stmtCredencial.setString(1, id_persona);
                stmtCredencial.executeUpdate();
            }

            String queryPersona = "DELETE FROM PERSONA " +
            					  "WHERE id_persona = ?";
            try (PreparedStatement stmtPersona = conn.prepareStatement(queryPersona)) {
                stmtPersona.setString(1, id_persona);
                stmtPersona.executeUpdate();
            }

            conn.commit();
            System.out.println("Datos de la persona con código " + id_persona + " eliminados.");

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }   
    
    public void modificarDatosPersonaSQL(Persona persona) {
    	
        String queryPersona = "UPDATE PERSONA SET cedula = ?, nombre = ?, apellido = ?, sexo = ?, fecha_nacimiento = ? WHERE id_persona = ?";
        int filas = 0;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtPersona = conn.prepareStatement(queryPersona)) {

            stmtPersona.setString(1, persona.getCedula());
            stmtPersona.setString(2, persona.getNombre());
            stmtPersona.setString(3, persona.getApellidos());
            stmtPersona.setString(4, obtenerSexoCorto(persona.getGenero()));
            stmtPersona.setDate(5, new java.sql.Date(persona.getFechaNacimiento().getTime()));
            stmtPersona.setInt(6, Integer.parseInt(persona.getCodigo()));

            filas = stmtPersona.executeUpdate();

            if (persona instanceof Paciente || persona.getRangoUser() == 4) {
                
            	Clinica.getInstance().modificarDatosPacienteSQL((Paciente) persona);
            } 

            else if (persona instanceof Doctor || persona.getRangoUser() == 3) {
                
            	Clinica.getInstance().modificarDatosDoctorSQL((Doctor) persona);
            } 

            Clinica.getInstance().modificarDatosCredencialSQL(persona);

            System.out.println("Persona modificada: " + filas);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void modificarDatosCredencialSQL(Persona persona) {
    	
        String query = "UPDATE CREDENCIAL SET userName = ?, passwordUser = ?, id_rango_persona = ? WHERE id_persona = ?";
        int fila = 0;
        
    	String hashedPassword = GestorUsuario.hashPassword(persona.getPassword());
    	
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, persona.getUser());
            stmt.setBytes(2, hashedPassword.getBytes());
            stmt.setInt(3, persona.getRangoUser());
            stmt.setInt(4, Integer.parseInt(persona.getCodigo()));

            fila = stmt.executeUpdate();
            System.out.println("Credencial modificada: " + fila);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarDatosPacienteSQL(Paciente paciente) {
        String query = "UPDATE PACIENTE SET tipoSangre = ?, id_vivienda = ? WHERE id_persona = ?";
        int fila = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, paciente.getTipoSangre());
            stmt.setObject(2, paciente.getMiVivienda() != null ? paciente.getMiVivienda().getCodigo() : null);
            stmt.setInt(3, Integer.parseInt(paciente.getCodigo()));

            fila = stmt.executeUpdate();
            System.out.println("Paciente modificado: " + fila);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarDatosDoctorSQL(Doctor doctor) {
        String query = "UPDATE DOCTOR SET especialidad = ?, enServicio = ? WHERE id_persona = ?";
        int fila = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, doctor.getEspecialidad());
            stmt.setBoolean(2, doctor.isEnServicio());
            stmt.setInt(3, Integer.parseInt(doctor.getCodigo()));

            fila = stmt.executeUpdate();
            System.out.println("Doctor modificado: " + fila);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //VIVIENDA SQL:
    
    public DefaultTableModel cargarDatosViviendaSQL() {
        String query = "SELECT v.id_vivienda, v.direccion " +
                       "FROM VIVIENDA AS v ";

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Código Vivienda", "Dirección"});

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[2];
                row[0] = rs.getString("id_vivienda");
                row[1] = rs.getString("direccion");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public void insertarDatosViviendaSQL(Vivienda vivienda) {
    	
        String query = "INSERT INTO VIVIENDA (id_vivienda, direccion) " + 
        			   "VALUES (?, ?)";
        int filas = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

        	stmt.setInt(1, Clinica.getInstance().obtenerMaximoIdVivienda());
            stmt.setString(2, vivienda.getDireccion());
            
            filas = stmt.executeUpdate();
            System.out.println("Viviendas agregadas: " + filas);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vivienda buscarViviendaByIdSQL(String id_vivienda) {
        
    	String query = "SELECT id_vivienda, direccion " + 
        			   "FROM VIVIENDA " + 
        			   "WHERE id_vivienda = ?";
        
        Vivienda vivienda = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id_vivienda);
            
            try (ResultSet rs = stmt.executeQuery()) {
                
            	if (rs.next()) {
                    String codigo_vivienda = rs.getString("id_vivienda");
                    String direccion = rs.getString("direccion");

                    vivienda = new Vivienda(codigo_vivienda, direccion);
                }
            	
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vivienda;
    }
    
    public void modificarDatosViviendaSQL(Vivienda vivienda) {
        
    	String query = "UPDATE VIVIENDA SET direccion = ? " +
    				   "WHERE id_vivienda = ?";
        int fila = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, vivienda.getDireccion());
            stmt.setString(2, vivienda.getCodigo());

            fila = stmt.executeUpdate();
            System.out.println("Vivienda modificada: " + fila);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void eliminarDatosViviendaSQL(String id_vivienda) {
        String updatePaciente = "UPDATE PACIENTE SET id_vivienda = NULL WHERE id_vivienda = ?";
        String deleteVivienda = "DELETE FROM VIVIENDA WHERE id_vivienda = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updatePaciente);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteVivienda)) {

        	updateStmt.setInt(1, Integer.parseInt(id_vivienda));
            
        	int sin_vivienda = updateStmt.executeUpdate();
            System.out.println(sin_vivienda + " pacientes actualizados sin vivienda");

            deleteStmt.setInt(1, Integer.parseInt(id_vivienda));
            
            int fila = deleteStmt.executeUpdate();
            System.out.println("Vivienda con código " + fila + " eliminada.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //CITA SQL:
    
    public DefaultTableModel cargarDatosCitaSQL() {
        
    	 DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         DateTimeFormatter hora = DateTimeFormatter.ofPattern("hh:mm a");
    	
    	String query = "SELECT c.id_cita, p.nombre AS nombre_paciente, p.apellido AS apellido_paciente, d.nombre AS nombre_doctor, d.apellido AS apellido_doctor, " +
    				   "c.fecha_cita, c.hora_cita, c.fecha_cita_creacion, crea_cita.nombre AS nombre_creador, crea_cita.apellido AS apellido_creador, c.completada " +
		    		   "FROM CITA AS c " +
		    		   "INNER JOIN PERSONA AS p ON c.id_persona = p.id_persona " +
		    		   "INNER JOIN DOCTOR AS doc ON c.id_doctor = doc.id_doctor " +
		    		   "INNER JOIN PERSONA AS d ON doc.id_persona = d.id_persona " +
		    		   "LEFT JOIN PERSONA AS crea_cita ON c.id_creador_cita = crea_cita.id_persona ";
    			
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Codigo", "Paciente", "Doctor", "Fecha/Hora", "Atendido por", "Completado", "Creacion"});

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getString("id_cita");
                row[1] = rs.getString("nombre_paciente") + " " + rs.getString("apellido_paciente");
                row[2] = rs.getString("nombre_doctor") + " " + rs.getString("apellido_doctor");
               
                LocalDate fechaCita = rs.getDate("fecha_cita").toLocalDate();
                LocalTime horaCita = rs.getTime("hora_cita").toLocalTime();
                String fechaHora = fechaCita.format(fecha) + " a las " + horaCita.format(hora);
                row[3] = fechaHora;

                String nombreCreador = rs.getString("nombre_creador");
                String apellidoCreador = rs.getString("apellido_creador");
                String atendidoPor = (nombreCreador != null && apellidoCreador != null) ? nombreCreador + " " + apellidoCreador : "Desconocido";
                row[4] = atendidoPor;
                
                row[5] = rs.getBoolean("completada") ? "Sí" : "No";
                
                LocalDate fechaCreacion	= rs.getDate("fecha_cita_creacion").toLocalDate();
                row[6] = fechaCreacion.format(fecha);
                
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
    
    public void insertarDatosCitaSQL(Cita cita) {
        
    	String queryDoctor = "SELECT id_doctor " + 
    					     "FROM DOCTOR " + 
    					     "WHERE id_persona = ?";
        
    	String queryInsert = "INSERT INTO CITA (id_cita, fecha_cita, hora_cita, fecha_cita_creacion, completada, id_doctor, id_persona, id_creador_cita) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int filas = 0;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtDoctor = conn.prepareStatement(queryDoctor);
             PreparedStatement stmtInsert = conn.prepareStatement(queryInsert)) {

            stmtDoctor.setInt(1, Integer.parseInt(cita.getMiDoctor().getCodigo()));
            ResultSet rs = stmtDoctor.executeQuery();

            if (rs.next()) {
                int id_doctor = rs.getInt("id_doctor");

                stmtInsert.setInt(1, Clinica.getInstance().obtenerMaximoIdCita());
                stmtInsert.setDate(2, new java.sql.Date(cita.getFechaCita().getTime()));
                stmtInsert.setTime(3, cita.getHoraCita());
                stmtInsert.setTimestamp(4, new java.sql.Timestamp(cita.getFechacreacion().getTime()));
                stmtInsert.setBoolean(5, cita.isRealizada());
                stmtInsert.setInt(6, id_doctor);
                stmtInsert.setInt(7, Integer.parseInt(cita.getMiPersona().getCodigo()));
                stmtInsert.setObject(8, Clinica.getInstance().getLoggedUser() != null ? Clinica.getInstance().getLoggedUser().getCodigo() : null);

                filas += stmtInsert.executeUpdate();
                System.out.println("Citas agregadas: " + filas);
                
            } else {
                System.out.println("Error: El id_persona proporcionado no está asociado con ningún doctor.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Cita obtenerCitaByIdSQL(String id_cita) {
        
    	String query = "SELECT c.id_cita, c.fecha_cita, c.hora_cita, c.fecha_cita_creacion, c.completada, c.id_persona, c.id_creador_cita, d.id_persona AS id_persona_doctor " +
    				   "FROM CITA AS c " +
    			       "INNER JOIN DOCTOR AS d ON c.id_doctor = d.id_doctor " +
    			       "WHERE c.id_cita = ? ";
        
        Cita cita = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id_cita);
            
            try (ResultSet rs = stmt.executeQuery()) {
                
            	if (rs.next()) {
            		
            		
            		String codigo = rs.getString("id_cita");
                    Date fechaCita = rs.getDate("fecha_cita");
                    Time horaCita = rs.getTime("hora_cita");
                    Date fechaCreacion = rs.getTimestamp("fecha_cita_creacion");
                    boolean realizada = rs.getBoolean("completada");
                    String id_persona_doctor = rs.getString("id_persona_doctor");
                    String id_persona = rs.getString("id_persona");

                    Persona miDoctor = Clinica.getInstance().buscarPersonaByIdSQL(id_persona_doctor);
                    Persona miPersona = Clinica.getInstance().buscarPersonaByIdSQL(id_persona);

                    cita = new Cita(codigo, miPersona, ((Doctor) miDoctor), fechaCita, horaCita);
                    cita.setFechacreacion(fechaCreacion);
                    cita.setRealizada(realizada);
                    
                }
            }
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
        }

        return cita;
    }

    public void modificarDatosCitaSQL(Cita cita) {
        
    	String queryDoctor = "SELECT id_doctor " + 
        					  "FROM DOCTOR " + 
        					  "WHERE id_persona = ?";
    	
        String queryUpdate = "UPDATE CITA SET fecha_cita = ?, hora_cita = ?, id_doctor = ?, id_persona = ?, completada = ? " +
        					 "WHERE id_cita = ? ";

        int filas = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtDoctor = conn.prepareStatement(queryDoctor);
             PreparedStatement stmtUpdate = conn.prepareStatement(queryUpdate)) {

            stmtDoctor.setInt(1, Integer.parseInt(cita.getMiDoctor().getCodigo()));
            ResultSet rs = stmtDoctor.executeQuery();

            if (rs.next()) {
                
            	int id_doctor = rs.getInt("id_doctor");

                stmtUpdate.setDate(1, new java.sql.Date(cita.getFechaCita().getTime()));
                stmtUpdate.setTime(2, cita.getHoraCita());
                stmtUpdate.setInt(3, id_doctor);
                stmtUpdate.setInt(4, Integer.parseInt(cita.getMiPersona().getCodigo()));
                stmtUpdate.setInt(5, cita.isRealizada() ? 1 : 0);
                stmtUpdate.setInt(6, Integer.parseInt(cita.getCodigo()));

                filas = stmtUpdate.executeUpdate();
                System.out.println("Citas actualizadas: " + filas);

            } else {
                System.out.println("Error: El id_persona proporcionado no está asociado con ningún doctor.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarDatosCitaSQL(String id_cita) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Eliminación en cadena para evitar errores con las claves foráneas

            String queryConsultaVacuna = "DELETE FROM CONSULTA_VACUNA WHERE id_consulta IN " +
                                         "(SELECT id_consulta FROM CONSULTA WHERE id_cita_solicitada = ?)";
            try (PreparedStatement stmtConsultaVacuna = conn.prepareStatement(queryConsultaVacuna)) {
                stmtConsultaVacuna.setString(1, id_cita);
                stmtConsultaVacuna.executeUpdate();
            }

            String queryEnfermedadConsulta = "DELETE FROM ENFERMEDAD_CONSULTA WHERE id_consulta IN " +
                                             "(SELECT id_consulta FROM CONSULTA WHERE id_cita_solicitada = ?)";
            try (PreparedStatement stmtEnfermedadConsulta = conn.prepareStatement(queryEnfermedadConsulta)) {
                stmtEnfermedadConsulta.setString(1, id_cita);
                stmtEnfermedadConsulta.executeUpdate();
            }

            String queryConsulta = "DELETE FROM CONSULTA " + 
            					   "WHERE id_cita_solicitada = ?";
            try (PreparedStatement stmtConsulta = conn.prepareStatement(queryConsulta)) {
                stmtConsulta.setString(1, id_cita);
                stmtConsulta.executeUpdate();
            }

            String queryCita = "DELETE FROM CITA " + 
            				   "WHERE id_cita = ?";
            try (PreparedStatement stmtCita = conn.prepareStatement(queryCita)) {
                stmtCita.setString(1, id_cita);
                stmtCita.executeUpdate();
            }

            conn.commit();
            System.out.println("Datos de la cita con código " + id_cita + " eliminados.");
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    System.err.println("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    //CONSULTAS SQL:

    public DefaultTableModel cargarDatosConsultasSQL() {
        DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String query = "SELECT c.id_consulta, c.fecha_consulta, p.nombre AS nombre_doctor, p.apellido AS apellido_doctor, c.diagnostico " +
                       "FROM CONSULTA AS c " +
                       "INNER JOIN DOCTOR AS d ON c.id_doctor = d.id_doctor " +
                       "INNER JOIN PERSONA AS p ON d.id_persona = p.id_persona";

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Codigo", "Fecha Consulta", "Consultado por", "Diagnostico"});

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                
            	Object[] row = new Object[4];
                
                row[0] = rs.getString("id_consulta");
                LocalDate fechaConsulta = rs.getDate("fecha_consulta").toLocalDate();
                row[1] = fechaConsulta.format(fechaFormatter);
                row[2] = rs.getString("nombre_doctor") + " " + rs.getString("apellido_doctor");
                row[3] = rs.getString("diagnostico") != null ? rs.getString("diagnostico") : "No tiene";
                model.addRow(row);
            }
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
        }

        return model;
    }

    
    public DefaultTableModel cargarDatosCitasPorDoctorSQL(String id_persona_doctor) {
        
    	DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter hora = DateTimeFormatter.ofPattern("hh:mm a");

        String query = "SELECT c.id_cita, c.fecha_cita, c.hora_cita, c.fecha_cita_creacion, p.nombre AS nombre_paciente, p.apellido AS apellido_paciente " +
                       "FROM CITA AS c " +
                       "INNER JOIN PERSONA AS p ON c.id_persona = p.id_persona " +
                       "WHERE c.id_doctor = (SELECT id_doctor FROM DOCTOR WHERE id_persona = ?) AND c.completada = 0";

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Codigo", "Fecha", "Hora", "Creacion", "Solicitante"});

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, id_persona_doctor);
            
            try (ResultSet rs = stmt.executeQuery()) {
                
            	while (rs.next()) {
                    
            		Object[] row = new Object[5];
                    row[0] = rs.getString("id_cita");
 
                    LocalDate fechaCita = rs.getDate("fecha_cita").toLocalDate();
                    LocalTime horaCita = rs.getTime("hora_cita").toLocalTime();
                    row[1] = fechaCita.format(fecha);
                    row[2] = horaCita.format(hora);

                    LocalDate fechaCreacion = rs.getDate("fecha_cita_creacion").toLocalDate();
                    row[3] = fechaCreacion.format(fecha);

                    row[4] = rs.getString("nombre_paciente") + " " + rs.getString("apellido_paciente");

                    model.addRow(row);
                }
            }
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
        }

        return model;
    }
    
    
    public boolean esUnPacienteSQL(String id_persona) {
        
    	String query = "SELECT COUNT(*) " + 
    				   "FROM PACIENTE " + 
    				   "WHERE id_persona = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, id_persona);
            try (ResultSet rs = stmt.executeQuery()) {
                
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
        }
        
        return false;
    }

    public void insertarNuevaPersonaEnPacienteSQL(String id_persona, String tipoSangre, int id_vivienda) {

        String insertarPacienteQuery = "INSERT INTO PACIENTE (id_paciente, id_persona, tipoSangre, id_vivienda) " +
                                       "VALUES (?, ?, ?, ?)";

        String actualizarCredencialQuery = "UPDATE CREDENCIAL " +
                                           "SET id_rango_persona = 4" +
                                           "WHERE id_persona = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtInsertarPaciente = conn.prepareStatement(insertarPacienteQuery);
             PreparedStatement stmtActualizarCredencial = conn.prepareStatement(actualizarCredencialQuery)) {

            stmtInsertarPaciente.setInt(1, Clinica.getInstance().obtenerMaximoIdPaciente());
            stmtInsertarPaciente.setInt(2, Integer.parseInt(id_persona));
            stmtInsertarPaciente.setString(3, tipoSangre);
            stmtInsertarPaciente.setObject(4, id_vivienda != 0 ? id_vivienda : null);

            int filaInsertada = stmtInsertarPaciente.executeUpdate();
            System.out.println("Pacientes agregados: " + filaInsertada);
            System.out.println("Paciente sangre: " + tipoSangre);

            stmtActualizarCredencial.setInt(1, Integer.parseInt(id_persona));

            int filaActualizada = stmtActualizarCredencial.executeUpdate();
            System.out.println("Credenciales actualizadas: " + filaActualizada);

        } catch (SQLException e) {
        	
            e.printStackTrace();
        }
    }
    
    public void insertarDatosConsultasSQL(Consulta miConsulta, ArrayList<String> vacunasAplicadas) {
        
    	String queryDoctor = "SELECT id_doctor " +
                             "FROM DOCTOR " +
                             "WHERE id_persona = ?";

        String queryInsertConsulta = "INSERT INTO CONSULTA (id_consulta, fecha_consulta, diagnostico, id_doctor, id_cita_solicitada) " +
                                     "VALUES (?, ?, ?, ?, ?)";
        
        String queryInsertEnfermedadConsulta = "INSERT INTO ENFERMEDAD_CONSULTA (id_enfermedad, id_consulta) " +
                                               "VALUES (?, ?)";
        
        String queryInsertConsultaVacuna = "INSERT INTO CONSULTA_VACUNA (id_consulta, id_vacuna_aplicada) " + 
        								   "VALUES (?, ?)";

        
        int filasConsulta = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtDoctor = conn.prepareStatement(queryDoctor);
             PreparedStatement stmtInsertConsulta = conn.prepareStatement(queryInsertConsulta);
             PreparedStatement stmtInsertEnfermedadConsulta = conn.prepareStatement(queryInsertEnfermedadConsulta);
        	 PreparedStatement stmtInsertConsultaVacuna = conn.prepareStatement(queryInsertConsultaVacuna)) {

        	
            stmtDoctor.setInt(1, Integer.parseInt(miConsulta.getMiCita().getMiDoctor().getCodigo()));
            ResultSet rsDoctor = stmtDoctor.executeQuery();

            int id_doctor = 0;
            if (rsDoctor.next()) {
                id_doctor = rsDoctor.getInt("id_doctor");
            }

            int id_consulta = Clinica.getInstance().obtenerMaximoIdConsulta();
            
            stmtInsertConsulta.setInt(1, id_consulta);
            stmtInsertConsulta.setDate(2, new java.sql.Date(miConsulta.getFechaConsulta().getTime()));
            stmtInsertConsulta.setString(3, miConsulta.getDiagnostico());
            stmtInsertConsulta.setInt(4, id_doctor);
            stmtInsertConsulta.setInt(5, Integer.parseInt(miConsulta.getMiCita().getCodigo()));

            filasConsulta = stmtInsertConsulta.executeUpdate();

            if (filasConsulta > 0) {
            	
                System.out.println("Consulta insertada exitosamente.");

                for (Enfermedad enfermedad : miConsulta.getMisEnfermedades()) {
                    stmtInsertEnfermedadConsulta.setInt(1, Integer.parseInt(enfermedad.getCodigo()));
                    stmtInsertEnfermedadConsulta.setInt(2, id_consulta);
                    stmtInsertEnfermedadConsulta.executeUpdate();
                }
                
                
                System.out.println("Relaciones de enfermedades insertadas correctamente.");

                for (String id_vacuna : vacunasAplicadas) {
                	
                	String codigo = id_vacuna.split(" ")[0].substring(2);
                    stmtInsertConsultaVacuna.setInt(1, id_consulta);
                    stmtInsertConsultaVacuna.setInt(2, Integer.parseInt(codigo));
                    stmtInsertConsultaVacuna.executeUpdate();
                }

                System.out.println("Relaciones de enfermedades insertadas correctamente.");
                
            } else {
                System.out.println("Error al insertar la consulta.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Object> obtenerConsultaByIdSQL(String id_consulta) {
        ArrayList<Object> result = new ArrayList<>();
        Consulta consulta = null;
        ArrayList<String> vacunasAplicadas = new ArrayList<>();

        String queryConsulta = "SELECT c.id_consulta, c.fecha_consulta, c.diagnostico, c.id_doctor, c.id_cita_solicitada " +
                               "FROM CONSULTA AS c " +
                               "WHERE c.id_consulta = ?";
                               
        String queryEnfermedades = "SELECT e.id_enfermedad, e.nombre, e.sintomas, e.tratamiento, e.id_gravedad_enfermedad " +
                                   "FROM ENFERMEDAD AS e " +
                                   "INNER JOIN ENFERMEDAD_CONSULTA AS ec ON e.id_enfermedad = ec.id_enfermedad " +
                                   "WHERE ec.id_consulta = ?";
                                   
        String queryVacunas = "SELECT v.id_vacuna, v.nombre " +
                              "FROM VACUNA AS v " +
                              "INNER JOIN CONSULTA_VACUNA AS cv ON v.id_vacuna = cv.id_vacuna_aplicada " +
                              "WHERE cv.id_consulta = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtConsulta = conn.prepareStatement(queryConsulta);
             PreparedStatement stmtEnfermedades = conn.prepareStatement(queryEnfermedades);
             PreparedStatement stmtVacunas = conn.prepareStatement(queryVacunas)) {

            stmtConsulta.setString(1, id_consulta);

            try (ResultSet rsConsulta = stmtConsulta.executeQuery()) {
                if (rsConsulta.next()) {
                    String codigo_consulta = rsConsulta.getString("id_consulta");
                    Date fechaConsulta = rsConsulta.getDate("fecha_consulta");
                    String diagnostico = rsConsulta.getString("diagnostico");
                    String idCitaSolicitada = rsConsulta.getString("id_cita_solicitada");
                    
                    Cita miCita = Clinica.getInstance().obtenerCitaByIdSQL(idCitaSolicitada);
                    
                    consulta = new Consulta(codigo_consulta, miCita);
                    consulta.setFechaConsulta(fechaConsulta);
                    consulta.setDiagnostico(diagnostico);

                    stmtEnfermedades.setString(1, id_consulta);
                    
                    try (ResultSet rsEnfermedades = stmtEnfermedades.executeQuery()) {
                        while (rsEnfermedades.next()) {
                            String codigo_enfermedad = rsEnfermedades.getString("id_enfermedad");
                            String nombre = rsEnfermedades.getString("nombre");
                            String sintomas = rsEnfermedades.getString("sintomas");
                            String tratamiento = rsEnfermedades.getString("tratamiento");
                            String gravedad = rsEnfermedades.getString("id_gravedad_enfermedad");

                            Enfermedad enfermedad = new Enfermedad(codigo_enfermedad, nombre, sintomas, tratamiento, obtenerValorGravedadByNombre(gravedad));
                            consulta.insertarEnfermedad(enfermedad);
                        }
                    }

                    // Obtener vacunas
                    stmtVacunas.setString(1, id_consulta);
                    
                    try (ResultSet rsVacunas = stmtVacunas.executeQuery()) {
                        while (rsVacunas.next()) {
                            String codigoVacuna = rsVacunas.getString("id_vacuna");
                            String nombreVacuna = rsVacunas.getString("nombre");
                            vacunasAplicadas.add("V-" + codigoVacuna + " " + nombreVacuna);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Agregar consulta y lista de vacunas al ArrayList
        result.add(consulta);
        result.add(vacunasAplicadas);

        return result;
    }

    private int obtenerValorGravedadByNombre(String gravedad) {
    	
    	String gravedadLowerCase = gravedad.toLowerCase();

        switch (gravedadLowerCase) {
            case "leve":
                return 1;
            case "moderada":
                return 2;
            default: //Es grave
                return 3;
        }
        
    } 
    
    public void modificarDatosConsultaSQL(Consulta consulta, ArrayList<String> vacunasAplicadas) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            
            // Eliminación en cadena para evitar errores con las claves foráneas
            
            String queryEliminarEnfermedades = "DELETE FROM ENFERMEDAD_CONSULTA " + 
            						           "WHERE id_consulta = ?";
            try (PreparedStatement stmtEliminarEnfermedades = conn.prepareStatement(queryEliminarEnfermedades)) {
                stmtEliminarEnfermedades.setInt(1, Integer.parseInt(consulta.getCodigo()));
                stmtEliminarEnfermedades.executeUpdate();
            }

            String queryEliminarVacunas = "DELETE FROM CONSULTA_VACUNA " + 
            							  "WHERE id_consulta = ?";
            try (PreparedStatement stmtEliminarVacunas = conn.prepareStatement(queryEliminarVacunas)) {
                stmtEliminarVacunas.setInt(1, Integer.parseInt(consulta.getCodigo()));
                stmtEliminarVacunas.executeUpdate();
            }

            String queryInsertarEnfermedades = "INSERT INTO ENFERMEDAD_CONSULTA (id_enfermedad, id_consulta) " +
            		 						   "VALUES (?, ?)";
            try (PreparedStatement stmtInsertarEnfermedades = conn.prepareStatement(queryInsertarEnfermedades)) {
                for (Enfermedad enfermedad : consulta.getMisEnfermedades()) {
                    stmtInsertarEnfermedades.setInt(1, Integer.parseInt(enfermedad.getCodigo()));
                    stmtInsertarEnfermedades.setInt(2, Integer.parseInt(consulta.getCodigo()));
                    stmtInsertarEnfermedades.executeUpdate();
                }
            }

            String queryInsertarVacunas = "INSERT INTO CONSULTA_VACUNA (id_consulta, id_vacuna_aplicada) " +
            							  "VALUES (?, ?)";
            try (PreparedStatement stmtInsertarVacunas = conn.prepareStatement(queryInsertarVacunas)) {
                for (String vacuna : vacunasAplicadas) {
                    String codigo = vacuna.split(" ")[0].substring(2);
                    stmtInsertarVacunas.setInt(1, Integer.parseInt(consulta.getCodigo()));
                    stmtInsertarVacunas.setInt(2, Integer.parseInt(codigo));
                    stmtInsertarVacunas.executeUpdate();
                }
            }

            String queryModificarDiagnostico = "UPDATE CONSULTA SET diagnostico = ? " +
            								   "WHERE id_consulta = ?";
            try (PreparedStatement stmtModificarDiagnostico = conn.prepareStatement(queryModificarDiagnostico)) {
                stmtModificarDiagnostico.setString(1, consulta.getDiagnostico());
                stmtModificarDiagnostico.setInt(2, Integer.parseInt(consulta.getCodigo()));
                stmtModificarDiagnostico.executeUpdate();
            }

            System.out.println("Consulta modificada correctamente con código " + consulta.getCodigo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }  
    
    public void eliminarDatosConsultaSQL(String id_consulta) {

        try (Connection conn = DatabaseConnection.getConnection()) {
            
        	//Eliminacion en cadena para evitar errores con las claves foraneas            
        	
            String queryEliminarEnfermedades = "DELETE FROM ENFERMEDAD_CONSULTA " + 
					   					       "WHERE id_consulta = ?";
            try (PreparedStatement stmtEliminarEnfermedades = conn.prepareStatement(queryEliminarEnfermedades)) {
                stmtEliminarEnfermedades.setString(1, id_consulta);
                int filasEliminadasEnfermedades = stmtEliminarEnfermedades.executeUpdate();
                System.out.println("Relaciones de consulas eliminadas: " + filasEliminadasEnfermedades);
            }
            
            String queryEliminarVacunas = "DELETE FROM CONSULTA_VACUNA " + 
            							  "WHERE id_consulta = ?";
			try (PreparedStatement stmtEliminarVacunas = conn.prepareStatement(queryEliminarVacunas)) {
				stmtEliminarVacunas.setInt(1, Integer.parseInt(id_consulta));
				stmtEliminarVacunas.executeUpdate();
				int filasEliminadasVacunas = stmtEliminarVacunas.executeUpdate();
	            System.out.println("Relaciones de vacunas eliminadas: " + filasEliminadasVacunas);
			}
            
            String queryEliminarConsulta = "DELETE FROM CONSULTA " + 
					                       "WHERE id_consulta = ?";
            try (PreparedStatement stmtEliminarConsulta = conn.prepareStatement(queryEliminarConsulta)) {
                stmtEliminarConsulta.setString(1, id_consulta);
                int filasEliminadasConsulta = stmtEliminarConsulta.executeUpdate();
                System.out.println("Consulta eliminada: " + filasEliminadasConsulta);
            }
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
        }
    }
    
    public Vivienda buscarViviendaByIdPersonaSQL(int id_persona) {
        
        String query = "SELECT v.id_vivienda, v.direccion " +
                       "FROM VIVIENDA v " +
                       "JOIN PACIENTE p ON v.id_vivienda = p.id_vivienda " +
                       "WHERE p.id_persona = ?";
        
        Vivienda vivienda = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id_persona);
            
            try (ResultSet rs = stmt.executeQuery()) {
                
                if (rs.next()) {
                    String codigo_vivienda = rs.getString("id_vivienda");
                    String direccion = rs.getString("direccion");

                    vivienda = new Vivienda(codigo_vivienda, direccion);
                }
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vivienda;
    }

    public String buscarTipoSangrePorIdPersonaSQL(int id_persona) {
        
    	String tipoSangre = null;
        
        String query = "SELECT tipoSangre " + 
        			   "FROM PACIENTE " + 
        			   "WHERE id_persona = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id_persona);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tipoSangre = rs.getString("tipoSangre");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoSangre;
    }

    public DefaultTableModel cargarCantidadConsultasPacienteSQL(String id_persona_paciente) {
    	
    	String query = "SELECT COUNT(*) AS Total_Consultas " +
    				   "FROM CITA AS c " +
    			       "WHERE c.completada = 1 AND c.id_persona = ? ";

		DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Cantidad de Consultas"});

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, id_persona_paciente);
            
            try (ResultSet rs = stmt.executeQuery()) {
                
            	while (rs.next()) {
                    
            		Object[] row = new Object[1];
                    row[0] = rs.getString("Total_Consultas");
                    model.addRow(row);
                }
            }
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
        }

        return model; 
    }
    
    public DefaultTableModel cargarDatosConsultaPacienteSQL(String id_persona_paciente) {
        
        String query = "SELECT p.nombre AS nombre_doctor, p.apellido AS apellido_doctor, " +
                       "cta.fecha_cita, CONVERT(VARCHAR(12), cta.hora_cita, 108) AS hora_cita, " +
                       "cst.diagnostico " +
                       "FROM CONSULTA AS cst " +
                       "INNER JOIN CITA AS cta ON cst.id_cita_solicitada = cta.id_cita " +
                       "INNER JOIN DOCTOR AS d ON cta.id_doctor = d.id_doctor " +
                       "INNER JOIN PERSONA AS p ON d.id_persona = p.id_persona " +
                       "INNER JOIN PACIENTE AS pa ON cta.id_persona = pa.id_persona " +
                       "INNER JOIN PERSONA AS p_pac ON pa.id_persona = p_pac.id_persona " +
                       "WHERE p_pac.id_persona = ?";

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Nombre Doctor", "Apellido Doctor", "Fecha Cita", "Hora Cita", "Diagnóstico"});

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id_persona_paciente);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Object[] row = new Object[5];
                    row[0] = rs.getString("nombre_doctor");
                    row[1] = rs.getString("apellido_doctor");
                    row[2] = rs.getDate("fecha_cita");
                    row[3] = rs.getString("hora_cita");
                    row[4] = rs.getString("diagnostico");
                    model.addRow(row);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model; 
    }
    
    
    //REPORTE GENERAL CLINICO SQL:
    
    public ChartPanel crearGraficoViviendasPobladas() {
      
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	
        String query = "SELECT TOP 3 v.direccion, COUNT(p.id_paciente) AS num_personas " +
                       "FROM VIVIENDA AS v " +
                       "INNER JOIN PACIENTE AS p ON v.id_vivienda = p.id_vivienda " +
                       "GROUP BY v.direccion " +
                       "ORDER BY num_personas DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                dataset.addValue(rs.getInt("num_personas"), "Personas", rs.getString("direccion"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "VIVIENDA MAS POBLADA",
                "DIRECCION",
                "NUMERO DE PERSONAS",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        return new ChartPanel(chart);
    }

    public ChartPanel crearGraficoEdadPromedioPaciente() {
        
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        String query = "SELECT AVG(DATEDIFF(YEAR, p.fecha_nacimiento, GETDATE())) AS promedio_edad " +
                       "FROM PERSONA AS p " +
                       "INNER JOIN PACIENTE AS pa ON p.id_persona = pa.id_persona";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                dataset.addValue(rs.getDouble("promedio_edad"), "Edad promedio", "Pacientes");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "EDAD PROMEDIO PACIENTES",
                "CATEGORIA",
                "EDAD",
                dataset
        );

        return new ChartPanel(chart);
    }

    public ChartPanel crearGraficoDistribucionSexoPaciente() {
        
    	DefaultPieDataset dataset = new DefaultPieDataset();
        
        String query = "SELECT " +
                       "SUM(CASE WHEN p.sexo = 'M' THEN 1 ELSE 0 END) AS masculino, " +
                       "SUM(CASE WHEN p.sexo = 'F' THEN 1 ELSE 0 END) AS femenino " +
                       "FROM PERSONA AS p " + 
                       "INNER JOIN PACIENTE AS pa ON p.id_persona = pa.id_persona";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                dataset.setValue("Masculino", rs.getInt("masculino"));
                dataset.setValue("Femenino", rs.getInt("femenino"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "DISTRIBUCION POR SEXO",
                dataset,
                true,
                true,
                false
        );
        
        return new ChartPanel(chart);
    }
    
    public ChartPanel crearGraficoEnfermedadesRecurrentes() {
        
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
    	String query = "SELECT TOP 3 e.nombre, COUNT(*) AS frecuencia " +
                       "FROM ENFERMEDAD AS e " +
                       "INNER JOIN ENFERMEDAD_CONSULTA AS ec ON e.id_enfermedad = ec.id_enfermedad " +
                       "GROUP BY e.nombre ORDER BY frecuencia DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                dataset.addValue(rs.getInt("frecuencia"), "Frecuencia", rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "ENFERMEDADES MAS RECURRENTES",
                "ENFERMEDAD",
                "FRECUENCIA",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        return new ChartPanel(chart);
    }

    public ChartPanel crearGraficoEnfermedadesMasGraves() {
        
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        String query = "SELECT TOP 3 e.nombre, ge.gravedad " +
                       "FROM ENFERMEDAD AS e " +
                       "INNER JOIN GRAVEDAD_ENFERMEDAD AS ge ON e.id_gravedad_enfermedad = ge.id_gravedad_enfermedad " +
                       "WHERE ge.gravedad = 'Grave' " +
                       "ORDER BY ge.gravedad DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                dataset.addValue(rs.getInt("gravedad"), "Gravedad", rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "ENFERMEDADES GRAVES CONSULTADAS",
                "ENFERMEDAD",
                "GRAVEDAD",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        return new ChartPanel(chart);
    }

    public ChartPanel crearGraficoDoctoresConMasCitas() {
        
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
    	String query = "SELECT TOP 3 p.nombre, p.apellido, COUNT(c.id_cita) AS num_citas " +
                       "FROM DOCTOR AS d " +
                       "INNER JOIN PERSONA AS p ON d.id_persona = p.id_persona " +
                       "INNER JOIN CITA c ON d.id_doctor = c.id_doctor " +
                       "GROUP BY p.nombre, p.apellido " +
                       "ORDER BY num_citas DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
                dataset.addValue(rs.getInt("num_citas"), "Citas", nombreCompleto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "DOCTORES CON MAS CITAS",
                "DOCTOR",
                "NUMERO DE CITAS",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        return new ChartPanel(chart);
    }
    
    public ChartPanel crearGraficoDistribucionSexoDoctor() {
        
    	DefaultPieDataset dataset = new DefaultPieDataset();
        
        String query = "SELECT " +
                       "SUM(CASE WHEN p.sexo = 'M' THEN 1 ELSE 0 END) AS masculino, " +
                       "SUM(CASE WHEN p.sexo = 'F' THEN 1 ELSE 0 END) AS femenino " +
                       "FROM PERSONA AS p " + 
                       "INNER JOIN DOCTOR AS d ON p.id_persona = d.id_persona";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                dataset.setValue("Masculino", rs.getInt("masculino"));
                dataset.setValue("Femenino", rs.getInt("femenino"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "DISTRIBUCION POR SEXO",
                dataset,
                true,
                true,
                false
        );
        
        return new ChartPanel(chart);
    }
    
    public ChartPanel crearGraficoVacunaMasCurativa() {
        
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        String query = "SELECT TOP 3 v.nombre, COUNT(e.id_enfermedad) AS num_enfermedades " +
                       "FROM VACUNA v " +
                       "INNER JOIN ENFERMEDAD_VACUNA AS ev ON v.id_vacuna = ev.id_vacuna " +
                       "INNER JOIN ENFERMEDAD AS e ON ev.id_enfermedad = e.id_enfermedad " +
                       "GROUP BY v.nombre " +
                       "ORDER BY num_enfermedades DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                dataset.addValue(rs.getInt("num_enfermedades"), "Número de enfermedades", rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "VACUNAS MAS CURATIVAS",
                "VACUNA",
                "NUMERO DE ENFERMEDADES",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        return new ChartPanel(chart);
    }
 
} 