package es.ucm.fdi.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LanesRoadTest {

	@Test
	public void factorReduccionTest() {
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		LanesRoad r1 = new LanesRoad ("r1", 100, 23, j1, j2, 3); // 3 carriles
		j1.addNewOutgoingRoad(r1);
		j2.addNewIncomingRoad(r1);
		List <Junction> l = new ArrayList<>();
		l.add(j1);
		l.add(j2);
		Vehicle v1 = new Vehicle ("v1", 28, l);
		Vehicle v2 = new Vehicle ("v2", 37, l);
		Vehicle v3 = new Vehicle ("v3", 32, l);
		Vehicle v4 = new Vehicle ("v4", 65, l);
		Vehicle v5 = new Vehicle ("v5", 10, l);
		r1.entraVehiculo(v1);
		r1.entraVehiculo(v2);
		r1.entraVehiculo(v3);
		r1.entraVehiculo(v4);
		r1.entraVehiculo(v5);
		r1.avanza();
		assertEquals(v1.getVelActual(), 14); // ((3*23)/5) + 1
		assertEquals(v5.getVelActual(), 10);
		r1.saleVehiculo(v1);
		r1.saleVehiculo(v2); //Ahora el maximo deberia ser 23. ((3*23)/3) + 1 > 23
		r1.avanza();
		assertEquals(v3.getVelActual(), 23);
		assertEquals(v3.getPos(), 37);
		assertEquals(v5.getPos(), 20);
	}
	@Test
	public void averiasLanesRoadTest(){
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		LanesRoad r1 = new LanesRoad ("r1", 100, 23, j1, j2, 3); // 3 carriles
		j1.addNewOutgoingRoad(r1);
		j2.addNewIncomingRoad(r1);
		List <Junction> l = new ArrayList<>();
		l.add(j1);
		l.add(j2);
		Vehicle v1 = new Vehicle ("v1", 28, l);
		Vehicle v2 = new Vehicle ("v2", 37, l);
		Vehicle v3 = new Vehicle ("v3", 32, l);
		Vehicle v4 = new Vehicle ("v4", 65, l);
		Vehicle v5 = new Vehicle ("v5", 10, l);
		r1.entraVehiculo(v1);
		r1.entraVehiculo(v2);
		r1.entraVehiculo(v3);
		r1.entraVehiculo(v4);
		r1.entraVehiculo(v5);
		v1.setTiempoAveria(1);
		v2.setTiempoAveria(2);
		v3.setTiempoAveria(3); // Averio al menos el mismo numero de carriles para ver si me reduce velBase  de uno que vaya detras a la mitad
		r1.avanza();
		assertEquals(v4.getVelActual(), 7); // velBase /= 2??
		r1.avanza(); // Ahora el el v1 dejara de estar averiado y la velocidad no tendra que dividirse entre 2
		assertEquals(v4.getVelActual(), 14);
	}
	
}
