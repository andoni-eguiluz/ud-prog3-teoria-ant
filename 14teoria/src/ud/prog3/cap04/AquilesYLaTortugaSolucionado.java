package ud.prog3.cap04;

public class AquilesYLaTortugaSolucionado {
	
	public static final double VEL_AQUILES = 10;   // metros / sg
	public static final double VEL_TORTUGA = 0.05; // m/sg (0.05m/sg = 1 metro cada 20 segs) 
	
	public static final double INICIO_AQUILES = 0;    // Aquiles empieza en el metro 0
	public static final double INICIO_TORTUGA = 1000; // La tortuga tiene 1 km de ventaja
	
	/** Devuelve la posición de Aquiles en la carrera, dado el tiempo transcurrido
	 * @param t	Tiempo transcurrido de carrera (en sgs)
	 * @return	Posición de Aquiles (en m)
	 */
	public static double dondeEstaAquiles( double t ) {
		return INICIO_AQUILES + VEL_AQUILES * t;
	}

	/** Devuelve la posición de la tortuga en la carrera, dado el tiempo transcurrido
	 * @param t	Tiempo transcurrido de carrera (en sgs)
	 * @return	Posición de la tortuga (en m)
	 */
	public static double dondeEstaLaTortuga( double t ) {
		return INICIO_TORTUGA + VEL_TORTUGA * t;
	}
	
	// Algoritmo recursivo
	public static double cuandoSeEncuentran() {
		return 0;  // TODO ???
	}
	
	public static void main(String[] args) {
		double t = 100;
		System.out.println( "Ejemplo. Tiempo = " + t + " segundos" );
		System.out.println( " Aquiles está en " + dondeEstaAquiles(t));
		System.out.println( " La tortuga está en " + dondeEstaLaTortuga(t));
		System.out.println( "Solución:" );
		
		try {
			double tSol = cuandoSeEncuentranRes( 0, 1000000 );
			System.out.println( "Tiempo de encuentro = " + tSol );
			System.out.println( "  Distancia de encuentro = " + dondeEstaAquiles(tSol));
		} catch (StackOverflowError e) {
			
		}
		
	}
	
	
	private static int numLlams = 0;
	// Algoritmo recursivo resuelto
	// Pre: en t1 Aquiles no ha alcanzado a la tortuga
	// Pre: en t2 Aquiles ha pasado a la tortuga
	public static double cuandoSeEncuentranRes( double t1, double t2 ) {
		numLlams++; System.out.println( "    Llamada " + numLlams + " (dif. ts = " + (t2-t1) + ")" );
		System.out.println( "       Tiempos llamada: " + t1 + " - " + t2);
		if (t2-t1<=0.00000000000002)
			return t1;
		else {
			double tInt = (t1+t2)/2;
			double seAcerca = dondeEstaLaTortuga(tInt) - dondeEstaAquiles(tInt);
			if (seAcerca>0) // Aún no llega Aquiles
				return cuandoSeEncuentranRes(tInt,t2);
			else // se ha pasado
				return cuandoSeEncuentranRes(t1, tInt);
		}
	}
	
}
	
