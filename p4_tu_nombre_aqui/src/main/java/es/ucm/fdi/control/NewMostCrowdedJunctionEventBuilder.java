package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewMostCrowdedJunctionEvent;
import es.ucm.fdi.model.SimulatorException;

public class NewMostCrowdedJunctionEventBuilder implements EventBuilder{
	private final static String TAG = "new_junction";
	@Override
	public Event parse(IniSection sec) throws IllegalArgumentException{
		if(sec.getTag().equals(TAG) && sec.getValue("type").equals("mc")){
			try{
				int time = parseInt(sec, "time", 0);
				String id = parseValidId(sec, "id");
				return new NewMostCrowdedJunctionEvent(time, id);
			}
			catch(NullPointerException npe){
				throw new IllegalArgumentException("Missing fields in the most crowded junction event section ", npe);
			}
		}
		else return null;
	}

}

