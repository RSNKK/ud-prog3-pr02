package ud.prog3.pr02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

/** Clase principal de minijuego de coche para Pr�ctica 02 - Prog III
 * Ventana del minijuego.
 * @author Andoni Egu�luz
 * Facultad de Ingenier�a - Universidad de Deusto (2014)
 */
public class VentanaJuego extends JFrame {
	private static final long serialVersionUID = 1L;  // Para serializaci�n
	JPanel pPrincipal;         // Panel del juego (layout nulo)
	MundoJuego miMundo;        // Mundo del juego
	CocheJuego miCoche;        // Coche del juego
	MiRunnable miHilo = null;  // Hilo del bucle principal de juego	
	boolean[] bMov = new boolean[4]; //Array de booleanos para el movimiento
	int punt = 0; //Puntuación
	int perd = 0; //Estrellas perdidas
	JLabel lMensaje;

	/** Constructor de la ventana de juego. Crea y devuelve la ventana inicializada
	 * sin coches dentro
	 */
	public VentanaJuego() {
		// Liberación de la ventana por defecto al cerrar
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		// Creación contenedores y componentes
		pPrincipal = new JPanel();

		// Formato y layouts
		pPrincipal.setLayout( null );
		pPrincipal.setBackground( Color.white );
		// Añadido de componentes a contenedores
		add( pPrincipal, BorderLayout.CENTER );
		lMensaje = new JLabel("",SwingConstants.CENTER);
		add( lMensaje, BorderLayout.SOUTH );
		// Formato de ventana
		setSize( 1000, 750 );
		setResizable( false );

		
		// Añadido para que también se gestione por teclado con el KeyListener
		pPrincipal.addKeyListener( new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP: {
						bMov[0] = true;
//						miCoche.acelera( +5, 1 );
						break;
					}
					case KeyEvent.VK_DOWN: {
						bMov[1] = true;
//						miCoche.acelera( -5, 1 );
						break;
					}
					case KeyEvent.VK_LEFT: {
						bMov[2] = true;
//						miCoche.gira( +10 );
						break;
					}
					case KeyEvent.VK_RIGHT: {
						bMov[3] = true;
//						miCoche.gira( -10 );
						break;
					}
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP: {
						bMov[0] = false;
						break;
					}
					case KeyEvent.VK_DOWN: {
						bMov[1] = false;
						break;
					}
					case KeyEvent.VK_LEFT: {
						bMov[2] = false;
						break;
					}
					case KeyEvent.VK_RIGHT: {
						bMov[3] = false;
						break;
					}
				}
			}	
		});
		pPrincipal.setFocusable(true);
		pPrincipal.requestFocus();
		pPrincipal.addFocusListener( new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				pPrincipal.requestFocus();
			}
		});
		// Cierre del hilo al cierre de la ventana
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (miHilo!=null) miHilo.acaba();
			}
		});
	}
	
	/** Programa principal de la ventana de juego
	 * @param args
	 */
	public static void main(String[] args) {
		// Crea y visibiliza la ventana con el coche
		try {
			final VentanaJuego miVentana = new VentanaJuego();
			SwingUtilities.invokeAndWait( new Runnable() {
				@Override
				public void run() {
					miVentana.setVisible( true );
				}
			});
			miVentana.miMundo = new MundoJuego( miVentana.pPrincipal );
			miVentana.miMundo.creaCoche( 150, 100 );
			miVentana.miCoche = miVentana.miMundo.getCoche();
			miVentana.miCoche.setPiloto( "Fernando Alonso" );
			// Crea el hilo de movimiento del coche y lo lanza
			miVentana.miHilo = miVentana.new MiRunnable();  // Sintaxis de new para clase interna
			Thread nuevoHilo = new Thread( miVentana.miHilo );
			nuevoHilo.start();
		} catch (Exception e) {
			System.exit(1);  // Error anormal
		}
	}
	
	/** Clase interna para implementaci�n de bucle principal del juego como un hilo
	 * @author Andoni Egu�luz
	 * Facultad de Ingenier�a - Universidad de Deusto (2014)
	 */
	class MiRunnable implements Runnable {
		boolean sigo = true;
		@Override
		public void run() {
			// Bucle principal forever hasta que se pare el juego...
			while (sigo) {
				//Control del movimiento
				double fuerza = 0;
				if(bMov[0])
					fuerza = miCoche.fuerzaAceleracionAdelante();
				if(bMov[1])
					fuerza = -miCoche.fuerzaAceleracionAtras();
				if(bMov[2])
					miCoche.gira(+10);
				if(bMov[3])
					miCoche.gira(-10);
				
				MundoJuego.aplicarFuerza(fuerza, miCoche);
				// Mover coche
				miCoche.mueve( 0.040 );
				// Chequear choques
				// (se comprueba tanto X como Y porque podría a la vez chocar en las dos direcciones (esquinas)
				if (miMundo.hayChoqueHorizontal(miCoche)) // Espejo horizontal si choca en X
					miMundo.rebotaHorizontal(miCoche);
				if (miMundo.hayChoqueVertical(miCoche)) // Espejo vertical si choca en Y
					miMundo.rebotaVertical(miCoche);
				
				miMundo.creaEstrella();
				perd += miMundo.quitaYRotaEstrellas(6000);
				punt += 5*miMundo.choquesConEstrellas();
				lMensaje.setText("Puntuación: " + punt + " Estrellas perdidas: "+perd);
				//Fin de la partida
				if(perd >= 10){
					lMensaje.setText("¡Fin de la partida! Puntuación: " +punt);
					acaba();
				}
				// Dormir el hilo 40 milisegundos
				try {
					Thread.sleep( 40 );
				} catch (Exception e) {
				}
			}
		}
		/** Ordena al hilo detenerse en cuanto sea posible
		 */
		public void acaba() {
			sigo = false;
		}
	};
	
}
