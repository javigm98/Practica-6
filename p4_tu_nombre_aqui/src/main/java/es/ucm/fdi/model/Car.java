package es.ucm.fdi.model;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Clase que extiende Vehicle y representa un objeto del tipo Car en el
 * simulador Añade atributos adicionales a los que ya tiene un vehiculo: los
 * kilometros que agunta sin averiarse (resistencia), el tiempo Maximo de averia
 * (tMaxAveria), el kilometros donde sucedio la ultima averia (kmUltimaAveria),
 * un generador de numeros aleatorios (numAleatorio) y la probabilidad de averia
 * (probAveria).
 * 
 * @author Javier Guzman y Jorge Villarrubia
 *
 */
public class Car extends Vehicle {
	private int resistencia, tMaxAveria, kmUltimaAveria = 0;
	private Random numAleatorio;
	private double probAveria;

	/**
	 * Constructor de la clase Car con parametros
	 * 
	 * @param id
	 * @param maxSpeed
	 * @param route
	 * @param resistencia
	 * @param tMaxAveria
	 * @param probAveria
	 * @param semilla
	 *            semilla para generar el objeto de la clase Random
	 *            numAleatorio.
	 * @throws SimulatorException
	 *             si no existe una carretera que una los dos primeros cruces
	 *             del itinerario y por tanto no se puede colocar el coche en
	 *             ninguna carretera inicialmente.
	 */
	public Car(String id, int maxSpeed, List<Junction> route, int resistencia,
			int tMaxAveria, double probAveria, long semilla)
			throws SimulatorException {
		super(id, maxSpeed, route);
		this.resistencia = resistencia;
		this.tMaxAveria = tMaxAveria;
		this.probAveria = probAveria;
		this.numAleatorio = new Random(semilla);
	}

	/**
	 * Método que avanza un coche, calculando primero si le corresponde
	 * averiarse (si ha superado los kilómetros de resistencia desde la ultima
	 * averia y el numero aletaorio generado es menor que la probabilidad de
	 * avería) y avanzando como cualquier otro vehículo tras realizar esta
	 * operación.
	 */
	@Override
	public void avanza() {
		if (!estaAveriado()) {
			if (km - kmUltimaAveria > resistencia) {
				if (numAleatorio.nextDouble() < probAveria) {
					setTiempoAveria(numAleatorio.nextInt(tMaxAveria) + 1);
					kmUltimaAveria = km;
				}
			}
		}
		super.avanza();
	}

	/**
	 * Añade al informe del vehiculo el tipo correspondiente (car).
	 */
	@Override
	public void fillReportDetails(Map<String, String> out) {
		out.put("type", "car");
		super.fillReportDetails(out);
	}
}
