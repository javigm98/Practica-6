package es.ucm.fdi.model;

public class NewRoundRobinJunctionEvent extends NewJunctionEvent{
	private int maxIntervalo, minIntervalo;
	public NewRoundRobinJunctionEvent(int time, String id, int minIntervalo, int maxIntervalo) {
		super(time, id);
		this.minIntervalo = minIntervalo;
		this.maxIntervalo = maxIntervalo;
	}
	
	@Override
	public void execute(RoadMap rm, int timeExecution){
		if(time == timeExecution){
			rm.addJunction(new RoundRobinJunction(id, minIntervalo, maxIntervalo));
		}
	}

}
