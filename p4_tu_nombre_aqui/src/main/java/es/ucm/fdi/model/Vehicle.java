package es.ucm.fdi.model;

import java.util.List;
import java.util.Map;



public class Vehicle extends SimObject implements Comparable<Vehicle>{
	protected int velMaxima;
	protected int velActual;
	private boolean haLlegado;
	private Road road;
	private int pos;
	private List<Junction> itinerario; 
	private int tiempoAveria;
	private int posEnIti; //posEnIti marca la posicion en el itinerario del proximo cruce al que vamos
	protected int km;
	

	public Vehicle(String id1, int maxSpeed1, List<Junction> route)throws SimulatorException{
		id = id1;
		velMaxima = maxSpeed1;
		haLlegado = false;
		velActual = 0;
		try{
			road = route.get(0).carreteraUneCruces(route.get(1));
		}
		catch(SimulatorException se){
			throw new SimulatorException("The initial road for the vehicle " + id + " doesn't exist ", se);
		}
		pos = 0;
		itinerario = route;
		tiempoAveria = 0;
		posEnIti = 1;
		km = 0;
	}
	
	public int getVelActual() {
		return velActual;
	}
	
	
	public Road getRoad(){
		return road;
	}
	
	public void setVelMaxima(int v){
		velMaxima = v;
	}
	
	public boolean estaAveriado(){
		return tiempoAveria > 0;
	}
	
	
	
	public void avanza(){
		if(tiempoAveria == 0){
			if(pos + velActual > road.getLongitud()){
				km += (road.getLongitud() - pos);
			}
			else km += velActual;
			pos += velActual;
			if(pos >= road.getLongitud()){
				velActual = 0;
				pos = road.getLongitud();
				itinerario.get(posEnIti).entraVehiculo(this);
			}
			
		}
		else {
			tiempoAveria--;
			velActual = 0;
		}
	}
	
	public void velActual(int v){
		velActual = v;
	}
	
	public int getPos(){
		return pos;
	}
	
	public boolean getHaLlegado() {
		return haLlegado;
	}

	public void setHaLlegado(boolean haLlegado) {
		this.haLlegado = haLlegado;
	}

	public void moverASiguienteCarretera(){
		road.saleVehiculo(this);
		if (posEnIti == itinerario.size() - 1){
			haLlegado = true;
			velActual = 0;
		}
		else{
			try{
				road = itinerario.get(posEnIti).carreteraUneCruces(itinerario.get(posEnIti + 1));
			}
			catch (SimulatorException se){
				throw new SimulatorException("Unkown road in the itinerary of the vehicle " + id + " ", se);
			}
		
		posEnIti++;
		pos = 0;
		road.entraVehiculo(this);
		}
		
	}
	
	public void setTiempoAveria(int n){
		tiempoAveria += n;
		if(tiempoAveria > 0) velActual = 0;
	}
	
	public void setVelocidadActual(int vel){
		if(vel > velMaxima) velActual = velMaxima;
		else velActual = vel;
	}
	
	
	public String getReportHeader(){
		return "vehicle_report";
	}
	
	public void fillReportDetails(Map<String, String> out){
		out.put("speed", Integer.toString(velActual));
		out.put("kilometrage",Integer.toString(km));
		out.put("faulty",Integer.toString(tiempoAveria));
		if(haLlegado) {
			out.put("location", "arrived");
		} else {
			out.put("location", "(" + road.getId() + "," + pos + ")");
		}
	}

	@Override
	public int compareTo(Vehicle o) {
		return this.pos - o.pos;
	}
	public String toString(){
		String s = "";
		s += "id = " + id + ", pos = " + pos + ", velActual = " + 
		velActual + ", km = " + km;
		return s;
		
	}
	

	
}
