package ud.prog3.cap01;

/** Utilidades de Strings de ejemplo
 * Clase preparada para hacer con ella pruebas unitarias con JUnit (con errores que hay que corregir)
 * @author Andoni Egu�luz Mor�n
 * Facultad de Ingenier�a - Universidad de Deusto
 */
public class EjemploUtilidadesStringParaJUnit_Original {

	/** Devuelve cualquier string sin saltos de l�nea ni tabuladores (para poder exportarlo de forma tabular sin conflicto)
	 * @param s	String con cualquier contenido
	 * @return	Mismo string sustituyendo \t con el car�cter | y \n con el car�cter #; Devuelve null si s es null.
	 */
	public static String quitarTabsYSaltosLinea( String s ) {
		if (s==null) return null;
		return s.replaceAll( "\n", "#" ).replaceAll( "\t", "#" );
	}
	
	/** Devuelve cualquier string truncado al n�mero de caracteres indicado, con puntos suspensivos al final si se ha truncado
	 * @param s	String con cualquier contenido o null
	 * @param largo	N�mero m�ximo de caracteres de longitud
	 * @return	String recortado si ocupaba m�s de largo caracteres con tres puntos suspensivos al final; mismo string en caso contrario; null si s es null.
	 * @throws IndexOutOfBoundsException	Si largo es negativo
	 */
	public static String wrapString( String s, int largo ) throws IndexOutOfBoundsException {
		if (s==null) 
			return null;
		else if (s.length()>largo)
			return s.substring(0, largo) + "...";
		else
			return s;
	}
	
	public static void main(String[] args) {
		// Prueba convencional (no estructurada, no exhaustiva, no automatizable)
		String prueba = "Hola\nEsto es un string con tres l�neas\ny\tvarios\ttabuladores.";
		System.out.println( prueba );
		System.out.println( quitarTabsYSaltosLinea( prueba ));
		System.out.println( wrapString( prueba, 3 ) );
		System.out.println( wrapString( prueba, 10 ) );
		System.out.println( wrapString( quitarTabsYSaltosLinea(prueba), 10 ) );
	}

}
