package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.MakeVehicleFaultyEvent;
import es.ucm.fdi.model.SimulatorException;

public class MakeVehicleFaultyEventBuilder implements EventBuilder {
	private final static String TAG = "make_vehicle_faulty";
	public Event parse(IniSection sec) throws IllegalArgumentException, SimulatorException{
		 if(sec.getTag().equals(TAG)) {
			try{ 
			 int time = parseInt(sec, "time", 0);
			 String[] lista = parseIdList(sec, "vehicles");
			 int duracion = parseIntGeneral(sec , "duration");
			 return new MakeVehicleFaultyEvent(time, duracion, lista);
			}
			catch(NullPointerException npe){
				throw new SimulatorException("Missing fields in the vehice faulty event section ", npe);
			}
		 }
		 else return null;
	}
}
