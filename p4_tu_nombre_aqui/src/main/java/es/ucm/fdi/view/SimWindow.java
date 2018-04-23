package es.ucm.fdi.view;


import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.NewJunctionEvent;
import es.ucm.fdi.model.NewVehicleEvent;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.model.TrafficSimulator;
import es.ucm.fdi.model.TrafficSimulator.SimulatorListener;
import es.ucm.fdi.model.Vehicle;
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
	private final String[] colsEvents = {"#", "Time", "Type"};
	private final String[] colsVehicles = {"ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary"};
	private final String[] colsRoads = {"ID", "Source", "Target", "Lenght", "Max Speed", "Vehicles"};
	private final String[] colsJunctions = {"ID", "Green", "Red"};
	
	
	private RoadMap map = new RoadMap();
	private int time;
	private MultiTreeMap <Integer, Event> listaEventos = new MultiTreeMap<>();
	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private Controller ctr = new Controller(new TrafficSimulator(out), 3);
	
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
	
	private TextEditor eventsEditor;
	private SimulatorTable eventsQueue;
	private JTextArea reportsArea;
	
	private SimulatorTable vehiclesTable;
	private SimulatorTable roadsTable;
	private SimulatorTable junctionsTable;
	
	private JPanel roadMap;
	
	

	
	public SimWindow() {
		super("Traffic Simulator");
		ctr.getSimulator().addSimulatorListener(this);
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
		eventsEditor = new TextEditor();
	
		eventsQueue = new SimulatorTable(colsEvents, listaEventos.valuesList());
		
		reportsArea = new JTextArea();
		reportsArea.append("");
		reportsArea.setEditable(false);
		reportsArea.setBorder(BorderFactory.createTitledBorder("Reports"));
		/*List<Vehicle> listaVeh = new ArrayList<>();
		List<Junction> listaJunc = new ArrayList<>();
		Junction j1 = new Junction ("j1"), j2 = new Junction("j2");
		
		Road r1 = new Road("r1", 1000, 100, j1, j2);
		j1.addNewOutgoingRoad(r1);
		j2.addNewIncomingRoad(r1);
		listaJunc.add(j1);
		listaJunc.add(j2);
		listaVeh.add(new Vehicle("v1", 200, listaJunc));
		;*/
		vehiclesTable = new SimulatorTable(colsVehicles, map.getListaVehiculos());
		vehiclesTable.setBorder(BorderFactory.createTitledBorder("Vehicles"));
		
		roadsTable = new SimulatorTable(colsRoads, map.getListaCarreteras());
		roadsTable.setBorder(BorderFactory.createTitledBorder("Roads"));
		
		junctionsTable = new SimulatorTable(colsJunctions, map.getListaCruces());
		junctionsTable.setBorder(BorderFactory.createTitledBorder("Junctions"));
		
		roadMap = new JPanel();
		
	}
	
	private void dividePantalla(){
		/*JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		mainPanel.add(panel1, BorderLayout.CENTER);
		
		JPanel panel2 = new JPanel(); 
		panel1.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		
		JPanel panel3 = new JPanel();
		panel1.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
		
		JPanel panel4 = new JPanel();
		panel1.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
		
		JPanel panel5 = new JPanel(new BorderLayout());
		
        panel1.add(panel2);                                              
		panel1.add(panel3);
		panel3.add(panel4);
		panel3.add(panel5);*/
		
		
		JPanel arriba = new JPanel();
		arriba.setLayout(new BoxLayout(arriba, BoxLayout.X_AXIS));
		arriba.add(eventsEditor);
		arriba.add(eventsQueue);
		arriba.add(reportsArea);
		
		JPanel abajoIzq = new JPanel();
		
		abajoIzq.setLayout(new BoxLayout(abajoIzq, BoxLayout.Y_AXIS));
		abajoIzq.add(vehiclesTable);
		abajoIzq.add(roadsTable);
		abajoIzq.add(junctionsTable);
		
		
		JSplitPane abajo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, abajoIzq, roadMap);
		JSplitPane todo = new JSplitPane(JSplitPane.VERTICAL_SPLIT, arriba, abajo);
		
		setVisible(true);
		abajo.setDividerLocation(.5);
		todo.setDividerLocation(.33);
		
		
		
		
		
		
		add(todo);
		
		
		
		
		/*JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, eventsEditor, eventsQueue);
		split1.setVisible(true);
		split1.setResizeWeight(.50);
		
		JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split1, reportsArea);
		split2.setVisible(true);
		split2.setResizeWeight(.66);
		
		
		JSplitPane split3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, vehiclesTable, roadsTable);
		split3.setVisible(true);
		split3.setResizeWeight(.50);
		
		JSplitPane split4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split3, junctionsTable);
		split4.setVisible(true);
		split4.setResizeWeight(.66);
		
	
		JSplitPane split5 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split4, roadMap);
		split5.setVisible(true);
		split5.setResizeWeight(.50);
		
		JSplitPane split6 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split2, split5);
		split6.setVisible(true);
		split6.setResizeWeight(.30);
		
		add(split6);
		*/
		
		
		
		
		
		
		
	/*	JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, eventsEditor, eventsQueue);
		split1.setDividerLocation(0.50);
		JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split1, reportsArea);
		split2.setDividerLocation(0.66);
		JSplitPane split3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, vehiclesTable, roadsTable);
		split3.setDividerLocation(0.50);
		JSplitPane split4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split3, junctionsTable);
		split4.setDividerLocation(0.66);
		JSplitPane split5 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split4, roadMap);
		split5.setDividerLocation(0.50);
		JSplitPane split6 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split2, split5);
		split6.setDividerLocation(0.30);
		add(split6);
		setVisible(true);	
		*/
		
	}
	
	private void instantiateActions(){
		
		open = new SimulatorAction(
				"Cargar", "open.png", "Cargar cosas",
				KeyEvent.VK_C, "control L", 
				()-> eventsEditor.loadFile());
		guardar = new SimulatorAction(
				"Guardar", "save.png", "Guardar cosas",
				KeyEvent.VK_G, "control S", 
				()-> eventsEditor.saveFile());
		
		limpiar = new SimulatorAction(
				"Borrar", "clear.png", "Limpiar la aplicacion",
				KeyEvent.VK_B, "control shift B", 
				()-> eventsEditor.clearFile());
		
		events = new SimulatorAction(
				"Eventos", "events.png", "Mostrar eventos",
				KeyEvent.VK_E, "control E", 
				()-> cargarEventos());
		
		play = new SimulatorAction(
				"Ejecutar", "play.png", "Ejecutar eventos",
				KeyEvent.VK_J, "control J", 
				()-> ejecutaSimulacion());
		
		reset = new SimulatorAction(
				"Reiniciar", "reset.png", "Reiniciar el simulador",
				KeyEvent.VK_R, "control R", 
				()-> System.err.println("reseteando..."));
		
		report = new SimulatorAction(
				"Escribir informes", "report.png", "Mostrar informes",
				KeyEvent.VK_I, "control I", 
				()-> generateReports());
		
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
		listaEventos = events;
		eventsQueue.setElements(listaEventos.valuesList());
		eventsQueue.update();
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
	
	public void generateReports(){
		out = (ByteArrayOutputStream) ctr.getSimulator().getOut();
		String s = new String(out.toByteArray());
		reportsArea.setText(s);	
	}
	
	public void cargarEventos(){
		ByteArrayInputStream bytes = new ByteArrayInputStream(eventsEditor.getText().getBytes(StandardCharsets.UTF_8));
		try {
			ctr.loadEvents(bytes);
			listaEventos = ctr.getSimulator().getEventsList();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void ejecutaSimulacion(){
		ctr.setPasos((int) steps.getValue());
		ctr.run();
	}
}