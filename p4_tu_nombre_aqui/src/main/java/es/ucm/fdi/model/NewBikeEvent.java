package es.ucm.fdi.model;

import java.util.List;
/**
 * Evento que representa un objeto de la clase Bike que se añadirá al simulador.
 * @author Javier Guzmán y Jorge Villarrubia
 *
 */

public class NewBikeEvent extends NewVehicleEvent{

	public NewBikeEvent(int time, String id, int maxSpeed, String[] route) {
		super(time, id, maxSpeed, route);
		this.type = "bike";
	}
	
	/**
	 * Metodo que añade una nueva bici al simulador con los datos que tenemos si le es momento de añadirla.
	 * Lanza una excepción si no se puede crear la bici con el constructor de Bike.
	 */
	@Override
	public void execute(RoadMap rm, int timeExecution)throws SimulatorException{
		if(time == timeExecution){
			super.execute(rm, timeExecution); 
			Bike b = new Bike(id, maxSpeed, itinerario); 
			rm.addVehicle(b); 
			b.getRoad().entraVehiculo(b);
		}
	}
	
}
