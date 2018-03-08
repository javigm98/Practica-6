package es.ucm.fdi.model;

import java.util.Comparator;

public class VehicleComparator implements Comparator<Vehicle>{

	@Override
	public int compare(Vehicle o1, Vehicle o2) {
		return o2.getPos() - o1.getPos();
	}
	
}
