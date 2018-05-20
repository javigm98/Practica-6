package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Evento que representa un objeto de la clase Vehicle que se añadirá al simulador.
 * @author Javier Guzmán y Jorge Villarrubia
 *
 */
public class NewVehicleEvent extends Event{
	protected int maxSpeed;
	protected String id;
	private String[] ruta;
	protected List<Junction> itinerario = new ArrayList<>();
	protected String type = "";
	public NewVehicleEvent(int time, String id, int maxSpeed, String[] ruta){
		this.time = time;
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.ruta = ruta;
	}
	
	/**
	 * Metodo que añade un nuevo vehículo al simulador con los datos que tenemos si es el momento de añadirlo.
	 * Lanza una excepción si no existe alguno de los cruces de su itinerario.
	 */
	@Override
	public void execute(RoadMap rm, int timeExecution) throws SimulatorException{
		if(time == timeExecution){
			if (rm.getVehicle(id) != null) {
				throw new SimulatorException("Duplicated Vehicle with the id: "
						+ id);
			}
			for(String s: ruta){
				try{
					itinerario.add(rm.getJunction(s));
				}
				catch(SimulatorException se){
					throw new SimulatorException("Unknown junction in the " + id + " itinerary ", se);
				}
			}
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
	@Override
	public String infoParaTabla(){
		return "New Vehicle " + id;
	}

	@Override
	public void describe(Map<String, String> out) {
		out.put("Time", "" + time);
		out.put ("Type", "New Vehicle " + id);
		
	}
}
