package logico;

import java.util.ArrayList;
import java.util.Date;

public class Doctor extends Persona {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 740248718284731713L;
	private String especialidad;
	boolean enServicio = false;
	private ArrayList<Paciente> misPacientesTratados;
	private ArrayList<Consulta> misConsultasTratadas;
	
	public Doctor(String codigo, String cedula, String nombre, String apellidos, Date fechaNacimiento, String genero,
			String user, String password, int rangoUser, String especialidad, boolean enServicio) {
		super(codigo, cedula, nombre, apellidos, fechaNacimiento, genero, user, password, rangoUser);
		this.especialidad = especialidad;
		this.enServicio = enServicio;
		this.misPacientesTratados = new ArrayList<Paciente>();
		this.misConsultasTratadas = new ArrayList<Consulta>();
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public boolean isEnServicio() {
		return enServicio;
	}

	public void setEnServicio(boolean enServicio) {
		this.enServicio = enServicio;
	}

	public ArrayList<Paciente> getMisPacientesTratados() {
		return misPacientesTratados;
	}

	public void setMisPacientesTratados(ArrayList<Paciente> misPacientesTratados) {
		this.misPacientesTratados = misPacientesTratados;
	}

	public ArrayList<Consulta> getMisConsultasTratadas() {
		return misConsultasTratadas;
	}

	public void setMisConsultasTratadas(ArrayList<Consulta> misConsultasTratadas) {
		this.misConsultasTratadas = misConsultasTratadas;
	}
	
	//METODOS PARA INSERTAR:u
	
	public void insertarPacienteTratado(Paciente paciente) {
		misPacientesTratados.add(paciente);
	}
	
	public void insertarConsultaTratada(Consulta consulta) {
		misConsultasTratadas.add(consulta);
	}
	
	//METODOS PARA ELIMINAR:
	
	public void eliminarPacienteTratado(Paciente paciente) {
		misPacientesTratados.remove(paciente);
	}

	public void eliminarConsultaTratada(Consulta consulta) {
		misConsultasTratadas.remove(consulta);
	}

	// METODOS PARA MODIFICAR:

	public void actualizarPacienteTratado(Paciente paciente) {
	    int index = buscarPacienteTratadoByCodigo(paciente.getCodigo());
	    misPacientesTratados.set(index, paciente);
	}
	
	private int buscarPacienteTratadoByCodigo(String codigo) {
	    int index = -1;
	    boolean encontrado = false;
	    int i = 0;

	    while (!encontrado && i < misPacientesTratados.size()) {
	        if (misPacientesTratados.get(i).getCodigo().equalsIgnoreCase(codigo)) {
	            encontrado = true;
	            index = i;
	        }
	        i++;
	    }

	    return index;
	}

	public void actualizarConsultaTratada(Consulta consulta) {
	    int index = buscarConsultaTratadaByCodigo(consulta.getCodigo());
	    misConsultasTratadas.set(index, consulta);
	}
	
	private int buscarConsultaTratadaByCodigo(String codigo) {
	    int index = -1;
	    boolean encontrado = false;
	    int i = 0;

	    while (!encontrado && i < misConsultasTratadas.size()) {
	        if (misConsultasTratadas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
	            encontrado = true;
	            index = i;
	        }
	        i++;
	    }

	    return index;
	}
}
