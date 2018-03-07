package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.MakeVehicleFaultyEvent;

public class MakeVehicleFaultyEventBuilder implements EventBuilder {
	private final static String TAG = "make_vehicle_faulty";
	public Event parse(IniSection sec) throws IllegalArgumentException{
		 if(sec.getTag().equals(TAG)) {
			 int time = parseInt(sec, "time", 0);
			 String[] lista = parseIdList(sec, "vehicles");
			 int duracion = Integer.parseInt(sec.getValue("duration"));
			 return new MakeVehicleFaultyEvent(time, duracion, lista);
		 }
		 else return null;
	}
}
