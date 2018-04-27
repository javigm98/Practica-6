package es.ucm.fdi.model;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Clase que representa un cruce en el simulador.
 * Sus atributos representan las carreteras entrantes al cruce(entradas cruce y incoming), las carreteras salientes
 * (salidasCruces) y la posición de las listas de carreteras entrantes en la que está verde el semáforo.
 * 
 * @author Javier Guzmán y Jorge Villarrubia
 *
 */
public class Junction extends SimObject{
protected Map<Road, IncomingRoad> entradasCruce = new HashMap<Road, IncomingRoad>();
protected ArrayList<IncomingRoad> incoming = new ArrayList<>();
private Map<Junction, Road> salidasCruce = new HashMap<>();
protected int semaforo;

/**
 * Constructor de la clase Junction con parámetros (sólo el id).
 * @param id
 */
public Junction(String id){
	this.id = id;
}
/**
 * Clase interna que representa un acrretera entrante a un cruce. Sus atributos son un objeto de la clase Road representando
 * la carretera propiamente dicha (road), un booleano que indica si el semaforo está en verde (light) y una
 * cola que contiene los vehículos que se encuentran esperando para entrar al cruce por esa carretera.
 * @author Javier Guzmán y Jorge Villarrubia.
 *
 */
protected class IncomingRoad{
	Road road;
	boolean light;
	ArrayDeque<Vehicle> cola = new ArrayDeque<>();
	/**
	 * Constructor de la clase IncomingRoad
	 * @param road1
	 */
	
	public IncomingRoad(Road road) {
		this.road = road;
		light = false;
	}
	
	public void advanceFirstVehicle(){
		if(!cola.isEmpty()){
			cola.removeFirst().moverASiguienteCarretera();
		}
	}
	@Override
	/**
	 * Metodo toString de la clase IncomingRoad que muestra el estado del semaforo, la carretera de la que se trata, y la
	 * cola de vehículos.
	 */

	public String toString(){
		StringBuilder sb = new StringBuilder();
			sb.append("(" + road.getId() + ",");
				sb.append(incoming.get(semaforo).equals(this) ?
				"green" : "red");
				sb.append(",[");
				List<String> list = new ArrayList<>();
		for(Vehicle v: cola){
			list.add(v.getId());
		}
		sb.append(String.join(",", list));
				sb.append("])");
				return sb.toString();
	}
}

/**
 * Añade un vehículo a la cola del IncomingRoad correspondiente a la carretera por la que viajaba el vehículo.
 * @param v vehículo a añadir a la cola.
 */
public void entraVehiculo(Vehicle v){
	entradasCruce.get(v.getRoad()).cola.addLast(v);

}
/**
 * Metodo que avanza la simulacion en el cruce, rotando el semáforo entre las diferentes carreteras entrantes
 * y avanzando al primer vehículo de la cola de la carretera que tiene el semáforo en verde de acuerdo con su itinerario.
 */
@Override
public void avanza() throws SimulatorException{
	if(!incoming.isEmpty()){
		incoming.get(semaforo).advanceFirstVehicle();
		switchLights();
	}
	
}

public void switchLights(){
	incoming.get(semaforo).light = false;
	semaforo = (semaforo + 1) % incoming.size();
	incoming.get(semaforo).light = true;
}



/**
 * Metodo que devuelve una carretera que sale del cruce de la clase y acaba en el cruce destino.
 * @param destino cruce final de la carretera buscada
 * @return la carretera que une el cruce desde donde se llama y destino.
 * @throws SimulatorException si no existe una carretera uniendo ambos cruces en el orden correcto.
 */
public Road carreteraUneCruces(Junction destino)throws SimulatorException{
	Road r = salidasCruce.get(destino);
	if(r == null) {
		throw new SimulatorException("Unexisting road from " + id + " to " + destino.getId());
	}
	return r;
}

@Override
public String getReportHeader(){
	return "junction_report";
}
@Override
public void fillReportDetails(Map<String, String> out) {
List<String> list = new ArrayList<String>();
	for(IncomingRoad ir: incoming){
		list.add(ir.toString());
	}
	String colas = String.join(",", list);
	out.put("queues", colas);
}
/**
 * Añade una nueva carretera entrante al cruce
 * @param road carretera a añadir al cruce.
 */
public void addNewIncomingRoad(Road road){
	IncomingRoad ir = new IncomingRoad(road);
	entradasCruce.put(road,ir);
	incoming.add(ir);
	semaforo = incoming.size()-1;
}
/**
 * Añade una nueva carretera saliente al cruce.
 * @param road carretera a añadir.
 */
public void addNewOutgoingRoad(Road road){
	salidasCruce.put(road.getcruceFin(), road);
}



public String queuesToString(){
	List<String> list = new ArrayList<>();
	for(IncomingRoad ir: incoming){
		list.add(ir.toString());
	}
		return String.join(",", list);
}

@Override
public String toString(){
	return id;
}

@Override
public void describe(Map<String, String> out) {
	out.put("ID", id);
	List<String> verdes = new ArrayList<>();
	List<String> rojos = new ArrayList<>();
	for(IncomingRoad ir:incoming){
		if(ir.light == true){
			verdes.add(ir.toString());
		}
		else{
			rojos.add(ir.toString());
		}
	}
	String v = "[" + String.join(",", verdes) + "]";
	out.put("Green", v);
	String r = "[" + String.join(",", rojos) + "]";
	out.put("Red", r);
	
}

public boolean estaVerde(Road r){
	return r.equals(incoming.get(semaforo).road);
}


}
