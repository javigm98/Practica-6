package es.ucm.fdi.model;

/**
 * Clase que representa un evento por el cual se averían uno o más vehículos cuyos ids están alamcenados en
 * listaVehiculos con un tiempo duracion.
 * @author Javier Guzmán y Jorge Villarrubia.
 *
 */
public class MakeVehicleFaultyEvent extends Event{
	private String[] listaVehiculos;
	private int duracion;
	
	public MakeVehicleFaultyEvent(int time, int duracion, String[] listaVehiculos){
		this.time = time;
		this.duracion = duracion;
		this.listaVehiculos = listaVehiculos;
	}
	/**
	 * El metodo execute aquí lo unico que hace es poner el tiempo de avería de los vehículos de listaVehiculos
	 * a duracion. 
	 */
	public void execute(RoadMap rm, int timeExecution){
		if(time == timeExecution){
			for(String s : listaVehiculos){
				rm.getVehicle(s).setTiempoAveria(duracion);
			}
		}
	}
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
