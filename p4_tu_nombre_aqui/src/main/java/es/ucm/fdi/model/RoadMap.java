package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoadMap {
	private List<Vehicle> listaVehiculos;
	private List<Junction> listaCruces;
	private List<Road> listaCarreteras;
	public RoadMap(){
		listaVehiculos = new ArrayList<>();
		listaCruces = new ArrayList<>();
		listaCarreteras = new ArrayList<>();
	}
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
	 public SimObject getSimulatedObject(String id){
		 SimObject objeto = getVehicle(id);
		 if(objeto == null){
			 objeto = getRoad(id);
			 if(objeto == null){
				 objeto = getJunction(id);
			 }
		 }
		 return objeto;
	 }
	 
	 public void addJunction(Junction j){
		 listaCruces.add(j);
	 }
	 public void addRoad(Road r){
		 listaCarreteras.add(r);
		 /*int i = 0;
		 boolean encontradoIni = false, encontradoFin = false;;
		 while((!encontradoIni || !encontradoFin) && i < listaCruces.size()){
			 if(r.getcruceIni().equals(listaCruces.get(i).getId())){
				 listaCruces.get(i).addNewIncomingRoad(r);
				 encontradoIni = true;
			 }
			 if(r.getcruceFin().equals(listaCruces.get(i).getId())){
				 listaCruces.get(i).addNewOutgoingRoad(r);
				 encontradoFin = true;
			 }
			 ++i;
		 } */
	 }
	 
	 public void addVehicle(Vehicle v){
		 listaVehiculos.add(v);
		/* boolean encontrado = false;
		int i = 0;
		 while(!encontrado && i < listaCarreteras.size()){
			 if(v.getRoad().getId().equals(listaCarreteras.get(i).getId())){
				 
			 }
		 }*/
	 }
	 public void clear(){
		 listaVehiculos.clear();
		 listaCarreteras.clear();
		 listaCruces.clear();
	 }
	 
	 //public String generateReport(){
		 
	 //}
	 
	 /*public void generateReport(int time, Map<String, String> out){
		 for(Vehicle v: listaVehiculos){
			 v.report(time, out);
		 }
		 for(Road r: listaCarreteras){
			 r.report(time, out);
		 }
		 for(Junction j: listaCruces){
			 j.report(time,  out);
		 }
	*/
}
