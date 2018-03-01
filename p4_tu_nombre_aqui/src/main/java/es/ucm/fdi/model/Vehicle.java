package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.Map;

import es.ucm.fdi.ini.IniSection;


public class Vehicle extends SimObject{
	private int velMaxima;
	private int velActual;
	private boolean haLlegado;
	private Road road;
	private int pos;
	private ArrayList<Junction> itinerario; // por ahora lo hacemois asi, quizas sea mas facil haciendo un array de junction
	private int tiempoAveria;
	private int posEnIti;
	private int km;
	
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
			
			//Poner velocidad actual al valor que corresponda (Hecho)
			pos += velActual;
			if(pos >= road.getLongitud()){
				pos = road.getLongitud();
				velActual = 0;
				road.saleVehiculo(this);
			// Insertar vehiculo en la cola del cruce
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
	
	public void moverASiguienteCarretera(){
		if (posEnIti == itinerario.size()){
			haLlegado = true;
			velActual = 0;
		}
		else{
		road = itinerario.get(posEnIti).carreteraUneCruces(itinerario.get(posEnIti + 1));
		posEnIti++;
		pos = 0;
		road.entraVehiculo(this); // ¿Se puede hacer esto por lo del this que esta feo?
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
	
	/*public String generaInforme(){
		String informe;
		IniSection ini = new IniSection("vehicle_report");
		ini.setValue("id", id);
		ini.setValue("time", time);
		ini.setValue("speed", velActual);
		ini.setValue("kilometrage", km);
		ini.setValue("faulty", tiempoAveria);
		if(haLlegado) {
			ini.setValue("location", "arrived");
		} else {
			ini.setValue("location", "(" + road.getId() + ", " + pos + ")");
		}
		informe = ini.toString();
		return informe;
	}*/
	
	public String getReportHeader(){
		return "[vehicle_report]";
	}
	
	public void fillReportDetails(Map<String, String> out){
		out.put("speed", Integer.toString(velActual));
		out.put("kilometrage",Integer.toString(km));
		out.put("faulty",Integer.toString(tiempoAveria));
		if(haLlegado) {
			out.put("location", "arrived");
		} else {
			out.put("location", "(" + road.getId() + ", " + pos + ")");
		}
	}
	
}
