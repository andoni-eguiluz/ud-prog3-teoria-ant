package ud.prog3.cap06;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class ArbolDeFunciones {

	private class Ventana extends JFrame {
		private JTree2 tree;
		public Ventana() {
			setTitle( "Arbol de funciones" );
			setSize( 640, 480 );
			setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
			setLocationRelativeTo( null );
			tree = new JTree2();
			JScrollPane spPrincipal = new JScrollPane(tree);
			getContentPane().add( spPrincipal, BorderLayout.CENTER );
		}
	}
	
	// Atributos y métodos principales de representación en árbol de llamadas
	
	private DefaultMutableTreeNode raiz;
	private DefaultTreeModel modeloArbol;
	private Ventana ventana;
	
	/** Construye un nuevo árbol de representación de funciones o llamadas, que incluye una ventana de representación y la muestra en pantalla
	 * @param textoNodoRaiz	Texto del nodo principal
	 */
	public ArbolDeFunciones(String textoNodoRaiz) {
		ventana = new Ventana();
		raiz = new DefaultMutableTreeNode(textoNodoRaiz);
		modeloArbol = new DefaultTreeModel( raiz );
		ventana.tree.setModel( modeloArbol );
		ventana.setVisible( true );
	}
	/** Crea un nuevo nodo hijo en el árbol
	 * @param texto	Texto del nodo
	 * @param padre	Nodo padre del que crear el nodo hijo. Si es null, se crea como hijo de la raíz principal del árbol
	 * @return	Nodo hijo recién creado
	 */
	public DefaultMutableTreeNode anyadeNodoHijo( String texto, DefaultMutableTreeNode padre ) {
		DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode( texto );
		if (padre==null) {
			raiz.add( nuevo );
			modeloArbol.nodesWereInserted( raiz, new int[] { raiz.getChildCount()-1 } );
		} else {
			padre.add( nuevo );
			modeloArbol.nodesWereInserted( padre, new int[] { padre.getChildCount()-1 } );
		}
		ventana.tree.setES( new TreePath(nuevo.getPath()), true );
		return nuevo;
	}
	/** Crea un nuevo nodo hijo en el árbol
	 * @param texto	Texto nuevo a poner en el nodo
	 * @param nodo	Nodo del que cambiar el texto. Si es null, se modifica el texto de la raíz principal del árbol
	 */
	public void cambiaValorNodo( String texto, DefaultMutableTreeNode nodo ) {
		if (nodo==null) nodo = raiz;
		nodo.setUserObject( texto );
		modeloArbol.nodeChanged(nodo); // Lanza evento de modificación en el modelo
	}

	
	// Métodos para pruebas de representación en árbol
	
		private static void pruebaAMano() {
			ArbolDeFunciones arbol = new ArbolDeFunciones( "Test Manual" );
			DefaultMutableTreeNode nodo = arbol.anyadeNodoHijo( "Prueba 1", null );
			arbol.anyadeNodoHijo( "Prueba 2", null );
			DefaultMutableTreeNode n3 = arbol.anyadeNodoHijo( "Prueba 3", nodo );
			arbol.anyadeNodoHijo( "Prueba 4", nodo );
			try { Thread.sleep( 2000 ); } catch (InterruptedException e) { }
			arbol.cambiaValorNodo( "Prueba 3 modificada", n3 );
		}
		
			private static int fibonacci( int n, ArbolDeFunciones arbol, DefaultMutableTreeNode padre ) {
				DefaultMutableTreeNode nuevaLlamada = arbol.anyadeNodoHijo( "fib("+n+")", padre );
				if (n<=2) {
					arbol.cambiaValorNodo( "1 <- fib("+n+")", nuevaLlamada );
					try { Thread.sleep( 500 ); } catch (InterruptedException e) { }
					return 1;
				} else {
					int resultado = fibonacci(n-1,arbol,nuevaLlamada) + fibonacci(n-2,arbol,nuevaLlamada);
					arbol.cambiaValorNodo( resultado + " <- fib("+n+")", nuevaLlamada );
					try { Thread.sleep( 500 ); } catch (InterruptedException e) { }
					return resultado;
				}
			}
		private static void pruebaFibonacci() {
			ArbolDeFunciones arbol = new ArbolDeFunciones( "Test Fibonacci" );
			System.out.println( "Fibonacci = " + fibonacci(11,arbol,null) );
		}
		
			private static void visuArrayList( Object al, ArbolDeFunciones arbol, DefaultMutableTreeNode padre ) {
				if (al!=null) {
					DefaultMutableTreeNode nuevoVal = arbol.anyadeNodoHijo( al.toString(), padre ); 
					if (al instanceof ArrayList)
						for (Object o : (ArrayList)al) {
							visuArrayList(o,arbol,nuevoVal);
						}
				}
			}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		private static void pruebaArrayList() {
			ArrayList l1 = new ArrayList();
			ArrayList l1a = new ArrayList();
			ArrayList l1b = new ArrayList();
			ArrayList l1b2 = new ArrayList();
			l1a.add( "Marta" ); l1a.add( "Luis" ); l1a.add( "Mikel" );
			l1b2.add( "Yolanda" ); l1b2.add( "Aitziber" );
			l1b.add( "Florencia" ); l1b.add( "Robustiano" ); l1b.add( l1b2 );
			l1.add( "Jaime" ); l1.add( l1a ); l1.add( l1b );
			ArbolDeFunciones arbol = new ArbolDeFunciones( "Test ArrayList" );
			visuArrayList(l1,arbol,null);
		}
		
	// Llamadas a las pruebas
		
	public static void main(String[] args) {
		// Prueba con visualización de arraylist
		pruebaArrayList();
		// Prueba manual
		pruebaAMano();
		// Prueba con fibonacci
		pruebaFibonacci();
	}

}

class JTree2 extends JTree {

	@Override
	protected void setExpandedState(TreePath path, boolean state) {
		super.setExpandedState(path, state);
	}
	
	public void setES(TreePath path, boolean state) {
		setExpandedState(path, state);
	}
	
}