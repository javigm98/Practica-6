package es.ucm.fdi.control;

import java.io.*;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator simulator;
	private int pasos;
	private InputStream in;
	private OutputStream out;
	private String s;
	
	private EventBuilder[] bs = {new NewVehicleEventBuilder(), new NewJunctionEventBuilder(), 
			new NewRoadEventBuilder(), new MakeVehicleFaultyEventBuilder()};
	
	public void loadEvents(InputStream in1) throws IOException{ // Capturar aqui mejor las excepciones
		
	}
	 
	 public Event parseSection(IniSection sec){
		Event e = null;
		for(EventBuilder eb: bs){
			if(eb.parse(sec) != null) {
				e = eb.parse(sec);
				break;
			}
		}
		return e;
	}
	 
	 public void run() throws IOException{ //Mejor capturar aqui la expcepion
		 simulator.run(pasos);
	 }
	
	public Controller(TrafficSimulator ts, int pasos1, InputStream in1, OutputStream out1){
		simulator = ts;
		pasos = pasos1;
		in = in1;
		out = out1;
	}
	
}
