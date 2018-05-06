package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewRoadEvent;
import es.ucm.fdi.model.SimulatorException;

/**
 * Credaor del evento de nueva carretera
 * @author Javier Guzmán y Jorge Villarrubia
 *
 */
public class NewRoadEventBuilder implements EventBuilder{
	private final static String TAG = "new_road";
	@Override
	 public Event parse(IniSection sec) throws IllegalArgumentException{
		 if(sec.getTag().equals(TAG) && (sec.getValue("type") == null)) {
			 try{
			 int time1 = parseInt(sec, "time", 0);
			 String id1 = parseValidId(sec, "id");
			 String iniId = sec.getValue("src");
			 String finId = sec.getValue("dest");
			 int maxVel = parseIntGeneral(sec, "max_speed");
			 int longitud = parseIntGeneral(sec, "length");
			 return new NewRoadEvent(time1, id1, iniId, finId, maxVel, longitud);
			 }
			 catch(NullPointerException npe){
					throw new IllegalArgumentException("Missing fields in the road event section ", npe);
			}
		 }
		 else return null;
	 }
}
