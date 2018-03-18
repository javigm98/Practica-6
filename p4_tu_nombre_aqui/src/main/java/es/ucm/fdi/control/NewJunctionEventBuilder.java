package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewJunctionEvent;
import es.ucm.fdi.model.SimulatorException;

public class NewJunctionEventBuilder implements EventBuilder{
	private final static String TAG = "new_junction";
	 public Event parse(IniSection sec) throws IllegalArgumentException, SimulatorException{
		 if(sec.getTag().equals(TAG)) {
			try{
			int time1 = parseInt(sec, "time", 0);
			 String id1 = parseValidId(sec, "id");
			 return new NewJunctionEvent(time1, id1);
			}
			catch(NullPointerException npe){
				throw new SimulatorException("Missing fields in the junction event section ", npe);
		}
			catch(NumberFormatException nfe){
				 throw new SimulatorException("Missing number fields in the road event section ", nfe);
			 }
		 }
		 else return null;
	 }
}
