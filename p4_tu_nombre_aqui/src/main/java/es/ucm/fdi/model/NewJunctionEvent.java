package es.ucm.fdi.model;

public class NewJunctionEvent extends Event{
	//private Junction junction;
	String id;
	public NewJunctionEvent(int time1, String id1){
		time = time1;
		id = id1;
		//junction = new Junction(id);
	}
	@Override
	public void execute(RoadMap rm, int timeExecution) {
		if(time == timeExecution){
			rm.addJunction(new Junction(id));
		}	
	}
}
