package ud.prog3.cap03;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestEjemploFicheros {

	private ArrayList<Usuario> miListaUsuarios;
	
	// Crea una lista de 4 usuarios de ejemplo para los tests
	@Before
	public void setUp() throws Exception {
		miListaUsuarios = new ArrayList<Usuario>();
		miListaUsuarios.add( new Usuario( "buzz", "#9ab,bf", "Buzz", "Lightyear", 101202303, TipoUsuario.Admin, "buzz@gmail.com", "amigo.de.woody@gmail.com" ) );
		miListaUsuarios.add( new Usuario( "woody", "woody", "Woody", "The cowboy", 666111222, TipoUsuario.Cliente, "woody-toystory@gmail.com" ) );
		miListaUsuarios.add( new Usuario( "pcruz", "4556gght", "Penélope", "Cruz", 609634852, TipoUsuario.Invitado, "penelope.cruz@gmail.com" ) );
		miListaUsuarios.add( new Usuario( "jbardem", "bardemthebest", "Javier", "Bardem", 607349552, TipoUsuario.Invitado, "javier.bardem@gmail.com" ) );
	}

	@After
	public void tearDown() throws Exception {
		miListaUsuarios.clear();
	}

	@Test
	public void testLeerYEscribirAFicheroConTags() {
		EjemploFicheros.escribirAFicheroConTags( "test/res/test-usuarios-tags.txt", miListaUsuarios );
		ArrayList<Usuario> l = EjemploFicheros.leerDeFicheroConTags( "test/res/test-usuarios-tags.txt" );
		assertEquals( miListaUsuarios.size(), l.size() );
		assertEquals( miListaUsuarios, l );  // Las listas deben ser iguales
		l.clear();
	}

	@Test
	public void testLeerYEscribirAFicheroConComas() {
		EjemploFicheros.escribirAFicheroConComas( "test/res/test-usuarios-comas.txt", miListaUsuarios );
		ArrayList<Usuario> l = EjemploFicheros.leerDeFicheroConComas( "test/res/test-usuarios-comas.txt" );
		assertEquals( miListaUsuarios.size(), l.size() );
		assertEquals( miListaUsuarios, l );  // Las listas deben ser iguales
		l.clear();
	}

	@Test
	public void testLeerYEscribirAFicheroSerializado() {
		EjemploFicheros.escribirAFicheroSerializado( "test/res/test-usuarios-comas.txt", miListaUsuarios );
		ArrayList<Usuario> l = EjemploFicheros.leerDeFicheroSerializado( "test/res/test-usuarios-comas.txt" );
		assertEquals( miListaUsuarios.size(), l.size() );
		assertEquals( miListaUsuarios, l );  // Las listas deben ser iguales
		l.clear();
	}

	@Test
	public void testLeerYEscribirABD() {
		int numInserts = EjemploFicheros.escribirABD( "usuarios.bd", miListaUsuarios );
		assertEquals( miListaUsuarios.size(), numInserts );
		// Cogemos todos (sin where)
		ArrayList<Usuario> alu = EjemploFicheros.leerDeBD( "usuarios.bd", "" );
		assertEquals( miListaUsuarios, alu );  // Las listas deben ser iguales
		// Cogemos solo uno (buzz)
		alu = EjemploFicheros.leerDeBD( "usuarios.bd", "nick='buzz'" );
		assertEquals( 1, alu.size() );  // Las listas deben ser iguales
		assertEquals( miListaUsuarios.get(0), alu.get(0) );  // Las listas deben ser iguales
		// Cogemos solo ninguno
		alu = EjemploFicheros.leerDeBD( "usuarios.bd", "nick='no existo'" );
		assertEquals( 0, alu.size() );  // Las listas deben ser iguales
	}
}
