package ud.prog3.cap04;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class PruebasRecursividadDonosti {
	private static int numLlamadas = 0;
	/** Método que devuelve la suma de todos los valores
	 * desde su parámetro hasta 0
	 * @param i	Valor actual
	 * @return	La suma de i + (i-1) + ... + 0
	 */
	private static int f(int i) {
		if (i==0) {
			return 0;
		} else {
			numLlamadas++;
			System.out.println( numLlamadas + " i = " + i);
			return i + f(i-1);
		}
	}
	
	private static int factorial(int i) {
		System.out.println( "Entrando en factorial con valor " + i);
		if (i==0) {
			return 1;
		} else {
			return i * factorial(i-1);
		}
	}
	
	static void sacaLista( ArrayList<String> al, int ind ) {
		if (ind==al.size()) {
			// Nada que hacer (caso base)
		} else {  // caso recursivo
			// LEER
			// ESCRIBIR
			sacaLista( al, ind+1 );   //2
			System.out.println( al.get(ind) );      //1
		}
	} 

	static void sacaLineaAlReves( String lin, int pos ) {
		if (pos==lin.length()) {
			// caso base
		} else {
			char c = lin.charAt(pos);
			sacaLineaAlReves( lin, pos+1 );
			System.out.print(c);
		}
	}
	
	static void leeFichero( BufferedReader br ) {
		try {
			String lin = br.readLine();
			if (lin==null) {
				// Nada que hacer
			} else {
				// ANTES
				leeFichero( br ); // REC
				sacaLineaAlReves( lin, 0 ); // DESPUES
				System.out.print("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/** Devuelve la posición de un elemento dado del array.
	 * El array debe estar ordenado en ascendente.
	 * @param array	Array a recorrer
	 * @param val	Valor a buscar en el array
	 * @return	Posición donde está val, o -1 si no está
	 */
	static int dondeEsta( int[] array, int val ) {
		for (int i=0; i<array.length; i++) {
			if (array[i]==val)
				return i;
		}
		return -1;
	}

	static int dondeEstaRec( int[] array, int val, int i ) {
		//for (int i=0; i<array.length; i++) {
		if (i==array.length) {
			// Caso base
			return -1;
		} else {
			if (array[i]==val) {
				// Segundo caso base: encontrado
				return i;
			} else {
				return dondeEstaRec(array, val, i+1);
			}
		}
	}
	
	// desde y hasta son ambos inclusive
	static int busquedaBinaria( int[] array, int val, 
			int desde, int hasta ) {
		if (desde==hasta) {
			if (array[desde]==val)
				return desde; // Segundo caso base
			else
				return -1; // Primer caso base
		} else {
			int mitad = (desde + hasta) / 2;
			if (val <= array[mitad]) {
				return busquedaBinaria(array, val, desde, mitad);
			} else {
				return busquedaBinaria(array, val, mitad+1, hasta);
			}
		}
	}

	
	public static void main(String[] args) {
		/*
		// Tests iniciales
		f(5);
		System.out.println( factorial(7) );
		*/
		
		// Test de visualización recursiva de array de strings
		ArrayList<String> ls = new ArrayList<>();
		ls.add( "a" );
		ls.add( "b" );
		ls.add( "c" );
		sacaLista( ls, 0 );
		/*
		// Test de lectura recursiva de fichero de texto		
  		long tIni = System.currentTimeMillis();
		try {
			BufferedReader br = new BufferedReader( new
				InputStreamReader( new FileInputStream( 
						"prueba.txt") ) );
			leeFichero( br );
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println( "Tiempo total: " + (System.currentTimeMillis() - tIni) ); 
		 */		
		
		// Test de búsqueda binaria en array de enteros
		int[] v = { 1, 10, 15, 25, 30, 43, 58, 72, 110};
		System.out.println( busquedaBinaria(v, 72, 0, v.length-1) );
		
	}

}
