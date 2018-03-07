package es.ucm.fdi.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.util.*;

public class Road extends SimObject{
private int longitud, maxVel;
private MultiTreeMap <Integer, Vehicle> listaVehiculos;

//hay que inicializar la lista vehiculos con el comparador mayor (>)
private Junction cruceIni, cruceFin;

public Road(String id1, int longitud1, int maxVel1, Junction ini, Junction fin){
	id = id1;
	longitud = longitud1;
	maxVel = maxVel1;
	cruceIni = ini;
	cruceFin = fin;
	
}

public void avanza(){
	long max;
	int velBase;
	boolean hayAverias = false;
	MultiTreeMap<Integer, Vehicle> nuevo = new MultiTreeMap<>();
	if(listaVehiculos.sizeOfValues() > 1) max = listaVehiculos.sizeOfValues();
	else max = 1;
	int secFact = (maxVel / (int) max) + 1;
	if(secFact < maxVel) velBase = secFact;
	else velBase = maxVel;
	for (Vehicle v: listaVehiculos.innerValues()){
		if (v.estaAveriado()) {
			hayAverias = true;
			v.setVelocidadActual(0);
		}
		else{
			if(hayAverias) velBase /= 2;
			v.setVelocidadActual(velBase);
		}
		v.avanza();
		nuevo.putValue(v.getPos(), v);
	}
	//Hacer clear del listaVehiculos anterior????
	listaVehiculos = nuevo;
}


public void entraVehiculo(Vehicle v){
	v.setVelMaxima(maxVel);
	listaVehiculos.putValue(0, v); // Devuelve un booleano pero no lo necesitamos
}

public void saleVehiculo(Vehicle v){
	listaVehiculos.remove(longitud, v);
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

public String getReportHeader(){
	return "[road_report]";
}
public void setCruceIni(Junction ini){
	cruceIni = ini;
}
public void setCruceFin(Junction fin){
	cruceFin = fin;
}


public void fillReportDetails(Map<String, String> out){
	String s = "";
	List <String> list = new ArrayList<String>();
	for (Vehicle v: listaVehiculos.innerValues()){
		list.add("(" + v.getId() + ", " + v.getPos() + ")");
	}
	s = String.join(", ", list);
	out.put("state", s);
}
} 
