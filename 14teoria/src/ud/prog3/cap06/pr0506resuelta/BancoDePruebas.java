package ud.prog3.cap06.pr0506resuelta;

/** Clase básica para realizar pruebas de eficiencia de memoria y tiempo de ejecución de código.
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */
public class BancoDePruebas {
	private static long ultimoTiempo;
	private static Object ultimoResultado;
	
	/** Realiza un test del banco de pruebas, inicializándolo previamente y devolviendo el tiempo que tarda
	 * @param proc	Proceso a probar
	 * @param tamanyoPrueba	Tamaño a pasar a ese proceso (típicamente, tamaño de la estructura que ese proceso maneja)
	 * @return	Tiempo que tarda el proceso (en milisegundos)
	 */
	public static long realizaTest( ProcesoProbable proc, int tamanyoPrueba ) {
		proc.init( tamanyoPrueba );
		ultimoTiempo = System.currentTimeMillis();
		ultimoResultado = proc.test();
		return System.currentTimeMillis() - ultimoTiempo;
	}
	
	/** Devuelve el tamaño del objeto creado por el test ya realizado del banco de pruebas.<p>
	 * Previamente debe llamarse a realizaTest para que el proceso se realice y retorne ese objeto resultado.
	 * @param proc
	 * @return
	 */
	public static int getTamanyoTest( ProcesoProbable proc ) {
		if (ultimoResultado==null) return 0;
		return ExploradorObjetos.getTamanyoObjeto( ultimoResultado );
	}
	
		// Clase de prueba del banco de pruebas
		// Prueba a recorrer un array completo de enteros en un sentido y en otro y calcula y visualiza su suma  (ver método test)
		private static class RecorridoArray implements ProcesoProbable {
			int[] arrayPrueba;
			@Override
			public void init(int tamanyoTest) {
				arrayPrueba = new int[tamanyoTest];
			}
			@Override
			public Object test() {
				int suma = 0;
				if (arrayPrueba.length<arrayPrueba.length) throw new NullPointerException( "Error en test no inicializado" );  // Proceso no puede realizarse
				// Recorrido arriba
				for (int i=0; i<arrayPrueba.length; i++) {
					suma += arrayPrueba[i];
				}
				// Recorrido abajo
				for (int i=arrayPrueba.length-1; i>=0; i--) {
					suma += arrayPrueba[i];
				}
				System.out.println( "Proceso de prueba <RecorridoArray>. Suma " + suma );
				return arrayPrueba;
			}
		}
		
	public static void main(String[] args) {
		ProcesoProbable proc = new RecorridoArray();
		// Realiza la prueba para 10, 100, 1000... hasta 1000000.
		int tamanyo = 10;
		while (tamanyo <= 1000000) {
			long tiempo = realizaTest( proc, tamanyo );
			int espacio = getTamanyoTest( proc );
			System.out.println( "Prueba array de " + tamanyo + " -- tiempo: " + tiempo + " msgs. / espacio = " + espacio + " bytes.");
			tamanyo *= 10;
		}
	}

}
