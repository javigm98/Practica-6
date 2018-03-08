package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;

public interface EventBuilder {
	
	public Event parse(IniSection sec);
	
	public default boolean isValidId(String id){
		for(int i = 0; i < id.length(); ++i){
			char c = id.charAt(i);
			if(!Character.isDigit(c) && !Character.isLetter(c) && c != '_') return false;
		}
		return true;
	}
	public default String[] parseIdList(IniSection sec, String key) throws IllegalArgumentException{
		String[] resultado = sec.getValue(key).split(",");
		for(String s: resultado){
			if(!isValidId(s)) throw new IllegalArgumentException("El ID " + s + " no es valido");
		}
		return resultado;
	}
	public default int parseInt(IniSection sec, String key, int porDefecto){
		String s = sec.getValue(key);
		if (s == null) return porDefecto;
		else return Integer.parseInt(s);
	}
}
