package logico;
import java.sql.Time;
import java.util.Date;

public class Cita {
	
	private String codigo;
	private Persona miPersona;
	private Doctor miDoctor;
	private Date fechaCita; //que dia sera la citau
	private Date fechacreacion; //que dia se creo
	private Time horaCita;
	private boolean realizada;
	public Cita(String codigo, Persona miPersona, Doctor miDoctor, Date fechaCita, Time horaCita) {
		super();
		this.codigo = codigo;
		this.miPersona = miPersona;
		this.miDoctor = miDoctor;
		this.fechaCita = fechaCita;
		this.horaCita = horaCita;
		this.realizada = false;//inicialmente en false porque la cita aun el paciente no ha ido
		this.fechacreacion = new Date();
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
		return miDoctor;//
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

}
