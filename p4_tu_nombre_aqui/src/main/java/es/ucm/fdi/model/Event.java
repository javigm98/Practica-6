package es.ucm.fdi.model;

/**
 * Clase que representa un evento de la simulación que tendra efecto en su
 * correspondiente momento (Cuando time sea igual al tiempo de la ejecucuión.
 * 
 * @author Javier Guzman y Jorge Villarrubia
 *
 */

public abstract class Event implements Comparable<Event>, Describable {
	protected int time;

	/**
	 * Cada evento se ejecuta y tiene un efecto sobre la simulacion cuando le
	 * corresponde entrar en acción
	 * 
	 * @param rm
	 *            Objeto que guarada todos los objetos simulados en un momento
	 *            dado para que actualice las modificaciones realizadas en la
	 *            ejecución del evento.
	 * @param timeExecution
	 *            paso de la simulación en el que nos encontramos, indicando si
	 *            al evento le corresponde ejecutarse o aún no.
	 */
	public abstract void execute(RoadMap rm, int timeExecution);

	/**
	 * Devuelve el atributo time de la clase.
	 */

	public int getTime() {
		return time;
	}

	/**
	 * Comparador entre dos eventos basado en la comparación de su atributo time
	 */
	public int CompareTo(Event e) {
		return time - e.getTime();
	}

	public abstract String infoParaTabla();
}
