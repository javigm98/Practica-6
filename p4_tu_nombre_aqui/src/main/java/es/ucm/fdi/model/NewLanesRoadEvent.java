package es.ucm.fdi.model;

public class NewLanesRoadEvent extends NewRoadEvent{
private int numCarriles;
	public NewLanesRoadEvent(int time, String id, String iniId,
			String finId, int maxVel, int longitud, int numCarriles) {
		super(time, id, iniId, finId, maxVel, longitud);
		this.numCarriles = numCarriles;
	}
	
	@Override
	public void execute(RoadMap rm, int timeExecution) throws IllegalArgumentException{
		if(time == timeExecution){
			try{
				Junction ini = checkJunctionExists(rm, iniId), fin = checkJunctionExists(rm, finId);
			
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
