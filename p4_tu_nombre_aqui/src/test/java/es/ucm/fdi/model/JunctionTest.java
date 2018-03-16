package es.ucm.fdi.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class JunctionTest {

	@Test
	public void test() {
		Junction j1 = new Junction ("j1");
		Junction j2 = new Junction ("j2");
		Junction j3 = new Junction ("j3");
		Junction j4 = new Junction ("j4");
		Road r1 = new Road ("r1", 20, 20, j1, j2) ;
		j1.addNewOutgoingRoad(r1);
		j2.addNewIncomingRoad(r1);
		Road r2 = new Road ("r2", 20, 20, j3, j2) ;
		j3.addNewOutgoingRoad(r2);
		j2.addNewIncomingRoad(r2);
		Road r3 = new Road ("r3", 2, 20, j2, j4) ;
		j2.addNewOutgoingRoad(r3);
		j4.addNewIncomingRoad(r3);
		List <Junction> l1 =  new ArrayList<>();
		List <Junction> l2 =  new ArrayList<>();
		l1.add(j1);
		l1.add(j2);
		l1.add(j4);
		l2.add(j3);
		l2.add(j2);
		l2.add(j4);
		Vehicle v1 = new Vehicle("v1", 20, l1 );
		r1.entraVehiculo(v1);
		Vehicle v2 = new Vehicle("v2", 20, l1 );
		r1.entraVehiculo(v2);
		Vehicle v3 = new Vehicle("v3", 20, l2 );
		r2.entraVehiculo(v3);
		Vehicle v4 = new Vehicle("v4", 20, l2 );
		r2.entraVehiculo(v4);
		r1.avanza();
		r2.avanza();
		j2.avanza();
		r1.avanza();
		r2.avanza();
		assertEquals(j2.toStringTest(), "(r1, green, [v1, v2]) (r2, red, [v3, v4]) ");
		j2.avanza();
		assertEquals(j2.toStringTest(), "(r1, red, [v2]) (r2, green, [v3, v4]) ");
		assertEquals(v1.toString(), "id = v1, pos = 0, velActual = 0, km = 20");
		j2.avanza();
		assertEquals(j2.toStringTest(), "(r1, green, [v2]) (r2, red, [v4]) ");
		j2.avanza();
		assertEquals(j2.toStringTest(), "(r1, red, []) (r2, green, [v4]) ");
		j2.avanza();
		assertEquals(j2.toStringTest(), "(r1, green, []) (r2, red, []) ");
	}

}
