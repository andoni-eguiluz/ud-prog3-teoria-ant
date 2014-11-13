package ud.prog3.cap06;

import javax.swing.*;

import java.awt.*;
import java.util.Random;

public class PruebaDibujarLinea extends JPanel {
	static PruebaDibujarLinea p = new PruebaDibujarLinea();
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		f.setSize( 800, 600 );
		f.add( p, BorderLayout.CENTER );
		f.setVisible( true );
		Graphics2D g = (Graphics2D) p.getGraphics();
		dibujarLinea( g, Color.black ); // Por qué esta no la dibuja?...
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		dibujarLinea( g, Color.blue ); // Y esta sí... pero relativamente...?
	}
	
	private static Random r = new Random();
	
	private static void dibujarLinea(Graphics2D g, Color c) {
		g.setColor( c );
		g.drawLine( r.nextInt(100), r.nextInt(100), 800 - r.nextInt(100), 600 - r.nextInt(100));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		dibujarLinea( (Graphics2D) g, Color.red ); // ...Sin embargo, esta no admite dudas!!
	}
}
