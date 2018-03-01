package es.ucm.fdi.model;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;

public class Junction extends SimObject{
private Map<Road, IncomingRoad> entradasCruce;
private ArrayList<IncomingRoad> incoming; //?????
private ArrayList<Road> salidasCruce;
private int semaforo = -1; //Significa que todos estan en rojo

private class IncomingRoad{
	Road road;///??????
	ArrayDeque<Vehicle> cola;
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
	for(IncomingRoad ir: incoming){
		/*for (Vehicle v: ir.cola.iterator()){
			
		}*/
	}
	
}



}
