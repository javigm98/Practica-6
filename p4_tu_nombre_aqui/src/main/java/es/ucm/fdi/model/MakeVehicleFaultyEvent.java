package es.ucm.fdi.model;

import java.util.List;

public class MakeVehicleFaultyEvent extends Event{
	private String[] listaVehiculos;
	private int duracion;
	
	public MakeVehicleFaultyEvent(int time1, int dur, String[] lista){
		time = time1;
		duracion = dur;
		listaVehiculos = lista;
	}
	//Execute ya los averia
	public void execute(RoadMap rm, int timeExecution){
		if(time == timeExecution){
			for(String s : listaVehiculos){
				rm.getVehicle(s).setTiempoAveria(duracion);
			}
		}
	}
}
