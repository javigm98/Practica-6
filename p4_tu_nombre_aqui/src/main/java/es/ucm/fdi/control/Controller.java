package es.ucm.fdi.control;

import java.io.*;
import java.util.List;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.model.TrafficSimulator;

/**
 * Controlador del simulador de tráfico, se encaraga de ejecutar los pasos de la ejecución.
 * @author Javier Guzmán y Jorge Villarrubia.
 *
 */
public class Controller {
	private TrafficSimulator simulator;
	private int pasos;

	
	private EventBuilder[] bs = {new NewVehicleEventBuilder(), new NewJunctionEventBuilder(), 
			new NewRoadEventBuilder(), new MakeVehicleFaultyEventBuilder(), 
			new NewCarEventBuilder(), new NewBikeEventBuilder(),
			new NewLanesRoadEventBuilder(), new NewDirtRoadEventBuilder(),
			new NewRoundRobinJunctionEventBuilder(), new NewMostCrowdedJunctionEventBuilder()};
	
	public Controller(TrafficSimulator simulator, int pasos){
		this.simulator = simulator;
		this.pasos = pasos;
	}
	
	/**
	 * Carga en la lista de eventos del simulador los eventos de un flujo de entrada dado.
	 * @param in1 flujo del que se leerán los datos.
	 * @throws IOException si no se puede acceder al fichero inicial.
	 */
	public void loadEvents(InputStream in1) throws IOException{
		Ini first = new Ini(in1);
		List<IniSection> listaSecciones = first.getSections();
		for (IniSection sec: listaSecciones){
			try{
				simulator.addEvent(parseSection(sec));
			}
			catch(IllegalArgumentException e){
				System.out.println(e);
			}
			catch(SimulatorException se){
				System.out.println("" + se + se.getCause());
			}
		}
	}
	 
	/**
	 * Dado una sección Ini decide a que evento corresponde.
	 * @param sec sección Ini a parsear.
	 * @return el evento correspondiente a la sección.
	 * @throws IllegalArgumentException si no se encuentra el evento al que corresponde la sección.
	 * @throws SimulatorException si el evento no respeta la lógica que se requiere para implementar el simulador.
	 */
	 public Event parseSection(IniSection sec)throws IllegalArgumentException, SimulatorException{
		Event e = null;
		for(EventBuilder eb: bs){
			if(eb.parse(sec) != null) {
				e = eb.parse(sec);
				break;
			}
		}
		if(e == null) throw new IllegalArgumentException("This is not a available event");
		return e;
	}
	 
	 /**
	  * Ejecuta la simulación durante una serie de pasos
	  * @throws IOException
	  */
	 public void run(){
		 try{
		 simulator.run(pasos);
		 }
		 catch (SimulatorException se){
			 System.out.println("" + se + se.getCause());
		 }
		 catch (IOException ioe){
			 System.out.println(ioe);
		 }
	 }
	
	
	
}
