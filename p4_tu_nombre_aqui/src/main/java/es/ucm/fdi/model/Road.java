package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.util.*;


/**
 * Clase que representa una carretera en el simulador. Tiene como atributos, aparte de los hererdados por ser un simObject,
 * su longitud (longitud), su velocidad máxima (maxVel) y un mapa con los Vehículos ordenados en orden inverso 
 * por sus posiciones. Además contiene referencias a los cruces inicial (cruceIni) y final (cruceFin) de la carretera.
 * 
 * @author Javier Guzmán y Jorge Villarrubia.
 */
public class Road extends SimObject{
protected int longitud;
protected int maxVel;
protected MultiTreeMap <Integer, Vehicle> listaVehiculos = new MultiTreeMap<Integer, Vehicle>(Collections.reverseOrder());
private Junction cruceIni, cruceFin;

public Road(String id, int longitud, int maxVel, Junction cruceIni, Junction cruceFin){
	this.id = id;
	this.longitud = longitud;
	this.maxVel = maxVel;
	this.cruceIni = cruceIni;
	this.cruceFin = cruceFin;
	
}

/**Método que implementa la operación avanzar en una carretera. Tras calcular la velocidad de cada vehículo, cambia la
 * velocidad de estos e invoca a su método avanzar.
 * Internamente, va guaradando las modificaciones en le avance de los vehículos en un mapa auxiliar que luego copia como
 * lista de vehículos de la carretera. 
 */
@Override
public void avanza(){
	long max;
	int velBase;
	boolean hayAverias = false;
	MultiTreeMap<Integer, Vehicle> nuevo = new MultiTreeMap<>(Collections.reverseOrder());
	if(listaVehiculos.sizeOfValues() > 1) {
		max = listaVehiculos.sizeOfValues();
	}
	else {
		max = 1;
	}
	int secFact = (maxVel / (int) max) + 1;
	if(secFact < maxVel) {
		velBase = secFact;
	}
	else {
		velBase = maxVel;
	}
	for (Vehicle v: listaVehiculos.innerValues()){
		if(!v.getHaLlegado()){
		if (v.estaAveriado()) {
			hayAverias = true;
			v.setVelocidadActual(0);
		}
		else{
			if(hayAverias) {
				velBase /= 2;
			}
			if(v.getPos() < longitud){
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
 * Añade un nuevo vehículo a la carretera, colocándolo en la posición 0 de la misma.
 * @param v Vehículo a introducir en la carretera.
 */
public void entraVehiculo(Vehicle v){
	listaVehiculos.putValue(0, v);
}

/**
 * Saca un vehículo de la carretera.
 * @param v Vehículo a sacar de la carretera.
 */
public void saleVehiculo(Vehicle v){
	listaVehiculos.removeValue(v.getPos(), v);
}

public Junction getcruceIni(){
	return cruceIni;
}

public Junction getcruceFin(){
	return cruceFin;
}

public int getLongitud(){
	return longitud;
}

@Override
public String getReportHeader(){
	return "road_report";
}

public void setCruceIni(Junction ini){
	cruceIni = ini;
}

public void setCruceFin(Junction fin){
	cruceFin = fin;
}

@Override
public void fillReportDetails(Map<String, String> out){
	String s = "";
	List <String> list = new ArrayList<String>();
	for (Vehicle v: listaVehiculos.innerValues()){
		list.add("(" + v.getId() + "," + v.getPos() + ")");
	}
	s = String.join(",", list);
	out.put("state", s);
}

public String toString(){
	String s ="";
	for (Vehicle v : listaVehiculos.innerValues()){
		s += v.toString();
		s += " ";
		}
	
	return s;
	
}
} 
