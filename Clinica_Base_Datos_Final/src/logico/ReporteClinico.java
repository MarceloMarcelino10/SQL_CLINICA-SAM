package logico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReporteClinico {
	
	private StringBuilder contenido;

    public ReporteClinico() {
        this.contenido = new StringBuilder();
        try {
            generarReporte();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StringBuilder getContenido() {
        return contenido;
    }
    
    public void guardarReporteEnArchivo(String path) {
        guardarEnArchivo(path, contenido.toString());
        System.out.println("Reporte clínico generado en: " + path + "\n");
        imprimirReporteEnConsola(path);
    }

    private void generarReporte() throws IOException {
    	
        if (contenido ==  null) {
        	contenido = new StringBuilder();
        }

        agregarLineaHorizontal(contenido);
        agregarTituloCentrado(contenido, "Reporte Clínico");
        agregarLineaHorizontal(contenido);

        agregarTextoNormal(contenido, "Fecha: " + obtenerFechaActual() + "\n\n");

        agregarTituloCentrado(contenido, "Información General:\n\n");
        
        agregarTextoNormal(contenido, "Viviendas:\n");
        informacionVivienda(contenido);
        
        agregarTextoNormal(contenido, "\nPersonas:\n");
        informacioPaciente(contenido);
        
        agregarTextoNormal(contenido, "\nEnfermedades:\n");
        informacionEnfermedad(contenido);
        
        agregarTextoNormal(contenido, "\nCitas:\n");
        informacioDoctor(contenido);
        
        agregarTextoNormal(contenido, "\nVacunas:\n");
        informacioVacuna(contenido);
        
        agregarTextoNormal(contenido, "\nConsultas:\n");
        informacioConsultas(contenido);
        
        agregarTituloCentrado(contenido, "¡Gracias por Preferirnos!");
        
        agregarLineaHorizontal(contenido);
    }

	private static void agregarTituloCentrado(StringBuilder contenido, String titulo) {
        int espacioEnBlanco = (80 - titulo.length()) / 2;
        agregarTextoConEspacios(contenido, titulo, espacioEnBlanco);
    }

    private static void agregarLineaHorizontal(StringBuilder contenido) {
        for (int i = 0; i < 82; i++) {
            contenido.append("=");
        }
        contenido.append("\n");
    }

    private static void agregarTextoNormal(StringBuilder contenido, String texto) {
        contenido.append(texto);
    }

    private static void agregarTextoConEspacios(StringBuilder contenido, String texto, int espacioEnBlanco) {
        agregarEspacios(contenido, espacioEnBlanco);
        contenido.append(texto);
        agregarEspacios(contenido, espacioEnBlanco);
        contenido.append("\n");
    }

    private static void agregarEspacios(StringBuilder contenido, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            contenido.append(" ");
        }
    }

    private static String obtenerFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(new Date());
    }

    private static void guardarEnArchivo(String path, String contenido) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(contenido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void informacionVivienda(StringBuilder contenido) {
        String infoViviendas = Clinica.getInstance().obtenerViviendasMasPobladas();
        agregarTextoNormal(contenido, infoViviendas);
    }
    
    private static void informacioPaciente(StringBuilder contenido) {
    	String infoEdad = Clinica.getInstance().calcularPromedioEdadPersonas();
    	String infoSexo = Clinica.getInstance().calcularPromedioSexoPersoanas();
    	String infoTotal = Clinica.getInstance().obtenerTotalPersonasAtendidas();
    	
    	agregarTextoNormal(contenido, infoEdad);
    	agregarTextoNormal(contenido, infoTotal);
    	agregarTextoNormal(contenido, infoSexo);
    	
    }
    
    private static void informacionEnfermedad(StringBuilder contenido) {
    	String infoEnRe = Clinica.getInstance().obtenerEnfermedadesMasRecurrentes();
    	String infoGrave = Clinica.getInstance().obtenerEnfermedadesMasGraves();
    	
    	agregarTextoNormal(contenido, infoEnRe);
    	agregarTextoNormal(contenido, infoGrave);
    }
    
    private static void informacioDoctor(StringBuilder contenido) {
    	String infoMasCitas = Clinica.getInstance().obtenerDoctorConMasCitas();
    	String infoReaCitas = Clinica.getInstance().obtenerTotalCitasRealizadas();
    	String infoSexo = Clinica.getInstance().calcularPromedioSexoDoctores();
    	
    	agregarTextoNormal(contenido, infoMasCitas);
    	agregarTextoNormal(contenido, infoReaCitas);
    	agregarTextoNormal(contenido, infoSexo);
    }
    
    private static void informacioVacuna(StringBuilder contenido) {
    	String infoMasCurativa = Clinica.getInstance().obtenerVacunaMasCurativa();
    	String infoCantV =  (Clinica.getInstance().getMisVacunas().isEmpty()) ? "Información no disponible.\n" : "Cantidad de vacunas creadas: " + Clinica.getInstance().getMisVacunas().size() + "\n";
    	
    	agregarTextoNormal(contenido, infoMasCurativa);
    	agregarTextoNormal(contenido, infoCantV);
    }
    
    private static void informacioConsultas(StringBuilder contenido) {
    	String infoCantC = (Clinica.getInstance().getMisVacunas().isEmpty()) ? "Información no disponible.\n" : "Cantidad de consltas realizadas: " + Clinica.getInstance().getMisConsultas().size() + "\n\n";
    	agregarTextoNormal(contenido, infoCantC);
	}
    
    private static void imprimirReporteEnConsola(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
