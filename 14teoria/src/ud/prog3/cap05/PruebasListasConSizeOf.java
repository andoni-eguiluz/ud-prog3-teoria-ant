package ud.prog3.cap05;

import static ud.prog3.cap05.AnalisisEjecucion.visuMem;
import static ud.prog3.cap05.AnalisisEjecucionConSizeOf.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class PruebasListasConSizeOf {
		
	private static void pruebaEspacioListas( int numPrueba ) {
		if (numPrueba==1 || numPrueba==3) {
			ArrayList<Object> l = new ArrayList<Object>();
			System.out.println( memDeep(l) + " Tras crear ArrayList (tam = " + l.size() + ", cap = " + UtilidadArrayList.getArrayListCapacity(l) + ")" );
			int num = 75;
			long espacio = 0;
			for (int i=0; i<num; i++) {
				int numAdds = 1;
				for (int j=0; j<numAdds; j++) l.add(null);
				if (espacio!=calcMemDeep(l))
					System.out.println( memDeep(l) + " Tras meter " + numAdds + " datos nulos en ArrayList (tam = " + l.size() + ", cap = " + UtilidadArrayList.getArrayListCapacity(l) + ")" );
				espacio = calcMemDeep(l);
			}
		}
		if (numPrueba==2 || numPrueba==3) {
			LinkedList<Object> l2 = new LinkedList<Object>();
			System.out.println( memDeep(l2) + " Tras crear LinkedList (tam = " + l2.size() + ")" );
			int num = 20;
			long espacio = 0;
			for (int i=0; i<num; i++) {
				int numAdds = 1;
				for (int j=0; j<numAdds; j++) l2.add(null);
				if (espacio!=calcMemDeep(l2))
					System.out.println( memDeep(l2) + " Tras meter " + numAdds + " datos nulos en LinkedList (tam = " + l2.size() + ")" );
				espacio = calcMemDeep(l2);
			}
		}
	}

		@SuppressWarnings("unused")
		private static void recorreLista( List<Object> l, int numVeces ) {
			System.out.print( "   Recorriendo lista..." );
			for (int i=0; i<numVeces; i++) {
				for (int j=0; j<l.size(); j++) {
					if (j%10000==0) System.out.print( " " + j );  // Feedback en procesos largos
					Object o = l.get(j);  // Nada, simplemente recorrer al derecho
				}
				for (int j=l.size()-1; j>=0; j--) {
					if (j%10000==0) System.out.print( " " + j );  // Feedback en procesos largos
					Object o = l.get(j);  // Nada, simplemente recorrer al revés
				}
			}
			System.out.println();
		}
		@SuppressWarnings("unused")
		private static void recorreListaConIterador( LinkedList<Object> l, int numVeces ) {
			System.out.print( "   Recorriendo lista..." );
			for (int i=0; i<numVeces; i++) {
				Iterator<Object> itL = l.iterator();
				int rec = 0;
				while (itL.hasNext()) {
					if (rec%10000==0) System.out.print( " " + rec );  // Feedback en procesos largos
					rec++;
					Object o = itL.next();  // Nada, simplemente recorrer al derecho
				}
				ListIterator<Object>itL2 = l.listIterator( l.size() );
				rec = l.size();
				while (itL2.hasPrevious()) {
					if (rec%10000==0) System.out.print( " " + rec );  // Feedback en procesos largos
					rec--;
					Object o = itL2.previous();  // Nada, simplemente recorrer al derecho
				}
			}
			System.out.println();
		}
		private static void cogePosicionesLista( List<Object> l, int numVeces ) {
			System.out.print( "   Cogiendo posiciones lista " + numVeces + " veces... " );
			for (int i=0; i<numVeces; i++) {
				if (i%1000==0) System.out.print( " " + i );
				l.get(0);
				l.get(l.size()/2);
				l.get(l.size()-1);
			}
			System.out.println();
		}
		
		private static void cargaLista( List<Object> l, int numEls ) {
			for (int i=0; i<numEls; i++) l.add( null );
		}
	
		private static void cargaListaInsMedio( List<Object> l, int numEls ) {
			for (int i=0; i<numEls; i++) l.add( i/2, null );
		}
	
	private static void pruebaTiempoListas( int numPrueba ) {
		ArrayList<Object> al = new ArrayList<>();
		LinkedList<Object> ll = new LinkedList<>();
		ArrayList<Object> al2 = new ArrayList<>();
		LinkedList<Object> ll2 = new LinkedList<>();
		int tamListas = 100000;
		visuTiempo( "Al inicio", true );
		if (numPrueba==1 || numPrueba==3) {
			cargaLista( al, tamListas );
			visuTiempo( "  Tras cargar ArrayList de " + tamListas );
			System.out.println( "  " + memDeep(al) + "  Y la memoria usada" );
		}
		if (numPrueba==2 || numPrueba==3) {
			visuMem( null, false );
			cargaLista( ll, tamListas );
			visuTiempo( "  Tras cargar LinkedList de " + tamListas );
			visuMem( "  Y la memoria usada", true );
			// Aquí no se puede usar memDeep porque hay demasiada recursión para sizeOf
			// System.out.println( "  " + memDeep(ll) + "  Y la memoria usada" );
		}
		System.out.println();
		if (numPrueba==1 || numPrueba==3) {
			cargaListaInsMedio( al2, tamListas );
			visuTiempo( "  Tras cargar ArrayList de " + tamListas + " insertando en medio" );
			System.out.println( "  " + memDeep(al2) + "  Y la memoria usada" );
		}
		if (numPrueba==2 || numPrueba==3) {
			visuMem( null, false );
			cargaListaInsMedio( ll2, tamListas );
			visuTiempo( "  Tras cargar LinkedList de " + tamListas + " insertando en medio" );
			visuMem( "  Y la memoria usada", true );
			// System.out.println( "  " + mem(ll2) + "  Y la memoria usada" );
		}
		System.out.println();
		if (numPrueba==1 || numPrueba==3) {
			recorreLista( al, 1 );
			visuTiempo( "  Tras recorrer arriba y abajo ArrayList de " + tamListas );
		}
		if (numPrueba==2 || numPrueba==3) {
			recorreLista( ll, 1 );
			visuTiempo( "  Tras recorrer arriba y abajo LinkedList de " + tamListas );
		}
		if (numPrueba==2 || numPrueba==3) {
			recorreListaConIterador( ll, 1 );
			visuTiempo( "  Tras recorrer arriba y abajo con ITERADOR LinkedList de " + tamListas );
		}
		System.out.println();
		int numVeces = 10000;
		if (numPrueba==1 || numPrueba==3) {
			cogePosicionesLista( al, numVeces );
			visuTiempo( "  Tras coger " + numVeces + " posiciones numéricas sueltas de ArrayList de " + tamListas );
		}
		if (numPrueba==2 || numPrueba==3) {
			cogePosicionesLista( ll, numVeces );
			visuTiempo( "  Tras coger " + numVeces + " posiciones numéricas sueltas de LinkedList de " + tamListas );
		}
	}
	
	public static void main(String[] args) {
		int numPrueba = 3;  // Ver ArrayList
		// int numPrueba = 2;  // Ver LinkedList
		// int numPrueba = 3;  // Ver ArrayList y LinkedList
		pruebaEspacioListas( numPrueba );
		pruebaTiempoListas( numPrueba );
	}
	
}
