package es.ucm.fdi.model;

public abstract class  SimObject {
	protected String id;
	protected int time;
public abstract void avanza();
public abstract String generaInforme();

String getId(){
	return id;
}
}
