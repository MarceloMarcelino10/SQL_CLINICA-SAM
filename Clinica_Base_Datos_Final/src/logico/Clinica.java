package logico;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import seguridad.GestorUsuario;
import sql.DatabaseConnection;

public class Clinica implements Serializable  {//u
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -212293991973296857L;
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
		//cargarLastIdUsed();
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

	//METODOS PARA INSERTAR EN LAS LISTAS:
	
    public void insertarVivienda(Vivienda vivienda) {
        //Clinica.getInstance().obtenerMaximoIdVivienda();
    	misViviendas.add(vivienda);
    	codVivienda++;
    }

    public void insertarPersona(Persona persona) {
        //Clinica.getInstance().obtenerMaximoIdPersona();
    	misPersonas.add(persona);
    	codPersona++;
    }

    public void insertarEnfermedad(Enfermedad enfermedad) {
    	//Clinica.getInstance().obtenerMaximoIdEnfermedad();
    	misEnfermedades.add(enfermedad);
    	codEnfermedad++;
    }

    public void insertarCita(Cita cita) {
        //Clinica.getInstance().obtenerMaximoIdCita();
    	misCitas.add(cita);
    	codCita++;
    }

    public void insertarVacuna(Vacuna vacuna) {
    	//Clinica.getInstance().obtenerMaximoIdVacuna();
        misVacunas.add(vacuna);
        codVacuna++;
    }

    public void insertarConsulta(Consulta consulta) {
       // Clinica.getInstance().obtenerMaximoIdConsulta();
    	misConsultas.add(consulta);
    	codConsulta++;
    }
    
    public void insertarHistoriaClinica(HistoriaClinica historiaClinica) {
        //Clinica.getInstance().obtenerMaximoIdHistoriaClinica();
    	misHistoriasClinicas.add(historiaClinica);
    	codHistoriaClinica++;
	}
    
    public static void setClinica(Clinica clinica) {
		Clinica.clinica = clinica;
	}
    
    //METODOS PARA ELIMINAR EN LAS LISTAS:
    
    public void eliminarVivienda(Vivienda vivienda) {
        misViviendas.remove(vivienda);
        guardarDatos();
    }

    public void eliminarPersona(Persona persona) {
        misPersonas.remove(persona);
        guardarDatos();
    }

    public void eliminarEnfermedad(Enfermedad enfermedad) {
        misEnfermedades.remove(enfermedad);
        guardarDatos();
    }

    public void eliminarCita(Cita cita) {
        misCitas.remove(cita);
        guardarDatos();
    }

    public void eliminarVacuna(Vacuna vacuna) {
        misVacunas.remove(vacuna);
        guardarDatos();
    }

    public void eliminarConsulta(Consulta consulta) {
        misConsultas.remove(consulta);
        guardarDatos();
    }
    
    public void eliminarHistoriaClinica(HistoriaClinica HistoriaClinica) {
        misHistoriasClinicas.remove(HistoriaClinica);
        guardarDatos();
    }
    
    
    public void insertarPaciente(Paciente paciente) {
        misPersonas.add(paciente);
        guardarDatos();
    }

    public void insertarDoctor(Doctor doctor) {
        misPersonas.add(doctor);
        guardarDatos();
    }
    
    //METODOS PARA ACTUALIZAR EL ELEMENTO DE UNA LISTA:
	
    public void actualizarVivienda(Vivienda vivienda) {
    	int index = buscarViviendaByIndex(vivienda.getCodigo());
    	misViviendas.set(index, vivienda);
    	guardarDatos();
    }
	
