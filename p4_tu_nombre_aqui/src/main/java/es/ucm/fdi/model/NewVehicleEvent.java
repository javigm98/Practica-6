package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{
	//private Vehicle vehicle;
	private int maxSpeed;
	private String id;
	private String[] ruta;
	public NewVehicleEvent(int time1, String id1, int maxSpeed1, String[] route){
		time = time1;
		id = id1;
		maxSpeed = maxSpeed1;
		ruta = route;
		/*List<Junction> ruta = new ArrayList<>();
		for(String s: route){
			ruta.add(new Junction(s));
		}
		vehicle = new Vehicle(id, maxSpeed, ruta);*/
	}
	@Override
	public void execute(RoadMap rm, int timeExecution) {
		if(time == timeExecution){
			List<Junction> itinerario = new ArrayList<>();
			for(String s: ruta){
				itinerario.add(rm.getJunction(s));
			}
			Vehicle v = new Vehicle(id, maxSpeed, itinerario);
			rm.addVehicle(v);
			v.getRoad().entraVehiculo(v);
		}
		
	}
}
