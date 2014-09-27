package varios;

import java.io.*;
import java.util.*;

public class CosasDeSystem {
	
		/* Prueba de los tres Streams por defecto que tiene System:
		 * System.in - entrada: asignado por defecto al teclado
		 * System.out - salida: asignado por defecto a la consola
		 * System.err - error: asignado por defecto a la misma consola 
		 *    (en Eclipse aparece en color rojo en lugar de en negro)
		 */
		private static void losStreamsDeSystem() {
			// Lectura de un String de teclado y salida a la consola:
			BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
			try {
				System.out.print( "Prueba de teclado (System.in). Introduce cualquier cosa por teclado: ");
				String inputLine = lector.readLine();
				System.out.println( "Se ha leído del teclado: " + inputLine );
			} catch (IOException e) {
				// Error si hay cualquier problema de E/S  (por ejemplo, el teclado está estropeado o desconectado)
				e.printStackTrace();
			}
			// Prueba de error a la consola (System.err)
			try {
				int divisorErroneo = 0;
				System.out.println( 4 / divisorErroneo );
			} catch (ArithmeticException e) {
				System.err.println( "Se ha producido un error:" );
				e.printStackTrace( System.err );
			}
		}

		/* Prueba del método arraycopy que copia elementos en bloque de un array a otro
		 */
		private static void copiaEntreArrays() {
			// Crea y visualiza el array origen
			final int NUM_ELEMENTOS = 40;
			int[] aOrigen = new int[NUM_ELEMENTOS];
			for(int i = 0; i < NUM_ELEMENTOS; ++i) aOrigen[i] = i + 1;
			System.out.print( "Array original: [ ");
			for(int i = 0; i < NUM_ELEMENTOS; ++i) System.out.print(aOrigen[i] + " ");
			System.out.println( "]" );
			// Copia un trozo del array fuente al destino
			int tamDestino = aOrigen.length / 2;
			int[] aDestino = new int[tamDestino];
			// arraycopy( array origen, índice inicial de copia, array destino,
			//            índice inicial al que se copia, número de elementos que se copian )
			System.arraycopy( aOrigen, 9, aDestino, 0, tamDestino );
			// Visualiza el array destino
			System.out.print( "Array destino: [ " );
			for(int i = 0; i < tamDestino; ++i) System.out.print( aDestino[i] + " " );
			System.out.println( "]" );		
		}
		
		/* System tiene una instancia privada de la clase Properties, que se usa para 
		 * mantener la configuración del entorno de trabajo actual.
		 */
		private static void propiedadesDelSistema() {
			System.out.println("Propiedad: Versión de Java: " + System.getProperty("java.version"));
			System.out.println("Propiedad: Versión de SO: " + System.getProperty("os.version"));
			System.out.println("Propiedad: Directorio raíz del usuario: " + System.getProperty("user.home"));		
			System.out.println("Propiedad: Directorio en curso: " + System.getProperty("user.dir"));		
			System.out.println("Todas las propiedades:" );
			// Saca todos los nombres de propiedades, las ordena alfabéticamente...
			ArrayList<Object> claves = new ArrayList<>();
			claves.addAll( System.getProperties().keySet() );
			claves.sort( new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return o1.toString().compareTo(o2.toString());
				}
			});
			// ...y visualizamos las propiedades y sus valores:
			for (Object key : claves) {
				System.out.println( "   Propiedad " + key + " --> " + System.getProperty( key.toString() ));
			}
		}
		
		/* System tiene acceso a las variables de entorno del Sistema Operativo.
		 */
		private static void variablesDeEntorno() {
			// Sacamos el mapa de variables de entorno
			Map<String, String> vars = System.getenv();
			// Sacamos sus claves y las ordenamos
			ArrayList<String> variables = new ArrayList<>( vars.keySet() );
			variables.sort( new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						return o1.compareToIgnoreCase(o2);
					}
			});
			// Y a visualizar
			System.out.println( "Variables de entorno:");
			for(String key: variables)
				System.out.println("   Variable: " + key + " --> " + vars.get(key));			
		}
		
	public static void main(String[] args) {
		losStreamsDeSystem();
		copiaEntreArrays();
		propiedadesDelSistema();
		variablesDeEntorno();
	}

}
