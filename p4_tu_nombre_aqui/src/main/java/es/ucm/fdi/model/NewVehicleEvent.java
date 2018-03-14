package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{
	//private Vehicle vehicle;
	protected int maxSpeed;
	protected String id;
	private String[] ruta;
	protected List<Junction> itinerario = new ArrayList<>();
	protected String type = "";
	public NewVehicleEvent(int time1, String id1, int maxSpeed1, String[] route){
		time = time1;
		id = id1;
		maxSpeed = maxSpeed1;
		ruta = route;
		/*List<Junction> ruta = new ArrayList<>();
		for(String s: route){
			ruta.add(new Junction(s));
		}
		Vehicle v = new Vehicle(id, maxSpeed, ruta, "");*/
	}
	@Override
	public void execute(RoadMap rm, int timeExecution) {
		if(time == timeExecution){
			//List<Junction> itinerario = new ArrayList<>();
			for(String s: ruta){
				itinerario.add(checkJunctionExists(rm, s));
			}
			//Si el tipo es coche o bici, no hacemos nada mas aqui, ya lo hara el metodo
			//de cada subclase tras llamar a este super para que cree el itinerario.
			if(type == ""){ 
			Vehicle v = new Vehicle(id, maxSpeed, itinerario);
			rm.addVehicle(v);
			v.getRoad().entraVehiculo(v);
			}
		}
	}
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
