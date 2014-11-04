package ud.prog3.cap06.pr0506resuelta.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ud.prog3.cap06.pr0506resuelta.BancoDePruebas;
import ud.prog3.cap06.pr0506resuelta.ProcesoProbable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class VentanaBancoDePruebas extends JFrame {
	// Datos de lógica
	private String[] nombresPruebas;
	private ArrayList<ProcesoProbable> procs;
	// Componentes manejables de la ventana
	private JTextField tfTamMin = new JTextField( "0", 5 );
	private JTextField tfTamMax = new JTextField( "1000", 5 );
	private JTextField tfIncTam = new JTextField( "100", 3 );
	private JRadioButton rbIncSuma = new JRadioButton( "suma" );
	private JRadioButton rbIncProd = new JRadioButton( "prod" );
	private JButton bCalcular = new JButton( "Calcular" );
	// Componentes de visualización
	private JTable tDatos = new JTable();
	private PanelDibujoBancoDePruebas pDibujo = new PanelDibujoBancoDePruebas( 3000, 2000, true );
	// Contenedores manejables de la ventana
	private JPanel pConfig = new JPanel();
	private JSplitPane spDatos = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );
	
	public VentanaBancoDePruebas() {
		// Configuración de ventana
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 800, 600 );
		// Configuración de componentes
		ButtonGroup bg = new ButtonGroup();
		bg.add( rbIncSuma );
		bg.add( rbIncProd );
		rbIncSuma.setSelected( true );
		tDatos.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		tDatos.setDefaultRenderer( Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, 
					boolean isSelected, boolean hasFocus, int row, int column) {
				Component def = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (column==0) {
					def.setForeground( pDibujo.getColor(row % procs.size()) ); 
				} else def.setForeground( Color.black );
				return def;
			}
		});
		// Asignación de componentes a contenedores
		spDatos.setLeftComponent( new JScrollPane( tDatos, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED ) );
		spDatos.setRightComponent( pDibujo );
		pConfig.add( new JLabel("Tam.min:") );
		pConfig.add( tfTamMin );
		pConfig.add( new JLabel("Tam.max:") );
		pConfig.add( tfTamMax );
		pConfig.add( new JLabel("Incr.:") );
		pConfig.add( tfIncTam );
		pConfig.add( rbIncSuma );
		pConfig.add( rbIncProd );
		pConfig.add( bCalcular );
		getContentPane().add( spDatos, BorderLayout.CENTER );
		getContentPane().add( pConfig, BorderLayout.SOUTH );
		// Eventos
		bCalcular.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bCalcular.setEnabled( false );
				// Esto es lo habitual (objeto de clase interna anónima):
				// Thread t = new Thread() { public void run() { calcular(); } };
				// Pero en Java 8 se puede hacer con notación lambda:
				Thread t = new Thread( () -> { calcular(); } );
					// Es como crear un objeto  Runnable r = () -> {calcular();};
					// Antes de Java 8, Runnable r = new Runnable() { public void run() { calcular(); } };
				t.start();
			}
		});
		pDibujo.addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				pDibujo.reiniciarDibujo();
			}
		});
		tDatos.getSelectionModel().addListSelectionListener( new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting())
					pDibujo.marcaLineas( tDatos.getSelectedRows() );
			}
		});
	}
	public void setProcesos( String[] pruebas, ArrayList<ProcesoProbable> procs ) {
		this.nombresPruebas = pruebas;
		this.procs = procs;
	}
	
	private void calcular() {
		if (procs==null) return;
		int tamanyo = 0;
		int tamMax = 0;
		int incTam = 0;
		try {
			tamanyo = Integer.parseInt( tfTamMin.getText() );
			tamMax = Integer.parseInt( tfTamMax.getText() );
			incTam = Integer.parseInt( tfIncTam.getText() );
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog( this, "Algún dato de tamaño no es numérico", "Error en datos", JOptionPane.ERROR_MESSAGE );
			return;  // No se calcula
		}
		pDibujo.iniciarDibujo( tamMax, nombresPruebas );
		int numTam = 0;
		// Crear tabla inicial de JTable (primera columna)
			DefaultTableModel dtm = new DefaultTableModel( new Object[] { "Prueba" }, 0 );
			for (int fila=0; fila<procs.size(); fila++) dtm.addRow( new Object[] { "t. " + nombresPruebas[fila] } );
			for (int fila=0; fila<procs.size(); fila++) dtm.addRow( new Object[] { "esp. " + nombresPruebas[fila] } );
			tDatos.setModel( dtm );
			tDatos.revalidate();
		while (tamanyo <= tamMax) {
			dtm.addColumn( ""+tamanyo, new Object[procs.size()*2] );
			//tDatos.revalidate();
			for (int prueba=0; prueba<procs.size(); prueba++) {
				long tiempo = BancoDePruebas.realizaTest( procs.get(prueba), tamanyo );
				int espacio = BancoDePruebas.getTamanyoTest( procs.get(prueba) );
				// System.out.println( "Prueba " + pruebas[prueba] + " de " + tamanyo + " -- tiempo: " + tiempo + " msgs. / espacio = " + espacio + " bytes.");
				// Añadir dato a tabla
				dtm.setValueAt( tiempo, prueba, numTam+1 );
				dtm.setValueAt( espacio/1024+"k", prueba+procs.size(), numTam+1 );
				// Dibujo
				pDibujo.dibujarTiempo( prueba, numTam, tamanyo, tiempo );
				pDibujo.dibujarEspacio( prueba, numTam, tamanyo, espacio );
				pDibujo.repaint();
			}
			if (rbIncSuma.isSelected()) tamanyo += incTam; else tamanyo *= incTam;
			numTam++;
		}
		bCalcular.setEnabled( true );  // Ya ha acabado el hilo, se puede volver a activar
	}


}

