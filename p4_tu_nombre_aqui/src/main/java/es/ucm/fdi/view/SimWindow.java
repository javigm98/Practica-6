package es.ucm.fdi.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import es.ucm.fdi.control.Controller;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
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
	private final String[] colsEvents = {"#", "Time", "Type"};
	private final String[] colsVehicles = {"ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary"};
	private final String[] colsRoads = {"ID", "Source", "Target", "Length", "Max Speed", "Vehicles"};
	private final String[] colsJunctions = {"ID", "Green", "Red"};
	
	
	private RoadMap map = new RoadMap();
	private int time = 0;
	private MultiTreeMap <Integer, Event> listaEventos = new MultiTreeMap<>();
	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private Controller ctr;
	private InputStream iniFile;
	
	private JFileChooser fc = new JFileChooser();
	
	
	private JMenuBar barraDeMenu;
	private JMenu fileMenu;
	private JMenu simulatorMenu;
	private JMenu reportsMenu;
	private JToolBar bar;
	private JPanel statusBar;
	private JLabel statusBarText;
	
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
	private SimulatorTable eventsQueue;
	private JTextArea reportsArea;
	
	private SimulatorTable vehiclesTable;
	private SimulatorTable roadsTable;
	private SimulatorTable junctionsTable;
	
	private SimulatorGraph roadMap;
	
	private JSplitPane splitAbajo;
	private JSplitPane splitTodo;
	
	

	
	public SimWindow(Controller ctr, String iniFileName) throws IOException {
		super("Traffic Simulator");
		this.ctr = ctr;
		ctr.getSimulator().setOut(out);
		if(iniFileName!= null){
			this.iniFile = new FileInputStream(iniFileName);
		}
		
		ctr.getSimulator().addSimulatorListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//addBars();
		initGUI();
		setSize(1000, 1000);
		setVisible(true);
		splitTodo.setDividerLocation(.33);
		splitAbajo.setDividerLocation(.5);
		
				
		
	}
	
	private void initGUI() throws IOException{
		instantiateActions();
		inicializaComponentes();
		dividePantalla();
	}
	
	private void inicializaComponentes() throws IOException{
		addBarraDeMenu();
		addBars();
		addEventsEditor();
		addEventsQueue();
		addReportsArea();
		addVehiclesTable();
		addRoadsTable();
		addJunctionsTable();
		addRoadMap();
		addStatusBar();
		createPopUpMenu();
	}
	
	private void addEventsEditor() throws IOException{
		eventsEditor = new JTextArea("");
		if(iniFile != null){
			Ini ini = new Ini(iniFile);
			String s = ini.toString();
			eventsEditor.setText(s);
			guardar.setEnabled(true);
			limpiar.setEnabled(true);
			events.setEnabled(true);
			reset.setEnabled(true);
		}
		eventsEditor.setEditable(true);
		eventsEditor.setLineWrap(true);
		eventsEditor.setWrapStyleWord(true);
	}
	
	private void addEventsQueue(){
		eventsQueue = new SimulatorTable(colsEvents, listaEventos.valuesList());
		eventsQueue.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Events Queue"));
	}
	
	private void addReportsArea(){
		reportsArea = new JTextArea("");
		reportsArea.setEditable(false);
	}
	
	private void addVehiclesTable(){
		vehiclesTable =  new SimulatorTable(colsVehicles, map.getListaVehiculos());
		vehiclesTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Vehicles"));
	}
	
	private void addRoadsTable(){
		roadsTable = new SimulatorTable(colsRoads, map.getListaCarreteras());
		roadsTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Roads"));
	}
	
	private void addJunctionsTable(){
		junctionsTable = new SimulatorTable(colsJunctions, map.getListaCruces());
		junctionsTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Junctions"));
	}
	
	private void addRoadMap(){
		roadMap = new SimulatorGraph(map);
	}
	
	

	private void dividePantalla(){
		JPanel arriba = new JPanel(new BorderLayout());
		arriba.setLayout(new GridLayout(1, 3));
		
		JPanel panelEditor = new JPanel(new BorderLayout());
		panelEditor.add(new JScrollPane(eventsEditor));
		panelEditor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Events"));
		arriba.add(panelEditor);
		
		arriba.add(eventsQueue);
		
		JPanel panelReports = new JPanel(new BorderLayout());
		panelReports.add(new JScrollPane(reportsArea));
		panelReports.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Reports"));
		arriba.add(panelReports);
		
		JPanel abajoIzq = new JPanel(new BorderLayout());
		
		abajoIzq.setLayout(new GridLayout(3, 1));
		abajoIzq.add(vehiclesTable);
		abajoIzq.add(roadsTable);
		abajoIzq.add(junctionsTable);
		
		
		splitAbajo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, abajoIzq, roadMap);
		splitTodo = new JSplitPane(JSplitPane.VERTICAL_SPLIT, arriba, splitAbajo);
	
		add(splitTodo);	
	}
	
	private void createPopUpMenu(){
		JPopupMenu menu = new JPopupMenu();
		JMenu subMenu = new JMenu("Add Template");
		try {
			Ini ini = new Ini(new FileInputStream("src/main/resources/extra/templates.ini"));
			List<IniSection> listaSec = ini.getSections();
			for(IniSection sec: listaSec){
				JMenuItem menuItem = new JMenuItem(sec.getValue("nombre"));
				sec.erase("nombre");
				menuItem.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						eventsEditor.append(sec.toString() + '\n');
						guardar.setEnabled(true);
						limpiar.setEnabled(true);
						events.setEnabled(true);
						reset.setEnabled(true);
					}
				});
				subMenu.add(menuItem);
			}
			menu.add(subMenu);
			menu.addSeparator();
			menu.add(open);
			menu.add(guardar);
			menu.add(limpiar);
			
			eventsEditor.addMouseListener(new MouseListener() {

				@Override
				public void mousePressed(MouseEvent e) {
					showPopup(e);
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					showPopup(e);
				}

				private void showPopup(MouseEvent e) {
					if (e.isPopupTrigger() && menu.isEnabled()) {
						menu.show(e.getComponent(), e.getX(), e.getY());
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	private void instantiateActions() {
		
		open = new SimulatorAction(
				"Cargar", "open.png", "Cargar cosas",
				KeyEvent.VK_C, "control L", 
				()-> loadFile());
		guardar = new SimulatorAction(
				"Guardar", "save.png", "Guardar cosas",
				KeyEvent.VK_G, "control S", 
				()-> saveFile(eventsEditor));
		guardar.setEnabled(false);
		
		limpiar = new SimulatorAction(
				"Borrar", "clear.png", "Limpiar la aplicacion",
				KeyEvent.VK_B, "control shift B", 
				()-> deleteIniText());
		limpiar.setEnabled(false);
		
		events = new SimulatorAction(
				"Eventos", "events.png", "Mostrar eventos",
				KeyEvent.VK_E, "control E", 
				()-> cargarEventos());
		
		events.setEnabled(false);
		
		play = new SimulatorAction(
				"Ejecutar", "play.png", "Ejecutar eventos",
				KeyEvent.VK_J, "control J", 
				()-> ejecutaSimulacion());
		play.setEnabled(false);
		
		reset = new SimulatorAction(
				"Reiniciar", "reset.png", "Reiniciar el simulador",
				KeyEvent.VK_R, "control R", 
				()-> reset());
		reset.setEnabled(false);
		
		report = new SimulatorAction(
				"Escribir informes", "report.png", "Mostrar informes",
				KeyEvent.VK_I, "control I", 
				()-> generateReports());
		
		report.setEnabled(false);
		
		deleteReports = new SimulatorAction(
				"Borrar informes", "delete_report.png", "Borrar los informes generados",
				KeyEvent.VK_B, "control B", 
				()-> deleteReportsText());
		
		deleteReports.setEnabled(false);
		
		saveReports = new SimulatorAction(
				"Guardar Informes", "save_report.png", "Guarda los informes",
				KeyEvent.VK_G, "control G", 
				()-> saveFile(reportsArea));
		saveReports.setEnabled(false);
		
		salir = new SimulatorAction(
					"Salir", "exit.png", "Salir de la aplicacion",
					KeyEvent.VK_S, "control shift X", 
					()-> System.exit(0));
	}
	private void addBars() {
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
		currentTime = new JTextField("" + time, 5);
		currentTime.setEditable(false);
		bar.add(currentTime);
		
		
		bar.add(report);
		bar.add(deleteReports);
		bar.add(saveReports);
		
		bar.addSeparator();
		bar.add(salir);
		add(bar, BorderLayout.NORTH);
	}
	
	private void addBarraDeMenu(){
		barraDeMenu = new JMenuBar();
		fileMenu = new JMenu("File");
		simulatorMenu = new JMenu("Simulator");
		reportsMenu = new JMenu("Reports");
		
		fileMenu.add(open);
		fileMenu.add(guardar);
		fileMenu.addSeparator();
		fileMenu.add(saveReports);
		fileMenu.addSeparator();
		fileMenu.add(salir);
		
		simulatorMenu.add(play);
		simulatorMenu.add(reset);
		
		reportsMenu.add(report);
		reportsMenu.add(deleteReports);
		barraDeMenu.add(fileMenu);
		barraDeMenu.add(simulatorMenu);
		barraDeMenu.add(reportsMenu);
		setJMenuBar(barraDeMenu);	
		}
	
	private void addStatusBar(){
		statusBar = new JPanel(new BorderLayout());
		statusBarText = new JLabel("Welcome to the simulator!");
		statusBar.add(statusBarText);
		add(statusBar, BorderLayout.SOUTH);
	}

	@Override
	public void registered(int time, RoadMap map,
			MultiTreeMap<Integer, Event> events) {
	}

	@Override
	public void reset(int time, RoadMap map, MultiTreeMap<Integer, Event> events) {
		listaEventos = events;
		this.map = map;
		this.time = time;	
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
		this.map = map;
		this.time = time;
		currentTime.setText("" + time);
		updateTables();
		roadMap.update(map);
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
		deleteReports.setEnabled(true);
		saveReports.setEnabled(true);
		statusBarText.setText("Reports generated");
	}
	
	public void cargarEventos() {
		String s = eventsEditor.getText();
		ByteArrayInputStream bytes = new ByteArrayInputStream(eventsEditor.getText().getBytes(StandardCharsets.UTF_8));
		resetCargaEventos();
		try {
			ctr.loadEvents(bytes);
			listaEventos = ctr.getSimulator().getEventsList();
			play.setEnabled(true);
			statusBarText.setText("Events loaded");
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e);
		} catch (SimulatorException e) {
			JOptionPane.showMessageDialog(this, e);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "The file isn't a correct one for the simulator");
		}
	}
	
	public void ejecutaSimulacion(){
		try{
			ctr.setPasos((int) steps.getValue());
			ctr.run();
			report.setEnabled(true);
			statusBarText.setText("Simulation advanced " + steps.getValue() + " steps");
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(null, "The file isn't a correct one for the simulator");
		}
		catch(SimulatorException se){
			JOptionPane.showMessageDialog(null, "" + se + se.getCause());
		}
	}
	
	private void reset(){
		resetCargaEventos();
		eventsEditor.setText("");
		reportsArea.setText("");
		eventsQueue.setElements(listaEventos.valuesList());
		eventsQueue.update();
		roadMap.update(map);
		statusBarText.setText("Simulator reseted");
		
		guardar.setEnabled(false);
		limpiar.setEnabled(false);
		events.setEnabled(false);
		play.setEnabled(false);
		reset.setEnabled(false);
		report.setEnabled(false);
		deleteReports.setEnabled(false);
		saveReports.setEnabled(false);
		
	}
	
	private void deleteIniText(){
		eventsEditor.setText("");
		statusBarText.setText("Initial text deleted");
		events.setEnabled(false);
		guardar.setEnabled(false);
	}
	
	private void deleteReportsText(){
		reportsArea.setText("");
		statusBarText.setText("Reports deleted");
		saveReports.setEnabled(false);
	}
	
	private void updateTables(){
		vehiclesTable.setElements(map.getListaVehiculos());
		roadsTable.setElements(map.getListaCarreteras());
		junctionsTable.setElements(map.getListaCruces());
		vehiclesTable.update();
		roadsTable.update();
		junctionsTable.update();
		
		
		
	}
	
	private void saveFile(JTextArea text){
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			writeFile(file, text.getText());
		}
		statusBarText.setText("Data saved");
	}
	
	private static void writeFile(File file, String content) {
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.print(content);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadFile() {
		try{
			int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				String s = readFile(file);
				eventsEditor.setText(s);
			}
				statusBarText.setText("Data loaded");
				guardar.setEnabled(true);
				limpiar.setEnabled(true);
				events.setEnabled(true);
				reset.setEnabled(true);
		}
		catch(NoSuchElementException se){
			JOptionPane.showMessageDialog(null, "The file isn't a correct one for the simulator");
		}
	}
	
	private static String readFile(File file) throws NoSuchElementException {
		String s = "";
		try {
			s = new Scanner(file).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return s;
	}
	
	private void resetCargaEventos(){
		ctr.getSimulator().reset();
		currentTime.setText("" + time);
		updateTables();
	}
	
}