package es.ucm.fdi.model;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Junction extends SimObject{
private Map<Road, IncomingRoad> entradasCruce;
private ArrayList<IncomingRoad> incoming; //?????
private ArrayList<Road> salidasCruce;
private int semaforo; //Significa que todos estan en rojo

public Junction(String id1){
	semaforo = -1;
	id = id1;
	entradasCruce = new HashMap<Road, IncomingRoad>();
	incoming = new ArrayList<>();
	salidasCruce = new ArrayList<>();
}
private class IncomingRoad{
	Road road;///??????
	boolean light;
	ArrayDeque<Vehicle> cola;
	
	public IncomingRoad(Road road1) {
		road = road1;
		light = false;
		cola = new ArrayDeque<>();
	}

	public String toString(){
		String s = "";
		s = s + "(" + road.getId() + ", ";
		if(incoming.get(semaforo).equals(this)){
			s += "green";
		}
		else{
			s+= "red";
		}
		s+=", [";
		List<String> list = new ArrayList<String>();
		for(Vehicle v: cola){
			list.add(v.getId());
		}
		s += String.join(", ", list);
		s += "])";
		return s;
		
	}
}

public void entraVehiculo(Vehicle v){ //Excepcion vehiculo que no es de esa carretera
	entradasCruce.get(v.getRoad()).cola.addLast(v);
	//Como apuntan al mismo incoming road se puede modificar desde cualquiera de los
	//dos sitios.
}
public void avanza(){
	if(!incoming.get(semaforo).cola.isEmpty()){
	incoming.get(semaforo).cola.getFirst().moverASiguienteCarretera();
	incoming.get(semaforo).cola.pop();
	}
	semaforo = (semaforo++) % incoming.size();
}




public Road carreteraUneCruces(Junction destino){
	for (Road r: salidasCruce){
		if (r.getcruceFin().equals(destino)) return r;
	}
	return null;
}

public String getReportHeader(){
	return "[junction_report]";
}
@Override
public void fillReportDetails(Map<String, String> out) {
List<String> list = new ArrayList<String>();
	for(IncomingRoad ir: incoming){
		list.add(ir.toString());
	}
	String colas = String.join(", ", list);
	out.put("queues", colas);
}

public void addNewIncomingRoad(Road road){
	IncomingRoad ir = new IncomingRoad(road);
	entradasCruce.put(road,ir);
	incoming.add(ir);
}

public void addNewOutgoingRoad(Road road){
	salidasCruce.add(road);
}



}
