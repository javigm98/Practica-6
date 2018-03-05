package es.ucm.fdi.model;

public abstract class Event {
	private int time;
	public abstract void execute (RoadMap rm);
}
