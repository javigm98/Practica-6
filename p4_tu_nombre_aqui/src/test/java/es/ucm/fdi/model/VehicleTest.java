package es.ucm.fdi.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class VehicleTest {

	@Test
	public void testAvanza1() {
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		Road r = new Road ("r1", 80, 23, j1, j2);
		j1.addNewOutgoingRoad(r);
		j2.addNewIncomingRoad(r);
		List <Junction> l = new ArrayList<>();
		l.add(j1);
		l.add(j2);
		Vehicle v = new Vehicle ("v1", 28, l);
		r.entraVehiculo(v);
		r.avanza();
		assertEquals(v.toString(), "id = v1, pos = 23, velActual = 23, km = 23");

	}
	@Test
	public void testAvanza2(){
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		Road r = new Road ("r1", 80, 23, j1, j2);
		j1.addNewOutgoingRoad(r);
		j2.addNewIncomingRoad(r);
		List <Junction> l = new ArrayList<>();
		l.add(j1);
		l.add(j2);
		Vehicle v = new Vehicle ("v1", 28, l);
		r.entraVehiculo(v);
		r.avanza();
		r.avanza();
		r.avanza();
		r.avanza();
		r.avanza();
		j2.avanza();
		assertEquals(v.toString(), "id = v1, pos = 80, velActual = 0, km = 80");
		assertTrue("Deberia haber llegado", v.getHaLlegado());
	}
	@Test
	public void testAvanza3(){
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		Junction j3 = new Junction("j3");
		Road r1 = new Road ("r1", 41, 23, j1, j2);
		Road r2 = new Road("r2", 30, 29, j2, j3);
		j1.addNewOutgoingRoad(r1);
		j2.addNewIncomingRoad(r1);
		j2.addNewOutgoingRoad(r2);
		j3.addNewIncomingRoad(r2);
		List <Junction> l = new ArrayList<>();
		l.add(j1);
		l.add(j2);
		l.add(j3);
		Vehicle v = new Vehicle ("v1", 28, l);
		r1.entraVehiculo(v);
		r1.avanza();
		r1.avanza();
		j2.avanza();
		r2.avanza();
		r2.avanza();
		j3.avanza();
		assertEquals(v.toString(), "id = v1, pos = 30, velActual = 0, km = 71");
		assertTrue("Deberia haber llegado", v.getHaLlegado());
	}
}
