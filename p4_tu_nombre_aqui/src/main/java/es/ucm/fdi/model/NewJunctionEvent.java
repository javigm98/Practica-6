package es.ucm.fdi.model;

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
	public void execute(RoadMap rm, int timeExecution) {
		if(time == timeExecution){
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
}
