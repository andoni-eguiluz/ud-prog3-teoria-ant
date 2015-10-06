package ud.prog3.cap04;

import java.util.Random;

public class MergeSortResuelto {

	/** Ordena recursivamente (mergesort) un array de enteros
	 * @param nums	Array de enteros a ordenar
	 * @param ini	Posición inicial de la ordenación
	 * @param fin	Posición final de la ordenación (inclusive)
	 */
	public static void mergeSort( int[] nums, int ini, int fin ) {
		System.out.println( "Entrando en merge: " + ini + "," + fin );
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
