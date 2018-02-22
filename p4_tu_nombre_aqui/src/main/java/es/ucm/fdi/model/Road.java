package es.ucm.fdi.model;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Road extends SimObject{
private int longitud, maxVel;
private ArrayList <Vehicle> listaVehiculos;
private Junction cruceIni, cruceFin;

public void avanza(){
	int max, velBase;
	if(listaVehiculos.size() > 1) max = listaVehiculos.size();
	else max = 1;
	int secFact = (maxVel / max) + 1;
	if(secFact < maxVel) velBase = secFact;
	else velBase = maxVel;
	
}


public ArrayList<Boolean> hayAverias(){
	ArrayList<Boolean> averias = new ArrayList<Boolean>(listaVehiculos.size(), true);
	int i = 0;
	boolean encontrado = false;
	while (i < listaVehiculos.size() && !encontrado){
		if (listaVehiculos.get(i).estaAveridado()) encontrado = true;
	++i;
	}
}
public void entraVehiculo(Vehicle v){
	listaVehiculos.add(listaVehiculos.size() - 1, v) // Devuelve un booleano pero no lo necesitamos
}

/*public void saleVehiculo(){
	listaVehiculos // Preguntar como funciona el ArrayDeque
}
*/

public Junction getcruceIni(){
	return cruceIni;
}

public Junction getcruceFin(){
	return cruceFin;
}

public int getLongitud(){
	return longitud;
}
}
