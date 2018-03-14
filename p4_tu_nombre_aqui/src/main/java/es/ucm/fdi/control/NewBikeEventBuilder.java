package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewBikeEvent;

public class NewBikeEventBuilder implements EventBuilder{
	private final static String TAG = "new_vehicle";
	 public Event parse(IniSection sec) throws IllegalArgumentException{
		 if(sec.getTag().equals(TAG) && sec.getValue("type").equals("bike")){
			 int time1 = parseInt(sec, "time", 0);
			 String id1 = parseValidId(sec, "id");
			 int maxVel = Integer.parseInt(sec.getValue("max_speed"));
			 String[] ruta = parseIdList(sec, "itinerary");
			 return new NewBikeEvent(time1, id1, maxVel, ruta);
		 }
		 else return null;
	 }

}
