package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewDirtRoadEvent;
import es.ucm.fdi.model.NewLanesRoadEvent;
import es.ucm.fdi.model.SimulatorException;

public class NewDirtRoadEventBuilder implements EventBuilder{
	private final static String TAG = "new_road";
	@Override
	 public Event parse(IniSection sec) throws IllegalArgumentException, SimulatorException{
		 if(sec.getTag().equals(TAG) && sec.getValue("type").equals("dirt")) {
			 try{
			 int time1 = parseInt(sec, "time", 0);
			 String id1 = parseValidId(sec, "id");
			 String iniId = sec.getValue("src");
			 String finId = sec.getValue("dest");
			 int maxVel = parseIntGeneral(sec, "max_speed");
			 int longitud = parseIntGeneral(sec, "length");
			 return new NewDirtRoadEvent(time1, id1, iniId, finId, maxVel, longitud);
			 }
			 catch(NullPointerException npe){
					throw new SimulatorException("Missing fields in the dirt road event section ", npe);
			}
			 
		 }
		 else return null;
	 }

}
