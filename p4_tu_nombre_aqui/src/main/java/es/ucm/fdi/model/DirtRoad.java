package es.ucm.fdi.model;

import java.util.Collections;
import java.util.Map;

import es.ucm.fdi.util.MultiTreeMap;

public class DirtRoad extends Road{

	public DirtRoad(String id, int longitud, int maxVel, Junction ini, Junction fin) {
		super(id, longitud, maxVel, ini, fin);
	}
@Override
public void avanza(){
	long max;
	int velBase = maxVel;
	int numAverias = 0;
	MultiTreeMap<Integer, Vehicle> nuevo = new MultiTreeMap<>(Collections.reverseOrder());
	for (Vehicle v: listaVehiculos.innerValues()){
		//if(v!= null){
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
		//}
	} 
	//listaVehiculos.clear();
	//Hacer clear del listaVehiculos anterior????
	listaVehiculos = nuevo;
}

@Override
public void fillReportDetails(Map<String, String> out){
out.put("type", "dirt");
super.fillReportDetails(out);
}
}
