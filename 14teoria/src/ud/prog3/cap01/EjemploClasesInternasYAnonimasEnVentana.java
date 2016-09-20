package ud.prog3.cap01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import java.util.Random;


//Opción 1: clase externa

class EscuchadorBotonVentanaHilos1 implements ActionListener
{
	JTextArea ta;
	public EscuchadorBotonVentanaHilos1(JTextArea ta) {
		this.ta = ta;
	}
	public void actionPerformed(ActionEvent e) {
		ta.append( "    Soy Swing y detecto [EXT] Botón Pulsado: " + new Date() + "\n" );		
	}
}

@SuppressWarnings("serial")
public class EjemploClasesInternasYAnonimasEnVentana extends JFrame
{
	
	// Opción 2: clase interna
	private class EscuchadorBotonVentanaHilos2 implements ActionListener
	{
		// Con clase interna, hay acceso directo a los atributos de la clase que la engloba
//		JTextArea ta;
//		public EscuchadorBotonVentanaHilos2(JTextArea ta) {
//			this.ta = ta;
//		}
		public void actionPerformed(ActionEvent e) {
			// test de carga excesiva de swing
			// double r = tardoUnBuenRato();
			// r = r * tardoUnBuenRato() * tardoUnBuenRato() * tardoUnBuenRato();
			// System.out.println( r );
			area.append( "    Soy Swing y detecto [INT] Botón Pulsado: " + new Date() + "\n" );		
		}
	}

	JPanel panelBoton;
	JTextArea area;
	JButton boton;

	public EjemploClasesInternasYAnonimasEnVentana()
	{
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );  // Cerramos el programa para acabar también el hilo de main (con DISPOSE_ON_CLOSE este seguiría)
		area = new JTextArea( 20, 50 );
		panelBoton = new JPanel();
		boton = new JButton("Pulsa Aquí");

		panelBoton.add(boton);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add( new JScrollPane(area), "Center" );
		this.getContentPane().add(panelBoton,"South");

		// Con las opciones 1 o 2, se añade un objeto escuchador
		
		// Con la 1 (externa) se crea pasando info de la textarea
		EscuchadorBotonVentanaHilos1 escuchador1 = 
				new EscuchadorBotonVentanaHilos1( area );
		boton.addActionListener( escuchador1 );

		// Con la 2 (interna) no hace falta esa información
		EscuchadorBotonVentanaHilos2 escuchador2 = 
				new EscuchadorBotonVentanaHilos2( );
		boton.addActionListener( escuchador2 );
		
		// Opción 3: Clase interna anónima
//
//		ActionListener escuchador3 = new ActionListener()
//			{
//				public void actionPerformed(ActionEvent e) {
//					area.append( "  Soy Swing y detecto [ANON] Botón Pulsado: " + new Date() + "\n" );		
//				}
//			}
//		boton.addActionListener( escuchador3 );
//      
//		O más anónimo aún...  (sin dar nombre al objeto)
//
		boton.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					area.append( "    Soy Swing y detecto [ANON] Botón Pulsado: " + new Date() + "\n" );		
				}
			}
		);

		this.setSize(600,400);
		this.setTitle("Ejemplo Sencillo para entender hilos");
	}
	
	private void procesoIntenso() {
		while (true) {
			// Hago algo que tarda mucho tiempo
			double r = tardoUnBuenRato();
			// Y visualizo el resultado
			area.append( "Soy main y calculo esto: " + r + "\n" );
		}
	}
	
	private double tardoUnBuenRato() {
		double r = (new Random()).nextFloat();
		for (int i = 0; i<2000000000; i++) {
			for (int j = 1; i<2000000000; i++) {
				for (int k = 0; i<200000000; i++) {
					r = r + i / j * k; // r * r * Math.PI * i * i / j * k * Math.sin( k );
				}
			}				
		}
		return r;
	}
	
	public static void main(String[] args)
	{
		EjemploClasesInternasYAnonimasEnVentana v = 
			new EjemploClasesInternasYAnonimasEnVentana();
		v.setVisible(true);
		v.procesoIntenso();
	}
}
