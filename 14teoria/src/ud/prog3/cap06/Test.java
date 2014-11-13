package ud.prog3.cap06;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class Test {
	
	private static JFrame crearVentana() {
		JFrame f = new JFrame();
		f.setSize( 400, 300 );
		f.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		return f;
	}
	
	private static void testLimitesEspacio() {
		JFrame f = crearVentana();
		JPanel p = new JPanel();
		p.setBorder( BorderFactory.createLineBorder( Color.red,4 ) );
		p.setBackground( Color.white );
		p.setMinimumSize( new Dimension(1000, 500) );
		p.setMaximumSize( new Dimension(1000, 500) );
		p.setPreferredSize( new Dimension(1000, 500) );
		JLabel l = new JLabel( "Soy una label ");
		l.setBorder( BorderFactory.createLineBorder( Color.blue,4 ) );
		l.setBackground( Color.yellow );
		l.setOpaque( true );  // Las label por defecto son transparentes
		l.setMinimumSize( new Dimension(1000, 500) );
		l.setMaximumSize( new Dimension(1000, 500) );
		l.setPreferredSize( new Dimension(1000, 500) );
		f.add( p, BorderLayout.CENTER );
		f.add( l, BorderLayout.SOUTH );
		f.setVisible( true );
	}
	
	private static void testSoloUnaVezCadaComponente() {
		JLabel l = new JLabel( "solo soy una" );
		JFrame f = crearVentana();
		f.add( l, BorderLayout.CENTER );
		f.add( l, BorderLayout.SOUTH );
		JFrame f2 = crearVentana();
		f2.add( l, BorderLayout.CENTER );
		f.setVisible( true );
		f2.setVisible( true );
	}
	
	private static void testJWindow() {
		JWindow w = new JWindow();
		w.setSize( 400, 300 );
		w.setLocation( 500, 400 );
		w.add( new JLabel("Soy una JWindow"), BorderLayout.CENTER );
		w.setVisible( true );
	}
	
	public static void main(String[] args) {
		testLimitesEspacio();
		testSoloUnaVezCadaComponente();
		testJWindow();
	}

}
