package es.ucm.fdi.model;

public class NewMostCrowdedJunctionEvent extends NewJunctionEvent {
	public NewMostCrowdedJunctionEvent(int time, String id) {
		super(time, id);
	}

	@Override
	public void execute(RoadMap rm, int timeExecution)
			throws SimulatorException {
		if (time == timeExecution) {
			if (rm.junctionExist(id)) {
				throw new SimulatorException(
						"Duplicated Junction with the id: " + id);
			}
			rm.addJunction(new MostCrowdedJunction(id));
		}
	}
}
