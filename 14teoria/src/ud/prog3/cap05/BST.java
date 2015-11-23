package ud.prog3.cap05;

import java.util.function.Consumer;

/** Ejemplo de BST con cualquier Comparable
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */
public class BST<T extends Comparable<T>> {
	NodoBST raiz;
	
	public void insertar( Comparable<T> nuevo ) {
		insertarRec( null, raiz, nuevo );
	}
		private void insertarRec( NodoBST padre, NodoBST bst, Comparable<T> nuevo ) {
			if (bst==null) {  // Caso base: hay que insertar
				NodoBST nuevoNodo = new NodoBST( nuevo, null, null );
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
	
	public void recorrerPreOrden( BST bst, Consumer<T> c ) {
		recorrerPOrec( bst.raiz, c );
	}
		private void recorrerPOrec( NodoBST<T> nodo, Consumer<T> c ) {
			if (nodo!=null) {   // Si no caso base
				c.accept( nodo.elemento );
				recorrerPOrec( nodo.izquierdo, c );
				recorrerPOrec( nodo.derecho, c );
			}
		}
	
	public void recorrerPostOrden( BST bst, Consumer<T> c ) {
		recorrerPtOrec( bst.raiz, c );
	}
		private void recorrerPtOrec( NodoBST<T> nodo, Consumer<T> c ) {
			if (nodo!=null) {   // Si no caso base
				recorrerPtOrec( nodo.izquierdo, c );
				recorrerPtOrec( nodo.derecho, c );
				c.accept( nodo.elemento );
			}
		}
	
	public void recorrerAnchura( BST bst, Consumer<T> c ) {
		for (int nivel = 0; nivel<bst.altura(bst); nivel++) 
			recorrerAncrec( bst.raiz, c, nivel );
	}
		private void recorrerAncrec( NodoBST<T> nodo, Consumer<T> c, int nivel ) {
			if (nodo!=null && nivel>=0) {   // Si no caso base
				recorrerAncrec( nodo.izquierdo, c, nivel-1 );
				if (nivel==0)
					c.accept( nodo.elemento );
				recorrerAncrec( nodo.derecho, c, nivel-1 );
			}
		}
		
	public int altura( BST bst ) {
		return alturaRec( bst.raiz );
	}
		public int alturaRec( NodoBST bst ) {
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
	
	public static void main(String[] args) {
		BST<Integer> bst = new BST<>();
		bst.insertar( 5 );
		bst.insertar( 3 );
		bst.insertar( 7 );
		bst.insertar( 1 );
		bst.insertar( 9 );
		bst.insertar( 4 );
		bst.insertar( 6 );
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
			bst.recorrerPreOrden( bst, (Integer i) -> { System.out.print( i + " " ); } );
		System.out.println( "}");
		System.out.print( "Recorrido árbol postorden = { ");
			bst.recorrerPostOrden( bst, (Integer i) -> { System.out.print( i + " " ); } );
		System.out.println( "}");
		System.out.print( "Recorrido árbol anchura = { ");
			bst.recorrerAnchura( bst, (Integer i) -> { System.out.print( i + " " ); } );
		System.out.println( "}");
	}
}

class NodoBST<T extends Comparable<T>> {
	T elemento;
	NodoBST izquierdo;
	NodoBST derecho;
	public NodoBST(T elemento, NodoBST izquierdo, NodoBST derecho) {
		this.elemento = elemento;
		this.izquierdo = izquierdo;
		this.derecho = derecho;
	}
	
}
