package ud.prog3.cap06.pr0506resuelta;

import java.util.HashSet;

public class AccesoAPersonasHS implements ProcesoProbable {

	private HashSet<Persona> l;
	
	@Override
	public void init(int tamanyoTest) {
		l = new HashSet<>();
		for (int i=0; i<tamanyoTest; i++) {
			l.add( new Persona( i*2+1, null, null )); //"Nombre " + i, "Apellido " + i ));
		}
	}

	public int cont;  // Se hace el contador atributo para que la actualizaci�n del contador del test no pueda ser optimizada (y eliminada) por el compilador
	@Override
	public Object test() {
		cont = 0;
		for (int i=0; i<l.size(); i++) {
			if (l.contains( new Persona(i,"","") )) cont++;
		}
		// System.out.println( "N�mero personas encontradas: " + cont );
		return l;
	}

	/** M�todo de prueba de la clase
	 * @param args
	 */
	public static void main(String[] args) {
		AccesoAPersonasHS proc = new AccesoAPersonasHS();
		long tiempo = BancoDePruebas.realizaTest( proc, 500000 );
		int espacio = BancoDePruebas.getTamanyoTest();
		System.out.println( "Prueba HashSet de 50000 -- tiempo: " + tiempo + " msgs. / espacio = " + espacio + " bytes.");
	}

}