	private int buscarViviendaByIndex(String codigo) {
		int index = -1;
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < misViviendas.size()) {
			if (misViviendas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
				encontrado = true;
				index = i;
			}
			i++;
		}	
		return index;
	}
	
	public void actualizarPersona(Persona persona) {
        int index = buscarPersonaByIndex(persona.getCodigo());
        if (index >= 0) {
        	misPersonas.set(index, persona);
            guardarDatos();
        }            
    }
	
	 public void agregarEnfermedad(Enfermedad enfermedad) {
	        misEnfermedades.add(enfermedad);
	        // AquÃ­ se deberÃ­a aÃ±adir la lÃ³gica para registrar la enfermedad en la base de datos si es necesario
	 }
	 


	

    private int buscarPersonaByIndex(String codigo) {
        int index = -1;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < misPersonas.size()) {
            if (misPersonas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                encontrado = true;
                index = i;
            }
            i++;
        }    
        return index;
    }

    public void actualizarEnfermedad(Enfermedad enfermedad) {
        int index = buscarEnfermedadByIndex(enfermedad.getCodigo());
            misEnfermedades.set(index, enfermedad);
            guardarDatos();
    }
    


    private int buscarEnfermedadByIndex(String codigo) {
        int index = -1;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < misEnfermedades.size()) {
            if (misEnfermedades.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                encontrado = true;
                index = i;
            }
            i++;
        }    
        return index;
    }

    public void actualizarCita(Cita cita) {
        int index = buscarCitaByIndex(cita.getCodigo());
            misCitas.set(index, cita);
            guardarDatos();
    }

    private int buscarCitaByIndex(String codigo) {
        int index = -1;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < misCitas.size()) {
            if (misCitas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                encontrado = true;
                index = i;
            }
            i++;
        }    
        return index;
    }

    public void actualizarVacuna(Vacuna vacuna) {
        int index = buscarVacunaByIndex(vacuna.getCodigo());
            misVacunas.set(index, vacuna);
            guardarDatos();
    }

    private int buscarVacunaByIndex(String codigo) {
        int index = -1;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < misVacunas.size()) {
            if (misVacunas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                encontrado = true;
                index = i;
            }
            i++;
        }    
        return index;
    }

    public void actualizarConsulta(Consulta consulta) {
        int index = buscarConsultaByIndex(consulta.getCodigo());
            misConsultas.set(index, consulta);
            guardarDatos();
    }

    private int buscarConsultaByIndex(String codigo) {
        int index = -1;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < misConsultas.size()) {
            if (misConsultas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                encontrado = true;
                index = i;
            }
            i++;
        }    
        return index;
    }
    
    public void actualizarHistorialClinica(HistoriaClinica historialClinica) {
    	int index = buscarHistorialClinica(historialClinica.getCodigo());
    	misHistoriasClinicas.set(index,historialClinica);
    	guardarDatos();
    }
    
	private int buscarHistorialClinica(String codigo) {
		int index = -1;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < misHistoriasClinicas.size()) {
            if (misHistoriasClinicas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                encontrado = true;
                index = i;
            }
            i++;
        }    
        return index;
	}

	//METODOS PARA EL LOGIN Y REGISTRO:

	public static Persona getLoggedUser() {
		return Clinica.loggedUser;
	}
	
	public boolean confirmLogin (String userName, String password) {
		boolean login = false;
		for (Persona user : misPersonas) {
			if(user.getUser().equals(userName) && user.getPassword().equals(password)) {
				loggedUser = user;
				login = true;
			}
		}
		if (login) {
		System.out.println(loggedUser.getRangoUser());			
		}
		return login;
	}
	
	public boolean existUserNameSQL(String userName) {
	    String query = "SELECT COUNT(*) FROM CREDENCIAL WHERE userName = ?";
	    
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

	
	
	//METODO PARA GUARDAR:
	
	public void guardarDatos() {
		guardarValoresEstaticos();
	    try (FileOutputStream tempClinica = new FileOutputStream("clinica.dat");
	        ObjectOutputStream clinicaWrite = new ObjectOutputStream(tempClinica)) {
	        clinicaWrite.writeObject(Clinica.getInstance());
	        clinicaWrite.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private void guardarValoresEstaticos() {
	    valoresEstaticos.add(getCodPersona());
	    valoresEstaticos.add(getCodCita());
	    valoresEstaticos.add(getCodVacuna());
	    valoresEstaticos.add(getCodEnfermedad());
	    valoresEstaticos.add(getCodConsulta());
	    valoresEstaticos.add(getCodVivienda());
	    valoresEstaticos.add(getCodHistoriaClinica());
	}

	public void cargarValoresEstaticos() {
	    if (!valoresEstaticos.isEmpty() && valoresEstaticos.size() >= 7) {
	        setCodPersona(valoresEstaticos.get(0));
	        setCodCita(valoresEstaticos.get(1));
	        setCodVacuna(valoresEstaticos.get(2));
	        setCodEnfermedad(valoresEstaticos.get(3));
	        setCodConsulta(valoresEstaticos.get(4));
	        setCodVivienda(valoresEstaticos.get(5));
	        setCodVivienda(valoresEstaticos.get(6));
	    }
	}
	
	//METODOS PARA BUCAR POR ID:
	
	public Vivienda buscarViviendaById(String codigo) {
		Vivienda temp = null;
		boolean encontrado = false;
		int i = 0;
		while(i < misViviendas.size() && !encontrado) {
			if(misViviendas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
				temp = misViviendas.get(i);
				encontrado = true;
			}
			i++;
		}
		return temp;
	}

	public Persona buscarPersonaById(String codigo) {
	    Persona temp = null;
	    boolean encontrado = false;
	    int i = 0;
	    while (i < misPersonas.size() && !encontrado) {
	        if (misPersonas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
	            temp = misPersonas.get(i);
	            encontrado = true;
	        }
	        i++;
	    }
	    return temp;
	}
	
	public Vacuna buscarVacunaById(String codigo) {
		Vacuna temp = null;
	    boolean encontrado = false;
	    int i = 0;
	    while (i < misVacunas.size() && !encontrado) {
	        if (misVacunas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
	            temp = misVacunas.get(i);
	            encontrado = true;
	        }
	        i++;
	    }
	    return temp;
	}
	
	private HistoriaClinica buscarHistorialClinicaById(String codigo) {
		HistoriaClinica temp = null;
	    boolean encontrado = false;
	    int i = 0;
	    while (i < misHistoriasClinicas.size() && !encontrado) {
	        if (misHistoriasClinicas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
	            temp = misHistoriasClinicas.get(i);
	            encontrado = true;
	        }
	        i++;
	    }
	    return temp;
	}


	//METODOS PARA BUSCAR VERIFICAR UNA PERSONA POR VIVIENDA:
	
	public boolean existePersonaEnVivienda(Persona persona) {
		boolean existe = false;
	    for (Vivienda vivienda : misViviendas) {
	        if (vivienda.getMisPersonas().contains(persona)) {
	            existe =  true;
	        }
	    }
	    return existe;
	}
	
	public void eliminarPersonaDeVivienda(Persona persona) {
	    Vivienda viviendaActual = null;
	    boolean encontrado = false;
	    int i = 0;

	    while (i < misViviendas.size() && !encontrado) {
	        Vivienda vivienda = misViviendas.get(i);
	        if (vivienda.getMisPersonas().contains(persona)) {
	            viviendaActual = vivienda;
	            encontrado = true;
	        }
	        i++;
	    }

	    if (encontrado) {
	        viviendaActual.eliminarPersona(persona);
	        if (persona instanceof Paciente) {
	            ((Paciente) persona).setMiVivienda(null);
	        }
	        actualizarVivienda(viviendaActual);
	        actualizarPersona(persona);
	        JOptionPane.showMessageDialog(null, "Persona eliminada correctamente de la vivienda", "Eliminar Persona", JOptionPane.INFORMATION_MESSAGE);
	        guardarDatos();
	    } else {
	        JOptionPane.showMessageDialog(null, "La persona no estï¿½ registrada en ninguna vivienda", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void desasociarVivienda (Vivienda vivienda) {
	    ArrayList<Persona> personasEnVivienda = vivienda.getMisPersonas();
	    for (Persona persona : personasEnVivienda) {
	        if (persona instanceof Paciente) {
	            ((Paciente) persona).setMiVivienda(null);
	            actualizarPersona(persona);
	        }
	    }
	    eliminarVivienda(vivienda);
	}
	
	//METODOS PARA CONTAR ELEMNTOS DE LAS CLASES:
	
	public int getNumCitasPendientesPorDoctor(Doctor doctor) {
	    
		if (doctor == null || misCitas == null) {
	        return 0;
	    }

	    int count = 0;
	    
	    for (Cita cita : misCitas) {
	        if (cita.getMiDoctor() != null 
	                && cita.getMiDoctor().equals(doctor) 
	                && !cita.isRealizada()) { // No realizada
	            count++;
	        }
	    }
	    
	    return count;
	}

	
	public ArrayList<Cita> getCitasPendientesPorDoctor(Doctor doctor) {
		ArrayList<Cita> citasPendientes = new ArrayList<>();
	    for (Cita cita : misCitas) {
	        if (cita.getMiDoctor().equals(doctor) && !cita.isRealizada()) {
	            citasPendientes.add(cita);
	        }
	    }
	    return citasPendientes;
	}


	//SETTERS AND GETTERS ESTATICOS (Probando guardar los valores esticaos)
	
	public static int getCodPersona() {
		return codPersona;
	}

	public static void setCodPersona(int codPersona) {
		Clinica.codPersona = codPersona;
	}

	public static int getCodCita() {
		return codCita;
	}

	public static void setCodCita(int codCita) {
		Clinica.codCita = codCita;
	}

	public static int getCodVacuna() {
		return codVacuna;
	}

	public static void setCodVacuna(int codVacuna) {
		Clinica.codVacuna = codVacuna;
	}

	public static int getCodEnfermedad() {
		return codEnfermedad;
	}

	public static void setCodEnfermedad(int codEnfermedad) {
		Clinica.codEnfermedad = codEnfermedad;
	}

	public static int getCodConsulta() {
		return codConsulta;
	}

	public static void setCodConsulta(int codConsulta) {
		Clinica.codConsulta = codConsulta;
	}

	public static int getCodVivienda() {
		return codVivienda;
	}

	public static void setCodVivienda(int codVivienda) {
		Clinica.codVivienda = codVivienda;
	}
	
	public ArrayList<Enfermedad> getEnfermedadesRegistradas() {
	    return misEnfermedades;
	}
	
	public static int getCodHistoriaClinica() {
		return codHistoriaClinica;
	}

	public static void setCodHistoriaClinica(int codHistoriaClinica) {
		Clinica.codHistoriaClinica = codHistoriaClinica;
	}
	
	//METODOS PARA GENERAR EL REPORTE CLINICO:
	
	public String obtenerViviendasMasPobladas() {
		
		if (misViviendas.isEmpty()) {
	        return "Informaciï¿½n no disponible!\n";
	    }
		ArrayList<Vivienda> viviendasOrdenadas = new ArrayList<>(misViviendas);
		
        Collections.sort(viviendasOrdenadas, Comparator.comparingInt(vivienda -> vivienda.getMisPersonas().size()));
        Collections.reverse(viviendasOrdenadas);
        
        int limite = Math.min(3, viviendasOrdenadas.size());
        StringBuilder resultado = new StringBuilder("Las viviendas mï¿½s pobladas son:\n");

        for (int i = 0; i < limite; i++) {
            Vivienda vivienda = viviendasOrdenadas.get(i);
            resultado.append(String.format("%d. %s: %s - Afiliados: %d personas\n", i + 1, vivienda.getCodigo(), vivienda.getDireccion(), vivienda.getMisPersonas().size()));
        }
        return resultado.toString();
    }
	
    public String calcularPromedioEdadPersonas() {
        if (misPersonas.isEmpty()) {
        	return "Informaciï¿½n no disponible!\n";
        }

        Calendar calHoy = Calendar.getInstance();
        int sumEdades = 0;

        for (Persona persona : misPersonas) {
        	
        	if (persona instanceof Paciente) {
        		
        		Calendar calNacimiento = Calendar.getInstance();
                calNacimiento.setTime(persona.getFechaNacimiento());

                int edad = calHoy.get(Calendar.YEAR) - calNacimiento.get(Calendar.YEAR);
                if (calHoy.get(Calendar.DAY_OF_YEAR) < calNacimiento.get(Calendar.DAY_OF_YEAR)) {
                    edad--;
                }

                sumEdades += edad;
            }
        }
        double promedioEdad = (double) sumEdades / misPersonas.size();
        return String.format("El promedio de edad de las personas tratadas: %.2f aï¿½os.\n", promedioEdad);
    }
	
    public String calcularPromedioSexoPersoanas() {
        if (misPersonas.isEmpty()) {
        	return "Informaciï¿½n no disponible!\n";
        }

        int countMasculino = 0;
        int countFemenino = 0;
        int cantPacientes = 0;

        for (Persona persona : misPersonas) {
        	if (persona instanceof Paciente) {
        		if ("Masculino".equalsIgnoreCase(persona.getGenero())) {
                    countMasculino++;
                } else if ("Femenino".equalsIgnoreCase(persona.getGenero())) {
                    countFemenino++;
                }
        		cantPacientes++;
        	} 
        }

        double porcentajeMasculino = (double) countMasculino / cantPacientes * 100;
        double porcentajeFemenino = (double) countFemenino / cantPacientes * 100;

        return String.format("Promedio de Genero:\nMasculino: %.2f%%\nFemenino: %.2f%%\n", porcentajeMasculino, porcentajeFemenino);
    }
    
    public String obtenerTotalPersonasAtendidas() {
        if (misPersonas.isEmpty()) {
        	return "Informaciï¿½n no disponible!\n";
        }
        int total = 0;
        for (Persona persona : misPersonas) {
        	if (persona instanceof Paciente) {
        		total++;
        	}
        }
        return String.format("El total de personas atendidas: %d.\n", total);
    }
    
    public String obtenerEnfermedadesMasRecurrentes() {
        if (misEnfermedades.isEmpty()) {
        	return "Informaciï¿½n no disponible!\n";
        }

        String[] nombresEnfermedades = new String[misEnfermedades.size()];
        int[] frecuenciasEnfermedades = new int[misEnfermedades.size()];

        for (int i = 0; i < misEnfermedades.size(); i++) {
            Enfermedad enfermedad = misEnfermedades.get(i);
            String nombreEnfermedad = enfermedad.getNombre();

            int indice = buscarEnfermedadPorNombre(nombresEnfermedades, nombreEnfermedad);

            if (indice != -1) {
                frecuenciasEnfermedades[indice]++;
            } else {
                nombresEnfermedades[i] = nombreEnfermedad;
                frecuenciasEnfermedades[i] = 1;
            }
        }

        ordenarPorFrecuenciaDescendente(nombresEnfermedades, frecuenciasEnfermedades);
        
        int limite = Math.min(3, misEnfermedades.size());
        StringBuilder resultado = new StringBuilder("Las enfermedades mï¿½s recurrentes son:\n");

        for (int i = 0; i < limite; i++) {
            resultado.append(String.format("%d. %s: %d veces\n", i + 1, nombresEnfermedades[i], frecuenciasEnfermedades[i]));
        }

        return resultado.toString();
    }

    private int buscarEnfermedadPorNombre(String[] nombresEnfermedades, String nombreEnfermedad) {
        for (int i = 0; i < nombresEnfermedades.length; i++) {
            if (nombreEnfermedad != null && nombreEnfermedad.equals(nombresEnfermedades[i])) {
                return i;
            }
        }
        return -1;
    }

    private void ordenarPorFrecuenciaDescendente(String[] nombresEnfermedades, int[] frecuenciasEnfermedades) {
        for (int i = 0; i < frecuenciasEnfermedades.length - 1; i++) {
            for (int j = i + 1; j < frecuenciasEnfermedades.length; j++) {
                if (frecuenciasEnfermedades[i] < frecuenciasEnfermedades[j]) {
                	
                    int tempFrecuencia = frecuenciasEnfermedades[i];
                    frecuenciasEnfermedades[i] = frecuenciasEnfermedades[j];
                    frecuenciasEnfermedades[j] = tempFrecuencia;

                    String tempNombre = nombresEnfermedades[i];
                    nombresEnfermedades[i] = nombresEnfermedades[j];
                    nombresEnfermedades[j] = tempNombre;
                }
            }
        }
    }
    
    public String obtenerEnfermedadesMasGraves() {
        if (misEnfermedades.isEmpty()) {
            return "Informaciï¿½n no disponible!\n";
        }

        ArrayList<Enfermedad> enfermedadesGraves = new ArrayList<>();

        for (Enfermedad enfermedad : misEnfermedades) {
            if (enfermedad.getGravedad() == 5) {
                enfermedadesGraves.add(enfermedad);
            }
        }

        if (enfermedadesGraves.isEmpty()) {
            return "No hay enfermedades graves registradas.\n";
        }

        enfermedadesGraves.sort(Comparator.comparing(Enfermedad::getNombre));
        StringBuilder resultado = new StringBuilder("Enfermedades Graves Atendidas:\n");

        for (Enfermedad enfermedad : enfermedadesGraves) {
            resultado.append(String.format("- %s\n", enfermedad.getNombre()));
        }

        return resultado.toString();
    }
    
    public String obtenerDoctorConMasCitas() {
        if (misCitas.isEmpty()) {
            return "Informaciï¿½n no disponible!\n";
        }

        Doctor doctorConMasCitas = null;
        int maxCitas = 0;

        for (Doctor doctor : obtenerTodosLosDoctores()) {
            int citasDoctor = contarCitasPorDoctor(doctor);

            if (citasDoctor > maxCitas) {
                maxCitas = citasDoctor;
                doctorConMasCitas = doctor;
            }
        }

        if (doctorConMasCitas != null) {
        	return String.format("El doctor con mï¿½s citas es: %s, con especialidad de %s, y tiene %d citas.\n", doctorConMasCitas.getNombre(), doctorConMasCitas.getEspecialidad(), maxCitas);
        } else {
            return "No hay doctores con citas registradas.\n";
        }
    }

    private ArrayList<Doctor> obtenerTodosLosDoctores() {
        ArrayList<Doctor> doctores = new ArrayList<>();

        for (Cita cita : misCitas) {
            Doctor doctor = cita.getMiDoctor();
            if (!doctores.contains(doctor)) {
                doctores.add(doctor);
            }
        }

        return doctores;
    }

    private int contarCitasPorDoctor(Doctor doctor) {
        int contador = 0;

        for (Cita cita : misCitas) {
            if (cita.getMiDoctor().equals(doctor)) {
                contador++;
            }
        }

        return contador;
    }

    public String obtenerTotalCitasRealizadas() {
        if (misCitas.isEmpty()) {
        	return "Informaciï¿½n no disponible!\n";
        }
        int totalCitasRealizadas = 0;
        for (Cita cita : misCitas) {
            if (cita.isRealizada()) {
                totalCitasRealizadas++;
            }
        }
        return String.format("El total de citas realizadas es: %d.\n", totalCitasRealizadas);
    }

    public String calcularPromedioSexoDoctores() {
        if (misPersonas.isEmpty()) {
        	return "Informaciï¿½n no disponible!\n";
        }

        int countMasculino = 0;
        int countFemenino = 0;
        int cantDoctores = 0;
        
        for (Persona persona : misPersonas) {
            if (persona instanceof Doctor) {
                if ("Masculino".equalsIgnoreCase(persona.getGenero())) {
                    countMasculino++;
                } else if ("Femenino".equalsIgnoreCase(persona.getGenero())) {
                    countFemenino++;
                }
                cantDoctores++;
            }
        }

        if (cantDoctores == 0) {
            return "No hay doctores registrados.\n";
        }

        double porcentajeMasculino = (double) countMasculino / cantDoctores * 100;
        double porcentajeFemenino = (double) countFemenino / cantDoctores * 100;

        return String.format("Promedio de Gï¿½nero entre los doctores:\nMasculino: %.2f%%\nFemenino: %.2f%%\n", porcentajeMasculino, porcentajeFemenino);
    }
    
    public String obtenerVacunaMasCurativa() {
        if (misVacunas.isEmpty()) {
        	return "Informaciï¿½n no disponible!\n";
        }

        Vacuna vacunaConMasEnfermedades = null;
        int maxEnfermedades = 0;

        for (Vacuna vacuna : misVacunas) {
            int cantidadEnfermedades = vacuna.getMisEnfermedades().size();
            if (cantidadEnfermedades > maxEnfermedades) {
                maxEnfermedades = cantidadEnfermedades;
                vacunaConMasEnfermedades = vacuna;
            }
        }

        if (vacunaConMasEnfermedades == null) {
            return "No hay vacunas con enfermedades asociadas.\n";
        }

        StringBuilder resultado = new StringBuilder("La vacuna que mï¿½s cura enfermedades es:\n");
        resultado.append(String.format("- %s (Cï¿½digo: %s)\n", vacunaConMasEnfermedades.getNombre(), vacunaConMasEnfermedades.getCodigo()));
        resultado.append("Enfermedades asociadas:\n");

        for (Enfermedad enfermedad : vacunaConMasEnfermedades.getMisEnfermedades()) {
            resultado.append(String.format("- %s\n", enfermedad.getNombre()));
        }

        return resultado.toString();
    }

	public Enfermedad buscarEnfermedadById(String idEnfermedad) {
		Enfermedad enfermedad = null;
		boolean encontrado = false;
		int i = 0;
		while(i<misEnfermedades.size()&&!encontrado) {
			if(misEnfermedades.get(i).getCodigo().equalsIgnoreCase(idEnfermedad)){
				enfermedad = misEnfermedades.get(i);
				encontrado = true;
			}
			i++;
		}
		return enfermedad;
	}
	
	public Consulta buscarConsultaById(String codigo) {
		
		Consulta consulta = null;
		boolean encontrado = false;
		int i = 0;
		
		while(i<misConsultas.size()&&!encontrado) {
			if(misConsultas.get(i).getCodigo().equalsIgnoreCase(codigo)){
				consulta = misConsultas.get(i);
				encontrado = true;
			}
			i++;
		}
		return consulta;
	}

	public Cita buscarCitaById(String codCita) {
		Cita cita = null;
		boolean encontrado = false;
		int i = 0;
		
		while(i<misCitas.size()&&!encontrado) {
			if(!misCitas.get(i).isRealizada()) {
				if(misCitas.get(i).getCodigo().equalsIgnoreCase(codCita)) {
					cita = misCitas.get(i);
					encontrado = true;
				}
			}
			i++;
		}
			
		return cita;
	}
	
	/*** IMPLEMENTACIONES SQL ***/


   //// aqui enfermedad luego
    
    
    


    
    
    
    
    
    
    // CITAS:
    
    public void cargarDatosCitaSQL() {
    	
    	Clinica.getInstance().getMisCitas().clear();
        
    	String query = "SELECT c.id_cita, c.fecha_cita, c.hora_cita, c.fecha_cita_creacion, c.completada, " +
        			   "c.id_doctor, c.id_persona, c.id_creador_cita, d.id_persona AS id_doctor_persona " +
        			   "FROM CITA AS c " +
        			   "INNER JOIN DOCTOR AS d ON c.id_doctor = d.id_doctor ";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String codigo = rs.getString("id_cita");
                String codigo_doctor = rs.getString("id_doctor_persona");
                String codigo_persona = rs.getString("id_persona");
                String codigo_creador_cita = rs.getString("id_creador_cita");
                Boolean esRealizada = rs.getBoolean("completada");

                Date fechaCita = rs.getDate("fecha_cita");
                Time horaCita = rs.getTime("hora_cita");
                Date fechaCreacion = rs.getDate("fecha_cita_creacion");

                Persona persona = Clinica.getInstance().buscarPersonaById(codigo_persona);
                Doctor doctor = (Doctor) Clinica.getInstance().buscarPersonaById(codigo_doctor);

                Cita cita = new Cita(codigo, persona, doctor, fechaCita, horaCita);
                cita.setFechacreacion(fechaCreacion);
                cita.setRealizada(esRealizada);

                Clinica.getInstance().insertarCita(cita);

                System.out.println("Codigo: " + codigo);
                System.out.println("FechaC: " + fechaCita);
                System.out.println("HoraC: " + horaCita);
                System.out.println("FechaCrea: " + fechaCreacion);
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //HISTORIAL CLINICA:
    
    public void cargarDatosHistoriaClinicaSQL() {
    	
    	Clinica.getInstance().getMisHistoriasClinicas().clear();
        
        String queryHistorial = "SELECT hc.id_historial_clinico, hc.id_paciente, p.id_persona AS id_paciente_persona " +
                                "FROM HISTORIAL_CLINICO AS hc " +
                                "INNER JOIN PACIENTE AS p ON hc.id_paciente = p.id_paciente ";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtHistorial = conn.prepareStatement(queryHistorial);
             ResultSet rsHistorial = stmtHistorial.executeQuery()) {
            
            while (rsHistorial.next()) {
                
                String codigo = rsHistorial.getString("id_historial_clinico");
                String id_paciente = rsHistorial.getString("id_paciente_persona");
                
                Paciente paciente = (Paciente) Clinica.getInstance().buscarPersonaById(id_paciente);
                
                ArrayList<Consulta> consultas = new ArrayList<>();
                ArrayList<Vacuna> vacunasAplicadas = new ArrayList<>();
                
                HistoriaClinica historiaClinica = new HistoriaClinica(codigo, paciente, consultas, vacunasAplicadas);
                Clinica.getInstance().insertarHistoriaClinica(historiaClinica);

                System.out.println("Historia Clï¿½nica ID: " + codigo);
                System.out.println("Paciente ID: " + id_paciente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // CONSULTAS:
   
    public void cargarDatosConsultaSQL() {
    	
    	Clinica.getInstance().getMisConsultas().clear();
        
        String queryConsulta = "SELECT c.id_consulta, c.fecha_consulta, c.id_historial_clinico, c.id_doctor, c.diagnostico, " +
                               "c.id_cita_solicitada, d.id_persona AS id_doctor_persona " +
                               "FROM CONSULTA AS c " +
                               "INNER JOIN DOCTOR AS d ON c.id_doctor = d.id_doctor " +
                               "INNER JOIN PERSONA AS p ON p.id_persona = d.id_persona ";
        
        String queryEnfermedad = "SELECT ec.id_enfermedad " +
                                 "FROM ENFERMEDAD_CONSULTA AS ec " +
                                 "WHERE ec.id_consulta = ?"; // "?", Es un parametro
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtConsulta = conn.prepareStatement(queryConsulta);
             PreparedStatement stmtEnfermedades = conn.prepareStatement(queryEnfermedad);
             ResultSet rsConsulta = stmtConsulta.executeQuery()) {
            
            while (rsConsulta.next()) {
                
                String codigo = rsConsulta.getString("id_consulta");
                Date fechaConsulta = rsConsulta.getDate("fecha_consulta");
                String id_historial_clinico = rsConsulta.getString("id_historial_clinico");
                String id_doctor = rsConsulta.getString("id_doctor_persona");
                String id_cita_solicitada = rsConsulta.getString("id_cita_solicitada");
                String diagnostico = rsConsulta.getString("diagnostico") != null ? rsConsulta.getString("diagnostico") : "No tiene";

                Doctor doctor = (Doctor) Clinica.getInstance().buscarPersonaById(id_doctor);
                Cita cita = Clinica.getInstance().buscarCitaById(id_cita_solicitada);
                
                Consulta consulta = new Consulta(codigo, cita);
                consulta.setDiagnostico(diagnostico);
                consulta.setFechaConsulta(fechaConsulta);

                stmtEnfermedades.setString(1, codigo); // "?" cambia el parametro, segun el codigo
                
                try (ResultSet rsEnfermedades = stmtEnfermedades.executeQuery()) {
                    while (rsEnfermedades.next()) {
                        String id_enfermedad = rsEnfermedades.getString("id_enfermedad");
                        Enfermedad enfermedad = Clinica.getInstance().buscarEnfermedadById(id_enfermedad);
                        if (enfermedad != null) {
                            consulta.insertarEnfermedad(enfermedad);
                        }
                    }
                }
                
                HistoriaClinica historiaClinica = Clinica.getInstance().buscarHistorialClinicaById(id_historial_clinico);
                
                if (historiaClinica != null) {
                    consulta.setMiHistoriaClinica(historiaClinica);
                    historiaClinica.getMisConsultas().add(consulta);
                }
                
                Clinica.getInstance().insertarConsulta(consulta);

            //    System.out.println("Consulta ID: " + codigo);
             //   System.out.println("Fecha Consulta: " + fechaConsulta);
             //   System.out.println("Historial Clï¿½nico ID: " + id_historial_clinico);
             //   System.out.println("Doctor ID: " + id_doctor);
             //   System.out.println("Cita Solicitada ID: " + id_cita_solicitada);
             //   System.out.println("Enfermedades: " + consulta.getMisEnfermedades().size());
             //   System.out.println();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 
/* 
    //VACUNAS:

    public void cargarDatosVacunaSQL() {
        
    	Clinica.getInstance().getMisVacunas().clear();
    	
        String queryVacuna = "SELECT v.id_vacuna, v.nombre, v.id_historial_clinico FROM VACUNA AS v";
        
        String queryEnfermedades = "SELECT ev.id_enfermedad FROM ENFERMEDAD_VACUNA AS ev WHERE ev.id_vacuna = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtVacuna = conn.prepareStatement(queryVacuna);
             PreparedStatement stmtEnfermedades = conn.prepareStatement(queryEnfermedades);
             ResultSet rsVacuna = stmtVacuna.executeQuery()) {

            while (rsVacuna.next()) {
                
                String codigo = rsVacuna.getString("id_vacuna");
                String nombre = rsVacuna.getString("nombre");
                String id_historial_clinico = rsVacuna.getString("id_historial_clinico");

                Vacuna vacuna = new Vacuna(codigo, nombre);

                HistoriaClinica historiaClinica = Clinica.getInstance().buscarHistorialClinicaById(id_historial_clinico);
                if (historiaClinica != null) {
                    vacuna.setMiHistoriaClinica(historiaClinica);
                    historiaClinica.getVacunasAplicadas().add(vacuna);
                }

                stmtEnfermedades.setString(1, codigo);
                
                try (ResultSet rsEnfermedades = stmtEnfermedades.executeQuery()) {
                    while (rsEnfermedades.next()) {
                        String idEnfermedad = rsEnfermedades.getString("id_enfermedad");
                        Enfermedad enfermedad = Clinica.getInstance().buscarEnfermedadById(idEnfermedad);
                        if (enfermedad != null) {
                            vacuna.agregarEnfermedad(enfermedad);
                        }
                    }
                }

                Clinica.getInstance().insertarVacuna(vacuna);

                //System.out.println("Vacuna ID: " + codigo);
              //  System.out.println("Nombre: " + nombre);
               // System.out.println("Historia Clï¿½nica ID: " + (historiaClinica != null ? historiaClinica.getCodigo() : "No asociada"));
               // System.out.println("Nï¿½mero de Enfermedades: " + vacuna.getMisEnfermedades().size());
               // System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
*/
 /*
    public void insertarDatosVacunaSQL(Vacuna vacuna) {
    	
    	Clinica.getInstance().obtenerMaximoIdVacuna();

        String queryVacuna = "INSERT INTO VACUNA (id_vacuna, nombre, id_historial_clinico) VALUES (?, ?, ?)";
        String queryEnferemedad = "INSERT INTO ENFERMEDAD_VACUNA (id_vacuna, id_enfermedad) VALUES (?, ?)";
        int filas = 0;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement insertVacunaStmt = conn.prepareStatement(queryVacuna)) {

            insertVacunaStmt.setString(1, vacuna.getCodigo());
            insertVacunaStmt.setString(2, vacuna.getNombre());
            insertVacunaStmt.setString(3, vacuna.getMiHistoriaClinica() != null ? vacuna.getMiHistoriaClinica().getCodigo() : null);

            // Ejecutar la inserción de la vacuna
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
            
            Clinica.getInstance().insertarVacuna(vacuna);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void actualizarDatosVacunaSQL(Vacuna vacuna) {

        String query = "UPDATE VACUNA SET nombre = ?, id_historial_clinico = ? WHERE id_vacuna = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vacuna.getNombre());
            stmt.setString(2, vacuna.getMiHistoriaClinica() != null ? vacuna.getMiHistoriaClinica().getCodigo() : null);
            stmt.setString(3, vacuna.getCodigo());

            int filas = stmt.executeUpdate();
            System.out.println("Vacunas actualizadas: " + filas);

            // Actualizar las enfermedades asociadas
            // Primero eliminamos las enfermedades existentes asociadas a la vacuna
            String deleteEnfermedadesQuery = "DELETE FROM ENFERMEDAD_VACUNA WHERE id_vacuna = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteEnfermedadesQuery)) {
                deleteStmt.setString(1, vacuna.getCodigo());
                deleteStmt.executeUpdate();
            }

            // Luego insertamos las nuevas asociaciones
            String insertEnfermedadesQuery = "INSERT INTO ENFERMEDAD_VACUNA (id_vacuna, id_enfermedad) VALUES (?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertEnfermedadesQuery)) {
                for (Enfermedad enfermedad : vacuna.getMisEnfermedades()) {
                    insertStmt.setString(1, vacuna.getCodigo());
                    insertStmt.setString(2, enfermedad.getCodigo());
                    insertStmt.addBatch(); //Agrupamos todas las llamadas.
                }
                insertStmt.executeBatch(); //Ejecutamos todas las llamadas.
            }
            
            Clinica.getInstance().actualizarVacuna(vacuna);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarDatosVacunaSQL(String id_vacuna) {

        String queryEnferemedadVacuna = "DELETE FROM ENFERMEDAD_VACUNA WHERE id_vacuna = ?";
        String queryVacuna = "DELETE FROM VACUNA WHERE id_vacuna = ?";
        int filas = 0;

        try (Connection conn = DatabaseConnection.getConnection()) {
        	
            try (PreparedStatement deleteEnfermedadesStmt = conn.prepareStatement(queryEnferemedadVacuna)) {
                deleteEnfermedadesStmt.setInt(1, Integer.parseInt(id_vacuna));
                deleteEnfermedadesStmt.executeUpdate();
            }

            try (PreparedStatement deleteVacunaStmt = conn.prepareStatement(queryVacuna)) {
                deleteVacunaStmt.setInt(1, Integer.parseInt(id_vacuna));
                filas = deleteVacunaStmt.executeUpdate();
                System.out.println(filas + " Vacunas eliminada.");
            }
 
            Vacuna vacuna = Clinica.getInstance().buscarVacunaById(id_vacuna);
            Clinica.getInstance().eliminarVacuna(vacuna);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    */
    
    //MANEJO DE INDICES BASE DE DATOS:
    
    public int obtenerMaximoIdAdministrador() {
        
    	String query = "SELECT MAX(id_administrador) AS max_administrador_id FROM ADMINISTRADOR";
        
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
        
    	String query = "SELECT MAX(id_secretario) AS max_secretario_id FROM SECRETARIO";
        
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
        
    	String query = "SELECT MAX(id_doctor) AS max_doctor_id FROM DOCTOR";
        
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
        
    	String query = "SELECT MAX(id_paciente) AS max_paciente_id FROM PACIENTE";
        
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
        
    	String query = "SELECT MAX(id_persona) AS max_persona_id FROM PERSONA";
        
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
        
    	String query = "SELECT MAX(id_enfermedad) AS max_enfermedad_id FROM ENFERMEDAD";
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
        String query = "SELECT MAX(id_consulta) AS max_consulta_id FROM CONSULTA";
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
        String query = "SELECT MAX(id_vivienda) AS max_vivienda_id FROM VIVIENDA";
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
        String query = "SELECT MAX(id_historial_clinico) AS max_historia_consulta_id FROM HISTORIAL_CLINICO";
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
        String query = "SELECT MAX(id_vacuna) AS max_vacuna_id FROM VACUNA";
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
        String query = "SELECT MAX(id_cita) AS max_cita_id FROM CITA";
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
        String query = "SELECT MAX(id_credencial) AS max_credencial_id FROM CREDENCIAL";
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

    
    public void cargarLastIdUsed() {
    	
    	//Metodo para cargar los id de la base de datos, al progrma debido a lo mucho que cuesta cargar toda la info!

    	
    	obtenerMaximoIdAdministrador();
    	obtenerMaximoIdSecretario();
    	obtenerMaximoIdDoctor();
    	obtenerMaximoIdPaciente();
    	obtenerMaximoIdPersona();
    	obtenerMaximoIdEnfermedad();
    	obtenerMaximoIdConsulta();
    	obtenerMaximoIdVivienda();
    	obtenerMaximoIdHistoriaClinica();
    	obtenerMaximoIdVacuna();
    	obtenerMaximoIdCita();
    }
    
    public void actualizarViviendaSQL(Vivienda vivienda) {
        String query = "UPDATE VIVIENDA SET direccion = ? WHERE id_vivienda = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, vivienda.getDireccion());
            stmt.setString(2, vivienda.getCodigo());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            //Clinica.getInstance().insertarEnfermedad(enfermedad);
            
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
            //Clinica.getInstance().actualizarEnfermedad(enfermedad);

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
        			   "INNER JOIN ENFERMEDAD_VACUNA AS ev ON v.id_vacuna = ev.id_vacuna " +
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
    	
    	//Clinica.getInstance().obtenerMaximoIdVacuna();

        String queryVacuna = "INSERT INTO VACUNA (id_vacuna, nombre, id_historial_clinico) VALUES (?, ?, ?)";
        String queryEnferemedad = "INSERT INTO ENFERMEDAD_VACUNA (id_vacuna, id_enfermedad) VALUES (?, ?)";
        int filas = 0;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement insertVacunaStmt = conn.prepareStatement(queryVacuna)) {

            insertVacunaStmt.setString(1, vacuna.getCodigo());
            insertVacunaStmt.setString(2, vacuna.getNombre());
            insertVacunaStmt.setString(3, vacuna.getMiHistoriaClinica() != null ? vacuna.getMiHistoriaClinica().getCodigo() : null);

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
            
            //Clinica.getInstance().insertarVacuna(vacuna);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    


    public void actualizarDatosVacunaSQL(Vacuna vacuna) {

        String query = "UPDATE VACUNA SET nombre = ?, id_historial_clinico = ? " +
        			   "WHERE id_vacuna = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vacuna.getNombre());
            stmt.setString(2, vacuna.getMiHistoriaClinica() != null ? vacuna.getMiHistoriaClinica().getCodigo() : null);
            stmt.setString(3, vacuna.getCodigo());

            int filas = stmt.executeUpdate();
            System.out.println("Vacunas actualizadas: " + filas);

            // Actualizar las enfermedades asociadas
            // Primero eliminamos las enfermedades existentes asociadas a la vacuna
            String deleteEnfermedadesQuery = "DELETE FROM ENFERMEDAD_VACUNA " +
            								 "WHERE id_vacuna = ?";
            
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteEnfermedadesQuery)) {
                deleteStmt.setString(1, vacuna.getCodigo());
                deleteStmt.executeUpdate();
            }

            // Luego insertamos las nuevas asociaciones
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
            
            //Clinica.getInstance().actualizarVacuna(vacuna);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarDatosVacunaSQL(String id_vacuna) {

        String queryEnferemedadVacuna = "DELETE FROM ENFERMEDAD_VACUNA " +
        								"WHERE id_vacuna = ?";
        String queryVacuna = "DELETE FROM " +
        					 "VACUNA WHERE id_vacuna = ?";
        int filas = 0;
        int filas_elim = 0;

        try (Connection conn = DatabaseConnection.getConnection()) {
        	
            try (PreparedStatement deleteEnfermedadesStmt = conn.prepareStatement(queryEnferemedadVacuna)) {
                deleteEnfermedadesStmt.setInt(1, Integer.parseInt(id_vacuna));
                filas_elim = deleteEnfermedadesStmt.executeUpdate();
            }

            try (PreparedStatement deleteVacunaStmt = conn.prepareStatement(queryVacuna)) {
                deleteVacunaStmt.setInt(1, Integer.parseInt(id_vacuna));
                filas = deleteVacunaStmt.executeUpdate();
                System.out.println(filas + " Vacuna eliminada con: " + filas_elim + " enfermedades en ella");
            }
            
            //Vacuna vacuna = Clinica.getInstance().obtenerVacunaByIdSQL(id_vacuna);
            //Clinica.getInstance().eliminarVacuna(vacuna);
            
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
                row[5] = rs.getBoolean("enServicio") ? "S�" : "No";
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
    	Clinica.getInstance().obtenerMaximoIdPaciente();
    	int fila = 0;
    	
        String query = "INSERT INTO PACIENTE (id_paciente, id_persona, tipoSangre, id_vivienda) " +
        			   "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, codPaciente);
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
        Clinica.getInstance().obtenerMaximoIdDoctor();
        int fila = 0;
        
        String query = "INSERT INTO DOCTOR (id_doctor, id_persona, especialidad, enServicio) " +
                       "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, codDoctor);
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
                    String password = new String(rs.getBytes("passwordUser"), StandardCharsets.UTF_8);
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
        
    	try (Connection conn = DatabaseConnection.getConnection()) {
    		
    		//Eliminacion en cadena para evitar errores con las claves foraneas
    		
            String queryEnfermedadVacuna = "DELETE FROM ENFERMEDAD_VACUNA WHERE id_vacuna IN  "+
            		 					   "(SELECT id_vacuna FROM VACUNA WHERE id_historial_clinico IN " +
					            		   "(SELECT id_historial_clinico FROM HISTORIAL_CLINICO " +
					            		   "WHERE id_paciente = (SELECT id_paciente FROM PACIENTE WHERE id_persona = ?)))";
            try (PreparedStatement stmtEnfermedadVacuna = conn.prepareStatement(queryEnfermedadVacuna)) {
                stmtEnfermedadVacuna.setString(1, id_persona);
                stmtEnfermedadVacuna.executeUpdate();
            }

            String queryVacuna = "DELETE FROM VACUNA WHERE id_historial_clinico IN " +
			            		 "(SELECT id_historial_clinico FROM HISTORIAL_CLINICO " +
			            		 "WHERE id_paciente = (SELECT id_paciente FROM PACIENTE WHERE id_persona = ?))";
            try (PreparedStatement stmtVacuna = conn.prepareStatement(queryVacuna)) {
                stmtVacuna.setString(1, id_persona);
                stmtVacuna.executeUpdate();
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

            System.out.println("Datos del paciente con c�digo " + id_persona + " eliminados.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void eliminarDatosDoctorSQL(String id_persona) {
        
    	try (Connection conn = DatabaseConnection.getConnection()) {
        	
    		//Eliminacion en cadena para evitar errores con las claves foraneas
    		
            String queryEnfermedadConsulta = "DELETE FROM ENFERMEDAD_CONSULTA WHERE id_consulta IN " +
            								 "(SELECT id_consulta FROM CONSULTA " +
            		                         "WHERE id_doctor IN (SELECT id_doctor FROM DOCTOR WHERE id_persona = ?))";
            try (PreparedStatement stmtEnfermedadConsulta = conn.prepareStatement(queryEnfermedadConsulta)) {
                stmtEnfermedadConsulta.setString(1, id_persona);
                stmtEnfermedadConsulta.executeUpdate();
            }
            
            String queryConsulta = "DELETE FROM CONSULTA " +
        			   			   "WHERE id_doctor IN (SELECT id_doctor FROM DOCTOR WHERE id_persona = ?)";
			try (PreparedStatement stmtConsulta = conn.prepareStatement(queryConsulta)) {
				stmtConsulta.setString(1, id_persona);
				stmtConsulta.executeUpdate();
			}
            
            String queryCita = "DELETE FROM CITA " +
            		 		   "WHERE id_doctor IN (SELECT id_doctor FROM DOCTOR WHERE id_persona = ?)";
            try (PreparedStatement stmtCita = conn.prepareStatement(queryCita)) {
                stmtCita.setString(1, id_persona);
                stmtCita.executeUpdate();
            }
			
            String queryCredencial = "DELETE FROM CREDENCIAL " +
            						 "WHERE id_persona = ?";
            try (PreparedStatement stmtCredencial = conn.prepareStatement(queryCredencial)) {
            	stmtCredencial.setString(1, id_persona);
            	stmtCredencial.executeUpdate();
			}
								
            String queryDoctor = "DELETE FROM DOCTOR " +
            		      		 "WHERE id_persona = ?";
            try (PreparedStatement stmtDoctor = conn.prepareStatement(queryDoctor)) {
                stmtDoctor.setString(1, id_persona);
                stmtDoctor.executeUpdate();
            }
            
            String queryPersona = "DELETE FROM PERSONA " +
					  			  "WHERE id_persona = ?";
			try (PreparedStatement stmtPersona = conn.prepareStatement(queryPersona)) {
				  stmtPersona.setString(1, id_persona);
				  stmtPersona.executeUpdate();
			}


            System.out.println("Datos del doctor con c�digo " + id_persona + " eliminados.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarDatosPersonaSQL(String id_persona) {
       
    	try (Connection conn = DatabaseConnection.getConnection()) {
            
    		//Eliminacion en cadena para evitar errores con las claves foraneas
    		
            String queryCita = "DELETE FROM CITA " +
            				  "WHERE id_persona = ? OR id_creador_cita = ?";
            try (PreparedStatement stmtCita = conn.prepareStatement(queryCita)) {
                stmtCita.setString(1, id_persona);
                stmtCita.setString(2, id_persona);
                stmtCita.executeUpdate();
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

            System.out.println("Datos de la persona con c�digo " + id_persona + " eliminados.");
        } catch (SQLException e) {
            e.printStackTrace();
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
                       "FROM VIVIENDA AS v " +
                       "LEFT JOIN PACIENTE AS p ON v.id_vivienda = p.id_vivienda";

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"C�digo Vivienda", "Direcci�n"});

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
            System.out.println("Vivienda con c�digo " + fila + " eliminada.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
      
} 