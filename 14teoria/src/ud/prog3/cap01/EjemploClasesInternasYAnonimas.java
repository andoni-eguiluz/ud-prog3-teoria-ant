package ud.prog3.cap01;

import java.util.GregorianCalendar;

import ud.prog3.cap01.Persona2.ProcesadorPersona;

/** Ejemplo de clases internas y anónimas para entender el concepto
 * y ver que no se aplica solo a escuchadores de ventanas (se puede utilizar para cualquier cosa)
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */


public class EjemploClasesInternasYAnonimas {
	public static void main(String[] args) {
		// Opción 1 - clase externa. Ejemplo de persona y fecha de nacimiento. 
		// Esto es lo más conveniente cuando las dos cosas tienen sentido por separado
		Persona p1 = new Persona( "Andoni", new FechaNacimiento( 14, 12, 1968 ) );
		System.out.println( p1 );
		
		// Opción 2 - clase interna. Ejemplo de persona y procesador de persona
		// Esto es lo más conveniente cuando la segunda cosa solo tiene sentido dentro de la primera
		// Y la segunda cosa NECESITA a la primera para funcionar (o sea necesita que haya una instancia de la primera)
		Persona2 p2 = new Persona2( "Andoni", new FechaNacimiento( 14, 12, 1968 ) );
		System.out.println();
		System.out.println( p2 );
		ProcesadorPersona procP = p2.new ProcesadorPersona();  // Obsérvese la sintaxis  
			// (si se crea dentro de la propia persona el this. no hace falta indicarlo, por ejemplo
			// p2.creaProcesador() -->
			//      procP = [this.]new Procesadorpersona();
		procP.run();
		
		// Opción 3 - clase interna anónima. Mismo ejemplo
		// Esto es lo más conveniente cuando la segunda cosa solo tiene sentido dentro de la primera, la necesita,  (como en la opción 2)
		// y además solo se va a crear una instancia y solo una de la segunda cosa  (no hace falta nombrar un "tipo")
		Persona3 p3 = new Persona3( "Andoni", new FechaNacimiento( 14, 12, 1968 ) );
		System.out.println();
		System.out.println( p3 );
		p3.procesarPersona();  
			// Observa cómo está codificado este método por dentro
			// Fíjate que en él se hace un new con el this
	}
}


//
// Opción 1 - Clase externa
//

class Persona { 
	String nombre;
	FechaNacimiento fecha;
	public Persona( String nombre, FechaNacimiento fecha ) {
		this.nombre = nombre; 
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return nombre + " " + fecha;
	}
}

class FechaNacimiento {
	int dia, mes, anyo;
	public FechaNacimiento( int dia, int mes, int anyo ) {
		this.dia = dia;
		this.mes = mes;
		this.anyo = anyo;
	}
	@Override
	public String toString() {
		return dia + "/" + mes + "/" + anyo;
	}
}

//
// Opción 2 - Clase interna
//

class Persona2 { 
	String nombre;
	FechaNacimiento fecha;
	public Persona2( String nombre, FechaNacimiento fecha ) {
		this.nombre = nombre; 
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return nombre + " " + fecha;
	}
	
	public class ProcesadorPersona implements Runnable {
		@Override
		public void run() {
			// Obsérvese que tengo acceso a ¡la Persona! 
			// (Mi instancia hace referencia a la instancia de la clase englobadora)
			GregorianCalendar gc = new GregorianCalendar();
			int edad = gc.get(GregorianCalendar.YEAR) - fecha.anyo;
			if ( (gc.get(GregorianCalendar.MONTH)+1 < fecha.mes) ||
				 (gc.get(GregorianCalendar.MONTH)+1 == fecha.mes && gc.get(GregorianCalendar.DAY_OF_MONTH) < fecha.dia) )
				System.out.println( "¡Este año cumples " + edad + " años!");
			else 
				System.out.println( "¡Este año has cumplido " + edad + " años!");
		}
	}
	
}


//
//Opción 3 - Clase interna anónima
//

class Persona3 { 
	String nombre;
	FechaNacimiento fecha;
	public Persona3( String nombre, FechaNacimiento fecha ) {
		this.nombre = nombre; 
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return nombre + " " + fecha;
	}
	
	public void procesarPersona() {
		Runnable procesoPersona = /* this. */ new Runnable() {  
			// No hace falta nombre de la clase interna (por eso se llama anónima)
			// No podría crearse otra instancia de la misma clase. Es una llamada única
				@Override
				public void run() {
					// Igual que en caso 2
					GregorianCalendar gc = new GregorianCalendar();
					int edad = gc.get(GregorianCalendar.YEAR) - fecha.anyo;
					if ( (gc.get(GregorianCalendar.MONTH)+1 < fecha.mes) ||
						 (gc.get(GregorianCalendar.MONTH)+1 == fecha.mes && gc.get(GregorianCalendar.DAY_OF_MONTH) < fecha.dia) )
						System.out.println( "¡Este año cumples " + edad + " años!");
					else 
						System.out.println( "¡Este año has cumplido " + edad + " años!");
				}
			};
		procesoPersona.run();
		// Nota: lo habitual en este caso sería crear solo una vez este objeto anónimo, en un atributo,
		// y usar ese mismo en llamadas sucesivas de este método.
		// Lo hacemos así para atraer la atención específicamente a cómo se crea un objeto
		// de una clase anónima.
		//
		// Podría incluso evitarse la variable temporal:
		// (new Runnable() { ... }).run();
	}
	
}
