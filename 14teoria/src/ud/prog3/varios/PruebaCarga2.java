package ud.prog3.varios;

public class PruebaCarga2 {
	public static void main(String[] args) {
		// Salida de prueba
		System.out.println( "Salida de prueba");
		System.out.println( "Salida de prueba 2");
		System.out.println( "Excepción de prueba:");
		throw new NullPointerException( "Excepción generada ad hoc" );
	}
}
