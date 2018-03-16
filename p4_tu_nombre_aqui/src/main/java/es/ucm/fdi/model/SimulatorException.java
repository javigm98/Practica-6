package es.ucm.fdi.model;

public class SimulatorException extends RuntimeException{
	public SimulatorException(String message){
		super(message);
	}
	public SimulatorException(String message, Throwable cause){
		super(message, cause);
	}
}
