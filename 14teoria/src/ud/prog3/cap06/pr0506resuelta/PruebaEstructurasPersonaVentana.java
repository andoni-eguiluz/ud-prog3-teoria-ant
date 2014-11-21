package ud.prog3.cap06.pr0506resuelta;

import java.util.ArrayList;
import ud.prog3.cap06.pr0506resuelta.Utils;
import ud.prog3.cap06.pr0506resuelta.gui.VentanaBancoDePruebas;

/** Prueba combinada de distintas estructuras de datos y distintos tamaños
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */
public class PruebaEstructurasPersonaVentana {

	public static void main(String[] args) {
		Utils.muestraThreadsActivos();
		String[] pruebas = { "ArrayList", "LinkedList", "HashSet", "TreeSet" };
		ArrayList<ProcesoProbable> procs = new ArrayList<ProcesoProbable>();
		procs.add( new AccesoAPersonasAL() );
		procs.add( new AccesoAPersonasLL() );
		procs.add( new AccesoAPersonasHS() );
		procs.add( new AccesoAPersonasTS() );
		VentanaBancoDePruebas vent = new VentanaBancoDePruebas();
		vent.setProcesos( pruebas, procs );
		vent.setVisible( true );
		Utils.muestraThreadsActivos();
	}

}
