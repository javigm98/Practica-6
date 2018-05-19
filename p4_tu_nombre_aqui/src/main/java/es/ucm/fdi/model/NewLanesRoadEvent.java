package es.ucm.fdi.model;

/**
 * Evento que representa un objeto de la clase LanesRoad que se añadirá al simulador.
 * @author Javier Guzmán y Jorge Villarrubia
 *
 */

public class NewLanesRoadEvent extends NewRoadEvent{
private int numCarriles;
	public NewLanesRoadEvent(int time, String id, String iniId,
			String finId, int maxVel, int longitud, int numCarriles) {
		super(time, id, iniId, finId, maxVel, longitud);
		this.numCarriles = numCarriles;
	}
	
	/**
	 * Metodo que añade una nueva autopista al simulador con los datos que tenemos si es el momento de añadirla.
	 * Lanza una excepción si no existen los cruces inicial o final.
	 */
	@Override
	public void execute(RoadMap rm, int timeExecution) throws IllegalArgumentException{
		if(time == timeExecution){
			if(rm.getRoad(id) != null){
				throw new SimulatorException("Duplicated Road with the id: " + id);
			}
			try{
				Junction ini = rm.getJunction(iniId),
						fin = rm.getJunction(finId);
			
			LanesRoad r = new LanesRoad(id, longitud, maxVel, ini, fin, numCarriles);
			rm.addRoad(r);
			ini.addNewOutgoingRoad(r);
			fin.addNewIncomingRoad(r);
			}
			catch(SimulatorException se){
				throw new SimulatorException("Ini or end Junction of the road " + id + " doesn't exist", se);
			}
		}
		
	}
	

}
