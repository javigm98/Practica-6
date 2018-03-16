package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewCarEvent;
import es.ucm.fdi.model.NewVehicleEvent;
import es.ucm.fdi.model.SimulatorException;

public class NewCarEventBuilder implements EventBuilder{
	private final static String TAG = "new_vehicle";
	 public Event parse(IniSection sec) throws IllegalArgumentException, SimulatorException{
		 if(sec.getTag().equals(TAG) && sec.getValue("type").equals("car")){
			 
			 try{
			 int time = parseInt(sec, "time", 0);
			 String id = parseValidId(sec, "id");
			 int maxVel = parseIntGeneral(sec, "max_speed");
			 String[] ruta = parseIdList(sec, "itinerary");
			 int resistance = parseIntGeneral(sec, "resistance");
			 int tMaxAveria = parseIntGeneral(sec, "max_fault_duration");
			 double probAveria = parseDoubleGeneral(sec, "fault_probability");
			 long semilla = parseLong(sec, "seed", System.currentTimeMillis());
			 return new NewCarEvent(time, id, maxVel, ruta, resistance, tMaxAveria, probAveria, semilla);
			 }
			 catch(NullPointerException npe){
					throw new SimulatorException("Missing fields in the car event section ", npe);
			}
			 
		 }
		 else return null;
	 }
}
