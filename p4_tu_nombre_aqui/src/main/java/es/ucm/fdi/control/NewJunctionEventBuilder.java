package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewJunctionEvent;

public class NewJunctionEventBuilder implements EventBuilder{
	private final static String TAG = "new_junction";
	 public Event parse(IniSection sec) throws IllegalArgumentException{
		 if(sec.getTag().equals(TAG)) {
			 //try{ excepcion si no existen las claves time e id, si time no es entero...
			 int time1 = parseInt(sec, "time", 0);
			 String id1 = parseValidId(sec, "id");
			 return new NewJunctionEvent(time1, id1);
			 //}
			 //catch (Inval)
		 }
		 else return null;
	 }
}
