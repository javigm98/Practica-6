package es.ucm.fdi.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CarTest {

	@Test
	public void test() {
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		Road r = new Road ("r1", 80, 23, j1, j2);
		j1.addNewOutgoingRoad(r);
		j2.addNewIncomingRoad(r);
		List <Junction> l = new ArrayList<>();
		l.add(j1);
		l.add(j2);
		Car v1 = new Car ("v1", 28, l, 20, 2, 0.8, 134); // Aguanta solo 20 km, 2 ciclos averiado, 0.98 e probabilidade averiarse
		r.entraVehiculo(v1);
		r.avanza();
		r.avanza();
		assertTrue("Deberia averiarse", v1.estaAveriado()); // Con esta semilla y probabilidad, se averia seguro
		Car v2 = new Car ("v2", 28, l, 20, 2, 0.7, 134); //Con la misma semilla y un 10% menos de probabilidad, ya no se averia
		r.entraVehiculo(v2);
		r.avanza();
		r.avanza();
		assertFalse("No deberia estar averiado", v2.estaAveriado());
	}

}
