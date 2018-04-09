package es.ucm.fdi.model;

import java.util.Map;

public class RoundRobinJunction extends JunctionWithTimeSlice{
	

	public RoundRobinJunction(String id, int minValorIntervalo, int maxValorIntervalo) {
		super(id);
		this.minValorIntervalo = minValorIntervalo;
		this.maxValorIntervalo = maxValorIntervalo;
	}
	
	@Override
	public void switchLights(){
		IncomingRoadWithTimeSlice verde = (IncomingRoadWithTimeSlice) incoming.get(semaforo);
		if(verde.getTimeSlice() == verde.getUsedTimeUnits()){
			if(verde.isFullyUsed()){
				verde.setTimeSlice(Math.min(verde.getTimeSlice() + 1, maxValorIntervalo));
			}
			else if(!verde.isUsed()){
				verde.setTimeSlice(Math.max(verde.getTimeSlice() - 1, minValorIntervalo));
			}
			verde.setUsedTimeUnits(0);
			super.switchLights();
		}
	}
	
	@Override
	public void fillReportDetails(Map<String, String> out){
		out.put("type", "rr");
		super.fillReportDetails(out);
	}
	
	
	
}
