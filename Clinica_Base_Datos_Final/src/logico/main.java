package logico;

public class main {

	public static void main(String[] args) {
	
		/*Prueba de login:
		Doctor d = new Doctor("123", "123", "Maxi Emiliano", "Alegrie", "Masculino", "Milan", "123", "Cirujano Nocturno", true);
		Paciente p = new Paciente("12", "12", "Juan", "Perez", "Masculino", "juan", "juan123", null, null,null);
		Paciente p1 = new Paciente("12", "12", "Perez", "Gomex", "Masculino", "Vol", "juan123", null, null,null); //Luego hacer un metodo para revisar que no se repita un usuario
		
		Clinica.getInstance().insertarPersona(p);
		Clinica.getInstance().insertarPersona(d);
		Clinica.getInstance().insertarPersona(p1);
		

		if (Clinica.getInstance().iniciarSesion("milan", "123") != null) {
			System.out.println("Nombre del usuario ingresado: "+Clinica.getInstance().iniciarSesion("Milan", "123").getNombre());
        } else {
            System.out.println("El usuario no existe.");
        }
		
		if (Clinica.getInstance().iniciarSesion("juan", "juan123") != null) {
			System.out.println("Nombre del usuario ingresado: "+Clinica.getInstance().iniciarSesion("juan", "juan123").getNombre());
        } else {
            System.out.println("El usuario no existe.");
        }
		if (Clinica.getInstance().iniciarSesion("adAmin", "admin")!= null) {
			System.out.println("Nombre del usuario ingresado: "+Clinica.getInstance().iniciarSesion("admin", "admin").getNombre());
        } else {
            System.out.println("El usuario no existe.");
        }

		d.insertarPacienteTratado(p);
		d.insertarPacienteTratado(p1);
		System.out.println("El doctor "+ d.getNombre() + " ha tratado a "+ d.getMisPacientesTratados().size() + " pacientes:");
		
		for (int i = 0; i < d.getMisPacientesTratados().size(); i++) {
            System.out.println(d.getMisPacientesTratados().get(i).getNombre());
        }*/
	}
}
