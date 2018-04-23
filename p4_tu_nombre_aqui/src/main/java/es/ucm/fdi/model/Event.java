package es.ucm.fdi.model;

/**
 * Clase que representa un evento de la simulación que tendra efecto en su correspondiente momento (Cuando time sea igual
 * al tiempo de la ejecucuión.
 * @author Javier Guzman y Jorge Villarrubia
 *
 */

public abstract class Event implements Comparable<Event>, Describable{
	protected int time;
	/**
	 * Cada evento se ejecuta y tiene un efecto sobre la simulacion cuando le corresponde entrar en acción
	 * @param rm Objeto que guarada todos los objetos simulados en un momento dado para que actualice las modificaciones
	 * realizadas en la ejecución del evento.
	 * @param timeExecution paso de la simulación en el que nos encontramos, 
	 * indicando si al evento le corresponde ejecutarse o aún no.
	 */
	public abstract void execute (RoadMap rm, int timeExecution);
	/**
	 * Devuelve el atributo time de la clase.
	 */
	
	public int getTime(){
		return time;
	}
	/**
	 * Devuelve un objeto Junction con el id dado buscando entre los Junctions disponibles en rm, 
	 * lanzando una excepción si no lo encuentra.
	 * @param rm Objeto que guarada todos los elementos de la simulación.
	 * @param id id del cruce a buscar.
	 * @return el cruce con el id especificado.
	 * @throws SimulatorException si no existe ningun cruce con el id proporcionado.
	 */
	public Junction checkJunctionExists(RoadMap rm, String id) throws SimulatorException{
		Junction j = rm.getJunction(id);
		if(j == null) {
			throw new SimulatorException("Could not find junction with the id: " + id + ". Expected ids: " + rm.getListaCruces());
		}
		return j;
		
	}
	/**
	 * Comparador entre dos eventos basado en la comparación de su atributo time
	 */
	public int CompareTo(Event e){
		return time - e.getTime();
	}
	public abstract String infoParaTabla();
}
