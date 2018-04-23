package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa un vehículo de la simulación. Alamcena su velocidad máxima, su velocidad actual,
 * si ha llegado a su destino o no, la carretera en la que se encuentra, la posción que ocupa en dicha carretera,
 * un alista de cruces con el itinerario a seguir, el tiempo que le queda estando averiado, la posicón dentro del itinerario,
 * y los kilómetros recorridos.
 * @author Javier Guzmán y Jorge Villarrubia.
 */
public class Vehicle extends SimObject implements Comparable<Vehicle>{
	protected int velMaxima;
	protected int velActual = 0;
	private boolean haLlegado = false;
	private Road road;
	private int pos = 0;
	private List<Junction> itinerario; 
	private int tiempoAveria = 0;
	private int posEnIti = 1; //posEnIti marca la posicion en el itinerario del proximo cruce al que vamos
	protected int km = 0;
	

	public Vehicle(String id, int velMaxima, List<Junction> route)throws SimulatorException{
		this.id = id;
		this.velMaxima = velMaxima;
		try{
			road = route.get(0).carreteraUneCruces(route.get(1));
		}
		catch(SimulatorException se){
			throw new SimulatorException("The initial road for the vehicle " + id + " doesn't exist ", se);
		}
		itinerario = route;
	}
	
	/**
	 * 
	 * @return la velocidad actual de un vehículo.
	 */
	public int getVelActual() {
		return velActual;
	}
	
	/**
	 * 
	 * @return la carretera por la que se mueve un vehículo.
	 */
	public Road getRoad(){
		return road;
	}
	
	/**
	 * 
	 * @return true si el vehículo está averiado, false si no.
	 */
	public boolean estaAveriado(){
		return tiempoAveria > 0;
	}
	
	
	/**
	 * Avanza un vehículo en la carretera, disminuyendo su tiempo de avería si está averiado o avanzando su posición e
	 * incrementando sus kilómetros si no lo está, y añadiéndolo a la cola del cruce correspondiente si ha llegado al 
	 * final de la carretera.
	 */
	@Override
	public void avanza(){
		if(tiempoAveria == 0){
			if(pos + velActual > road.getLongitud()){
				km += (road.getLongitud() - pos);
			}
			else {
				km += velActual;
			}
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
	
	/**
	 * 
	 * @return la posición del vehículo en la carretera.
	 */
	public int getPos(){
		return pos;
	}
	
	/**
	 * 
	 * @return true si el vehículo ha llegado a su destino, false si no.
	 */
	public boolean getHaLlegado() {
		return haLlegado;
	}
	/**
	 * 
	 * @param haLlegado true si el vehículo ha llegado a su destino, false si no.
	 */

	public void setHaLlegado(boolean haLlegado) {
		this.haLlegado = haLlegado;
	}
	
	/**
	 * Mueve el vehículo a la siguiente carretera de su itinerario, actualizando si ha llegado a su destino en caso
	 * de ser así.
	 */

	public void moverASiguienteCarretera() throws SimulatorException{
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
	/**
	 * 
	 * @param n pasos de la simulación que debe estra averiado el vehículo.
	 */
	public void setTiempoAveria(int n){
		tiempoAveria += n;
		if(tiempoAveria > 0) velActual = 0;
	}
	
	/**
	 * 
	 * @param vel nueva velocidad actual del vehículo.
	 */
	public void setVelocidadActual(int vel){
		if(vel > velMaxima) velActual = velMaxima;
		else velActual = vel;
	}
	
	@Override
	public String getReportHeader(){
		return "vehicle_report";
	}
	
	@Override
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
	
	@Override
	public String toString(){
		String s = "";
		s += "id = " + id + ", pos = " + pos + ", velActual = " + 
		velActual + ", km = " + km;
		return s;
		
	}

	@Override
	public void describe(Map<String, String> out) {
		out.put("ID", id);
		out.put("Road", road.getId());
		out.put("Location", "" + pos);
		out.put("Speed", "" + velActual);
		out.put("Km", "" + km);
		out.put("Faulty Units", "" + tiempoAveria);
		List<String> ruta = new ArrayList<>();
		for(Junction j : itinerario){
			ruta.add(j.getId());
		}
		String s = "[" + String.join(",", ruta) + "]";
		out.put("Itinerary", s);	
	}
	

	
}
