package ud.prog3.pr02;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class JLabelEstrella extends JLabel {
	private static final long serialVersionUID = 2L;  // Para serialización
	public static final int TAMANYO_ESTRELLA = 40;  // Píxeles (igual ancho que algo)
	public static final int RADIO_ESFERA_ESTRELLA = 17;  // Radio en píxeles del bounding circle de la estrella(para choques)
	private static final boolean DIBUJAR_ESFERA_ESTRELLA = false;  // Dibujado (para depuración) del bounding circle de choque la estrella
	private long tCreacion;
	
	/** Construye y devuelve el JLabel de la estrella con su gráfico y tamaño
	 */
	public JLabelEstrella() {
		try {
			setIcon( new ImageIcon( JLabelEstrella.class.getResource( "img/estrella.png" ).toURI().toURL() ) );
		} catch (Exception e) {
			System.err.println( "Error en carga de recurso: estrella.png no encontrado" );
			e.printStackTrace();
		}
		setBounds( 0, 0, TAMANYO_ESTRELLA, TAMANYO_ESTRELLA);
		this.tCreacion = System.currentTimeMillis();
	}
	
	// giro
	private double miGiro = Math.PI/2;
	/** Cambia el giro del JLabel
	 * @param gradosGiro	Grados a los que tiene que "apuntar" la estrella,
	 * 						considerados con el 0 en el eje OX positivo,
	 * 						positivo en sentido antihorario, negativo horario.
	 */
	public void setGiro( double gradosGiro ) {
		miGiro = gradosGiro/180*Math.PI;
		miGiro = -miGiro;  // Cambio el sentido del giro
		miGiro = miGiro + Math.PI/2; 
	}

	/**
	 * Añade grados al giro actual de la estrella
	 * @param grados Grados a añadir
	 */
	public void girar(double grados){
		miGiro = miGiro+(grados/180*Math.PI);
	}
	
	// Redefinición del paintComponent para que se escale y se rote el gráfico
	@Override
	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);   // En este caso no nos sirve el pintado normal de un JLabel
		Image img = ((ImageIcon)getIcon()).getImage();
		Graphics2D g2 = (Graphics2D) g;  // El Graphics realmente es Graphics2D
		// Escalado más fino con estos 3 parámetros:
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	
		// Prepara rotación (siguientes operaciones se rotarán)
        g2.rotate( miGiro, TAMANYO_ESTRELLA/2, TAMANYO_ESTRELLA/2 ); // getIcon().getIconWidth()/2, getIcon().getIconHeight()/2 );
        // Dibujado de la imagen
        g2.drawImage( img, 0, 0, TAMANYO_ESTRELLA, TAMANYO_ESTRELLA, null );
        if (DIBUJAR_ESFERA_ESTRELLA) g2.drawOval( TAMANYO_ESTRELLA/2-RADIO_ESFERA_ESTRELLA, TAMANYO_ESTRELLA/2-RADIO_ESFERA_ESTRELLA,
        		RADIO_ESFERA_ESTRELLA*2, RADIO_ESFERA_ESTRELLA*2 );
	}
	
	public long getTCreacion(){
		return this.tCreacion;
	}
}