package ud.prog3.cap03.ejemploPartidas;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// dtd de logs  copiado en  http://docs.oracle.com/javase/7/docs/technotes/guides/logging/overview.html
// El formato del logger deja un único registro log que contiene n registros "record". Cada uno de ellos 
// tiene los siguientes campos (los datos de cada log):
// "date", "millis", "sequence", "logger", "level", "class", "method", "thread", "message"
// Los más importantes son:
// - millis    - Fecha-hora exacta del log en el formato millis de Java (msgs. transcurridos desde 1/1/1970)
// - level     - Importancia del error de acuerdo a la clase Level: FINEST, FINER, FINE, CONFIG, INFO, WARNING, SEVERE
// - class     - Clase que causa el log
// - method    - Método que causa el log
// - message   - Mensaje de log
// - exception - (solo a veces) Excepción volcada al log. Es un registro que a su vez genera los siguientes campos:
//   - message   - Cada mensaje de excepción
//   - frame     - un registro por cada llamada de la pila de llamadas en esa excepción
//     - class     - Clase que provoca la llamada
//     - method    - Método que provoca la llamada
//     - line      - Línea del fichero fuente dentro de ese método donde está la llamada

// Usada librería SAX que permite leer XML de una forma sencilla
// Si se quisieran también generar se puede usar en su lugar StAX. Ver referencia en:
// https://docs.oracle.com/javase/tutorial/jaxp/stax/why.html

public class EjemploProcesoLog {

		private static boolean enExcepcion = false;
	public static void parseXMLSAX( String xmlFile ) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new DefaultHandler() {
				String campoEnCurso = null;
				public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
					// System.out.println("Inicio de elemento:" + qName);
					campoEnCurso = qName;
					if (qName.equals("exception")) enExcepcion = true;
				}
				public void characters(char ch[], int start, int length) throws SAXException {
					String valor = new String(ch, start, length);
					// System.out.println("Valor de elemento:" + valor);
					// Visualizar
					System.out.print( campoEnCurso+"="+valor + "\t" );
					if (enExcepcion)
						procesoExcepcion( campoEnCurso, valor );
					else
						proceso( campoEnCurso, valor );
				}
				public void endElement(String uri, String localName, String qName) throws SAXException {
					// System.out.println("Fin de elemento:" + qName);
					if (qName.equals("record")) System.out.println();
					if (qName.equals("exception")) enExcepcion = false;
				}
			};
			saxParser.parse( xmlFile, handler );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Proceso particular de log
		private static int numLogs = 0;
		private static int numSevere = 0;
		private static ArrayList<String> excepciones = new ArrayList<String>();
		private static ArrayList<ArrayList<String>> llamadasExcepciones = new ArrayList<>();
	private static void proceso( String campo, String valor ) {
		numLogs++;
		if (campo.equals("level") && valor.equals("SEVERE")) numSevere++;
	}
	
	// Proceso particular de excepción
		private static String llamadaEnProceso = "";
	private static void procesoExcepcion( String campo, String valor ) {
		switch (campo) {
			case "message" : {
				excepciones.add( valor );
				llamadasExcepciones.add( new ArrayList<String>() );
				break;
			}
			case "class" : {
				llamadaEnProceso = valor;
				break;
			}
			case "method" : {
				llamadaEnProceso += ("." + valor);
				break;
			}
			case "line" : {
				llamadasExcepciones.get( llamadasExcepciones.size()-1 ).add( llamadaEnProceso + " (" + valor + ")" );
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		parseXMLSAX( "bd.log.xml" );
		System.out.println( "Logs: " + numLogs + " --> severos: " + numSevere );
		System.out.println( "Excepciones:");
		for (int i=0; i<excepciones.size(); i++) {
			System.out.println( "  " + excepciones.get(i) );
			for (String s : llamadasExcepciones.get(i)) {
				System.out.println( "    " + s );
			}
		}
	}

}
