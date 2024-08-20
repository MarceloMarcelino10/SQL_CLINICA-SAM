package logico;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codigo;
    private Persona miPersona;
    private Doctor miDoctor;
    private Date fechaCita;
    private Date fechacreacion;
    private Time horaCita;
    private boolean realizada;

    public Cita(String codigo, Persona miPersona, Doctor miDoctor, Date fechaCita, Time horaCita) {
        this.codigo = codigo;
        this.miPersona = miPersona;
        this.miDoctor = miDoctor;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.realizada = false;
        this.fechacreacion = new Date();
    }

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Persona getMiPersona() {
		return miPersona;
	}

	public void setMiPersona(Persona miPersona) {
		this.miPersona = miPersona;
	}

	public Doctor getMiDoctor() {
		return miDoctor;
	}

	public void setMiDoctor(Doctor miDoctor) {
		this.miDoctor = miDoctor;
	}

	public Date getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(Date fechaCita) {
		this.fechaCita = fechaCita;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public Time getHoraCita() {
		return horaCita;
	}

	public void setHoraCita(Time horaCita) {
		this.horaCita = horaCita;
	}

	public boolean isRealizada() {
		return realizada;
	}

	public void setRealizada(boolean realizada) {
		this.realizada = realizada;
	}

    
}
