package logico;

import java.util.ArrayList;
import java.util.Date;

import javax.print.attribute.standard.DateTimeAtProcessing;

public class Consulta {
	
	private String codigo;
	private ArrayList<Enfermedad> misEnfermedades;
	private Date fechaConsulta;
	private Cita miCita;
	public Consulta(String codigo, Cita miCita) {
		super();
		this.codigo = codigo;
		this.misEnfermedades = new ArrayList<Enfermedad>();
		this.fechaConsulta = new Date();
		this.miCita = miCita;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public ArrayList<Enfermedad> getMisEnfermedades() {
		return misEnfermedades;
	}
	
	public void setMisEnfermedades(ArrayList<Enfermedad> misEnfermedades) {
		this.misEnfermedades = misEnfermedades;
	}
	
	public Date getFechaConsulta() {
		return fechaConsulta;
	}
	
	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
	
	public Cita getMiCita() {
		return miCita;
	}
	
	public void setMiCita(Cita miCita) {
		this.miCita = miCita;
	}
	
	//METODO PARA INSERTAR:
	
	public void insertarEnfermedad(Enfermedad enfermedad) {
        misEnfermedades.add(enfermedad);
    }
	
	//METODO PARA ELIMINAR:u
	
	public void eliminarEnfermedad(Enfermedad enfermedad) {
        misEnfermedades.remove(enfermedad);
    }
	
	// METODO PARA MODIFICAR:
	
	public void actualizarEnfermedad(Enfermedad enfermedad) {
        int index = buscarEnfermedadByIndex(enfermedad.getCodigo());
            misEnfermedades.set(index, enfermedad);
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
}
