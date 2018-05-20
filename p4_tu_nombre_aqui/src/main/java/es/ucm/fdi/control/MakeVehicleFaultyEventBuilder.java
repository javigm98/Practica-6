package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.MakeVehicleFaultyEvent;

/**
 * Creador del evento de avería de vehículos
 * 
 * @author Javier Guzmán y Jorge Villarrubia
 *
 */
public class MakeVehicleFaultyEventBuilder implements EventBuilder {
	private final static String TAG = "make_vehicle_faulty";

	@Override
	public Event parse(IniSection sec) throws IllegalArgumentException {
		if (sec.getTag().equals(TAG)) {
			try {
				int time = parseInt(sec, "time", 0);
				String[] lista = parseIdList(sec, "vehicles");
				int duracion = parseIntGeneral(sec, "duration");
				return new MakeVehicleFaultyEvent(time, duracion, lista);
			} catch (NullPointerException npe) {
				throw new IllegalArgumentException(
						"Missing fields in the vehice faulty event section ",
						npe);
			}
		} else
			return null;
	}
}
