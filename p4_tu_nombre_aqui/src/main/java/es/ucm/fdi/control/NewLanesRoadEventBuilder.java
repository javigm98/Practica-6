package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewLanesRoadEvent;
import es.ucm.fdi.model.SimulatorException;

public class NewLanesRoadEventBuilder implements EventBuilder{
	private final static String TAG = "new_road";
	@Override
	 public Event parse(IniSection sec) throws IllegalArgumentException, SimulatorException{
		 if(sec.getTag().equals(TAG) && sec.getValue("type").equals("lanes")) {
			 try{
			 int time1 = parseInt(sec, "time", 0);
			 String id1 = parseValidId(sec, "id");
			 String iniId = sec.getValue("src");
			 String finId = sec.getValue("dest");
			 int maxVel = parseIntGeneral(sec, "max_speed");
			 int longitud = parseIntGeneral(sec, "length");
			 int numCarriles = parseIntGeneral(sec, "lanes");
			 return new NewLanesRoadEvent(time1, id1, iniId, finId, maxVel, longitud, numCarriles);
			 }
			 catch(NullPointerException npe){
					throw new SimulatorException("Missing fields in the lane road event section ", npe);
			}
			 catch(NumberFormatException nfe){
				 throw new SimulatorException("Missing number fields in the road event section ", nfe);
			 }
		 }
		 else return null;
	 }
}
