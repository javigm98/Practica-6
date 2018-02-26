package es.ucm.fdi.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import es.ucm.fdi.util.*;

public class Road extends SimObject{
private int longitud, maxVel;
private MultiTreeMap <Integer, Vehicle> listaVehiculos;
private Junction cruceIni, cruceFin;

public void avanza(){
	long max;
	int velBase;
	if(listaVehiculos.sizeOfValues() > 1) max = listaVehiculos.sizeOfValues();
	else max = 1;
	int secFact = (maxVel / (int) max) + 1;
	if(secFact < maxVel) velBase = secFact;
	else velBase = maxVel;
	
}


public int hayAverias(){
	int i = 0;
	boolean encontrado = false;
	while (i < listaVehiculos.size() && !encontrado){
		
		if (listaVehiculos) encontrado = true;
	++i;
	}
}
public void entraVehiculo(Vehicle v){
	listaVehiculos.add(listaVehiculos.size() - 1, v); // Devuelve un booleano pero no lo necesitamos
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
} 
