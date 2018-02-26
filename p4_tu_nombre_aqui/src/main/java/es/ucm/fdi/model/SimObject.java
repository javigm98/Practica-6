package es.ucm.fdi.model;

import java.util.Map;

public abstract class  SimObject {
	protected String id;
	protected int time;
	public abstract void avanza();
	public abstract String generaInforme();
	public abstract String getReportHeader();
	public abstract void fillReportDetails(Map<String, String> out);

String getId(){
	return id;
}

public void report(int time, Map<String, String> out){
	out.put("", getReportHeader());
	out.put ("id", id);
	out.put ("time", Integer.toString(time));
	fillReportDetails(out);
} //EN EL SIMULADOR HAY QUE HACER UN METODO QUE DADO EL MAPA LO MUESTRE!!!!!!
}
