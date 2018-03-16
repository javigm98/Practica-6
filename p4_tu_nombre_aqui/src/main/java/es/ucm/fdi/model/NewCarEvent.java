package es.ucm.fdi.model;

import java.util.List;
import java.util.Random;

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
