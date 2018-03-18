package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewBikeEvent;
import es.ucm.fdi.model.SimulatorException;

/**
 * Credaor del evento de nueva bicicleta
 * @author Javier Guzmán y Jorge Villarrubia
 *
 */
public class NewBikeEventBuilder implements EventBuilder{
	private final static String TAG = "new_vehicle";
	@Override
	 public Event parse(IniSection sec) throws IllegalArgumentException, SimulatorException{
		 if(sec.getTag().equals(TAG) && sec.getValue("type").equals("bike")){
			try{ 
			 int time1 = parseInt(sec, "time", 0);
			 String id1 = parseValidId(sec, "id");
			 int maxVel = parseIntGeneral(sec, "max_speed");
			 String[] ruta = parseIdList(sec, "itinerary");
			 return new NewBikeEvent(time1, id1, maxVel, ruta);
			}
			catch(NullPointerException npe){
				throw new SimulatorException("Missing fields in the bike event section ", npe);
			}
			catch(NumberFormatException nfe){
				 throw new SimulatorException("Missing number fields in the road event section ", nfe);
			 }
		 }
		 else return null;
	 }

}
