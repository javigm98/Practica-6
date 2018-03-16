package es.ucm.fdi.model;

public class NewRoadEvent extends Event{
	//private Road road;
	protected String id;
	protected String iniId;
	protected String finId;
	protected int longitud;
	protected int maxVel;
	public NewRoadEvent(int time1, String id1, String iniId1, String finId1, int maxVel1, int longitud1){
		time = time1;
		id = id1;
		iniId = iniId1;
		finId = finId1;
		maxVel = maxVel1;
		longitud = longitud1;
		//road = new Road(id, longitud, maxVel, iniId ,finId);
	}
	
	
	@Override
	public void execute(RoadMap rm, int timeExecution) throws SimulatorException{
		if(time == timeExecution){
			try{
				Junction ini = checkJunctionExists(rm, iniId), fin = checkJunctionExists(rm, finId);
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
	
}
