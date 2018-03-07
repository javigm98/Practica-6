package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewVehicleEvent;

public class NewVehicleEventBuilder implements EventBuilder{
	private final static String TAG = "new_vehicle";
	 public Event parse(IniSection sec) throws IllegalArgumentException{
		 if(sec.getTag().equals(TAG)){
			 int time1 = parseInt(sec, "time", 0);
			 String id1 = sec.getValue("id");
			 if(!isValidId(id1)) throw new IllegalArgumentException("El ID " + id1 + " no es valido");
			 int maxVel = Integer.parseInt(sec.getValue("max_speed"));
			 String[] ruta = parseIdList(sec, "itinerary");//No sabemos si lo partira bien???????? 
			 return new NewVehicleEvent(time1, id1, maxVel, ruta);
		 }
		 else return null;
	 }
}
