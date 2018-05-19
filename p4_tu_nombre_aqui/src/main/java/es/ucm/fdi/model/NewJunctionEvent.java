package es.ucm.fdi.model;

import java.util.Map;

public class NewJunctionEvent extends Event{
	/**
	 * Evento que representa un objeto de la clase Junction que se añadirá al simulador.
	 * @author Javier Guzmán y Jorge Villarrubia
	 *
	 */
	protected String id;
	public NewJunctionEvent(int time, String id){
		this.time = time;
		this.id = id;
		
	}
	@Override
	/**
	 * Añade un nuevo Cruce al simulador con los datos almacenados si es momento de añadirlo.
	 */
	public void execute(RoadMap rm, int timeExecution) throws SimulatorException{
		if(time == timeExecution){
			if(rm.junctionExist(id)){
				throw new SimulatorException("Duplicated Junction with the id: " + id);
			}
			rm.addJunction(new Junction(id));
		}	
	}
	@Override
	public int compareTo(Event arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String infoParaTabla(){
		return "New Junction " + id;
	}
	
	@Override
	public void describe(Map<String, String> out) {
		out.put("Time", "" + time);
		out.put ("Type", "New Junction " + id);
		
	}
}
