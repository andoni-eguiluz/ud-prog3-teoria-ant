package ud.prog3.cap01;

/** Utilidades de Strings de ejemplo
 * Clase preparada para hacer con ella pruebas unitarias con JUnit
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */
public class EjemploUtilidadesStringParaJUnit {

	/** Devuelve cualquier string sin saltos de línea ni tabuladores (para poder exportarlo de forma tabular sin conflicto)
	 * @param s	String con cualquier contenido
	 * @return	Mismo string sustituyendo \t con el carácter | y \n con el carácter #; Devuelve null si s es null.
	 */
	public static String quitarTabsYSaltosLinea( String s ) {
		if (s==null) return null;
		return s.replaceAll( "\n", "#" ).replaceAll( "\t", "|" );
	}
	
	/** Devuelve cualquier string truncado al número de caracteres indicado, con puntos suspensivos al final si se ha truncado
	 * @param s	String con cualquier contenido o null
	 * @param largo	Número máximo de caracteres de longitud
	 * @return	String recortado si ocupaba más de largo caracteres con tres puntos suspensivos al final; mismo string en caso contrario; null si s es null.
	 */
	public static String wrapString( String s, int largo ) {
		if (s==null) 
			return null;
		else if (s.length()>largo)
			return s.substring(0, largo) + "...";
		else
			return s;
	}
	
	public static void main(String[] args) {
		// Prueba convencional (no estructurada, no exhaustiva, no automatizable)
		String prueba = "Hola\nEsto es un string con tres líneas\ny\tvarios\ttabuladores.";
		System.out.println( prueba );
		System.out.println( quitarTabsYSaltosLinea( prueba ));
		System.out.println( wrapString( prueba, 3 ) );
		System.out.println( wrapString( prueba, 10 ) );
		System.out.println( wrapString( quitarTabsYSaltosLinea(prueba), 10 ) );
	}

}
