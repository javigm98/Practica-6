package es.ucm.fdi.model;

import java.util.List;
import java.util.Map;

/** Esta clase extiende la clase Vehicle y representa un objeto del tipo "bike" en el simulador.
 * Una bibicleta no necesita mas atributos adicionales a los que ya tiene por tratarse de un vehiculo.
 * 
 * @author Javier Guzman y Jorge Villarrubia
 *
 */
public class Bike extends Vehicle{
	/**
	 * Constructor con parametros de la clase Bike.
	 * @param id
	 * @param maxSpeed
	 * @param route
	 * @throws SimulatorException si no existe una carretera que una los dos primeros cruces del itinerario
	 * y por tanto no se puede colocar el vehiculo en ninguna carretera inicialmente.
	 */

	public Bike(String id, int maxSpeed, List<Junction> route) throws SimulatorException{
		super(id, maxSpeed, route);
	}
	/**
	 * Una bici solo se avería si va a una velocidad superior a la mitad de su velocidad maxima
	 */
	@Override
	public void setTiempoAveria(int n){
		if(velActual > (velMaxima /2)){
			super.setTiempoAveria(n);
		}
	}
	/**
	 * Añade al informe del vehiculo el tipo correspondiente (bike).
	 */
	@Override
	public void fillReportDetails(Map<String, String> out){
		out.put("type", "bike");
		super.fillReportDetails(out);
	}

}
