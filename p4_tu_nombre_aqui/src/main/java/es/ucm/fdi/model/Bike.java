package es.ucm.fdi.model;

import java.util.List;
import java.util.Map;

public class Bike extends Vehicle{

	public Bike(String id1, int maxSpeed1, List<Junction> route) {
		super(id1, maxSpeed1, route);
	}
	@Override
	public void setTiempoAveria(int n){
		if(velActual > (velMaxima /2)){
			super.setTiempoAveria(n);
		}
	}
	@Override
	public void fillReportDetails(Map<String, String> out){
		out.put("type", "bike");
		super.fillReportDetails(out);
	}

}
