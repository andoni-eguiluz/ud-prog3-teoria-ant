package ud.prog3.cap04;

// Basado en el algoritmo de Heron de Alejandr�a:
// Para calcular ra�z cuadrada de x
// - Haz un intento: G
// - Mejora el intento calculando la media de G y X/G
// - Sigue mejorando el intento hasta que sea suficientemente bueno
//
// Redefinici�n recursiva:
// La ra�z cuadrada de x es la ra�z cuadrada de x con el intento g  (1 vez)
// La ra�z cuadrada de x con el intento g es:  (recursivo)
// - el intento si est� suficientemente cerca (g*g casi== x) 
// - la ra�z cuadrada de x con el intento (g + x/g)/2

public class RaizCuadrada {

	public static double raizCuadradaDe( double x ) {
		numLlamadas = 0; // Para informaci�n interna
		return raizCuadradaDe( x, 1 );
	}
	private static int numLlamadas; // Para informaci�n interna
	public static double raizCuadradaDe( double x, double intento ) {
		numLlamadas++; // Para informaci�n interna
		System.out.println( "   Llamada " + numLlamadas + " - intento " + x ); // Para informaci�n interna
		System.out.println( "       Dif = " + Math.abs(intento*intento - x) );
		if ( Math.abs(intento*intento - x) < 0.000000000001 )  // Caso base: el intento es suficientemente bueno
			return intento;
		else {
			return raizCuadradaDe( x, (intento + x/intento)/2 );  // (1)
		}
	}
	
	public static void main(String[] args) {
		System.out.println( raizCuadradaDe( 2510 ) );
		System.out.println( raizCuadradaDe( 1600 ) );
		System.out.println( raizCuadradaDe( 1000000 ) );
		System.out.println( raizCuadradaDe( 29330957451.24895 ));  // Ojo: por qu� el error?   Precisi�n de double
		// System.out.println( raizCuadradaDe_v2( 29330957451.24895 ) );  // Soluci�n a la aproximaci�n recursiva de doubles
	}

	
	// Soluciona el error cambiar (1) por (2):
	public static double raizCuadradaDe_v2( double x ) {
		numLlamadas = 0; // Para informaci�n interna
		return raizCuadradaDe_v2( x, 1 );
	}
	public static double raizCuadradaDe_v2( double x, double intento ) {
		numLlamadas++; // Para informaci�n interna
		System.out.println( "   Llamada " + numLlamadas + " - intento " + intento ); // Para informaci�n interna
		if ( Math.abs(intento*intento - x) < 0.000001 )  // Caso base: el intento es suficientemente bueno
			return intento;
		else {
			// (2)
			double nuevoIntento = (intento + x/intento)/2;
			if (nuevoIntento==intento) return intento;  // Si no hay progresi�n es que hemos llegado a la precisi�n m�xima del double
			return raizCuadradaDe_v2( x, nuevoIntento );
		}
	}
	
	// (3) 
	// Otra soluci�n es limitar "a mano" la profundidad de llamada, por ejemplo si numLlamadas == 100 caso base, devolver lo que haya
	
}
