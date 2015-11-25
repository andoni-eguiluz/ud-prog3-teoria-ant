package ud.prog3.varios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;

import javax.swing.JOptionPane;

/** Prueba de compilación externa de un fuente Java desde el propio Java
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */
public class CompilacionYCargaDeJavaDesdeJava {
	
	// Configurar esta variable de acuerdo al path de Java de la máquina en particular
	private static String pathJava = "C:/Program Files/Java/jdk1.8.0_66/bin";
	private static String pathSrc = "src";
	private static String pathBin = "bin";
	
	private static void llamarCompilador( String comando ) {
		ProcessBuilder builder = new ProcessBuilder(
		            "cmd.exe", "/c", "cd " + pathSrc
		            	+ " && \"" + pathJava + "/javac\"", "ud/prog3/varios/" + comando );
 	
        builder.redirectErrorStream(true);  // Redirige la salida de error a la consola del proceso
        Process p;
		try {
			p = builder.start();
	        BufferedReader rOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        	// Abre la salida de consola del proceso para leerla desde Java
	        String line;
	        while (true) {
	            line = rOut.readLine();
	            if (line == null) { break; }
	            System.out.println(line);   // Proceso de salida del comando (aquí se saca a consola, pero se podría hacer cualquier otra cosa)
	        }	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	
	private static void llamarInterprete( String comando ) {
		ProcessBuilder builder = new ProcessBuilder(
		            "cmd.exe", "/c", "cd " + pathBin
		            	+ " && \"" + pathJava + "/java\"", comando );
 	
        builder.redirectErrorStream(true);  // Redirige la salida de error a la consola del proceso
        Process p;
		try {
			p = builder.start();
	        BufferedReader rOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        	// Abre la salida de consola del proceso para leerla desde Java
	        String line;
	        while (true) {
	            line = rOut.readLine();
	            if (line == null) { break; }
	            System.out.println(line);   // Proceso de salida del comando (aquí se saca a consola, pero se podría hacer cualquier otra cosa)
	        }	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
		String fichJava = JOptionPane.showInputDialog(null, "Introduce nombre de fichero a ejecutar: ", "PruebaCarga.java" );
		System.out.println( fichJava );
		llamarCompilador( fichJava );
		String fichJavaCompilado = JOptionPane.showInputDialog(null, "Introduce nombre de fichero a ejecutar: ", "ud.prog3.varios.PruebaCarga2" );
		System.out.println( fichJavaCompilado );
		llamarInterprete( fichJavaCompilado );
	}

}

