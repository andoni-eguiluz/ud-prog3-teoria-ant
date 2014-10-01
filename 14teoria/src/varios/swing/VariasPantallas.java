package varios.swing;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

/** Crea una ventana en cada pantalla de nuestro sistema
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */
public class VariasPantallas {

	public static void main(String[] args) {
		GraphicsDevice[] pantallas = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		for( GraphicsDevice pantalla : pantallas ) {
			GraphicsConfiguration[] gcs = pantalla.getConfigurations();
			for (GraphicsConfiguration gc : gcs) {
				System.out.println( gc.getBounds() );
				JFrame ssp = new JFrame();
				ssp.setLocation( gc.getBounds().x, gc.getBounds().y );
				ssp.setSize( gc.getBounds().width, gc.getBounds().height );
				ssp.setTitle( pantalla.getIDstring() );
				ssp.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
				ssp.setVisible( true );
			}
		}
	}

}
