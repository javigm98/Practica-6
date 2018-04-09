package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.List;

public class JunctionWithTimeSlice extends Junction{
	protected int minValorIntervalo;
	protected int maxValorIntervalo;
	public JunctionWithTimeSlice(String id) {
		super(id);
	}
	protected class IncomingRoadWithTimeSlice extends IncomingRoad{
		private int timeSlice = maxValorIntervalo, usedTimeUnits = 0;
		private boolean used = false, fullyUsed = true;

		public IncomingRoadWithTimeSlice(Road road ) {
			super(road);
		}
		
		public void advanceFirstVehicle(){
			if(usedTimeUnits < timeSlice){
			if(!cola.isEmpty()){
				used = true;
				cola.removeFirst().moverASiguienteCarretera();
			}
			else{
				fullyUsed = false;
			}
			++usedTimeUnits;	
		}
		}

		public int getTimeSlice() {
			return timeSlice;
		}

		public void setTimeSlice(int timeSlice) {
			this.timeSlice = timeSlice;
		}

		public int getUsedTimeUnits() {
			return usedTimeUnits;
		}

		public void setUsedTimeUnits(int usedTimeUnits) {
			this.usedTimeUnits = usedTimeUnits;
		}

		public boolean isUsed() {
			return used;
		}
		public boolean isFullyUsed() {
			return fullyUsed;
		}
		
		@Override
		
		public String toString(){
			String s = "";
			s = s + "(" + road.getId() + ",";
			if(incoming.get(semaforo).equals(this)){
				s = s + "green:" + (timeSlice - usedTimeUnits);
			}
			else{
				s+= "red";
			}
			s+=",[";
			List<String> list = new ArrayList<String>();
			for(Vehicle v: cola){
				list.add(v.getId());
			}
			s += String.join(",", list);
			s += "])";
			return s;
		}

		
	}
}
