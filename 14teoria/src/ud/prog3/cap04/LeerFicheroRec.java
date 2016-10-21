package ud.prog3.cap04;

import java.io.*;

public class LeerFicheroRec {

	private static void leerRecursoRec( BufferedReader br ) throws IOException {
		String linea = br.readLine();
		if (linea!=null) {
			if (!linea.isEmpty()) System.out.println( linea );
			leerRecursoRec( br );
		}
	}
	@SuppressWarnings("unused")
	private static void leerRecursoNormal( BufferedReader br ) throws IOException {
		String linea = br.readLine();
		while (linea!=null) {
			if (!linea.isEmpty()) System.out.println( linea );
			linea = br.readLine();
		}
	}
	private static void leerRecurso( String nombreRecurso ) {
		try {
			BufferedReader br = new BufferedReader( new InputStreamReader( 
				LeerFicheroRec.class.getResourceAsStream( nombreRecurso ) ) );
			leerRecursoRec( br );
			// leerRecursoNormal( br );  // Esto sería en interativo
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		leerRecurso( "res/verbosRegulares.txt" );
	}

}
