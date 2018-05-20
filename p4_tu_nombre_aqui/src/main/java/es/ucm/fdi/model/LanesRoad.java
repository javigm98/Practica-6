package es.ucm.fdi.model;

import java.util.Collections;
import java.util.Map;

import es.ucm.fdi.util.MultiTreeMap;

/**
 * Representa una autopista en el simulador y extiende la clase Road, añadiendo
 * un nuevo atributo referente al número de carriles.
 * 
 * @author Javier Guzmán y Jorge Villarrubia.
 *
 */
public class LanesRoad extends Road {
	private int numCarriles;

	/**
	 * Constructor de la clase LanesRoad
	 * 
	 * @param id
	 * @param longitud
	 * @param maxVel
	 * @param ini
	 * @param fin
	 * @param numCarriles
	 */
	public LanesRoad(String id, int longitud, int maxVel, Junction ini,
			Junction fin, int numCarriles) {
		super(id, longitud, maxVel, ini, fin);
		this.numCarriles = numCarriles;
	}

	/**
	 * Método que avanza la simulación en un aatopista, considerando el numero
	 * de vehículos y carriles además de las averías para el cálculo de la
	 * velocidad base y el factor de reducción.
	 */
	@Override
	public void avanza() {
		long max;
		int velBase;
		int contAverias = 0;
		MultiTreeMap<Integer, Vehicle> nuevo = new MultiTreeMap<>(
				Collections.reverseOrder());
		if (listaVehiculos.sizeOfValues() > 1) {
			max = listaVehiculos.sizeOfValues();
		} else {
			max = 1;
		}
		int secFact = (maxVel * numCarriles / (int) max) + 1;
		if (secFact < maxVel) {
			velBase = secFact;
		} else {
			velBase = maxVel;
		}
		for (Vehicle v : listaVehiculos.innerValues()) {
			if (!v.getHaLlegado()) {
				if (v.estaAveriado()) {
					contAverias++;
					v.setVelocidadActual(0);
				} else {
					if (numCarriles <= contAverias) {
						velBase /= 2;
					}
					if (v.getPos() < longitud) { // Si esta esperando en un
													// cruce su velocidad es 0
						v.setVelocidadActual(velBase);
					}
				}
				if (v.getPos() < longitud) { // Si esta al final de la carretera
												// ya esta esperando la cola del
												// cruce.
					v.avanza();
				}
				nuevo.putValue(v.getPos(), v);
			}
		}
		listaVehiculos = nuevo;
	}

	@Override
	public void fillReportDetails(Map<String, String> out) {
		out.put("type", "lanes");
		super.fillReportDetails(out);
	}

}
