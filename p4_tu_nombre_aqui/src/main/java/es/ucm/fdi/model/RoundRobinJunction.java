package es.ucm.fdi.model;

public class RoundRobinJunction extends JunctionWithTimeSlice{
	private int minValorIntervalo, maxValorIntervalo;

	public RoundRobinJunction(String id, int minValorIntervalo, int maxValorIntervalo) {
		super(id);
		this.minValorIntervalo = minValorIntervalo;
		this.maxValorIntervalo = maxValorIntervalo;
		intervaloTiempo = maxValorIntervalo;
	}
	
	
	
}
