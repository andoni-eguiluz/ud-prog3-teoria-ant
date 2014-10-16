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
	static int dondeEstaRec2( int[] array, int val, 
			int desde, int hasta ) {
		if (desde==hasta) {
			if (array[desde]==val)
				return desde; // Segundo caso base
			else
				return -1; // Primer caso base
		} else {
			int mitad = (desde + hasta) / 2;
			if (val <= array[mitad]) {
				return dondeEstaRec2(array, val, desde, mitad);
			} else {
				return dondeEstaRec2(array, val, mitad+1, hasta);
			}
		}
	}

	/** Ordena recursivamente (mergesort) un array de enteros
	 * @param nums	Array de enteros a ordenar
	 * @param ini	Posición inicial de la ordenación
	 * @param fin	Posición final de la ordenación (inclusive)
	 */
	public static void mergeSort( int[] nums, int ini, int fin ) {
		if (ini>=fin) {
			return;  // Caso base, nada que ordenar
		} else {
			int med = (ini+fin)/2;
			mergeSort(nums,ini,med);
			mergeSort(nums,med+1,fin);
			mezclaMergeSort(nums,ini,med,fin);
		}
	}
		// Algoritmo de mezcla (no recursivo)
		// Mezcla en nums las mitades ya ordenadas (ini1 a fin1) con (fin1+1 a fin2)
		private static void mezclaMergeSort( int[] nums, int ini1, int fin1, int fin2 ) {
			int initotal = ini1; // Guardamos el inicio
			int ini2 = fin1+1; // Inicio segunda mitad
			// Mezclar las dos mitades. Primero llevarlas mezcladas a un array intermedio:
			int[] destino = new int[fin1-ini1+fin2-ini2+2];
			int posDest = 0;
			int posEnCurso = -1;
			while (ini1<=fin1 || ini2<=fin2) {  // Van subiendo ini1 e ini2 hasta acabar (fin1 y fin2)
				// Hay que comparar ini1 con ini2
				boolean menorEsIni1 = true;  // Suponemos en principio que es <= ini1 
				if (ini1>fin1) // No hay ya elementos en la primera mitad
					menorEsIni1 = false; // En este caso no lo es
				else if (ini2<=fin2 && nums[ini1]>nums[ini2])
					menorEsIni1 = false;  // En este caso tampoco
				if (menorEsIni1) { // Si es menor el de la mitad 1 se lleva de 1
					destino[posDest] = nums[ini1];
					ini1++;
				} else {  // Si es menor el de la mitad 2 se lleva de 2
					destino[posDest] = nums[ini2];
					ini2++;
				}
				posDest++;
			}
			// Copiar el array intermedio a la listaOriginal
			posDest = 0;
			for( int i=initotal; i<=fin2; i++ ) {
				nums[i] = destino[posDest];
				posDest++;
			}
		}
	
	
	public static void main(String[] args) {
		/*
		// Tests iniciales
		f(5);
		System.out.println( factorial(7) );
		
		// Test de visualización recursiva de array de strings
		ArrayList<String> ls = new ArrayList<>();
		ls.add( "a" );
		ls.add( "b" );
		ls.add( "c" );
		sacaLista( ls, 0 );
		*/
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
		System.out.println( dondeEstaRec2(v, 72, 0, v.length-1) );
		
		// Test de ordenación por mezcla (array de enteros)
		Random r = new Random();
		int CUANTOS_NUMS_ALEATORIOS = 1000;
		int [] nums = new int[CUANTOS_NUMS_ALEATORIOS];
		for (int i=0; i<CUANTOS_NUMS_ALEATORIOS; i++) {
			nums[i] = r.nextInt( 100000 );  // Entero entre 0 y 1000000
		}
		System.out.println( "Array sin ordenar:" );
		for (int i=0; i<CUANTOS_NUMS_ALEATORIOS; i++) System.out.print( nums[i] + ", " );
		mergeSort(nums, 0, CUANTOS_NUMS_ALEATORIOS-1);
		System.out.println();
		System.out.println( "Array ya ordenado:" );
		for (int i=0; i<CUANTOS_NUMS_ALEATORIOS; i++) System.out.print( nums[i] + ", " );
	}

}
