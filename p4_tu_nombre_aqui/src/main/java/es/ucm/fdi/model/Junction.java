package es.ucm.fdi.model;
import java.util.ArrayList;

public class Junction extends SimObject{
private ArrayList <Road> cSalida;




public Road carreteraUneCruces(Junction destino){
	for (Road r: cSalida){
		if (r.getcruceFin().equals(destino)) return r;
	}
	return null;
}

public String getReportHeader(){
	return "[junction_report]";
}

}
