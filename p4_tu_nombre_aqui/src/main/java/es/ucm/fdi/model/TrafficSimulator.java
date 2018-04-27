package es.ucm.fdi.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.util.MultiTreeMap;

/**
 * Clase que representa el simulador de tráfico. Alamcena como atributos un
 * RoadMap que guarda todos los objetos presentes en la simulación (rm), una
 * lista de eventos a ejecutar (listaEventos), el tiempo de la simulación (time)
 * y un flujo de salida en el que se van generando informes con el resultado de
 * el avance en la simulación.
 * 
 * @author Javier Guzmán y Jorge Villarrubia.
 *
 */
public class TrafficSimulator {

	private int time = 0;
	private MultiTreeMap<Integer, Event> listaEventos = new MultiTreeMap<Integer, Event>();
	private RoadMap rm = new RoadMap();
	private OutputStream out;

	private List<SimulatorListener> listeners = new ArrayList<>();

	public void addSimulatorListener(SimulatorListener l) {
		listeners.add(l);
		notifyRegistered(l);
	}

	public void removeListener(SimulatorListener l) {
		listeners.remove(l);
	}

	private void notifyRegistered(SimulatorListener l) {
		l.registered(time, rm, listaEventos);
	}

	private void notifyReset() { // las excepciones se deben cazar aqui para que
									// se puedan notificar
		for (SimulatorListener sl : listeners) {
			sl.reset(time, rm, listaEventos);
		}
	}

	private void notifyEventAdded() {
		for (SimulatorListener sl : listeners) {
			sl.eventAdded(time, rm, listaEventos);
		}
	}

	private void notifyAdvanced() {
		for (SimulatorListener sl : listeners) {
			sl.advanced(time, rm, listaEventos);
		}
	}

	private void notifyError(SimulatorException e) {
		for (SimulatorListener sl : listeners) {
			sl.simulatorError(time, rm, listaEventos, e);
		}
	}

	// uso interno, evita tener que escribir el mismo bucle muchas veces
	/*
	 * private void fireUpdateEvent(EventType type, String error) { for(Listener
	 * l: listeners){
	 * 
	 * } }
	 */

	public TrafficSimulator(OutputStream out) {
		this.out = out;
	}

	/**
	 * Matodo que ejecuta una serie de pasos en la simulación, guardando el
	 * resultado de cada uno de ellos en el flujo de salida (out) de la clase.
	 * 
	 * @param numPasos
	 *            numero de pasos que se quiere avanzar en la simulación.
	 * @throws IOException
	 *             si no se puede abrir el flujo de salida.
	 * @throws SimulatorException
	 *             si se intenta ejecutar algún evento o avance que vaya en
	 *             contra de la lógica del simulador.
	 */
	public void run(int numPasos) throws IOException, SimulatorException {
		int limiteTiempo = time + numPasos - 1;
		while (time <= limiteTiempo) {
			run();
			notifyAdvanced();
		}
	}

	/**
	 * Ejecuta un paso de la simulación, ejecutando primero los eventos
	 * correspondientes a ese tiempo, avanzando en uno el tiempo de la
	 * simulación, avanzando los cruces y las carreteras y almacenando el
	 * resultado de ese avance en un flujo de salida
	 * 
	 * @throws IOException
	 *             si no se puede abrir el flujo de salida
	 * @throws SimulatorException
	 *             si se intenta hacer algo contrario a la lógica del simulador.
	 */
	public void run() throws IOException, SimulatorException {
		for (Event e : listaEventos.innerValues()) {
			e.execute(rm, time);
		}
		for (Road r : rm.getListaCarreteras()) {
			r.avanza();
		}
		for (Junction j : rm.getListaCruces()) {
			j.avanza();
		}
		++time;
		if (out != null)
			writeReport();
	}

	/**
	 * Añade un evento a la lista de eventos a ejecutar.
	 * 
	 * @param e
	 *            evento a añadir
	 */
	public void addEvent(Event e) {
		if (e.getTime() >= time) {
			listaEventos.putValue(e.getTime(), e);
			notifyEventAdded();
		}
	}

	public void setOut(OutputStream out1) {
		out = out1;
	}

	/**
	 * Método que almacena en el flujo de salida out el estado de la simulación,
	 * mostrando información correspondiente a cruces, carreteras y vehículos
	 * presentes en un determinado momento.
	 * 
	 * @throws IOException
	 *             si no se puede abrir el flujo de salida.
	 */
	public void writeReport() throws IOException {
		Map<String, String> aux = new LinkedHashMap<>();
		for (Junction j : rm.getListaCruces()) {
			j.report(time, aux);
			mapAIni(aux, j.getReportHeader()).store(out);
			out.write('\n');
			aux.clear();
		}
		for (Road r : rm.getListaCarreteras()) {
			r.report(time, aux);
			mapAIni(aux, r.getReportHeader()).store(out);
			out.write('\n');
			aux.clear();
		}
		for (Vehicle v : rm.getListaVehiculos()) {
			v.report(time, aux);
			mapAIni(aux, v.getReportHeader()).store(out);
			out.write('\n');
			aux.clear();
		}
	}

	/**
	 * Crea una IniSection dados un mapa de pares clave-valor y una etiqueta.
	 * 
	 * @param mapa
	 *            con los pares clave-valor de la sección.
	 * @param tag
	 *            cabecera de la sección
	 * @return una IniSection con toda la información suministrada.
	 */
	private IniSection mapAIni(Map<String, String> mapa, String tag) {
		IniSection sec = new IniSection(tag);
		for (Map.Entry<String, String> entry : mapa.entrySet()) {
			sec.setValue(entry.getKey(), entry.getValue());
		}
		return sec;
	}

	public interface SimulatorListener {
		public void registered(int time, RoadMap map,
				MultiTreeMap<Integer, Event> events);

		public void reset(int time, RoadMap map,
				MultiTreeMap<Integer, Event> events);

		public void eventAdded(int time, RoadMap map,
				MultiTreeMap<Integer, Event> events);

		public void advanced(int time, RoadMap map,
				MultiTreeMap<Integer, Event> events);

		public void simulatorError(int time, RoadMap map,
				MultiTreeMap<Integer, Event> events, SimulatorException e);

	}

	public enum EventType {
		REGISTERED, RESET, NEW_EVENT, ADVANCED, ERROR;
	}

	// clase interna en el simulador
	/*
	 * public UpdateEvent(EventType tipo){ this.tipo = tipo; } public EventType
	 * getEvent() { return tipo; } public RoadMap getRoadMap() { return rm; }
	 * public MultiTreeMap<Integer, Event> getEvenQueue() { return listaEventos;
	 * } public int getCurrentTime() { return time; }
	 * 
	 * }
	 */
	public void reset() {
		time = 0;
		listaEventos.clear();
		rm.clear();
		notifyReset();
	}

	public MultiTreeMap<Integer, Event> getEventsList() {
		return listaEventos;
	}

	public OutputStream getOut() {
		return out;
	}

}
