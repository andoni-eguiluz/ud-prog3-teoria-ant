package ud.prog3.cap01;

import java.lang.reflect.Method;

public class EjemploReflectividad {

	@SuppressWarnings("unused")  // Para que no salga el warning de que metodo1 no se usa
	private void metodo1( int i ) {
		System.out.println( "M�todo1 = " + i );
	}
	
	public void metodo2( double d ) {
		System.out.println( d );
	}
	
	public static void main(String[] args) {
		Class<?> c = EjemploReflectividad.class;
		System.out.println( "M�todos p�blicos:");
		Method[] mets = c.getMethods();
		for (Method m : mets)
			System.out.println( m );
		System.out.println();
		System.out.println( "M�todos declarados:");
		mets = c.getDeclaredMethods();
		for (Method m : mets)
			System.out.println( m );
		System.out.println();
		System.out.println( "Llamada por reflectividad a un m�todo: " );
		for (Method m : mets)
			if (m.getName().equals("metodo1")) {
				try {
					m.invoke( new EjemploReflectividad(), 5 );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}
}
