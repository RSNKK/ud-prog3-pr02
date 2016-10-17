package ud.prog3.pr02;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class CocheTest {

	Coche c;
	
	@Before
	public void setUp(){
		this.c = new Coche();
	}
	
	@Test
	public void testFuerzaAceleracionAtras(){ //Esto es copy-paste
	    double[] tablaVel = { -500, -425, -300, -250, -200, -100, 0, 125, 250, 500, 1100 };
	    double[] tablaFuerza = { 0, 0.5, 1, 1, 1, 0.65, 0.3, 0.575, 0.85, 0.85, 0.85 };
	    for (int i = 0; i < tablaVel.length; i++){
	      this.c.setVelocidad(tablaVel[i]);
	      assertEquals("Velocidad " + tablaVel[i], tablaFuerza[i] * Coche.FUERZA_BASE_ATRAS, this.c.fuerzaAceleracionAtras(), 0.0000001);
	    }
	}
	
	@Test
	public void testFuerzaAceleracionAdelante(){//Esto no
	    double[] tablaVel = { -500, -425, -300, -250, -200, -75, 0, 125, 250, 500, 1000 };
	    double[] tablaFuerza = { 1, 1, 1, 1, 1, 0.75, 0.5, 0.75, 1, 1, 0 };
	    for (int i = 0; i < tablaVel.length; i++){
	      this.c.setVelocidad(tablaVel[i]);
	      assertEquals("Velocidad " + tablaVel[i], tablaFuerza[i] * Coche.FUERZA_BASE_ADELANTE, this.c.fuerzaAceleracionAdelante(), 0.0000001);
	    }
	}
	
	

}
