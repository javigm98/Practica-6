package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import es.ucm.fdi.control.Controller;
import es.ucm.fdi.extra.graphlayout.Graph;
import es.ucm.fdi.extra.texteditor.TextEditorExample;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewJunctionEvent;
import es.ucm.fdi.model.NewVehicleEvent;
import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.model.TrafficSimulator.SimulatorListener;
import es.ucm.fdi.util.MultiTreeMap;

/**
 * Esto es sólo para empezar a jugar con las interfaces
 * de la P5. 
 * 
 * El código <i>no</i> está bien organizado, y meter toda
 * la funcionalidad aquí sería un disparate desde un punto
 * de vista de mantenibilidad.
 */

public class SimWindow extends JFrame implements SimulatorListener{
	
	private Controller ctr;
	private RoadMap map;
	private int time;
	private MultiTreeMap <Integer, Event> listaEventos;
	private OutputStream out;
	
	private JMenu fileMenu;
	private JMenu simulatorMenu;
	private JMenu reportsMenu;
	private JToolBar bar;
	
	
	private SimulatorAction open;
	private SimulatorAction guardar;
	private SimulatorAction limpiar;
	
	private SimulatorAction events;
	private SimulatorAction play;
	private SimulatorAction reset;
	
	private SimulatorAction report;
	private SimulatorAction deleteReports;
	private SimulatorAction saveReports;
	
	private SimulatorAction salir;
	
	private JSpinner steps;
	private JTextField currentTime;
	
	private JTextArea eventsEditor;
	private JTable eventsQueue;
	private JTextArea reportsArea;
	
	private JTable vehiclesTable;
	private JTable roadsTable;
	private JTable junctionsTable;
	
	private JPanel roadMap;
	
	

	
	public SimWindow() {
		super("Traffic Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//addBars();
		initGUI();
		
		
		setSize(1000, 1000);		
		setVisible(true);
	}
	
	private void initGUI(){
		instantiateActions();
		addBars();
		inicializaComponentes();
		dividePantalla();
		
	}
	
	public void inicializaComponentes(){
		eventsEditor = new JTextArea();
		/*listaEventos = new MultiTreeMap(); //Para probar la tabla
		listaEventos.putValue(3, new NewJunctionEvent(3, "j1"));
		*/
		eventsQueue = new JTable(new EventsTableModel(listaEventos));
		reportsArea = new JTextArea();
		vehiclesTable = new JTable();
		roadsTable = new JTable();
		junctionsTable = new JTable();
		roadMap = new JPanel();
	}
	
	private void dividePantalla(){
		JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, eventsEditor, eventsQueue);
		split1.setVisible(true);
		split1.setResizeWeight(0.5);
		JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split1, reportsArea);
		split2.setVisible(true);
		split2.setResizeWeight(0.66);
		JSplitPane split3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, vehiclesTable, roadsTable);
		split3.setVisible(true);
		split3.setResizeWeight(0.5);
		JSplitPane split4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split3, junctionsTable);
		split4.setVisible(true);
		split4.setResizeWeight(0.66);
		JSplitPane split5 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split4, roadMap);
		split5.setVisible(true);
		split5.setResizeWeight(0.5);
		JSplitPane split6 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split2, split5);
		split6.setVisible(true);
		split6.setResizeWeight(0.3);
		add(split6);
	}
	
	private void instantiateActions(){
		
		open = new SimulatorAction(
				"Cargar", "open.png", "Cargar cosas",
				KeyEvent.VK_C, "control L", 
				()-> System.err.println("cargando..."));
		guardar = new SimulatorAction(
				"Guardar", "save.png", "Guardar cosas",
				KeyEvent.VK_G, "control S", 
				()-> System.err.println("guardando..."));
		
		limpiar = new SimulatorAction(
				"Borrar", "clear.png", "Limpiar la aplicacion",
				KeyEvent.VK_B, "control shift B", 
				()-> System.err.println("limpiando..."));
		
		events = new SimulatorAction(
				"Eventos", "events.png", "Mostrar eventos",
				KeyEvent.VK_E, "control E", 
				()-> System.err.println("cargando eventos..."));
		
		play = new SimulatorAction(
				"Ejecutar", "play.png", "Ejecutar eventos",
				KeyEvent.VK_J, "control J", 
				()-> System.err.println("ejecutando eventos..."));
		
		reset = new SimulatorAction(
				"Reiniciar", "reset.png", "Reiniciar el simulador",
				KeyEvent.VK_R, "control R", 
				()-> System.err.println("reseteando..."));
		
		report = new SimulatorAction(
				"Escribir informes", "report.png", "Mostrar informes",
				KeyEvent.VK_I, "control I", 
				()-> System.err.println("reseteando..."));
		
		reset = new SimulatorAction(
				"Reiniciar", "reset.png", "Reiniciar el simulador",
				KeyEvent.VK_R, "control R", 
				()-> System.err.println("reseteando..."));
		
		deleteReports = new SimulatorAction(
				"Borrar informes", "delete_report.png", "Borrar los informes generados",
				KeyEvent.VK_B, "control B", 
				()-> System.err.println("reseteando..."));
		
		saveReports = new SimulatorAction(
				"Guardar Informes", "save_report.png", "Guarda los informes",
				KeyEvent.VK_G, "control G", 
				()-> System.err.println("reseteando..."));
		
		
		salir = new SimulatorAction(
					"Salir", "exit.png", "Salir de la aplicacion",
					KeyEvent.VK_S, "control shift X", 
					()-> System.exit(0));
	}
	private void addBars() {
		//Hacer luego en initGUI
		
		//instantiateActions();
		bar = new JToolBar();
		bar.add(open);
		bar.add(guardar);
		bar.add(limpiar);
		bar.addSeparator();
		bar.add(events);
		bar.add(play);
		bar.add(reset);
		
		
		bar.add(new JLabel(" Steps: ")); 
		steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		bar.add(steps);
		
		bar.add(new JLabel(" Time: ")); 
		currentTime = new JTextField("0", 5);
		currentTime.setEditable(false);
		bar.add(currentTime);
		
		
		bar.add(report);
		bar.add(deleteReports);
		bar.add(saveReports);
		
		bar.addSeparator();
		bar.add(salir);
		add(bar, BorderLayout.NORTH);
		
		
		
		
		/*JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, eventsEditor, eventsQueue);
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
		eventsEditor.add(prueba);*/
		

		// add actions to menubar, and bar to window
		/*JMenu file = new JMenu("File");
		file.add(load);
		file.add(guardar);
		file.add(salir);
		JMenu simulator = new JMenu("Simulator");
		JMenuBar menu = new JMenuBar();
		menu.add(file);
		setJMenuBar(menu);*/
	}
	
	public static void main(String ... args) {
		SwingUtilities.invokeLater(() -> new SimWindow());
	}

	@Override
	public void registered(int time, RoadMap map,
			MultiTreeMap<Integer, Event> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset(int time, RoadMap map, MultiTreeMap<Integer, Event> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventAdded(int time, RoadMap map,
			MultiTreeMap<Integer, Event> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void advanced(int time, RoadMap map,
			MultiTreeMap<Integer, Event> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void simulatorError(int time, RoadMap map,
			MultiTreeMap<Integer, Event> events, SimulatorException e) {
		// TODO Auto-generated method stub
		
	}
}