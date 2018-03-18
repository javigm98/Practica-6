package es.ucm.fdi.model;

import java.util.Collections;
import java.util.Map;

import es.ucm.fdi.util.MultiTreeMap;

/**Esta clase representa un dirt road en el simulador, extendiendo la clase Road, sin añadir ningún atributo más.
 * 
 * @author Javier Guzmán y Jorge Villarrubia
 *
 */
public class DirtRoad extends Road{
	/**
	 * Constructor con parámetros de la clase DirtRoad
	 * @param id
	 * @param longitud
	 * @param maxVel
	 * @param ini
	 * @param fin
	 */
	public DirtRoad(String id, int longitud, int maxVel, Junction ini, Junction fin) {
		super(id, longitud, maxVel, ini, fin);
	}
	
	
	/**El metodo avanza de un DirtRoad es bastante similar al de de Road con la unica diferencia de que el factor de reduccion
	 * depende del número de vehículos averiados delante del que queremos avanzar y la velocidad base es siempre la máxima 
	 * del camino.
	 * 
	 */
@Override
public void avanza(){
	int velBase = maxVel;
	int numAverias = 0;
	MultiTreeMap<Integer, Vehicle> nuevo = new MultiTreeMap<>(Collections.reverseOrder());
	for (Vehicle v: listaVehiculos.innerValues()){
		if(!v.getHaLlegado()){
		if (v.estaAveriado()) {
			numAverias++;
			v.setVelocidadActual(0);
		}
		else{
			velBase /= (numAverias + 1);
			if(v.getPos() < longitud){ //Si esta esperando en un cruce su velocidad es 0
				v.setVelocidadActual(velBase);
			}
		}
		if(v.getPos()< longitud){ //Si esta al final de la carretera ya esta esperando la cola del cruce.
		v.avanza();
		}
		nuevo.putValue(v.getPos(), v);
		}
	} 
	listaVehiculos = nuevo;
}


/**
 * Añade al informe de la carretera el tipo correspondiente (dirt).
 */
	@Override
	public void fillReportDetails(Map<String, String> out){
		out.put("type", "dirt");
		super.fillReportDetails(out);
	}
	}
