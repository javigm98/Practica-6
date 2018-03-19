package es.ucm.fdi.model;

public class JunctionWithTimeSlice extends Junction{
	protected int unidadesTiempoUsadas = 0;
	protected int intervaloTiempo;
	public JunctionWithTimeSlice(String id) {
		super(id);
	}
	private class IncomingRoadWithTimeSlice extends IncomingRoad{

		public IncomingRoadWithTimeSlice(Road road ) {
			super(road);
		}
		
	}
}
