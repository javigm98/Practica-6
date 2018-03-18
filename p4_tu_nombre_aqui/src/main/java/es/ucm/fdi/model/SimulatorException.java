package es.ucm.fdi.model;


/**
 * Excpeción ocurrida durante la crecación de la simulación cuando se intentan realizar acciones que van contra
 * la lógica de la misma, tales como mover un vehículo entre dos cruces sin carretera o añadir una carretera con 
 * un cruce final y/o inicial inexistente.
 * @author Javier Guzmán y Jorge Villarrubia.
 *
 */
public class SimulatorException extends RuntimeException{
	public SimulatorException(String message){
		super(message);
	}
	public SimulatorException(String message, Throwable cause){
		super(message, cause);
	}
}
