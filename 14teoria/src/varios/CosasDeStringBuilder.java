package varios;

// La clase String tiene objetos "inmutables". Eso quiere decir que no cambian nunca su valor. Se crean nuevos.
// Para algunas cosas se usa en su lugar la clase StringBuilder, que permite objetos "mutables", strings que sí pueden cambiar.

/** Pruebas de aspectos útiles de la clase StringBuilder y algunas relacionadas
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */
public class CosasDeStringBuilder {
	
	private static void invertirStringBuilder() {
		String s = "String de prueba";
		StringBuilder sb = new StringBuilder(s);
		sb.reverse();
		System.out.println( "String normal: " + s );
		System.out.println( "String invertido: " + sb );
	}
	
	private static void cambiarStringBuilder() {
		StringBuilder sb = new StringBuilder("StringBuilder de prueba");
		System.out.println( sb );
		sb.append( " con añadido." );  // Añade al final
		System.out.println( sb );
		sb.insert( 23, " con insertado y" );  // Inserta por el medio
		System.out.println( sb );
		sb.delete( 23, 51 );  // Borra por el medio
		System.out.println( sb + " (después de borrar un trozo)" );
		sb.replace( 17, 23, "reemplazo" );  // Reemplaza un substring por otro
		System.out.println( sb );
	}
	
	public static void main(String[] args) {
		invertirStringBuilder();
		cambiarStringBuilder();
	}
}
