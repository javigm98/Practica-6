package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewJunctionEvent;
import es.ucm.fdi.model.NewRoadEvent;


public class NewRoadEventBuilder implements EventBuilder{
	private final static String TAG = "new_road";
	 public Event parse(IniSection sec) throws IllegalArgumentException{
		 if(sec.getTag().equals(TAG)) {
			 int time1 = parseInt(sec, "time", 0);
			 String id1 = parseValidId(sec, "id");
			 String iniId = sec.getValue("src");
			 String finId = sec.getValue("dest");
			 int maxVel = Integer.parseInt(sec.getValue("max_speed"));
			 int longitud = Integer.parseInt(sec.getValue("length"));
			 return new NewRoadEvent(time1, id1, iniId, finId, maxVel, longitud);
		 }
		 else return null;
	 }
}
