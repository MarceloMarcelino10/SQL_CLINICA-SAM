package seguridad;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import logico.Clinica;
import logico.Doctor;
import logico.Paciente;
import logico.Persona;
import sql.DatabaseConnection;

public class GestorUsuario {

    // Metodo para hashear la contrase�a
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
	
    // Metodo para verificar la contrase�a
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

    // Metodo para confirmar el login 
    public static boolean confirmLogin(String userName, String password) {
       
    	boolean login = false;
       
        String query = "SELECT c.userName, c.passwordUser, c.id_rango_persona, c.id_persona, " +
                	   "p.nombre, p.apellido, p.cedula, p.fecha_nacimiento, p.sexo, " +
                       "d.especialidad, d.enServicio, pa.tipoSangre " +
                       "FROM CREDENCIAL AS c " +
                       "INNER JOIN PERSONA AS p ON c.id_persona = p.id_persona " +
                       "LEFT JOIN DOCTOR AS d ON p.id_persona = d.id_persona " +
                       "LEFT JOIN PACIENTE AS pa ON p.id_persona = pa.id_persona " +
                       "WHERE c.userName = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    byte[] storedHash = rs.getBytes("passwordUser"); //Tomar Bytes
                    
                    String storedHashStr = new String(storedHash, StandardCharsets.UTF_8); //Convertir a String
                    
                    if (checkPassword(password, storedHashStr)) {// Verificar la contrase�a
                        
                        SetLoggedUser(rs);
                        login = true;
                        
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return login;
    }
    
    private static void SetLoggedUser(ResultSet rs) throws SQLException {
        
    	String codigo = rs.getString("id_persona");
        String cedula = rs.getString("cedula");
        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellido");
        Date fecNacim = rs.getDate("fecha_nacimiento");
        String sexo = rs.getString("sexo");
        String userName = rs.getString("userName");
        String password = new String(rs.getBytes("passwordUser"), StandardCharsets.UTF_8);
        int rangoUser = rs.getInt("id_rango_persona");

        Persona persona;
        switch (rangoUser) {
            case 1:
                persona = new Persona(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, rangoUser);
                break;
            case 2:
                persona = new Persona(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, rangoUser);
                break;
            case 3:
                String especialidad = rs.getString("especialidad");
                boolean enServicio = rs.getBoolean("enServicio");
                persona = new Doctor(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, rangoUser, especialidad, enServicio);
                break;
            case 4:
                String tipoSangre = rs.getString("tipoSangre");
                persona = new Paciente(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, rangoUser, null, tipoSangre);
                break;
            case 5:
                persona = new Persona(codigo, cedula, nombre, apellidos, fecNacim, sexo, userName, password, rangoUser);
                break;
            default:
                persona = null;
                break;
        }

        // Asignar el objeto Persona a loggedUser en la Clinica.
        Clinica.getInstance().loggedUser = persona;
    }
    
    

    public static boolean existUserNameSQL(String userName) {
       
    	boolean exists = false;
        
    	String query = "SELECT 1 " +
        		       "FROM CREDENCIAL " +
        		       "WHERE userName = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { // Si devuelve true significa que hay al menos un registro.
                    exists = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return exists;
    }

    
    
}
