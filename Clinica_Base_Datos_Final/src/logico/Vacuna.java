package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class Vacuna implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String nombre;
	private ArrayList<Enfermedad> misEnfermedades;
	private HistoriaClinica miHistoriaClinica;
	
    public Vacuna(String codigo, String nombre ) {
        super();
        this.codigo = codigo;
        this.nombre = nombre;
        this.misEnfermedades = new ArrayList<>();
        this.miHistoriaClinica = miHistoriaClinica;
    }

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public HistoriaClinica getMiHistoriaClinica() {
		return miHistoriaClinica;
	}

	public void setMiHistoriaClinica(HistoriaClinica miHistoriaClinica) {
		this.miHistoriaClinica = miHistoriaClinica;
	}

	public ArrayList<Enfermedad> getMisEnfermedades() {
		return misEnfermedades;
	}

	public void setMisEnfermedades(ArrayList<Enfermedad> misEnfermedades) {
		this.misEnfermedades = misEnfermedades;
	}
	
    public void agregarEnfermedad(Enfermedad enfermedad) {
        misEnfermedades.add(enfermedad);
    }    
}
