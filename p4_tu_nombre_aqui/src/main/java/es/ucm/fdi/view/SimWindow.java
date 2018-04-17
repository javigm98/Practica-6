package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import es.ucm.fdi.model.TrafficSimulator.SimulatorListener;

/**
 * Esto es sólo para empezar a jugar con las interfaces
 * de la P5. 
 * 
 * El código <i>no</i> está bien organizado, y meter toda
 * la funcionalidad aquí sería un disparate desde un punto
 * de vista de mantenibilidad.
 */

public class SimWindow extends JFrame implements SimulatorListener{
	public SimWindow() {
		super("Traffic Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addBars();
		
		setSize(1000, 1000);		
		setVisible(true);
	}
	
	private void addBars() {
		// instantiate actions
		SimulatorAction salir = new SimulatorAction(
				"Salir", "exit.png", "Salir de la aplicacion",
				KeyEvent.VK_S, "control shift X", 
				()-> System.exit(0));
		
		SimulatorAction limpiar = new SimulatorAction(
				"Borrar", "clear.png", "Limpiar la aplicacion",
				KeyEvent.VK_B, "control shift B", 
				()-> System.err.println("limpiando..."));
		SimulatorAction guardar = new SimulatorAction(
				"Guardar", "save.png", "Guardar cosas",
				KeyEvent.VK_G, "control S", 
				()-> System.err.println("guardando..."));
		
		SimulatorAction load = new SimulatorAction(
				"Cargar", "open.png", "Cargar cosas",
				KeyEvent.VK_C, "control L", 
				()-> System.err.println("cargando..."));
		
		SimulatorAction events = new SimulatorAction(
				"Eventos", "events.png", "Mostrar eventos",
				KeyEvent.VK_E, "control E", 
				()-> System.err.println("cargando eventos..."));
		
		SimulatorAction play = new SimulatorAction(
				"Ejecutar", "play.png", "Ejecutar eventos",
				KeyEvent.VK_J, "control J", 
				()-> System.err.println("ejecutando eventos..."));
		
		JPanel eventsEditor = new JPanel();
		JPanel eventsQueue = new JPanel();
		JPanel reportsArea = new JPanel();
		JPanel vehiclesTable = new JPanel();
		JPanel roadsTable = new JPanel();
		JPanel junctionTable = new JPanel();
		JPanel roadMap = new JPanel();
		JToolBar bar = new JToolBar();
		
		eventsEditor.setBorder(BorderFactory.createTitledBorder("Events Editor"));
		eventsQueue.setBorder(BorderFactory.createTitledBorder("Events Queue"));
		reportsArea.setBorder(BorderFactory.createTitledBorder("Reports"));
		vehiclesTable.setBorder(BorderFactory.createTitledBorder("Vehicles"));
		roadsTable.setBorder(BorderFactory.createTitledBorder("Roads"));
		junctionTable.setBorder(BorderFactory.createTitledBorder("Junctions"));
		
		
		
		
		JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, eventsEditor, eventsQueue);
		split1.setVisible(true);
		split1.setResizeWeight(0.5);
		JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split1, reportsArea);
		split2.setVisible(true);
		split2.setResizeWeight(0.66);
		JSplitPane split3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, vehiclesTable, roadsTable);
		split3.setVisible(true);
		split3.setResizeWeight(0.5);
		JSplitPane split4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split3, junctionTable);
		split4.setVisible(true);
		split4.setResizeWeight(0.66);
		JSplitPane split5 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split4, roadMap);
		split5.setVisible(true);
		split5.setResizeWeight(0.5);
		JSplitPane split6 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split2, split5);
		split6.setVisible(true);
		split6.setResizeWeight(0.3);
		add(split6);
		JTextArea prueba = new JTextArea("hola");
		eventsEditor.add(prueba);
		bar.add(load);
		bar.add(guardar);
		bar.add(limpiar);
		bar.add(events);
		bar.add(play);
		bar.add(salir);
		add(bar, BorderLayout.NORTH);

		// add actions to menubar, and bar to window
		JMenu file = new JMenu("File");
		file.add(load);
		file.add(guardar);
		file.add(salir);
		JMenu simulator = new JMenu("Simulator");
		JMenuBar menu = new JMenuBar();
		menu.add(file);
		setJMenuBar(menu);
	}
	
	public static void main(String ... args) {
		SwingUtilities.invokeLater(() -> new SimWindow());
	}
}