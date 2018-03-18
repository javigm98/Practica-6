package es.ucm.fdi.model;

import java.util.Map;

/**
 * Clase genérica que representa cualquier objeto de la simulación. Cuenta con dos atributos que indican el
 * identificador del objeto (id) y el tiempo asociado a su ejecución.
 * @author Javier Guzmán y Jorge Villarrubia.
 *
 */
public abstract class  SimObject {
	protected String id;
	protected int time;
	/**
	 * Avanza un paso la simulación del objeto correspondiente realizando las operaciones que sean necesarias.
	 */
	public abstract void avanza();
	
	/**
	 * @return el encabezado para los informes de cada objeto correspondiente.
	 */
	public abstract String getReportHeader();
	
	/**
	 * Rellena los campos específicos para el informe de un determinado objeto.
	 * @param out mapa en el que se guardan las claves y los valores asociados a estos para generar posteriormente
	 * el informe correspondiente.
	 */
	public abstract void fillReportDetails(Map<String, String> out);
	
	/**
	 * @return el id del objeto.
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * genera el informe correspondiente a un objeto simulado 
	 * @param time timepo de la simulación
	 * @param out mapa en el que se guardan las relaciones clave-valor de los campos a mostrar en el objeto.
	 */
	public void report(int time, Map<String, String> out){
		out.put ("id", id);
		out.put ("time", Integer.toString(time));
		fillReportDetails(out);
	}
}
