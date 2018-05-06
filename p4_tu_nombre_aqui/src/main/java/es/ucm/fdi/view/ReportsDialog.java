package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.Vehicle;

/**
 * Esta clase representa el dialogo que saltará en la interfaz gráfica para elegir
 * los objectos de simulacion de los qe see quiere un report
 * 
 * @authors Jorge y Javier
 *
 */

public class ReportsDialog extends JDialog{
	private SimulatorList vehiclesList, roadsList, junctionsList;
	private Runnable generate;
	
	/**
	 * @param owner es el JFrame sobre el que se lanzara este dialogo
	 * @param v lista de vehiculos cuyos ids mostrara en una tabla
	 * @param r lista de carreteras cuyos ids mostrara en una tabla
	 * @param j lista de cruces cuyos ids mostrara en una tabla
	 * @param generate runnable relativo a la accion del boton de generar que mostrará la ventana
	 */
	public ReportsDialog(JFrame owner, List<Vehicle> v, List<Road> r, List<Junction> j , Runnable generate){
		super(owner, "Generate Reports");
		this.generate = generate;
		vehiclesList = new SimulatorList(v);
		vehiclesList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Vehicles"));
		roadsList = new SimulatorList(r);
		roadsList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Roads"));
		junctionsList = new SimulatorList(j);
		junctionsList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Junction"));
		addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					System.out.println(e.getKeyChar());
				}
			
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
		initGUI();
		setSize(500, 500);
		setVisible(true);
	}
	
	/**
	 * Inicializa todos los componentes (etiquetas, tablas y botones) del dialogo y los coloca
	 */
	private void initGUI(){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JPanel etiquetas = new JPanel(new BorderLayout());
		etiquetas.setLayout(new BoxLayout(etiquetas, BoxLayout.Y_AXIS));
		etiquetas.add(new JLabel("Select items for which you want to generate reports."));
		etiquetas.add(new JLabel("Use 'c' to deselect all."));
		etiquetas.add(new JLabel("Use Ctrl + A to select all."));
		etiquetas.add(new JLabel("Use Crtl + clic to multiple selection"));
		mainPanel.add(etiquetas);
		JPanel listas = new JPanel(new BorderLayout());
		listas.setLayout(new GridLayout(1, 3));
		listas.add(vehiclesList);
		listas.add(roadsList);
		listas.add(junctionsList);
		mainPanel.add(listas);
		JPanel botones = new JPanel(new BorderLayout());
		JButton cancelar = new JButton("Cancel");
		
		cancelar.addActionListener( new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 dispose();
			 }
		});
		
		JButton generar = new JButton("Generate");
		
		generar.addActionListener( new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 //new Thread(generate).start();
				 SwingUtilities.invokeLater(generate);
				 dispose();
			 }
		});
		botones.setLayout(new BoxLayout(botones, BoxLayout.X_AXIS));
		generar.setAlignmentX(CENTER_ALIGNMENT);
		cancelar.setAlignmentX(CENTER_ALIGNMENT);
		botones.add(cancelar);
		botones.add(generar);
		mainPanel.add(botones);
		add(mainPanel);
	}
	
	/**
	 * @return los objectos de la simulacion seleccionados por el usuario en un mapa no ordenado
	 */
	public HashMap<String, Boolean> getSelected(){
		// Nos interesa devolver un mapa. Haremos muchas consultas que así serán O(1)
		HashMap<String, Boolean> seleccionados = new HashMap<>();
		
		for(String s : vehiclesList.getSeleccionados().keySet()){
			seleccionados.put(s, vehiclesList.getSeleccionados().get(s));
		}
		for(String s : roadsList.getSeleccionados().keySet()){
			seleccionados.put(s, roadsList.getSeleccionados().get(s));
		}
		for(String s : junctionsList.getSeleccionados().keySet()){
			seleccionados.put(s, junctionsList.getSeleccionados().get(s));
		}
		return seleccionados;
	}
}
