package ud.prog3.cap05;

import java.util.ArrayList;
import java.util.function.Consumer;

/** Ejemplo de BST con cualquier Comparable
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */
public class BST<T extends Comparable<T>> {
	NodoBST<T> raiz;
	
	@SuppressWarnings("unchecked")
	public void insertar( T... nuevos ) {
		for (T nuevo : nuevos) insertarRec( null, raiz, nuevo );
	}
	public void insertar( T nuevo ) {
		insertarRec( null, raiz, nuevo );
	}
		private void insertarRec( NodoBST<T> padre, NodoBST<T> bst, T nuevo ) {
			if (bst==null) {  // Caso base: hay que insertar
				NodoBST<T> nuevoNodo = new NodoBST<T>( nuevo, null, null );
				if (padre==null) // Insertar en raíz
					raiz = nuevoNodo;
				else if (padre.elemento.compareTo(nuevo)<0)
					padre.derecho = nuevoNodo;
				else
					padre.izquierdo = nuevoNodo;
			} else {  // Caso general
				int comp = (bst.elemento.compareTo(nuevo));
				if (comp==0) // Caso base: elemento ya existe -se podría insertar iz o de pero siempre igual. O no insertar si no se permiten repeticiones (es lo que hacemos ahora)
					; // Nada que hacer - retorno
				else if (comp<0) // Insertar a la derecha
					insertarRec( bst, bst.derecho, nuevo );
				else
					insertarRec( bst, bst.izquierdo, nuevo );
			}
		}
		
	public void recorrerInOrden( Consumer<T> c ) {
		recorrerIOrec( raiz, c );
	}
		private void recorrerIOrec( NodoBST<T> nodo, Consumer<T> c ) {
			if (nodo!=null) {   // Si no caso base
				recorrerIOrec( nodo.izquierdo, c );
				c.accept( nodo.elemento );
				recorrerIOrec( nodo.derecho, c );
			}
		}
	
	public void recorrerPreOrden( Consumer<T> c ) {
		recorrerPOrec( raiz, c );
	}
		private void recorrerPOrec( NodoBST<T> nodo, Consumer<T> c ) {
			if (nodo!=null) {   // Si no caso base
				c.accept( nodo.elemento );
				recorrerPOrec( nodo.izquierdo, c );
				recorrerPOrec( nodo.derecho, c );
			}
		}
	
	public void recorrerPostOrden( Consumer<T> c ) {
		recorrerPtOrec( raiz, c );
	}
		private void recorrerPtOrec( NodoBST<T> nodo, Consumer<T> c ) {
			if (nodo!=null) {   // Si no caso base
				recorrerPtOrec( nodo.izquierdo, c );
				recorrerPtOrec( nodo.derecho, c );
				c.accept( nodo.elemento );
			}
		}
	
	public void recorrerAnchura( Consumer<T> c ) {
		for (int nivel = 0; nivel<altura(); nivel++) 
			recorrerAncrec( raiz, c, nivel );
	}
		private void recorrerAncrec( NodoBST<T> nodo, Consumer<T> c, int nivel ) {
			if (nodo!=null && nivel>=0) {   // Si no caso base
				recorrerAncrec( nodo.izquierdo, c, nivel-1 );
				if (nivel==0)
					c.accept( nodo.elemento );
				recorrerAncrec( nodo.derecho, c, nivel-1 );
			}
		}
		
	public int altura() {
		return alturaRec( raiz );
	}
		public int alturaRec( NodoBST<T> bst ) {
			if (bst==null)
				return 0;
			else {
				return 1 + Math.max( alturaRec( bst.izquierdo ), alturaRec( bst.derecho ) );
			}
		}
		
	public int size() {
		return sizeRec( raiz );
	}
		private int sizeRec( NodoBST<T> nodo ) {
			if (nodo==null)
				return 0;
			else
				return 1 + sizeRec( nodo.izquierdo ) + sizeRec( nodo.derecho );
		}

