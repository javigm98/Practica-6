package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewCarEvent;
import es.ucm.fdi.model.NewVehicleEvent;

public class NewCarEventBuilder implements EventBuilder{
	private final static String TAG = "new_vehicle";
	 public Event parse(IniSection sec) throws IllegalArgumentException{
		 if(sec.getTag().equals(TAG) && sec.getValue("type").equals("car")){
			 int time = parseInt(sec, "time", 0);
			 String id = parseValidId(sec, "id");
			 int maxVel = Integer.parseInt(sec.getValue("max_speed"));
			 String[] ruta = parseIdList(sec, "itinerary");
			 int resistance = Integer.parseInt(sec.getValue("resistance"));
			 int tMaxAveria = Integer.parseInt(sec.getValue("max_fault_duration"));
			 double probAveria = Double.parseDouble(sec.getValue("fault_probability"));
			 long semilla = parseLong(sec, "seed", System.currentTimeMillis());
			 return new NewCarEvent(time, id, maxVel, ruta, resistance, tMaxAveria, probAveria, semilla);
		 }
		 else return null;
	 }
}
