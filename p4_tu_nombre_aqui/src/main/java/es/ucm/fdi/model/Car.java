package es.ucm.fdi.model;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Car extends Vehicle{
private int resistencia, tMaxAveria, kmUltimaAveria = 0;
private Random numAleatorio;
private double probAveria;
public Car(String id, int maxSpeed, List<Junction> route, int resistencia,
		int tMaxAveria, double probAveria, long semilla) {
	super(id, maxSpeed, route);
	this.resistencia = resistencia;
	this.tMaxAveria = tMaxAveria;
	this.probAveria = probAveria;
	this.numAleatorio = new Random(semilla);
}
@Override
public void avanza(){
	if(!estaAveriado()){
		if(km - kmUltimaAveria >= resistencia){
			 if(numAleatorio.nextDouble() < probAveria){
				 setTiempoAveria(numAleatorio.nextInt(tMaxAveria) + 1);
				 kmUltimaAveria = km;
			 }
		}
	}
	super.avanza(); 
}
@Override
public void fillReportDetails(Map<String, String> out){
	out.put("type", "car");
	super.fillReportDetails(out);
}
}