		private volatile ArrayList<StringBuffer> lineas;
	@Override
	public String toString() {
		lineas = new ArrayList<>(); lineas.add( new StringBuffer("") );
		toStringRec( raiz, 0 );
		String ret = "";
		for (StringBuffer linea : lineas) if (!linea.toString().isEmpty()) ret += (linea + "\n");
		return ret;
	}
		private void toStringRec( NodoBST<T> nodo, int nivel ) {
			if (nodo!=null) {   // Si no caso base
				if (lineas.size() <= nivel+1) lineas.add( new StringBuffer("") ); 
				toStringRec( nodo.izquierdo, nivel+1 );
				int largoInferior = lineas.get(nivel+1).length();
				toStringRec( nodo.derecho, nivel+1 );
				rellenaEspacios( nivel, largoInferior, lineas.get(nivel+1).length(), nodo.elemento.toString() );
			}
		}
		private void rellenaEspacios( int nivel, int ancho1, int ancho2, String elem ) {
			int faltanEspacios = (ancho1 + ancho2) / 2;
			faltanEspacios = faltanEspacios - lineas.get(nivel).length();
			for (int i=0; i<faltanEspacios-1; i++) lineas.get(nivel).append( " " );
			lineas.get(nivel).append( elem ); lineas.get(nivel).append( " " );
		}
	
	public static void main(String[] args) {
		BST<Integer> bst = new BST<>();
		bst.insertar( 5, 3, 7, 1, 9, 4, 6 );
		System.out.println( "Árbol = " );
		System.out.print( bst );
		System.out.println( "Altura árbol: " + bst.altura() );
		System.out.print( "Recorrido árbol inorden = { ");
		bst.recorrerInOrden( (Integer i) -> { System.out.print( i + " " ); } );
		// O en sintaxis Java 7
		// bst.recorrerInOrden( new Consumer<Integer>() {
		// 	@Override
		// 	public void accept(Integer t) {
		// 		System.out.print( t + " " );
		// 	}
		// });
		System.out.println( "}");
		System.out.print( "Recorrido árbol preorden = { ");
			bst.recorrerPreOrden( (Integer i) -> { System.out.print( i + " " ); } );
		System.out.println( "}");
		System.out.print( "Recorrido árbol postorden = { ");
			bst.recorrerPostOrden( (Integer i) -> { System.out.print( i + " " ); } );
		System.out.println( "}");
		System.out.print( "Recorrido árbol anchura = { ");
			bst.recorrerAnchura( (Integer i) -> { System.out.print( i + " " ); } );
		System.out.println( "}");
		System.out.println();
		System.out.println( "Obsérvese la diferencia con este otro árbol:" );
		bst = new BST<>();
		bst.insertar( 1, 3, 4, 5, 6, 7, 9 );
		System.out.print( bst );
		System.out.println( "Altura árbol: " + bst.altura() );
		System.out.print( "Recorrido árbol inorden = { ");
		bst.recorrerInOrden( (Integer i) -> { System.out.print( i + " " ); } );
		System.out.println( "}");
		System.out.print( "Recorrido árbol preorden = { ");
			bst.recorrerPreOrden( (Integer i) -> { System.out.print( i + " " ); } );
		System.out.println( "}");
		System.out.print( "Recorrido árbol postorden = { ");
			bst.recorrerPostOrden( (Integer i) -> { System.out.print( i + " " ); } );
		System.out.println( "}");
		System.out.print( "Recorrido árbol anchura = { ");
			bst.recorrerAnchura( (Integer i) -> { System.out.print( i + " " ); } );
		System.out.println( "}");
	}
}

class NodoBST<T extends Comparable<T>> {
	T elemento;
	NodoBST<T> izquierdo;
	NodoBST<T> derecho;
	public NodoBST(T elemento, NodoBST<T> izquierdo, NodoBST<T> derecho) {
		this.elemento = elemento;
		this.izquierdo = izquierdo;
		this.derecho = derecho;
	}
	
}
