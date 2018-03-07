package es.ucm.fdi.model;

public abstract class Event {
	protected int time;
	public abstract void execute (RoadMap rm, int timeExecution);
	
	public int getTime(){
		return time;
	}
	//public int compareTo(Event e) return int
	
	public Junction checkJunctionExists(RoadMap rm, String id){
		return rm.getJunction(id);
	}
	public Road checkRoadExists(RoadMap rm, String id){
		return rm.getRoad(id);
	}
	public Vehicle checkVehicleExists(RoadMap rm, String id){
		return rm.getVehicle(id);
	}
	public SimObject checkSimObjectExists(RoadMap rm, String id){
		return rm.getSimulatedObject(id);
	}
	
	//public List<Junction> parseListOfJunctions(RoadMap rm, String ) Que es el string?? para que sirve?
}
