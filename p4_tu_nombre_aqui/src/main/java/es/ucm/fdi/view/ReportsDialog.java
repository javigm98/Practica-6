package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.SimObject;
import es.ucm.fdi.model.Vehicle;

public class ReportsDialog extends JFrame{
	private SimulatorList vehiclesList, roadsList, junctionsList;
	public ReportsDialog(List<Vehicle> v, List<Road> r, List<Junction> j ){
		super("Generate Reports");
		vehiclesList = new SimulatorList(v);
		roadsList = new SimulatorList(r);
		junctionsList = new SimulatorList(j);
		initGUI();
		setSize(500, 500);
		setVisible(true);
	}
	
	private void initGUI(){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(new JLabel("Selec items for which you want to generate reports."
				+ "\n Use 'c' to deselect all.\n Use Ctrl + A to select all.\n"));
		JPanel listas = new JPanel(new GridLayout(1, 3));
		listas.add(vehiclesList);
		listas.add(roadsList);
		listas.add(junctionsList);
		mainPanel.add(listas);
		JPanel botones = new JPanel(new BorderLayout());
		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener( new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 System.exit(0);
			 }
		});
		botones.add(cancelar);
		mainPanel.add(botones);
		add(mainPanel);
	}
}
