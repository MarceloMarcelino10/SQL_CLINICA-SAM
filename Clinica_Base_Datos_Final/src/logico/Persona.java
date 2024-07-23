package logico;

import java.io.Serializable;
import java.util.Date;

public class Persona implements Serializable {
	
	/**
	 * u
	 */
	private static final long serialVersionUID = -4533483389910246079L;
	private String codigo;
	private String cedula;
	private String nombre;
	private String apellidos;
	private Date fechaNacimiento;
	private String genero;
	private String user;
	private String password;
	private int rangoUser;
	
	public Persona(String codigo, String cedula, String nombre, String apellidos, Date fechaNacimiento, String genero,
			String user, String password, int rangoUser) {
		super();
		this.codigo = codigo;
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.genero = genero;
		this.user = user;
		this.password = password;
		this.rangoUser = rangoUser;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getCedula() {
		return cedula;
	}
	
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public String getGenero() {
		return genero;
	}
	
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getRangoUser() {
		return rangoUser;
	}
	
	public void setRangoUser(int rangoUser) {
		this.rangoUser = rangoUser;
	}
}
