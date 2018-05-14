package es.ucm.fdi.control;

import javax.swing.SwingUtilities;

/**
 * Clase que controla la ejecucion  de la simulacion paso a paso
 * @author Javier Guzman y Jorge Villarrubia
 *
 */

public class Stepper {
	private Runnable before;
	private Runnable during;
	private Runnable after;
	
	private boolean stopRequested = false;
	private int steps;
	
	/**
	 * 
	 * @param before runnable que se ejecuta antes de el avance de la simulacion
	 * @param during runnable que se ejecuta cada paso de la simulacion
	 * @param after runnable que se ejecuta tras concluirse la simulacion
	 */
	public Stepper(Runnable before, Runnable during, Runnable after) {
		super();
		this.before = before;
		this.during = during;
		this.after = after;
	}
	
	/**
	 * Avanza la simulacion
	 * @param steps  numero de pasos que avanza
	 * @param delay tiempo entre cada paso
	 * @return el hilo con la ejecucion de la simulacion
	 */
	
	public Thread start(int steps, int delay){
		this.steps = steps;
		this.stopRequested = false;
		
		Thread t = new Thread(()-> {
		try {
			SwingUtilities.invokeLater(before);
			while(!stopRequested && Stepper.this.steps > 0){
				SwingUtilities.invokeLater(during);
				try {
					Thread.sleep(delay);
				}
				catch(InterruptedException ie){
					
				}
				Stepper.this.steps --;
			}
		}finally{
			SwingUtilities.invokeLater(after);
		}
		});
		t.start();
		return t;
	}
	
	/**
	 * Para la ejecucion de la simulacion
	 */
	public void stop(){
		stopRequested = true;
	}
	
	/**
	 * @return el numero de pasos que quedan de la simulacion
	 */
	
	public int getSteps(){
		return steps;
	}
	
	
}
