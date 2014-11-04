package ud.prog3.cap06.pr0506resuelta;

import java.util.TreeSet;

public class AccesoAPersonasTS implements ProcesoProbable {

	private TreeSet<Persona> l;
	
	@Override
	public void init(int tamanyoTest) {
		l = new TreeSet<>();
		for (int i=0; i<tamanyoTest; i++) {
			l.add( new Persona( i*2+1, "Nombre " + i, "Apellido " + i ));
		}
	}

	@Override
	public Object test() {
		int cont = 0;
		for (int i=0; i<l.size(); i++) {
			if (l.contains( new Persona(i,"","") )) cont++;
		}
		System.out.println( "Número personas encontradas: " + cont );
		return l;
	}

	/** Método de prueba de la clase
	 * @param args
	 */
	public static void main(String[] args) {
		AccesoAPersonasTS proc = new AccesoAPersonasTS();
		long tiempo = BancoDePruebas.realizaTest( proc, 500000 );
		int espacio = BancoDePruebas.getTamanyoTest( proc );
		System.out.println( "Prueba TreeSet de 50000 -- tiempo: " + tiempo + " msgs. / espacio = " + espacio + " bytes.");
	}

}
