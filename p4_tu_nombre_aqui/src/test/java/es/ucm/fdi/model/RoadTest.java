package es.ucm.fdi.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RoadTest {

	@Test
	public void testAverias() {
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		Road r1 = new Road ("r1", 87, 40, j1, j2);
		j1.addNewOutgoingRoad(r1);
		j2.addNewIncomingRoad(r1);
		List <Junction> l = new ArrayList<>();
		l.add(j1);
		l.add(j2);
		Vehicle v1 = new Vehicle ("v1", 28, l);
		Vehicle v2 = new Vehicle ("v2", 37, l);
		r1.entraVehiculo(v1);
		r1.avanza();
		r1.entraVehiculo(v2);
		v1.setTiempoAveria(4);
		r1.avanza();
		r1.avanza();
		r1.avanza();
		r1.avanza();
		r1.avanza();
		assertEquals(r1.toString(), "id = v2, pos = 72, velActual = 21, km = 72 id = v1, pos = 49, velActual = 21, km = 49 ");
	}

}
