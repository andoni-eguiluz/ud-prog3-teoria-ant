package ud.prog3.varios;

public class CuriosoElSwitch {

	public static void main(String[] args) {
		int i = 0;  // Obsérvese qué pasa con 1 y con 2
		switch (i) {
		case 0:
			System.out.println( "0" );
			break; /* no sigue */
		case 1:
			System.out.println( "1" );
			/* ojo: sigue */
		case 2:
			System.out.println( "2" );
			/* ojo: sigue */
		default:
			System.out.println( "Por defecto" );
			/* sigue - como es el último acaba */
		}
	}

}
