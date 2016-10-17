package ud.prog3.pr02;

/** Clase para definir instancias l�gicas de coches con posici�n, direcci�n y velocidad.
 * @author Andoni Egu�luz
 * Facultad de Ingenier�a - Universidad de Deusto (2014)
 */
public class Coche {
	public static final double MASA = 1.0;
	public static final double FUERZA_BASE_ADELANTE = 2000.0;
	public static final double FUERZA_BASE_ATRAS = 1000.0;
	public static final double COEF_RZTO_SUELO = 15.5;
	public static final double COEF_RZTO_AIRE = 0.35;
	protected double miVelocidad;  // Velocidad en pixels/segundo
	protected double miDireccionActual;  // Direcci�n en la que estoy mirando en grados (de 0 a 360)
	protected double posX;  // Posici�n en X (horizontal)
	protected double posY;  // Posici�n en Y (vertical)
	protected String piloto;  // Nombre de piloto
	
	// Constructores
	
	public Coche() {
		miVelocidad = 0;
		miDireccionActual = 0;
		posX = 300;
		posY = 300;
	}
	
	/** Devuelve la velocidad actual del coche en p�xeles por segundo
	 * @return	velocidad
	 */
	public double getVelocidad() {
		return miVelocidad;
	}

	/** Cambia la velocidad actual del coche
	 * @param miVelocidad
	 */
	public void setVelocidad( double miVelocidad ) {
		this.miVelocidad = miVelocidad;
	}

	public double getDireccionActual() {
		return miDireccionActual;
	}

	public void setDireccionActual( double dir ) {
		// if (dir < 0) dir = 360 + dir;
		if (dir > 360) dir = dir - 360;
		miDireccionActual = dir;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosicion( double posX, double posY ) {
		setPosX( posX );
		setPosY( posY );
	}
	
	public void setPosX( double posX ) {
		this.posX = posX; 
	}
	
	public void setPosY( double posY ) {
		this.posY = posY; 
	}
	
	public String getPiloto() {
		return piloto;
	}

	public void setPiloto(String piloto) {
		this.piloto = piloto;
	}


	/** Cambia la velocidad actual del coche
	 * @param aceleracion	Incremento/decremento de la velocidad en pixels/segundo
	 * @param tiempo	Tiempo transcurrido en segundos
	 */
	public void acelera( double aceleracion, double tiempo ) {
		miVelocidad = MundoJuego.calcVelocidadConAceleracion( miVelocidad, aceleracion, tiempo );
	}
	
	/** Cambia la direcci�n actual del coche
	 * @param giro	Angulo de giro a sumar o restar de la direcci�n actual, en grados (-180 a +180)
	 * 				Considerando positivo giro antihorario, negativo giro horario
	 */
	public void gira( double giro ) {
		setDireccionActual( miDireccionActual + giro );
	}
	
	/** Cambia la posici�n del coche dependiendo de su velocidad y direcci�n
	 * @param tiempoDeMovimiento	Tiempo transcurrido, en segundos
	 */
	public void mueve( double tiempoDeMovimiento ) {
		setPosX( posX + MundoJuego.calcMovtoX( miVelocidad, miDireccionActual, tiempoDeMovimiento ) );
		setPosY( posY + MundoJuego.calcMovtoY( miVelocidad, miDireccionActual, tiempoDeMovimiento ) );
	}
	
	@Override
	public String toString() {
		return piloto + " (" + posX + "," + posY + ") - " +
			   "Velocidad: " + miVelocidad + " ## Dirección: " + miDireccionActual; 
	}
	
	 /** Devuelve la fuerza de aceleración del coche, de acuerdo al motor definido en la práctica 2
	  * @return Fuerza de aceleración en Newtixels   
	  */  
	public double fuerzaAceleracionAdelante() {
		if (miVelocidad<=-150) return FUERZA_BASE_ADELANTE;
		else if (miVelocidad<=0)
			return FUERZA_BASE_ADELANTE*(-miVelocidad/150*0.5+0.5);
		else if (miVelocidad<=250)
			return FUERZA_BASE_ADELANTE*(miVelocidad/250*0.5+0.5);
		else if (miVelocidad<=750)
			return FUERZA_BASE_ADELANTE;
		else return FUERZA_BASE_ADELANTE*(-(miVelocidad-1000)/250);
	}
	
	 /** Devuelve la fuerza de aceleración del coche, de acuerdo al motor definido en la práctica 2
	  * @return Fuerza de aceleración en Newtixels   
	  */  
	  
	  public double fuerzaAceleracionAtras(){
	    if (this.miVelocidad <= -350) {
	      return FUERZA_BASE_ATRAS * ((this.miVelocidad + 500) / 150);
	    }
	    if (this.miVelocidad <= -200) {
	      return FUERZA_BASE_ATRAS;
	    }
	    if (this.miVelocidad <= 0) {
	      return FUERZA_BASE_ATRAS * (-this.miVelocidad / 200* 0.7 + 0.3);
	    }
	    if (this.miVelocidad <= 250) {
	      return FUERZA_BASE_ATRAS * (this.miVelocidad / 250* 0.55 + 0.3);
	    }
	    return 850;
	  }
}
