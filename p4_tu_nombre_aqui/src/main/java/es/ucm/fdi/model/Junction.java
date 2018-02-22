package es.ucm.fdi.model;
import java.util.ArrayList;

public class Junction extends SimObject{
ArrayList <Road> cSalida;



Road carreteraUneCruces(Junction destino){
	for (Road r: cSalida){
		if (r.getcruceFin().equals(destino)) return r;
	}
	return null;
}

}
