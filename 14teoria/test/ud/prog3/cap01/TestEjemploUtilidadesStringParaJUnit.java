package ud.prog3.cap01;

import static org.junit.Assert.*;
import static ud.prog3.cap01.EjemploUtilidadesStringParaJUnit.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestEjemploUtilidadesStringParaJUnit {

	private static String test1;
	private static String test2;
	private static String test3;
	private static String test4;
	private static String test5;
	@Before
	public void setUp() throws Exception {
		test1 = "Hola\nEsto es un string con tres líneas\ny\tvarios\ttabuladores.";
		test2 = "Esto solo tiene\ttabs";
		test3 = "Y esto no tiene líneas ni tabuladores";
		test4 = "";
		test5 = null;
	}

	@After
	public void tearDown() throws Exception {
		// En este caso no hay nada que "limpiar"
	}

	@Test
	public void testQuitarTabsYSaltosLinea() {
		assertEquals( "Hola#Esto es un string con tres líneas#y|varios|tabuladores.", quitarTabsYSaltosLinea( test1 ));   // Corregido error de # en lugar de |
		assertEquals( "Esto solo tiene|tabs", quitarTabsYSaltosLinea( test2 ));
		assertEquals( "Y esto no tiene líneas ni tabuladores", quitarTabsYSaltosLinea( test3 ));
		assertEquals( "", quitarTabsYSaltosLinea( test4 ));
		assertEquals( null, quitarTabsYSaltosLinea( test5 ));   // Corregido error de null -> nullpointer
	}

	/** Devuelve cualquier string truncado al número de caracteres indicado, con puntos suspensivos al final si se ha truncado
	 * @param s	String con cualquier contenido o null
	 * @param largo	Número máximo de caracteres de longitud
	 * @return	String recortado si ocupaba más de largo caracteres con tres puntos suspensivos al final; mismo string en caso contrario; null si s es null.
	 */
	
	@Test
	public void testWrapString() {
		assertEquals( "Hol...", wrapString( test1, 3 ) );
		assertEquals( "Hola\nEsto ...", wrapString( test1, 10 ) );
		assertEquals( "Esto solo tiene\ttabs", wrapString( test2, 20) );
		assertEquals( "Esto solo tiene\ttabs", wrapString( test2, 21) );
		assertEquals( "Esto solo tiene\ttab...", wrapString( test2, 19) );
		assertEquals( "...", wrapString( test2, 0) );
		assertEquals( "", wrapString( test4, 40) );
		assertEquals( null, wrapString( null, 5 ) );
	}
	
	@Test
	public void test3() {
		// Manera 1 de chequear excepciones
		try {
			wrapString( test4, -5);   // Encontrado IndexOutOfBounds si se pasa un negativo -> Añadido a especificación del método:
									  // * @throws IndexOutOfBoundsException	Si largo es negativo
			fail( "Error - debería generar IndexOutOfBounds" );
		} catch (IndexOutOfBoundsException e) {  // Ok
		}
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testWrapStringException() {
		// Manera 2 de chequear excepciones  (usar cualquiera de las dos, se indican las dos para conocerlas, pero no tiene sentido duplicar el mismo test)
		wrapString( test4, -5 );
	}
	
	@Test
	public void testWrapYQuitar() {  // Test conjunto de los dos métodos (a veces es interesante probar varios métodos interactuando entre sí)
		assertEquals( "Hola#Esto ...", wrapString( quitarTabsYSaltosLinea(test1), 10 ) );
		assertEquals( "Hola#Esto ...", quitarTabsYSaltosLinea( wrapString(test1,10) ) );
	}

}
