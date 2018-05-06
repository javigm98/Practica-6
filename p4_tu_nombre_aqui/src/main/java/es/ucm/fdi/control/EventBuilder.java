package es.ucm.fdi.control;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;

/**
 * Creador de eventos para la simulación
 * @author Javier Guzmán y Jorge Villarrubia
 *
 */
public interface EventBuilder {
	/**
	 * Dada una sección INI decidir si se trata de la correspondiente a ese evento y parsear sus datos.
	 * @param sec sección a parsear
	 * @return un evento con los datos correspondientes a la sección, null si no se corresponde con u a sección de 
	 * ese evento.
	 */
	public Event parse(IniSection sec);
	
	/**
	 * Comprueba que un id sea válido, es decir, que solo contenga caracteres alfanuméricos y guiones bajos (_).
	 * @param id id de la cual queremos comprobar su validez.
	 * @return true si el id es válido, false si no. 
	 */
	public default boolean isValidId(String id) throws IllegalArgumentException{
		return id.matches("[a-zA-Z0-9_]+");
	} 
	
	/**
	 * Dada una clave cuyo valor debe ser un string lo busca en la sección sec
	 * @param sec sección en la que queremos buscar la clave.
	 * @param key clave cuyo valor deseamos conocer.
	 * @return el valor correspondiente a la clave key
	 * @throws NullPointerException si la clave no se encuentra en la sección INI.
	 */
	public default String parseString(IniSection sec, String key)throws NullPointerException{
		String s = sec.getValue(key);
		if (s == null) throw new NullPointerException("The key " + key + " doesn't exist ");
		return s;
	}
	
	/**
	 * Dada una clave cuyo valor debe ser un entero devuelve el valor asociado a la misma.
	 * @param sec sección INI en la que debemos buscar el valor asociado a la clave.
	 * @param key clave cuyo valor queremos conocer.
	 * @return el valor correspondiente a la clave key.
	 * @throws NullPointerException si no se encuentra la clave en la sección INI.
	 */
	public default int parseIntGeneral(IniSection sec, String key)throws NullPointerException{
		String s = sec.getValue(key);
		if (s == null) throw new NullPointerException("The key " + key + " doesn't exist ");
		return Integer.parseInt(s);
	}
	
	/**
	 * Dada una clave cuyo valor debe ser un double devuelve el valor asociado a la misma.
	 * @param sec sección INI en la que debemos buscar el valor asociado a la clave.
	 * @param key clave cuyo valor queremos conocer.
	 * @return el valor correspondiente a la clave key.
	 * @throws NullPointerException si no se encuentra la clave en la sección INI.
	 */
	public default double parseDoubleGeneral(IniSection sec, String key)throws NullPointerException{
		String s = sec.getValue(key);
		if (s == null) throw new NullPointerException("The key " + key + " doesn't exist ");
		return Double.parseDouble(s);
	}
	/**
	 * Dada una clave y una secciómn INI busca en la misma el valor asociado a la misma, comprobando además que se 
	 * trate de un id válido.
	 * @param sec sección INI en la que queremos buscar el valor asociado a la clave.
	 * @param key clave vuyo valor desamos obtener
	 * @return el valor asociado a la clave key
	 * @throws IllegalArgumentException si no es un id válido
	 * @throws NullPointerException si no se encuentra la clava en la sección.
	 */
	public default String parseValidId(IniSection sec, String key) throws IllegalArgumentException, NullPointerException{
		String s = parseString(sec, key);
		if(!isValidId(s)) {
			throw new IllegalArgumentException("La lista de IDs " + key + " no es valida, en seccion " + sec);
		}
		return s;
	}
	/**
	 * Dada una clave correspondiente a una lista de ids y una sección devuelve el valor asociado a la calve, 
	 * comprobando que todos los ids de la lista sena válidos.
	 * @param sec sección en la que queremos buscar la lista de ids con la clave key.
	 * @param key clave asociada a la lista de ids.
	 * @return la lista de ids asociados a la clave key.
	 * @throws IllegalArgumentException si algún id no es válido
	 * @throws NullPointerException  si no se encuentra la clave key en la sección sec.
	 */
	public default String[] parseIdList(IniSection sec, String key) throws IllegalArgumentException, NullPointerException{
		String[] resultado = parseString(sec, key).split("[, ]+");
		for(String s: resultado){
			if(!isValidId(s)) {
				throw new IllegalArgumentException("La lista de IDs " + key + " no es valida, en seccion " + sec);
			}
		}
		return resultado;
	}
	/**
	 * Dada una clave cuyo valor debe ser un entero devuelve el valor asociado a la misma o el valor porDefecto si
	 * no encuentra la clave key en la sección sec
	 * @param sec sección en la que se que ir a buscar el valor para la clave key
	 * @param key clave cuyo valor se desa conocer
	 * @param porDefecto valor por defecto para la clave key si no se encontrase en la sección sec
	 * @return el valor asociado o key o porDefecto si no encuentra key en sec
	 */
	public default int parseInt(IniSection sec, String key, int porDefecto){
		String s = sec.getValue(key);
		if (s == null) return porDefecto;
		else return Integer.parseInt(s);
	}
	
	/**
	 * Dada una clave cuyo valor debe ser un long devuelve el valor asociado a la misma o el valor porDefecto si
	 * no encuentra la clave key en la sección sec
	 * @param sec sección en la que se queire buscar el valor para la clave key
	 * @param key clave cuyo valor se desa conocer
	 * @param porDefecto valor por defecto para la clave key si no se encontrase en la sección sec
	 * @return el valor asociado o key o porDefecto si no encuentra key en sec
	 */
	public default long parseLong(IniSection sec, String key, long porDefecto){
		String s = sec.getValue(key);
		if(s == null) return porDefecto;
		else return Long.parseLong(s);
		
	}
}
