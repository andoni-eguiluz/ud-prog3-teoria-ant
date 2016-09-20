package ud.prog3.cap01;

import java.util.GregorianCalendar;

import ud.prog3.cap01.Persona2.ProcesadorPersona;

/** Ejemplo de clases internas y an�nimas para entender el concepto
 * y ver que no se aplica solo a escuchadores de ventanas (se puede utilizar para cualquier cosa)
 * @author Andoni Egu�luz Mor�n
 * Facultad de Ingenier�a - Universidad de Deusto
 */


public class EjemploClasesInternasYAnonimas {
	public static void main(String[] args) {
		// Opci�n 1 - clase externa. Ejemplo de persona y fecha de nacimiento. 
		// Esto es lo m�s conveniente cuando las dos cosas tienen sentido por separado
		Persona p1 = new Persona( "Andoni", new FechaNacimiento( 14, 12, 1968 ) );
		System.out.println( p1 );
		
		// Opci�n 2 - clase interna. Ejemplo de persona y procesador de persona
		// Esto es lo m�s conveniente cuando la segunda cosa solo tiene sentido dentro de la primera
		// Y la segunda cosa NECESITA a la primera para funcionar (o sea necesita que haya una instancia de la primera)
		Persona2 p2 = new Persona2( "Andoni", new FechaNacimiento( 14, 12, 1968 ) );
		System.out.println();
		System.out.println( p2 );
		ProcesadorPersona procP = p2.new ProcesadorPersona();  // Obs�rvese la sintaxis  
			// (si se crea dentro de la propia persona el this. no hace falta indicarlo, por ejemplo
			// p2.creaProcesador() -->
			//      procP = [this.]new Procesadorpersona();
		procP.run();
		
		// Opci�n 3 - clase interna an�nima. Mismo ejemplo
		// Esto es lo m�s conveniente cuando la segunda cosa solo tiene sentido dentro de la primera, la necesita,  (como en la opci�n 2)
		// y adem�s solo se va a crear una instancia y solo una de la segunda cosa  (no hace falta nombrar un "tipo")
		Persona3 p3 = new Persona3( "Andoni", new FechaNacimiento( 14, 12, 1968 ) );
		System.out.println();
		System.out.println( p3 );
		p3.procesarPersona();  
			// Observa c�mo est� codificado este m�todo por dentro
			// F�jate que en �l se hace un new con el this
	}
}


//
// Opci�n 1 - Clase externa
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
// Opci�n 2 - Clase interna
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
			// Obs�rvese que tengo acceso a �la Persona! 
			// (Mi instancia hace referencia a la instancia de la clase englobadora)
			GregorianCalendar gc = new GregorianCalendar();
			int edad = gc.get(GregorianCalendar.YEAR) - fecha.anyo;
			if ( (gc.get(GregorianCalendar.MONTH)+1 < fecha.mes) ||
				 (gc.get(GregorianCalendar.MONTH)+1 == fecha.mes && gc.get(GregorianCalendar.DAY_OF_MONTH) < fecha.dia) )
				System.out.println( "�Este a�o cumples " + edad + " a�os!");
			else 
				System.out.println( "�Este a�o has cumplido " + edad + " a�os!");
		}
	}
	
}


//
//Opci�n 3 - Clase interna an�nima
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
			// No hace falta nombre de la clase interna (por eso se llama an�nima)
			// No podr�a crearse otra instancia de la misma clase. Es una llamada �nica
				@Override
				public void run() {
					// Igual que en caso 2
					GregorianCalendar gc = new GregorianCalendar();
					int edad = gc.get(GregorianCalendar.YEAR) - fecha.anyo;
					if ( (gc.get(GregorianCalendar.MONTH)+1 < fecha.mes) ||
						 (gc.get(GregorianCalendar.MONTH)+1 == fecha.mes && gc.get(GregorianCalendar.DAY_OF_MONTH) < fecha.dia) )
						System.out.println( "�Este a�o cumples " + edad + " a�os!");
					else 
						System.out.println( "�Este a�o has cumplido " + edad + " a�os!");
				}
			};
		procesoPersona.run();
		// Nota: lo habitual en este caso ser�a crear solo una vez este objeto an�nimo, en un atributo,
		// y usar ese mismo en llamadas sucesivas de este m�todo.
		// Lo hacemos as� para atraer la atenci�n espec�ficamente a c�mo se crea un objeto
		// de una clase an�nima.
		//
		// Podr�a incluso evitarse la variable temporal:
		// (new Runnable() { ... }).run();
	}
	
}
