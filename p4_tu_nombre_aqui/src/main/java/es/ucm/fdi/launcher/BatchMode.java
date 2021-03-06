package es.ucm.fdi.launcher;

import java.io.IOException;
import java.io.InputStream;

import es.ucm.fdi.control.Controller;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.model.TrafficSimulator.SimulatorListener;
import es.ucm.fdi.util.MultiTreeMap;

public class BatchMode implements SimulatorListener {
	/**
	 * 
	 * @param ctr
	 *            proporciona el simulador sobre el que añadir esta clase como
	 *            listener
	 * @param in
	 *            flujo de entrada que servirá para cargar los eventos
	 * @throws IllegalArgumentException
	 *             si no se pudo parsear por algún motivo al cargar los eventos
	 *             e la entrada
	 * @throws IOException
	 *             si no se puede acceder al fichero inicial
	 */
	public void runBachMode(Controller ctr, InputStream in)
			throws IllegalArgumentException, IOException {
		ctr.getSimulator().addSimulatorListener(this);
		ctr.loadEvents(in);
		ctr.run();
	}

	@Override
	public void registered() {

	}

	@Override
	public void reset(int time, RoadMap map, MultiTreeMap<Integer, Event> events) {

	}

	@Override
	public void eventAdded(MultiTreeMap<Integer, Event> events) {

	}

	@Override
	public void advanced(int time, RoadMap map) {

	}

	@Override
	public void simulatorError(String errorMessage) {
		System.err.println(errorMessage);

	}

}
