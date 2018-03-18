package es.ucm.fdi.model;



/**
 * Evento que representa un objeto de la clase Car que se añadirá al simulador.
 * @author Javier Guzmán y Jorge Villarrubia
 *
 */

public class NewCarEvent extends NewVehicleEvent{
	private int resistencia, tMaxAveria;
	private double probAveria;
	long semilla;
	public NewCarEvent(int time, String id, int maxSpeed, String[] route,
			int resistencia, int tMaxAveria, double probAveria, long semilla) {
		super(time, id, maxSpeed, route);
		this.resistencia = resistencia;
		this.tMaxAveria = tMaxAveria;
		this.probAveria = probAveria;
		this.semilla = semilla;
		this.type = "car";
	}
	/**
	 * Metodo que añade un nuevo coche al simulador con los datos que tenemos si le es momento de añadirlo.
	 * Lanza una excepción si no se puede crear el coche con el constructor de Car.
	 */
	
	@Override
	public void execute(RoadMap rm, int timeExecution) throws SimulatorException{
		if(time == timeExecution){
			super.execute(rm, timeExecution);
			Car c = new Car(id, maxSpeed, itinerario, resistencia, tMaxAveria, probAveria, semilla);
			rm.addVehicle(c);
			c.getRoad().entraVehiculo(c);
		}	
	}
	
}
