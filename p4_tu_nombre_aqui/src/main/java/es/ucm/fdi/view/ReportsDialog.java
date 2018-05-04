package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.SimObject;
import es.ucm.fdi.model.Vehicle;

public class ReportsDialog extends JDialog{
	private SimulatorList vehiclesList, roadsList, junctionsList;
	public ReportsDialog(JFrame owner, List<Vehicle> v, List<Road> r, List<Junction> j ){
		super(owner, "Generate Reports");
		vehiclesList = new SimulatorList(v);
		vehiclesList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Vehicles"));
		roadsList = new SimulatorList(r);
		roadsList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Roads"));
		junctionsList = new SimulatorList(j);
		junctionsList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Junction"));
		initGUI();
		setSize(500, 500);
		setVisible(true);
	}
	
	private void initGUI(){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(new JLabel("Select items for which you want to generate reports."));
		mainPanel.add(new JLabel("Use 'c' to deselect all."));
		mainPanel.add(new JLabel("Use Ctrl + A to select all."));
		JPanel listas = new JPanel(new GridLayout(1, 3));
		listas.add(vehiclesList);
		listas.add(roadsList);
		listas.add(junctionsList);
		mainPanel.add(listas);
		JPanel botones = new JPanel(new BorderLayout());
		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener( new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 dispose();
			 }
		});
		botones.add(cancelar);
		mainPanel.add(botones);
		add(mainPanel);
	}
}
