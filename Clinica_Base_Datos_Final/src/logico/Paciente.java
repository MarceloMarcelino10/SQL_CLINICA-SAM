package logico;

import java.util.Date;

public class Paciente extends Persona {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8403063405019021597L;
	private Vivienda miVivienda;
	private HistoriaClinica miHistoria;
	private String tipoSangre;
	
	public Paciente(String codigo, String cedula, String nombre, String apellidos, Date fechaNacimiento, String genero,
			String user, String password, int rangoUser, Vivienda miVivienda, String tipoSangre) {
		super(codigo, cedula, nombre, apellidos, fechaNacimiento, genero, user, password, rangoUser);
		this.miVivienda = miVivienda;
		this.tipoSangre = tipoSangre;
	}

	public Vivienda getMiVivienda() {
		return miVivienda;
	}

	public void setMiVivienda(Vivienda miVivienda) {
		this.miVivienda = miVivienda;
	}

	public HistoriaClinica getMiHistoria() {
		return miHistoria;
	}

	public void setMiHistoria(HistoriaClinica miHistoria) {
		this.miHistoria = miHistoria;
	}

	public String getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}
}
