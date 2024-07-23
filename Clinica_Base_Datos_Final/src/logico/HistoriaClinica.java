package logico;

import java.util.ArrayList;

public class HistoriaClinica {

    private ArrayList<Consulta> misConsultas;
    private ArrayList<Vacuna> vacunasAplicadas;

    public HistoriaClinica() {
        super();
        this.misConsultas = new ArrayList<Consulta>();
        this.vacunasAplicadas = new ArrayList<Vacuna>();
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

    public static HistoriaClinica getInstance() {
    	
        return new HistoriaClinica();
    }
}
