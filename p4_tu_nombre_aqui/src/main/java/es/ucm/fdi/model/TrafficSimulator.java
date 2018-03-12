package es.ucm.fdi.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.util.MultiTreeMap;

public class TrafficSimulator {
	
private int time;
private MultiTreeMap<Integer, Event> listaEventos;
//private List<Event> listaEventos;
private RoadMap rm;
private OutputStream out;
	public TrafficSimulator(OutputStream out1){
		time = 0;
		listaEventos = new MultiTreeMap<Integer, Event>();
		rm = new RoadMap();
		out = out1;
	}
	public void run(int numPasos) throws IOException, IllegalArgumentException{
		int limiteTiempo = time + numPasos -1;
		while(time <= limiteTiempo){
			run();
		}
	}
	
	public void run() throws IOException, IllegalArgumentException{
		for(Event e: listaEventos.innerValues()){
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
			listaEventos.putValue(e.getTime(), e);
		}
	}
	void reset(){
		time = 0;
		listaEventos = new MultiTreeMap<Integer, Event>();
		rm = new RoadMap();
	}
	
	public void setOut(OutputStream out1){
		out = out1;
	}
	
	public void writeReport() throws IOException{
		Map <String,String> aux = new LinkedHashMap<>();
		 for(Vehicle v : rm.getListaVehiculos()){
			 v.report(time,  aux);
			 mapAIni(aux, v.getReportHeader()).store(out);
			 out.write('\n');
			 aux.clear();
		 }
		 for(Road r : rm.getListaCarreteras()){
			 r.report(time,  aux);
			 mapAIni(aux, r.getReportHeader()).store(out);
			 out.write('\n');
			 aux.clear();
		 }
		 for(Junction j : rm.getListaCruces()){
			 j.report(time,  aux);
			 mapAIni(aux, j.getReportHeader()).store(out);
			 out.write('\n');
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
