package ud.prog3.cap04;

import javax.swing.*;

import java.awt.*;

public class OchoDamas extends JFrame {
	JLabel[][] tablero = new JLabel[8][8];
	JLabel lMensaje = new JLabel();
	public OchoDamas() {
		setSize( 800,600 );
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		JPanel pCentro = new JPanel();
		pCentro.setLayout( new GridLayout(8,8) );
		for (int fila = 0; fila<8; fila++)
			for (int col = 0; col<8; col++) {
				tablero[fila][col] = new JLabel( " " );
				tablero[fila][col].setBorder( BorderFactory.createLineBorder( Color.black ));
				tablero[fila][col].setFont( new Font( "Arial", Font.BOLD, 24 ) );
				tablero[fila][col].setHorizontalAlignment( JLabel.CENTER );
				pCentro.add( tablero[fila][col] );
			}
		lMensaje.setFont( new Font( "Arial", Font.BOLD, 24 ) );
		lMensaje.setHorizontalAlignment( JLabel.CENTER );
		getContentPane().add( pCentro, BorderLayout.CENTER );
		getContentPane().add( lMensaje, BorderLayout.SOUTH );
	}
	
	public static void main(String[] args) {
		OchoDamas v = new OchoDamas();
		v.setVisible( true );
		v.resolverTableroDesdeFila( v.tablero, 0 );
	}

	public void resolverTableroDesdeFila( JLabel[][] tablero, int fila ) {
		try { Thread.sleep(100); } catch (Exception e) {}
		// TODO Aquí cambiar y hacer:
		tablero[0][0].setText( "D" );
		tablero[1][0].setText( "D" );
		lMensaje.setText( "Es correcto? " + esPosicionCorrecta(tablero) );
	}
	
	public boolean esPosicionCorrecta( JLabel[][] tablero ) {
		for (int fila = 0; fila<8; fila++)
			for (int col = 0; col<8; col++)
				if (tablero[fila][col].getText().equals("D")) {
					boolean hayOtraDama = hayOtraDamaEn( tablero, fila, col );
					if (hayOtraDama) return false;
				}
		return true;
	}
	
	public boolean hayOtraDamaEn( JLabel[][] tablero, int fila, int col ) {
		for (int f = 0; f<8; f++)
			for (int c = 0; c<8; c++)
				if ((fila!=f || col!=c) && tablero[f][c].getText().equals("D")) {
					System.out.print( "Dama en " + fila + "," + col );
					System.out.println( " amenaza a otra dama en " + f + "," + c );
					return true;
				}
		return false;
	}
	
}
