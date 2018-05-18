package es.ucm.fdi.model;

import java.util.Map;

/**
 * Evento que representa un objeto de la clase Road que se añadirá al simulador.
 * @author Javier Guzmán y Jorge Villarrubia
 *
 */

public class NewRoadEvent extends Event{
	protected String id;
	protected String iniId;
	protected String finId;
	protected int longitud;
	protected int maxVel;
	public NewRoadEvent(int time, String id, String iniId, String finId, int maxVel, int longitud){
		this.time = time;
		this.id = id;
		this.iniId = iniId;
		this.finId = finId;
		this.maxVel = maxVel;
		this.longitud = longitud;
	}
	/**
	 * Metodo que añade una nueva carretera al simulador con los datos que tenemos si es el momento de añadirla.
	 * Lanza una excepción si no existen los cruces inicial o final.
	 */
	
	@Override
	public void execute(RoadMap rm, int timeExecution) throws SimulatorException{
		if(rm.getRoad(id) != null){
			throw new SimulatorException("Duplicated Road with the id: " + id);
		}
		if(time == timeExecution){
			try{
				Junction ini = rm.getJunction(iniId),
						fin = rm.getJunction(finId);
				Road r = new Road(id, longitud, maxVel, ini, fin);
				rm.addRoad(r);
				ini.addNewOutgoingRoad(r);
				fin.addNewIncomingRoad(r);
			}
			catch(SimulatorException se){
				throw new SimulatorException("Ini or end Junction of the road " + id + " doesn't exist ", se);
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
		return "New Road " + id;
	}
	
	@Override
	public void describe(Map<String, String> out) {
		out.put("Time", "" + time);
		out.put ("Type", "New Road " + id);
		
	}
	
}
