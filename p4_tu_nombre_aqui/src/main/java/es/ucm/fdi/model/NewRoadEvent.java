package es.ucm.fdi.model;

public class NewRoadEvent extends Event{
	//private Road road;
	private String id, iniId, finId;
	private int longitud, maxVel;
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
	public void execute(RoadMap rm, int timeExecution) {
		if(time == timeExecution){
			Junction ini = rm.getJunction(iniId), fin = rm.getJunction(finId);
			Road r =new Road(id, longitud, maxVel, ini, fin);
			rm.addRoad(r);
			ini.addNewOutgoingRoad(r);
			fin.addNewIncomingRoad(r);
		}
		
	}
	
}
