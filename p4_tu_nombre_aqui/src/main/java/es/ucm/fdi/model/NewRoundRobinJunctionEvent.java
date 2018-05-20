package es.ucm.fdi.model;

public class NewRoundRobinJunctionEvent extends NewJunctionEvent {
	private int maxIntervalo, minIntervalo;

	public NewRoundRobinJunctionEvent(int time, String id, int minIntervalo,
			int maxIntervalo) {
		super(time, id);
		this.minIntervalo = minIntervalo;
		this.maxIntervalo = maxIntervalo;
	}

	@Override
	public void execute(RoadMap rm, int timeExecution)
			throws SimulatorException {
		if (time == timeExecution) {
			if (rm.junctionExist(id)) {
				throw new SimulatorException(
						"Duplicated Junction with the id: " + id);
			}
			rm.addJunction(new RoundRobinJunction(id, minIntervalo,
					maxIntervalo));
		}
	}

}
