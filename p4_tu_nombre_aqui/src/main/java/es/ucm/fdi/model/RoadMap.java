package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.List;

public class RoadMap {
	private List<Vehicle> listaVehiculos = new ArrayList<>();
	private List<Junction> listaCruces = new ArrayList<>();
	private List<Road> listaCarreteras = new ArrayList<>();
	public List<Vehicle> getListaVehiculos() {
		return listaVehiculos;
	}
	public List<Junction> getListaCruces() {
		return listaCruces;
	}
	public List<Road> getListaCarreteras() {
		return listaCarreteras;
	}
	
	public Vehicle getVehicle(String id){ 
		//Si se recorre la lista y no ha devuleto nada aun es que el vehiculo no esta y se devuelve null.
		for(Vehicle v: listaVehiculos){
			if(v.getId().equals(id)) return v;
		}
		return null;
	}
	
	public Junction getJunction(String id){ 
		//Si se recorre la lista y no ha devuleto nada aun es que el cruce no esta y se devuelve null.
		for(Junction j: listaCruces){
			if(j.getId().equals(id)) return j;
		}
		return null;
	}
	
	public Road getRoad(String id){ 
		//Si se recorre la lista y no ha devuleto nada aun es que la carretera no esta y se devuelve null.
		for(Road r: listaCarreteras){
			if(r.getId().equals(id)) return r;
		}
		return null;
	}
}
