package ud.prog3.cap05;

/** Clase para medici�n simplificada de tiempo de ejecuci�n y espacio de memoria utilizado 
 * @author Andoni Egu�luz Mor�n
 * Facultad de Ingenier�a - Universidad de Deusto
 */
public class AnalisisEjecucion {
	private static Runtime runtime = Runtime.getRuntime();  // Sistema de ejecuci�n para calcular la memoria libre
	private static long memRecienUsada = -1;
	private static long memLibre = -1;
	private static long tiempoRecienUsado = -1;
	private static long ultimoTiempo = -1;
	private static final long TIEMPO_SLEEP = 100;
	
	/** Calcula la memoria de heap aproximada utilizada desde la �ltima vez
	 * que se llam� a este mismo m�todo.<p>
	 */
	private static void calcMemAprox() {
		for (int i=0; i<1; i++) {   // Por si se quisiera repetir la "limpieza" de memoria
			try {
				System.gc();
				Thread.sleep(TIEMPO_SLEEP);
				System.runFinalization();
				System.gc();
				Thread.sleep(TIEMPO_SLEEP);
			} catch (InterruptedException e) {}
		}
			// Llama varias veces al garbage collector para que la JVM se quede lo m�s limpia posible
			// No es exacto y depende de la implementaci�n de la JVM. Tampoco es una manera adecuada
			// de funcionar, salvo para pruebas, como estamos haciendo aqu�.
		if (memLibre != -1)
			memRecienUsada = memLibre-runtime.freeMemory();
		memLibre = runtime.freeMemory();
	}
	
	/** Visualiza la memoria reci�n usada, el mensaje y la memoria libre
	 * @param mens	Mensaje a visualizar en consola System.out
	 * 				Si es null se calculan las memorias relativas pero no se visualizan.
	 * @param visuSiempre	Si true, saca siempre el mensaje. Si false, solo
	 * 						lo saca si se ha usado algo de memoria desde la �ltima llamada.
	 * @param separa	Si true, separa una l�nea este mensaje
	 */
	public static void visuMem( String mens, boolean visuSiempre, boolean separa ) {
		if (separa) System.out.println();
		visuMem( mens, visuSiempre );
	}
	
	/** Visualiza la memoria reci�n usada, el mensaje y la memoria libre
	 * @param mens	Mensaje a visualizar en consola System.out
	 * 				Si es null se calculan las memorias relativas pero no se visualizan.
	 * @param visuSiempre	Si true, saca siempre el mensaje. Si false, solo
	 * 						lo saca si se ha usado algo de memoria desde la �ltima llamada.
	 */
	public static void visuMem( String mens, boolean visuSiempre ) {
		calcMemAprox();
		if (mens==null) return;
		if (visuSiempre || memRecienUsada!=0) {
			if (memRecienUsada != -1) {
				System.out.print( "[" + String.format( "%1$,1d", memRecienUsada ) + " bytes] " );
			}
			System.out.println( mens );
			// Sacar la memoria total libre:
			// System.out.println( String.format( " - %1$,1d bytes libres", memLibre ) );
		}
	}
	
	/** Visualiza el tiempo usado desde la llamada anterior en System.out
	 * @param mens	Mensaje a visualizar en consola System.out, seguido del tiempo.
	 * 				Si es null se calculan los tiempos relativos pero no se visualizan.
	 * @param separa	Si true, separa una l�nea
	 */
	public static void visuTiempo( String mens, boolean separa ) {
		if (separa) System.out.println();
		visuTiempo(mens);
	}
	
	/** Visualiza el tiempo usado desde la llamada anterior en System.out
	 * @param mens	Mensaje a visualizar en consola System.out, seguido del tiempo.
	 * 				Si es null se calculan los tiempos relativos pero no se visualizan.
	 */
	public static void visuTiempo( String mens ) {
		if (mens!=null) {
			if (ultimoTiempo != -1) {
				tiempoRecienUsado = System.currentTimeMillis() - ultimoTiempo;
				if (mens!=null) System.out.print( "[" + String.format( "%1$,1d", tiempoRecienUsado ) + " msgs.] " );
			}
			System.out.println( mens );
			// Sacar el tiempo absoluto del sistema:
			// System.out.println( String.format( "    (%1$,1d msgs)", System.currentTimeMillis() ) );
		}
		ultimoTiempo = System.currentTimeMillis();
	}
	
}
