package logico;

import java.util.ArrayList;

public class Vacuna {

	private String codigo;
	private String nombre;
	private ArrayList<Enfermedad> misEnfermedades;
	
    public Vacuna(String codigo, String nombre ) {
        super();
        this.codigo = codigo;
        this.nombre = nombre;
        this.misEnfermedades = new ArrayList<>();

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
