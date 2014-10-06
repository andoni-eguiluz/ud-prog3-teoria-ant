package ud.prog3.cap01;
import java.util.logging.*;

public class EjemploLogger {

	// Obtener un logger d�ndole nombre como la clase
	private static Logger logger = Logger.getLogger( EjemploLogger.class.getName() );
	
	// Niveles de importancia para el log:
	// FINEST / FINER / FINE / CONFIG / INFO / WARNING / SEVERE
	// Por defecto se muestran en consola de error solo INFO-WARNING-SEVERE
	
	public static void main(String argv[]) {
		logger.setLevel( Level.ALL );  // Cambiando esto se loggean m�s o menos mensajes
		// logger.addHandler( consoleHandler );
		try {
			// Al logger se le pueden a�adir gestores (handler) que adem�s
			// de a la consola de error saquen a fichero, xml, pantalla...
			Handler h = new StreamHandler( System.out, new SimpleFormatter() );
			h.setLevel( Level.FINEST );
			logger.addHandler( h );  // Saca todos los errores a out
			logger.addHandler( new FileHandler( "EjemploLogger.log.xml") ); // Y tambi�n a un xml
		} catch (Exception e) {
			logger.log( Level.SEVERE, e.toString(), e );
		}
		logger.log( Level.INFO, "Empezando");
		try{
			for (int j=7; j>=0; j--) {
				logger.log( Level.FINER, "proceso bucle j = {0}", j );
				int i = 1000/j;
				System.out.println( i );
			}
		} catch (Exception ex) {
			// Log un mensaje SEVERE -m�xima prioridad-
			logger.log( Level.SEVERE, "error de c�lculo", ex );
		}
		logger.info("Se acab�");
	}
}