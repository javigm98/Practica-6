package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase que almacena tres listas con los vehículos, cruces y carreteras que se encuentran en la simulación en un momento dado.
 * @author Javier Guzmán y Jorge Villarrubia.
 *
 */
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
	
	
	/**
	 * Dado un id busca y devuelve un vehículo con ese mismo campo.
	 * @param id id del vehículo a buscar.
	 * @return el vehículo con el id pasado como parámetro o null en caso de no haber ningún vehículo con ese id.
	 */
	public Vehicle getVehicle(String id){ 
		for(Vehicle v: listaVehiculos){
			if(v.getId().equals(id)) return v;
		}
		return null;
	}
	
	/**
	 * Dado un id busca y devuelve un cruce con ese mismo campo.
	 * @param id id del cruce a buscar.
	 * @return el cruce con el id pasado como parámetro o null en caso de no haber ningún vehículo con ese id.
	 */
	public Junction getJunction(String id){ 
		//Si se recorre la lista y no ha devuleto nada aun es que el cruce no esta y se devuelve null.
		for(Junction j: listaCruces){
			if(j.getId().equals(id)) return j;
		}
		return null;
		
	}
	
	/**
	 * Dado un id busca y devuelve una carretera con ese mismo campo.
	 * @param id id de la carretera a buscar.
	 * @return la carretera con el id pasado como parámetro o null en caso de no haber ningún vehículo con ese id.
	 */
	public Road getRoad(String id){ 
		//Si se recorre la lista y no ha devuleto nada aun es que la carretera no esta y se devuelve null.
		for(Road r: listaCarreteras){
			if(r.getId().equals(id)) return r;
		}
		return null;
	}
	 
	/**
	 * Añade un cruce a la lista de cruces de RoadMap.
	 * @param j cruce a añadir.
	 */
	 public void addJunction(Junction j){
		 listaCruces.add(j);
	 }
	 
	 /**
	  * Añade una carretera a la lista de carreteras de RoadMap.
	  * @param r carretera a añadir.
	  */
	 public void addRoad(Road r){
		 listaCarreteras.add(r);
	 }
	 
	 /**
	  * Añade un vehículo a la lista de carreteras de RoadMap.
	  * @param v vehículo a añadir.
	  */
	 public void addVehicle(Vehicle v){
		 listaVehiculos.add(v);
	 }
}
