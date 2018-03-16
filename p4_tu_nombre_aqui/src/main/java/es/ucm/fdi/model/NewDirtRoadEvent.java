package es.ucm.fdi.model;

public class NewDirtRoadEvent extends NewRoadEvent{

	public NewDirtRoadEvent(int time, String id, String iniId,
			String finId, int maxVel, int longitud1) {
		super(time, id, iniId, finId, maxVel, longitud1);
	}
	
	@Override
	public void execute(RoadMap rm, int timeExecution) throws SimulatorException{
		if(time == timeExecution){
			try{
			Junction ini = checkJunctionExists(rm, iniId), fin = checkJunctionExists(rm, finId);
			DirtRoad r = new DirtRoad(id, longitud, maxVel, ini, fin);
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
