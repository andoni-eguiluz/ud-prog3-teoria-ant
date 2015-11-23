package ud.prog3.cap06;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PruebasVariasSwing {

	private static void pruebaDecoraciones() {
		JFrame miJFrame = new JFrame();
		miJFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		miJFrame.setUndecorated( false );
		miJFrame.setTitle ( "Ventana decorada" );
		miJFrame.setSize( 320, 200 );
		miJFrame.setVisible( true );
		
		miJFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		miJFrame.setTitle ( "Ventana con internal frames" );
		miJFrame.setSize( 640, 400 );
		miJFrame.setVisible( true );
		JDesktopPane fondo = new JDesktopPane();
		miJFrame.setContentPane( fondo );
		
		JInternalFrame miFrameInterno1 = new JInternalFrame();
		miFrameInterno1.setTitle( "JFinterno 1 con iconos" );
		miFrameInterno1.setIconifiable( true );
		miFrameInterno1.setMaximizable( true );
		miFrameInterno1.setClosable( true );
		miFrameInterno1.setBounds( 10, 10, 280, 160);
		JInternalFrame miFrameInterno2 = new JInternalFrame();
		miFrameInterno2.setTitle( "JFinterno 2 por defecto" );
		// miFrameInterno2.setIconifiable( false );
		// miFrameInterno2.setMaximizable( false );
		// miFrameInterno2.setClosable( true );
		miFrameInterno2.setBounds( 300, 50, 280, 160);
		fondo.add( miFrameInterno1 );
		fondo.add( miFrameInterno2 );
		miFrameInterno1.setVisible( true );
		miFrameInterno2.setVisible( true );
	}

		// Usa JPanelConFondo
	private static void pruebaVentanaConGraficoDeFondo() {
		JFrame prueba = new JFrame();
		prueba.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		prueba.setSize( 600, 500 );
		JPanel panelFondo = new JPanelConFondo( "bicho.png" );
		panelFondo.setLayout( new BorderLayout() );
		JButton botonPrueba = new JButton( "Botón de prueba encima de panel con imagen" );
		botonPrueba.setOpaque( false );
		panelFondo.add( botonPrueba, "North" );
		prueba.getContentPane().add( panelFondo, "Center" );
		prueba.setVisible( true );
	}

	private static void pruebaCentradoVertical() {
		// Con GridLayout
		JFrame miJFrame = new JFrame();
			JLabel etiq1 = new JLabel("Prueba etiqueta");
			miJFrame.getContentPane().setLayout( new GridLayout(1,3) );
			etiq1.setBorder( BorderFactory.createLineBorder( Color.RED ));
		miJFrame.getContentPane().add( etiq1 );
			JTextArea ta1 = new JTextArea("Prueba textArea");
			ta1.setBorder( BorderFactory.createLineBorder( Color.RED ));
			ta1.setAlignmentY( SwingConstants.CENTER );
		miJFrame.getContentPane().add( ta1 );
			JPanel panel = new JPanel();
			panel.setAlignmentY( SwingConstants.CENTER );
				JLabel etiq2 = new JLabel("Prueba etiqueta en panel");
				etiq2.setAlignmentY( SwingConstants.CENTER );
				etiq2.setBorder( BorderFactory.createLineBorder( Color.RED ));
			panel.add( etiq2 );
		miJFrame.getContentPane().add( panel );
		miJFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		miJFrame.setTitle ( "Alineación vertical en GridLayout" );
		miJFrame.setSize( 320, 200 );
		miJFrame.setVisible( true );
		// Con GridBagLayout
	    JFrame frame = new JFrame();
	    frame.setLayout(new GridBagLayout());
	    JPanel panelC = new JPanel();
	    	JLabel etiq3 = new JLabel("Etiqueta en panel en GridBagLayout");
	    	etiq3.setBorder( BorderFactory.createLineBorder( Color.RED ));
	    panelC.add( etiq3 );
	    panelC.setBorder(BorderFactory.createLineBorder( Color.black ));
	    frame.add(panelC, new GridBagConstraints());  // Lo pone por defecto - centrado en hor y vert.
	    frame.setSize(400, 400);
		frame.setTitle ( "Alineación vertical en GridBagLayout" );
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setVisible(true);

	    // Con BoxLayout
			    JPanel panelC2 = new JPanel();
		    	JLabel etiq4 = new JLabel("Etiqueta en panel en BoxLayout");
		    	etiq4.setBorder( BorderFactory.createLineBorder( Color.RED ));
		    panelC2.add( etiq4 );
		    panelC2.setBorder(BorderFactory.createLineBorder( Color.black ));
	    Box verticalBox = Box.createVerticalBox();  // Box
	    verticalBox.add(Box.createVerticalGlue());
	    verticalBox.add(panelC2);
	    verticalBox.add(Box.createVerticalGlue());
	    JFrame vent3 = new JFrame();
	    vent3.add( verticalBox );  // Lo pone por defecto - centrado en hor y vert.
	    vent3.setSize(400, 400);
		vent3.setTitle ( "Alineación vertical en BoxLayout" );
	    vent3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    vent3.setVisible(true);
	}

	private static JLabel mens = new JLabel();
	private static JLabel mens2 = new JLabel();
	private static long tiempo;
	public static void pruebaTiempoDeFont() {
		JFrame vent = new JFrame();
		vent.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		vent.setSize( 600, 500 );
		vent.setTitle( "Ventana sin font");
		vent.add( mens, BorderLayout.NORTH );
		vent.addWindowListener( new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				mens.setText( (System.currentTimeMillis()-tiempo) + " milisegundos. ");
			}
		});
		tiempo = System.currentTimeMillis();
		vent.setVisible( true );
		try { Thread.sleep(1000); } catch (Exception e) {}
		JFrame vent2 = new JFrame();
		vent2.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		vent2.setSize( 600, 500 );
		vent2.setTitle( "Ventana con font");
		mens2.setFont( new Font( "Courier", 50, 50 ) );
		vent2.add( mens2, BorderLayout.NORTH );
		vent2.addWindowListener( new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				mens2.setText( (System.currentTimeMillis()-tiempo) + " milisegundos. ");
			}
		});
		tiempo = System.currentTimeMillis();
		vent2.setVisible( true );
	}

	/** Devuelve el contenedor principal del componente indicado
	 * @param c	Cualquier componente visual
	 * @return	Devuelve el contenedor de primer nivel al final de su cadena de contenedores
	 */
	public static Container getContenedorPrincipal( Component c ) {
		if (c==null) return null;
		Container ret = c.getParent();
		while (ret != null) {
			c = ret;
			ret = ret.getParent();
		}
		return (Container) c;
	}
	
	private static void pruebaConsultorContenedorPrincipal() {
		JFrame f = new JFrame( "Prueba contenedor principal" );
		f.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		f.setSize(300,200);
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JLabel lTest = new JLabel( "Etiqueta test" );
		f.add( p1 );
		p1.add( p2 );
		p2.add( p3 );
		p3.add( lTest );
		f.setVisible( true );
		System.out.println( getContenedorPrincipal( lTest ) );
	}
	
	private static JTextField tfTexto;
	private static void pruebaLimitarJTextField() {
		JFrame f = new JFrame( "Prueba cuadro de texto limitado" );
		f.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		f.setSize(300,80);
		JLabel lTest = new JLabel( "Introduce texto limitado:" );
		f.add( lTest, BorderLayout.NORTH );
		tfTexto = new JTextField( "", 10 );
		f.add( tfTexto, BorderLayout.SOUTH );
		f.setVisible( true );
		tfTexto.addKeyListener( new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ev) {
				if (tfTexto.getText().length() >= 3)
					// Aquí se controla que la longitud no sea mayor que 3
					// Se podría controlar cualquier otra cosa
					ev.consume(); // Si se consume el evento, no llega
						// al proceso estándar del cuadro de texto, y
						// por tanto no llega a introducirse en el cuadro
			}
		});
	}
	
	public static void main( String[] s ) {
		// TODO: Descomentar la prueba a realizar
			// Decoración JFrame y JInternalFrame
		pruebaDecoraciones();
			// Fondo de pantalla con un gráfico
		pruebaVentanaConGraficoDeFondo();  // ver clase JPanelConFondo
			// Centrado vertical con GridLayout y con GridBagLayout
		pruebaCentradoVertical();
			// Prueba de tiempo que tarda una ventana y tiempo que tarda Font
		pruebaTiempoDeFont();
			// Prueba de consultor de contenedor principal
		pruebaConsultorContenedorPrincipal();
			// Prueba de text field con entrada limitada
		pruebaLimitarJTextField();
	}
}



@SuppressWarnings("serial")
class JPanelConFondo extends JPanel {

	private BufferedImage imagenOriginal;
	public JPanelConFondo( String nombreImagenFondo ) {
        URL imgURL = getClass().getResource("img/" + nombreImagenFondo);
        try {
        	imagenOriginal = ImageIO.read( imgURL );
        } catch (IOException e) {
        }
	}

	protected void paintComponent(Graphics g) {
		Rectangle espacio = g.getClipBounds();  // espacio de dibujado del panel
		// setBounds( espacio );
		//super.paintComponent(g);  en vez de esto...
		Graphics2D g2 = (Graphics2D) g;  // El Graphics realmente es Graphics2D
		// Código para que el dibujado se reescale al área disponible
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	
		// Dibujado
		g2.drawImage(imagenOriginal, 0, 0, (int)espacio.getWidth(), (int)espacio.getHeight(), null);
	}

}
