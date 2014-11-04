package ud.prog3.cap06.pr0506resuelta;

/** Prueba combinada de distintas estructuras de datos y distintos tamaños
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */
public class PruebaEstructurasPersona {

	public static void main(String[] args) {
		String[] pruebas = { "ArrayList", "LinkedList", "HashSet", "TreeSet" };
		ProcesoProbable[] procs = new ProcesoProbable[4];
		procs[0] = new AccesoAPersonasAL();
		procs[1] = new AccesoAPersonasLL();
		procs[2] = new AccesoAPersonasHS();
		procs[3] = new AccesoAPersonasTS();
		int tamanyo= 100;
		while (tamanyo <= 100000) {
			for (int prueba=0; prueba<4; prueba++) {
				long tiempo = BancoDePruebas.realizaTest( procs[prueba], tamanyo );
				int espacio = BancoDePruebas.getTamanyoTest( procs[prueba] );
				System.out.println( "Prueba " + pruebas[prueba] + " de " + tamanyo + " -- tiempo: " + tiempo + " msgs. / espacio = " + espacio + " bytes.");
			}
			tamanyo *= 10;
		}
	}

}
