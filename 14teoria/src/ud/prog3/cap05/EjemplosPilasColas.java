package ud.prog3.cap05;

import java.util.LinkedList;

public class EjemplosPilasColas {

	public static LinkedList<ElementoExpresion> expresionStringACola( String expresion ) {
		
		return null;
	}
	
	public static LinkedList<ElementoExpresion> expresionInfijaAPostFija( LinkedList<ElementoExpresion> expresion ) {
		return null;
	}

	public static void main(String[] args) {
		String expresion = "2 +35.7/(4-2.3)";
		LinkedList<ElementoExpresion> colaExpresion = expresionStringACola( expresion );
		colaExpresion = expresionInfijaAPostFija( colaExpresion );
	}

}

abstract class ElementoExpresion {
}
class OperandoNumero extends ElementoExpresion {
	double valor;
	public OperandoNumero( double valor ) { this.valor = valor; }
}
class OperadorBinario extends ElementoExpresion {
	char operador;
	public OperadorBinario( char operador ) { this.operador = operador; }
}
class OperadorUnarioPrefijo extends ElementoExpresion {
	char operador;
	public OperadorUnarioPrefijo( char operador ) { this.operador = operador; }
}
class Parentesis extends ElementoExpresion {
	char parentesis;
	public Parentesis( char parentesis ) { this.parentesis = parentesis; }
}