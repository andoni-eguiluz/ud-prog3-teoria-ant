package ud.prog3.cap04;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PruebasRecursividadBilbao {

	private static int numLls = 0;
	public static void f( int i ) {
		numLls++;
		System.out.println( i + " llamada " + numLls );
		if (i>0)
			f( i-1 );
	}
	
	public static int producto( int op1, int op2 ) {
		if (op2==0)
			return 0;
		else
			return op1 + producto(op1,op2-1);
	}
	
	public static int factorial(int i) {
		System.out.println( "Entrando en factorial de " + i );
		if (i==0) 
			return 1;
		else 
			return i * factorial(i-1);
	}
	
	private static void sacaLista( ArrayList<String> al, 
			int ind ) {
		if (ind==al.size()) {
			// Nada que hacer (caso base)
		} else {  // caso recursivo
			sacaLista( al, ind+1 );             //2
			System.out.println( al.get(ind) );  //1
		}
	} 

	private static void leeFichero( BufferedReader br ) {
		String linea;
		try {
			linea = br.readLine();
			if (linea==null) {
				// Caso base
			} else {
				leeFichero( br );
				System.out.println( linea );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

	static int busquedaBinaria( int[] array, int val, int desde, int hasta ) {
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
		// Ejemplo 1
		int i = 5000;
		f(i);
		//
		// Ejemplo 2: producto
		int op1 = 5;
		int op2 = 4;
		System.out.println( producto(5,4) );
		// Ejemplo 3: factorial
		System.out.println( factorial(7) );
		// Ejemplo 4: AL de Strings
		ArrayList<String> miALS = new ArrayList<>();
		miALS.add( "primero" );
		miALS.add( "2" );
		miALS.add( "3º" );
		sacaLista( miALS, 0 );
		//Ejemplo 5: Leer fichero de texto al derecho y al revés
		try {
			BufferedReader br = new BufferedReader( new
				InputStreamReader( new FileInputStream( 
						"prueba.txt") ) );
			leeFichero( br );
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		System.out.println( factorial(7) );

		// Test de búsqueda binaria en array de enteros
		int[] v = { 1, 10, 15, 25, 30, 43, 58, 72, 110 };
		System.out.println( busquedaBinaria(v, 72, 0, v.length-1) );
		
	}
	
	
}
