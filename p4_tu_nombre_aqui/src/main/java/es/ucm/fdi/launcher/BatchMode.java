package es.ucm.fdi.launcher;

import java.io.IOException;
import java.io.InputStream;

import es.ucm.fdi.control.Controller;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.model.TrafficSimulator.SimulatorListener;
import es.ucm.fdi.util.MultiTreeMap;

public class BatchMode implements SimulatorListener{

	public void runBachMode(Controller ctr, InputStream in) throws IllegalArgumentException, IOException {
		ctr.getSimulator().addSimulatorListener(this);
		ctr.loadEvents(in);
		ctr.run();
	}
	
	@Override
	public void registered(int time, RoadMap map,
			MultiTreeMap<Integer, Event> events) {
		
	}

	@Override
	public void reset(int time, RoadMap map, MultiTreeMap<Integer, Event> events) {
		
	}

	@Override
	public void eventAdded(int time, RoadMap map,
			MultiTreeMap<Integer, Event> events) {
		
	}

	@Override
	public void advanced(int time, RoadMap map,
			MultiTreeMap<Integer, Event> events) {
		
	}

	@Override
	public void simulatorError(int time, RoadMap map,
			MultiTreeMap<Integer, Event> events, SimulatorException e) {
		System.err.println("" + e + e.getCause());
		
	}

}
