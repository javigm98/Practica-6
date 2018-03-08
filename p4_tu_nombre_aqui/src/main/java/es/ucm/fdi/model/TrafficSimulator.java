package es.ucm.fdi.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.ini.IniSection;

public class TrafficSimulator {
	
private int time;
private List<Event> listaEventos;
private RoadMap rm;
private OutputStream out;
	public TrafficSimulator(OutputStream out1){
		time = 0;
		listaEventos = new ArrayList<>();
		rm = new RoadMap();
		out = out1;
	}
	public void run(int numPasos) throws IOException{
		int limiteTiempo = time + numPasos -1;
		while(time <= limiteTiempo){
			run();
		}
	}
	
	public void run() throws IOException{
		for(Event e: listaEventos){
			e.execute(rm, time);
		}
		for(Road r: rm.getListaCarreteras()){
			r.avanza();
		}
		for(Junction j: rm.getListaCruces()){
			j.avanza();
		}
		++time;
		if(out != null) writeReport();
	}
	
	public void addEvent(Event e){
		if(e.getTime() >= time){
			listaEventos.add(e);
		}
		
		//Luego la ordenamos!!!!!!!
	}
	void reset(){
		time = 0;
		listaEventos = new ArrayList<>();
		rm = new RoadMap();
	}
	
	public void setOut(OutputStream out1){
		out = out1;
	}
	
	public void writeReport() throws IOException{
		Map <String,String> aux = new HashMap<>();
		 for(Vehicle v : rm.getListaVehiculos()){
			 v.report(time,  aux);
			 mapAIni(aux, v.getReportHeader()).store(out);
			 aux.clear();
		 }
		 for(Road r : rm.getListaCarreteras()){
			 r.report(time,  aux);
			 mapAIni(aux, r.getReportHeader()).store(out);
			 aux.clear();
		 }
		 for(Junction j : rm.getListaCruces()){
			 j.report(time,  aux);
			 mapAIni(aux, j.getReportHeader()).store(out);
			 aux.clear();
		 } 
	}
	
	private IniSection mapAIni(Map<String, String> mapa, String tag){
		IniSection sec = new IniSection(tag);
		for(Map.Entry<String, String> entry: mapa.entrySet()){
			sec.setValue(entry.getKey(), entry.getValue());
		}
		return sec;
	}
	
	
}
