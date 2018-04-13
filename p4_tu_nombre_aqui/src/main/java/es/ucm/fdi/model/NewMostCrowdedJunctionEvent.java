package es.ucm.fdi.model;

public class NewMostCrowdedJunctionEvent extends NewJunctionEvent{
	public NewMostCrowdedJunctionEvent(int time, String id) {
		super(time, id);
	}
	
	@Override
	public void execute(RoadMap rm, int timeExecution){
		if(time == timeExecution){
			rm.addJunction(new MostCrowdedJunction(id));
		}
	}
}
