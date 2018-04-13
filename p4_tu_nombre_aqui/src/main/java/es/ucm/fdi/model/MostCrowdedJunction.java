package es.ucm.fdi.model;

import java.util.Map;

import es.ucm.fdi.model.JunctionWithTimeSlice.IncomingRoadWithTimeSlice;

public class MostCrowdedJunction extends JunctionWithTimeSlice{
	
	public MostCrowdedJunction(String id){
		super(id);
	}
	
	@Override
	public void addNewIncomingRoad(Road road){
		IncomingRoadWithTimeSlice ir = new IncomingRoadWithTimeSlice(road, 0);
		entradasCruce.put(road,ir);
		incoming.add(ir);
		semaforo = incoming.size()-1;
	}
	
	@Override
	public void switchLights(){
		IncomingRoadWithTimeSlice verde = (IncomingRoadWithTimeSlice) incoming.get(semaforo);
		if(verde.getTimeSlice() == verde.getUsedTimeUnits()){
			incoming.get(semaforo).light = false;
		}
		int max = -1;
		semaforo = 0;
		for(int i = 0; i < incoming.size(); ++i){
			if(!incoming.get(i).equals(verde) && incoming.get(i).cola.size() > max){
				max = incoming.get(i).cola.size();
				semaforo = i;
			}
		}
		incoming.get(semaforo).light = true;
		((IncomingRoadWithTimeSlice) incoming.get(semaforo)).setTimeSlice(Math.max(max/2,  1));
		((IncomingRoadWithTimeSlice) incoming.get(semaforo)).setUsedTimeUnits(0);	
	}
	
	@Override
	public void fillReportDetails(Map<String, String> out){
		super.fillReportDetails(out);
		out.put("type", "mc");
	}
}
