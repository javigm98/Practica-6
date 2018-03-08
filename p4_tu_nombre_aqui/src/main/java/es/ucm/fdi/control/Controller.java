package es.ucm.fdi.control;

import java.io.*;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator simulator;
	private int time;
	private InputStream in;
	private OutputStream out;
	
	private EventBuilder[] bs = {new NewVehicleEventBuilder(), new NewJunctionEventBuilder(), 
			new NewRoadEventBuilder(), new MakeVehicleFaultyEventBuilder()};
	 
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
	 
	 public void run(){ ///metodos run
		 
	 }
	
	public Controller(TrafficSimulator ts, int time1, InputStream in1, OutputStream out1){
		simulator = ts;
		time = time1;
		in = in1;
		out = out1;
	}
	
	public Controller(TrafficSimulator ts, OutputStream out1){
		
	}
}
