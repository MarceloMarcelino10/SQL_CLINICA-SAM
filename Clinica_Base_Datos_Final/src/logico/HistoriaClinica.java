package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class HistoriaClinica implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Codigo;
	private Paciente miPaciente;
    private ArrayList<Consulta> misConsultas;
    private ArrayList<Vacuna> vacunasAplicadas;
	
	public HistoriaClinica(String codigo, Paciente miPaciente, ArrayList<Consulta> misConsultas,
			ArrayList<Vacuna> vacunasAplicadas) {
		super();
		Codigo = codigo;
		this.miPaciente = miPaciente;
		this.misConsultas = misConsultas;
		this.vacunasAplicadas = vacunasAplicadas;
	}
    
    public String getCodigo() {
		return Codigo;
	}
	public void setCodigo(String codigo) {
		Codigo = codigo;
	}
	public Paciente getMiPaciente() {
		return miPaciente;
	}
	public void setMiPaciente(Paciente miPaciente) {
		this.miPaciente = miPaciente;
	}
	public ArrayList<Consulta> getMisConsultas() {
		return misConsultas;
	}
	public void setMisConsultas(ArrayList<Consulta> misConsultas) {
		this.misConsultas = misConsultas;
	}
	public ArrayList<Vacuna> getVacunasAplicadas() {
		return vacunasAplicadas;
	}
	public void setVacunasAplicadas(ArrayList<Vacuna> vacunasAplicadas) {
		this.vacunasAplicadas = vacunasAplicadas;
	}
}
