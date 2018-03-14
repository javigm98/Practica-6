package es.ucm.fdi.model;

import java.util.List;

public class NewBikeEvent extends NewVehicleEvent{

	public NewBikeEvent(int time, String id, int maxSpeed, String[] route) {
		super(time, id, maxSpeed, route);
		this.type = "bike";
	}
	
	@Override
	public void execute(RoadMap rm, int timeExecution){
		if(time == timeExecution){
			super.execute(rm, timeExecution); //Aqui creo el itinerario ya que type == "bike"
			Bike b = new Bike(id, maxSpeed, itinerario); //creo un objeto bike que añado al roadMap
			rm.addVehicle(b); 
			b.getRoad().entraVehiculo(b);
		}
	}
	
}
