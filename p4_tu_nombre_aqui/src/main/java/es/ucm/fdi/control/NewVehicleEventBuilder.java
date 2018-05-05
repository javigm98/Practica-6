package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewVehicleEvent;
import es.ucm.fdi.model.SimulatorException;

/**
 * Creador del evento de nuevo vehículo
 * @author Javier Guzmán y Jorge Villarrubia
 *
 */
public class NewVehicleEventBuilder implements EventBuilder{
	private final static String TAG = "new_vehicle";
	@Override
	 public Event parse(IniSection sec) throws IllegalArgumentException, SimulatorException{
		 if(sec.getTag().equals(TAG) && (sec.getValue("type") == null)){
			 try{
			 int time1 = parseInt(sec, "time", 0);
			 String id1 = parseValidId(sec, "id");
			 int maxVel = parseIntGeneral(sec, "max_speed");
			 String[] ruta = parseIdList(sec, "itinerary");
			 return new NewVehicleEvent(time1, id1, maxVel, ruta);
			 }
			 catch(NullPointerException npe){
					throw new SimulatorException("Missing fields in the vehicle event section ", npe);
			}
		 }
		 else return null;
	 }
}
