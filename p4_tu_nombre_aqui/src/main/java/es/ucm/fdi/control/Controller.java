package es.ucm.fdi.control;

import java.io.*;
import java.util.List;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator simulator;
	private int pasos;

	
	private EventBuilder[] bs = {new NewVehicleEventBuilder(), new NewJunctionEventBuilder(), 
			new NewRoadEventBuilder(), new MakeVehicleFaultyEventBuilder(), 
			new NewCarEventBuilder(), new NewBikeEventBuilder(),
			new NewLanesRoadEventBuilder(), new NewDirtRoadEventBuilder()};
	
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
	 
	 public void run() throws IOException{
		 try{
		 simulator.run(pasos);
		 }
		 catch (SimulatorException se){
			 System.out.println("" + se + se.getCause());
		 }
	 }
	
	public Controller(TrafficSimulator ts, int pasos1){
		simulator = ts;
		pasos = pasos1;
	}
	
}
