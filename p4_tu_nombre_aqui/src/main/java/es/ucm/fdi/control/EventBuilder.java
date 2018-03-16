package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;

public interface EventBuilder {
	
	public Event parse(IniSection sec);
	
	public default boolean isValidId(String id) throws IllegalArgumentException{
		return id.matches("[a-zA-Z0-9_]+");
	} 
	
	public default String parseString(IniSection sec, String key)throws NullPointerException{
		String s = sec.getValue(key);
		if (s == null) throw new NullPointerException("The key " + key + " doesn't exist ");
		return s;
	}
	public default int parseIntGeneral(IniSection sec, String key)throws NullPointerException{
		String s = sec.getValue(key);
		if (s == null) throw new NullPointerException("The key " + key + " doesn't exist ");
		return Integer.parseInt(s);
	}
	public default double parseDoubleGeneral(IniSection sec, String key)throws NullPointerException{
		String s = sec.getValue(key);
		if (s == null) throw new NullPointerException("The key " + key + " doesn't exist ");
		return Double.parseDouble(s);
	}
	
	public default String parseValidId(IniSection sec, String key) throws IllegalArgumentException, NullPointerException{
		String s = parseString(sec, key);
		if(!isValidId(s)) {
			throw new IllegalArgumentException("La lista de IDs " + key + " no es valida, en seccion " + sec);
		}
		return s;
	}
	
	public default String[] parseIdList(IniSection sec, String key) throws IllegalArgumentException, NullPointerException{
		String[] resultado = parseString(sec, key).split("[, ]+");
		for(String s: resultado){
			if(!isValidId(s)) {
				throw new IllegalArgumentException("La lista de IDs " + key + " no es valida, en seccion " + sec);
			}
		}
		return resultado;
	}
	public default int parseInt(IniSection sec, String key, int porDefecto){
		String s = sec.getValue(key);
		if (s == null) return porDefecto;
		else return Integer.parseInt(s);
	}
	public default long parseLong(IniSection sec, String key, long porDefecto){
		String s = sec.getValue(key);
		if(s == null) return porDefecto;
		else return Long.parseLong(s);
		
	}
}
