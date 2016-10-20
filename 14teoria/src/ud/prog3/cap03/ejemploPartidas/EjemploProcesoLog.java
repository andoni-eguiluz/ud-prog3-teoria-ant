package ud.prog3.cap03.ejemploPartidas;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// dtd copiado de  http://docs.oracle.com/javase/7/docs/technotes/guides/logging/overview.html
// Usada librería SAX que permite leer XML de una forma sencilla
// Si se quisieran también generar se puede usar StAX. Ver referencia en:
// https://docs.oracle.com/javase/tutorial/jaxp/stax/why.html

public class EjemploProcesoLog {

	public static void parseXMLSAX( String xmlFile ) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new DefaultHandler() {
				String campoEnCurso = null;
				public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
					// System.out.println("Inicio de elemento:" + qName);
					campoEnCurso = qName;
				}
				public void characters(char ch[], int start, int length) throws SAXException {
					String valor = new String(ch, start, length);
					// System.out.println("Valor de elemento:" + valor);
					// Visualizar
					// System.out.print( campoEnCurso + ":" + valor + "\t" );
					System.out.print( valor + "\t" );
					proceso( campoEnCurso, valor );
				}
				public void endElement(String uri, String localName, String qName) throws SAXException {
					// System.out.println("Fin de elemento:" + qName);
					if (qName.equals("record")) System.out.println();
				}
			};
			saxParser.parse( xmlFile, handler );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Proceso particular
		private static int numLogs = 0;
		private static int numSevere = 0;
	private static void proceso( String campo, String valor ) {
		numLogs++;
		if (campo.equals("level") && valor.equals("SEVERE")) numSevere++;
	}
	
	public static void main(String[] args) {
		parseXMLSAX( "bd.log.xml" );
		System.out.println( "Logs: " + numLogs + " --> severos: " + numSevere );
	}

}
