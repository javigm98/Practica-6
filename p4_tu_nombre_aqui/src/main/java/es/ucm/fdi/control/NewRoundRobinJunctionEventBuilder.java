package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewRoundRobinJunctionEvent;
import es.ucm.fdi.model.SimulatorException;

public class NewRoundRobinJunctionEventBuilder implements EventBuilder{
	private final static String TAG = "new_junction";
	@Override
	public Event parse(IniSection sec) {
		if(sec.getTag().equals(TAG) && sec.getValue("type").equals("rr")){
			try{
				int time = parseInt(sec, "time", 0);
				String id = parseValidId(sec, "id");
				int maxIntervalo = parseIntGeneral(sec, "max_time_slice");
				int minIntervalo = parseIntGeneral(sec, "min_time_slice");
				return new NewRoundRobinJunctionEvent(time, id, minIntervalo, maxIntervalo);
			}
			catch(NullPointerException npe){
				throw new SimulatorException("Missing fields in the round robin junction event section ", npe);
			}
		}
		else return null;
	}

}
