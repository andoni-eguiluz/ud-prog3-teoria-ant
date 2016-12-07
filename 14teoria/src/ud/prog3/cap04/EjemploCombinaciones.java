package ud.prog3.cap04;

import java.util.ArrayList;

public class EjemploCombinaciones {

	public static void main(String[] args) {
		String[] test = { "A", "B", "C", "D", "E" };
		ArrayList<ArrayList<String>> al = calcCombs( test, 4 );
		for (ArrayList<String> a : al) {
			System.out.println( a );
		}
	}

	/** Calcula combinaciones de elementos en el mismo orden en el que están en la secuencia
	 * @param sec	Secuencia original de strings
	 * @param n	Tamaño de las combinaciones que se quieren calcular partiendo de esa secuencia
	 * @return	Lista de las combinaciones calculadas
	 */
	public static ArrayList<ArrayList<String>> calcCombs( String[] sec, int n ) {
		ArrayList<ArrayList<String>> combs = new ArrayList<ArrayList<String>>();
		calcCombs( combs, new ArrayList<String>(), sec, 0, n );
		return combs;
	}
	
	// Concepto recursivo:
	// Dada una secuencia de strings sec = ("a", "b", "c", ...)
	// Las combinaciones de tamaño "n" de elementos de esa secuencia en el mismo orden son:
	// - La lista de secuencias de la secuencia "a" combinada con las combinaciones de tamaño "n-1" de la secuencia ("b", "c", ...)
	// - La lista de secuencias de las combinaciones de tamaño "n" de la secuencia "b", "c", ...)
	@SuppressWarnings("unchecked")
	private static void calcCombs( ArrayList<ArrayList<String>> combs, ArrayList<String> comb, String[] sec, int start, int tamDeseado ) {
		if (comb.size()==tamDeseado) {  // Combinación encontrada
			combs.add( (ArrayList<String>) comb.clone() );
		} else if (start==sec.length) {  // No hay más combinaciones posibles
			return;
		} else if ((sec.length-start) + comb.size() < tamDeseado) {  // No hay suficientes elementos para una comb de tamaño n
			return;
		} else{  // comb.size() < n
			calcCombs( combs, comb, sec, start+1, tamDeseado );  // Probar combinaciones saltando siguiente
			comb = ((ArrayList<String>) comb.clone());
			comb.add( sec[start] );
			calcCombs( combs, comb, sec, start+1, tamDeseado );  // Probar combinaciones incluyendo siguiente
		}
	}

}
