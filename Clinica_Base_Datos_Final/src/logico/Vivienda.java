package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class Vivienda implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6046120080348923872L;
	private String codigo;
	private ArrayList<Persona> misPersonas;
	private String direccion;
	public Vivienda(String codigo, String direccion) {
		super();
		this.codigo = codigo;
		this.misPersonas = new ArrayList<Persona>();
		this.direccion = direccion;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public ArrayList<Persona> getMisPersonas() {
		return misPersonas;
	}
	public void setMisPersonas(ArrayList<Persona> misPersonas) {
		this.misPersonas = misPersonas;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	//METODOS PARA INSERTAR EN LA LISTA:
	
    public void insertarPersona(Persona persona) {
        misPersonas.add(persona);
    }
    
    //METODOS PARA ELIMINAR EN LA LISTA:
    
    public void eliminarPersona(Persona persona) {
        misPersonas.remove(persona);
    }
    
    //METODO PARA ACTUALIZAR EL ELEMENTO DE UNA LISTA:
    
    public void actualizarPersona(Persona persona) {
        int index = buscarPersonaByIndex(persona.getCodigo());
            misPersonas.set(index, persona);
    }

    private int buscarPersonaByIndex(String codigo) {
        int index = -1;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < misPersonas.size()) {
            if (misPersonas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                encontrado = true;
                index = i;
            }
            i++;
        }    
        return index;
    }
    
    //METODO PARA COMPROBAR UN AFILIADO:

    public boolean buscarAfiliado(String afiliado) {
        boolean encontrado = false;
    	if (Clinica.getInstance().loggedUser.getRangoUser() != 4) { //Solo doctores y secretarios
            if (misPersonas.isEmpty()) {
            	encontrado =  true;
            } else {
                for (Persona persona : misPersonas) {
                    if (persona.getCedula().equalsIgnoreCase(afiliado)) {
                    	encontrado =  true;
                    }
                }
            }
        }

        return encontrado;
    }

}//
