package logico;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JOptionPane;

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
	private static Clinica clinica = null;
	public static int codPersona = 1;
	public static int codCita= 1;
	public static int codVacuna = 1;
	public static int codEnfermedad = 1;
	public static int codConsulta = 1;
	public static int codVivienda = 1;
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

	//METODOS PARA INSERTAR EN LAS LISTAS:
	
    public void insertarVivienda(Vivienda vivienda) {
        misViviendas.add(vivienda);
        codVivienda++;
        guardarDatos();
    }

    public void insertarPersona(Persona persona) {
        misPersonas.add(persona);
        codPersona++;
        guardarDatos();
    }

    public void insertarEnfermedad(Enfermedad enfermedad) {
        misEnfermedades.add(enfermedad);
        codEnfermedad++;
       // guardarDatos();
    }

    public void insertarCita(Cita cita) {
        misCitas.add(cita);
        codCita++;
        guardarDatos();
    }

    public void insertarVacuna(Vacuna vacuna) {
        misVacunas.add(vacuna);
        codVacuna++;
        guardarDatos();
    }

    public void insertarConsulta(Consulta consulta) {
        misConsultas.add(consulta);
        codConsulta++;
        guardarDatos();
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

    public void eliminarEnfermedad(String codigoEnfermedad) {
        misEnfermedades.remove(codigoEnfermedad);
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
		return login;
	}
	
	public boolean existUserName(String userName) {
		boolean exist = false;
	    for (Persona user : misPersonas) {
	        if (user.getUser().equals(userName)) {
	            exist = true;
	        }
	    }
	    return exist;
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
	}

	public void cargarValoresEstaticos() {
	    if (!valoresEstaticos.isEmpty() && valoresEstaticos.size() >= 6) {
	        setCodPersona(valoresEstaticos.get(0));
	        setCodCita(valoresEstaticos.get(1));
	        setCodVacuna(valoresEstaticos.get(2));
	        setCodEnfermedad(valoresEstaticos.get(3));
	        setCodConsulta(valoresEstaticos.get(4));
	        setCodVivienda(valoresEstaticos.get(5));
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
	        JOptionPane.showMessageDialog(null, "La persona no est� registrada en ninguna vivienda", "Error", JOptionPane.ERROR_MESSAGE);
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
	
	//METODOS PARA GENERAR EL REPORTE CLINICO:
	
	public String obtenerViviendasMasPobladas() {
		
		if (misViviendas.isEmpty()) {
	        return "Informaci�n no disponible!\n";
	    }
		ArrayList<Vivienda> viviendasOrdenadas = new ArrayList<>(misViviendas);
		
        Collections.sort(viviendasOrdenadas, Comparator.comparingInt(vivienda -> vivienda.getMisPersonas().size()));
        Collections.reverse(viviendasOrdenadas);
        
        int limite = Math.min(3, viviendasOrdenadas.size());
        StringBuilder resultado = new StringBuilder("Las viviendas m�s pobladas son:\n");

        for (int i = 0; i < limite; i++) {
            Vivienda vivienda = viviendasOrdenadas.get(i);
            resultado.append(String.format("%d. %s: %s - Afiliados: %d personas\n", i + 1, vivienda.getCodigo(), vivienda.getDireccion(), vivienda.getMisPersonas().size()));
        }
        return resultado.toString();
    }
	
    public String calcularPromedioEdadPersonas() {
        if (misPersonas.isEmpty()) {
        	return "Informaci�n no disponible!\n";
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
        return String.format("El promedio de edad de las personas tratadas: %.2f a�os.\n", promedioEdad);
    }
	
    public String calcularPromedioSexoPersoanas() {
        if (misPersonas.isEmpty()) {
        	return "Informaci�n no disponible!\n";
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
        	return "Informaci�n no disponible!\n";
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
        	return "Informaci�n no disponible!\n";
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
        StringBuilder resultado = new StringBuilder("Las enfermedades m�s recurrentes son:\n");

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
            return "Informaci�n no disponible!\n";
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
            return "Informaci�n no disponible!\n";
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
        	return String.format("El doctor con m�s citas es: %s, con especialidad de %s, y tiene %d citas.\n", doctorConMasCitas.getNombre(), doctorConMasCitas.getEspecialidad(), maxCitas);
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
        	return "Informaci�n no disponible!\n";
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
        	return "Informaci�n no disponible!\n";
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

        return String.format("Promedio de G�nero entre los doctores:\nMasculino: %.2f%%\nFemenino: %.2f%%\n", porcentajeMasculino, porcentajeFemenino);
    }
    
    public String obtenerVacunaMasCurativa() {
        if (misVacunas.isEmpty()) {
        	return "Informaci�n no disponible!\n";
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

        StringBuilder resultado = new StringBuilder("La vacuna que m�s cura enfermedades es:\n");
        resultado.append(String.format("- %s (C�digo: %s)\n", vacunaConMasEnfermedades.getNombre(), vacunaConMasEnfermedades.getCodigo()));
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
	
    public void cargarDatosDesdeSQL() throws ParseException {
        
    	// Ver metodos de carga:
    	
    	//cargarDatosPersonaSQL();
    	cargarDatosViviendaSQL();
    	cargarDatosEnfermedadSQL();
    	cargarDatosDoctorSQL();
    	cargarDatosPacienteSQL();
    } 
	
	// METODOS SQL (CARGA DE DATOS):
    
    //PERSONAS:

    public void cargarDatosPersonaSQL() throws ParseException {
        String query = "SELECT p.id_persona, p.cedula, p.nombre, p.apellido, p.sexo, p.fecha_nacimiento, " +
                       "c.userName, c.passwordUser, r.rango " +
                       "FROM PERSONA p " +
                       "JOIN CREDENCIAL c ON p.id_persona = c.id_persona " +
                       "JOIN RANGO_PERSONA r ON c.id_persona = r.id_rango_persona";


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String codigo = rs.getString("id_persona");
                String cedula = rs.getString("cedula");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String sexo = rs.getString("sexo");
                String fec_nacimSQL = rs.getString("fecha_nacimiento");
                String user = rs.getString("userName");
                String password = rs.getString("passwordUser");
                String rango = rs.getString("rango");

                Date fec_nacim = null;

                try {
                    if (fec_nacimSQL != null && !fec_nacimSQL.isEmpty()) {
                        fec_nacim = sdf.parse(fec_nacimSQL);
                    }
                } catch (ParseException e) {
                    System.out.println("Error parsing date: " + fec_nacimSQL);
                }

                // Imprimir los datos
                System.out.println("Codigo: " + codigo);
                System.out.println("Cedula: " + cedula);
                System.out.println("Nombre: " + nombre);
                System.out.println("Apellido: " + apellido);
                System.out.println("Sexo: " + sexo);
                System.out.println("Fecha de Nacimiento: " + (fec_nacim != null ? sdf.format(fec_nacim) : "Fecha inv�lida o no disponible"));
                System.out.println("User: " + user);
                System.out.println("Password: " + password);
                System.out.println("Rango: " + rango);
                System.out.println();

                Persona persona = new Persona(codigo, cedula, nombre, apellido, fec_nacim, sexo, user, password, obtenerRango(rango));
                clinica.getInstance().insertarPersona(persona);
                
                
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private int obtenerRango(String rango) {
	    // EN EL PROGRMA DE JAVA: 4 ADMIN, 3 SECRETARIO, 2 DOCTOR, 1 PACIENTE, 0 PERSONA
        switch (rango) {
            case "Administrador":
                return 4;
            case "Secretario":
                return 3;
            case "Doctor":
                return 2;
            case "Paciente":
                return 1;
            default: // Persona
                return 5;
        }
    }
    
    //DOCTORES:
    
    public void cargarDatosDoctorSQL() {
    	
    	String query = "SELECT * " + 
	    			   "FROM PERSONA AS p " + 
	    			   "INNER JOIN DOCTOR AS d ON p.id_persona = d.id_persona " + 
	    			   "INNER JOIN CREDENCIAL AS c ON d.id_persona = c.id_persona " + 
	    			   "INNER JOIN RANGO_PERSONA AS ra ON c.id_rango_persona = ra.id_rango_persona " + 
	    			   "WHERE ra.rango = 'Doctor' ";
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String codigo = rs.getString("id_persona");
                String cedula = rs.getString("cedula");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String sexo = rs.getString("sexo");
                String fec_nacimSQL = rs.getString("fecha_nacimiento");
                String user = rs.getString("userName");
                String password = rs.getString("passwordUser");
                String rango = rs.getString("rango");
                
                String codigo_doctor = rs.getString("id_doctor");
                String especialidad = rs.getString("especialidad");
                Boolean enServicio = rs.getBoolean("enServicio");
                
                Date fec_nacim = null;

                try {
                    if (fec_nacimSQL != null && !fec_nacimSQL.isEmpty()) {
                        fec_nacim = sdf.parse(fec_nacimSQL);
                    }
                } catch (ParseException e) {
                    System.out.println("Error parsing date: " + fec_nacimSQL);
                }
                
                Doctor doctor = new Doctor(codigo, cedula, nombre, apellido, fec_nacim, sexo, user, password, obtenerRango(rango), especialidad, enServicio);
                Clinica.getInstance().insertarPersona(doctor);    
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    }
    
    public void cargarDatosPacienteSQL() {
    	
    	String query = "SELECT * " + 
	    			   "FROM PERSONA AS p " + 
	    			   "INNER JOIN PACIENTE AS pc ON p.id_persona = pc.id_persona " + 
	    			   "INNER JOIN CREDENCIAL AS c ON pc.id_persona = c.id_persona " + 
	    			   "INNER JOIN RANGO_PERSONA AS ra ON c.id_rango_persona = ra.id_rango_persona " + 
	    			   "INNER JOIN VIVIENDA AS v ON pc.id_vivienda = v.id_vivienda " +
	    			   "WHERE ra.rango = 'Paciente' ";
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String codigo = rs.getString("id_persona");
                String cedula = rs.getString("cedula");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String sexo = rs.getString("sexo");
                String fec_nacimSQL = rs.getString("fecha_nacimiento");
                String user = rs.getString("userName");
                String password = rs.getString("passwordUser");
                String rango = rs.getString("rango");
                
                String codigo_paciente = rs.getString("id_paciente");
                String tipoSangre = rs.getString("tipoSangre");
                Boolean dirreccion = rs.getBoolean("direccion");
                
                String id_vivienda = rs.getString("id_vivienda");
                
                Date fec_nacim = null;

                try {
                    if (fec_nacimSQL != null && !fec_nacimSQL.isEmpty()) {
                        fec_nacim = sdf.parse(fec_nacimSQL);
                    }
                } catch (ParseException e) {
                    System.out.println("Error parsing date: " + fec_nacimSQL);
                }
                
                Vivienda vivienda = null;
                
                if (id_vivienda != null) {
                    vivienda = Clinica.getInstance().buscarViviendaById(id_vivienda);
                }

                Paciente paciente = new Paciente(codigo, cedula, nombre, apellido, fec_nacim, sexo, user, password, obtenerRango(rango), vivienda, tipoSangre);
                Clinica.getInstance().insertarPersona(paciente);

                if (vivienda != null) {
                    vivienda.insertarPersona(paciente);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    }
    
    
    public void cargarDatosPersona() {
    	
    	
    }

    //ENFERMEDADES:
    
    public void cargarDatosEnfermedadSQL() {
        String query = "SELECT e.id_enfermedad, e.nombre, e.sintomas, e.tratamiento, e.id_gravedad_enfermedad " + 
        			   "FROM ENFERMEDAD e";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String codigo = rs.getString("id_enfermedad");
                String nombre = rs.getString("nombre");
                String sintomas = rs.getString("sintomas");
                String tratamiento = rs.getString("tratamiento");
                String gravedad = rs.getString("id_gravedad_enfermedad");

                System.out.println("Codigo: " + codigo);
                System.out.println("Nombre: " + nombre);
                System.out.println("Sintomas: " + sintomas);
                System.out.println("Tratamiento: " + tratamiento);
                System.out.println("Gravedad: " + gravedad);
                System.out.println();
                
                Enfermedad enfermedad = new Enfermedad(codigo, nombre, sintomas, tratamiento, Integer.parseInt(gravedad));
                Clinica.getInstance().insertarEnfermedad(enfermedad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //VIVIENDAS:

    public void cargarDatosViviendaSQL() {
    	
        String query = "SELECT * " +
        			   "FROM VIVIENDA AS v " +
        			   "LEFT JOIN PACIENTE AS p ON v.id_vivienda = p.id_vivienda ";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
        	
            while (rs.next()) {
                String codigo_vivienda = rs.getString("id_vivienda");
                String direccion = rs.getString("direccion");
                
                Vivienda vivienda = new Vivienda(codigo_vivienda, direccion);
               Clinica.getInstance().insertarVivienda(vivienda);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
}

	
	




