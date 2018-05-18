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
import es.ucm.fdi.control.Stepper;

/**
 * Ventana principal de la interfaz grafica de la aplicación
 * @author Javier Guzman y Jorge Villarrubia
 *
 */

public class SimWindow extends JFrame implements SimulatorListener {
	private final String[] colsEvents = { "#", "Time", "Type" };
	private final String[] colsVehicles = { "ID", "Road", "Location", "Speed",
			"Km", "Faulty Units", "Itinerary" };
	private final String[] colsRoads = { "ID", "Source", "Target", "Length",
			"Max Speed", "Vehicles" };
	private final String[] colsJunctions = { "ID", "Green", "Red" };

	private RoadMap map = new RoadMap();
	private int time = 0;
	private MultiTreeMap<Integer, Event> listaEventos = new MultiTreeMap<>();
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
	private SimulatorAction stop;
	private SimulatorAction reset;

	private SimulatorAction report;
	private SimulatorAction deleteReports;
	private SimulatorAction saveReports;

	private SimulatorAction salir;
	
	private JSpinner delay;
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

	private ReportsDialog rd;
	
	private Stepper stepper;
	
	/**
	 * 
	 * @param ctr controlador de la aplicacion con los metodos
	 * que realizaran las acciones.
	 * 
	 * @param iniFileName archivo inicial del que se cargan los 
	 * eventos (opcional).
	 * 
	 * @throws IOException si no se puede abrir el archivo ini inicial.
	 */

	public SimWindow(Controller ctr, String iniFileName) throws IOException {
		super("Traffic Simulator");
		this.ctr = ctr;
		ctr.getSimulator().setOut(out);
		if (iniFileName != null) {
			this.iniFile = new FileInputStream(iniFileName);
		}

		ctr.getSimulator().addSimulatorListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
		setSize(1000, 1000);
		setVisible(true);
		splitTodo.setDividerLocation(.33);
		splitAbajo.setDividerLocation(.5);

	}
	/**
	 * @throws IOException si hay algun problema con la lectura de archivos 
	 * en las acciones que lo requieran
	 */

	private void initGUI() throws IOException {
		instantiateActions();
		inicializaComponentes();
		dividePantalla();
	}
	
	/**
	 * Inicializa los distintos componentes de la ventana
	 * @throws IOException si hay algun problema con la lectura de archivos
	 * en las acciones
	 */

	private void inicializaComponentes() throws IOException {
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
	/**
	 * Añade el editor de eventos basado en un JTextArea
	 * @throws IOException si no se puede abrir el archivo seleccionado
	 */

	private void addEventsEditor() throws IOException {
		eventsEditor = new JTextArea("");
		//Si hemos pasado un archivo inicial para cargar eventos
		if (iniFile != null) {
			Ini ini = new Ini(iniFile);
			String s = ini.toString();
			eventsEditor.setText(s);
			guardar.setEnabled(true);
			events.setEnabled(true);
			reset.setEnabled(true);
		}
		eventsEditor.setEditable(true);
		eventsEditor.setLineWrap(true);
		eventsEditor.setWrapStyleWord(true);
	}
	/**
	 * Añade la cola de eventos como un SimulatorTable
	 */

	private void addEventsQueue() {
		eventsQueue = new SimulatorTable(colsEvents, listaEventos.valuesList());
		eventsQueue
				.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.black, 2),
						"Events Queue"));
	}
	
	/**
	 * Añade la zona en la que se muestran los informes generados
	 */

	private void addReportsArea() {
		reportsArea = new JTextArea("");
		reportsArea.setEditable(false);
	}
	/**
	 * Añade la tabla con la informacion de los vehiculos
	 */

	private void addVehiclesTable() {
		vehiclesTable = new SimulatorTable(colsVehicles,
				map.getListaVehiculos());
		vehiclesTable.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2), "Vehicles"));
	}
	
	/**
	 * Añade la tabla con la informacion de las carreteras
	 */

	private void addRoadsTable() {
		roadsTable = new SimulatorTable(colsRoads, map.getListaCarreteras());
		roadsTable.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2), "Roads"));
	}
	
	/**
	 * Añade la tabla con la informacion delos cruces
	 */

	private void addJunctionsTable() {
		junctionsTable = new SimulatorTable(colsJunctions, map.getListaCruces());
		junctionsTable.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2), "Junctions"));
	}
	
	/**
	 * Añade el grafo con el mapa de carreteras
	 */

	private void addRoadMap() {
		roadMap = new SimulatorGraph(map);
	}
	
	/**
	 * Añade los distintos componentes a la pantalla con el formato establecido.
	 */

	private void dividePantalla() {
		JPanel arriba = new JPanel(new BorderLayout());
		arriba.setLayout(new GridLayout(1, 3));

		JPanel panelEditor = new JPanel(new BorderLayout());
		panelEditor.add(new JScrollPane(eventsEditor));
		panelEditor.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2), "Events"));
		arriba.add(panelEditor);

		arriba.add(eventsQueue);

		JPanel panelReports = new JPanel(new BorderLayout());
		panelReports.add(new JScrollPane(reportsArea));
		panelReports.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2), "Reports"));
		arriba.add(panelReports);

		JPanel abajoIzq = new JPanel(new BorderLayout());

		abajoIzq.setLayout(new GridLayout(3, 1));
		abajoIzq.add(vehiclesTable);
		abajoIzq.add(roadsTable);
		abajoIzq.add(junctionsTable);

		splitAbajo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, abajoIzq,
				roadMap);
		splitTodo = new JSplitPane(JSplitPane.VERTICAL_SPLIT, arriba,
				splitAbajo);

		add(splitTodo);
	}
	
	/**
	 * Crea el menu que se despliega al hacer clic derecho en
	 * el editor de eventos
	 */

	private void createPopUpMenu() {
		JPopupMenu menu = new JPopupMenu();
		JMenu subMenu = new JMenu("Add Template");
		//Carga el fichero con las plantillas de los eventos
		try {
			Ini ini = new Ini(new FileInputStream(
					"src/main/resources/extra/templates.ini"));
			List<IniSection> listaSec = ini.getSections();
			for (IniSection sec : listaSec) {
				JMenuItem menuItem = new JMenuItem(sec.getValue("nombre"));
				sec.erase("nombre");
				menuItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						eventsEditor.append(sec.toString() + '\n');
						guardar.setEnabled(true);
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
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "The templates file can not be opened", 
					"Reading error", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	/**
	 * Crea las distintas acciones del simulador
	 * y crea la configuracion inicial de botones activos
	 */

	private void instantiateActions() {

		open = new SimulatorAction("Cargar", "open.png",
				"Cargar eventos desde archivo", KeyEvent.VK_C, "control L",
				() -> loadFile());
		guardar = new SimulatorAction("Guardar", "save.png",
				"Guardar archivo de eventos", KeyEvent.VK_G, "control S",
				() -> saveFile(eventsEditor));
		guardar.setEnabled(false);

		limpiar = new SimulatorAction("Borrar", "clear.png",
				"Limpiar editor de eventos", KeyEvent.VK_B, "control shift B",
				() -> deleteIniText());
		limpiar.setEnabled(true); //Los limpiar y delete todo el rato disponibles. Su acción es nula si no debiesen

		events = new SimulatorAction("Eventos", "events.png", "Cargar eventos",
				KeyEvent.VK_E, "control E", () -> cargarEventos());

		events.setEnabled(false);

		play = new SimulatorAction("Ejecutar", "play.png",
				"Ejecutar simulación", KeyEvent.VK_J, "control J",
				() -> ejecutaSimulacion());
		play.setEnabled(false);
		
		stop = new SimulatorAction("Parar", "stop.png", "Parar la simulacion", 
				KeyEvent.VK_P, "control P", () -> stop());
		
		stop.setEnabled(false);

		reset = new SimulatorAction("Reiniciar", "reset.png",
				"Reiniciar el simulador", KeyEvent.VK_R, "control R",
				() -> reset());
		reset.setEnabled(false);

		report = new SimulatorAction("Escribir informes", "report.png",
				"Generar informes", KeyEvent.VK_I, "control I",
				() -> rd = new ReportsDialog(this, map.getListaVehiculos(),
						map.getListaCarreteras(), map.getListaCruces(),
						() -> generateReports()));

		report.setEnabled(false);

		deleteReports = new SimulatorAction("Borrar informes",
				"delete_report.png", "Borrar los informes generados",
				KeyEvent.VK_B, "control B", () -> deleteReportsText());
		deleteReports.setEnabled(true);

		saveReports = new SimulatorAction("Guardar Informes",
				"save_report.png", "Guardar los informes", KeyEvent.VK_G,
				"control G", () -> saveFile(reportsArea));
		saveReports.setEnabled(false);

		salir = new SimulatorAction("Salir", "exit.png",
				"Salir de la aplicacion", KeyEvent.VK_S, "control shift X",
				() -> System.exit(0));
	}
	
	/**
	 * Añade la barra de herramientas superior con las acciones y 
	 * los contadores de tiempo y pasos de la simulacion
	 */

	private void addBars() {
		bar = new JToolBar();
		bar.add(open);
		bar.add(guardar);
		bar.add(limpiar);
		bar.addSeparator();
		bar.add(events);
		bar.add(play);
		bar.add(stop);
		bar.add(reset);
		
		bar.add(new JLabel("Delay: "));
		delay = new JSpinner(new SpinnerNumberModel(300, 0, 10000, 1));
		bar.add(delay);

		bar.add(new JLabel(" Steps: "));
		steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		bar.add(steps);

		bar.add(new JLabel(" Time: "));
		currentTime = new JTextField("" + time, 5);
		currentTime.setEditable(false);
		bar.add(currentTime);
		
		bar.addSeparator();

		bar.add(report);
		bar.add(deleteReports);
		bar.add(saveReports);

		bar.addSeparator();
		bar.add(salir);
		add(bar, BorderLayout.NORTH);
	}
	
	/**
	 * Añade la barra de menus desplegables superior a la barra de herramientas
	 */

	private void addBarraDeMenu() {
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
	
	/**
	 * Añade la barra de informacion de la parte inferior de la ventana 
	 */

	private void addStatusBar() {
		statusBar = new JPanel(new BorderLayout());
		statusBarText = new JLabel("Welcome to the simulator!");
		statusBar.add(statusBarText);
		add(statusBar, BorderLayout.SOUTH);
	}
	
	//Implementacion de los metodos de la interfaz SimulatorListener

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
			MultiTreeMap<Integer, Event> events, SimulatorException se) {
		JOptionPane.showMessageDialog(this, "" + se + se.getCause(), 
				"Simulator Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Abre la ventana de seleccion de informes y 
	 * los muestra en el area de texto correspondiente
	 */

	private void generateReports() {
		//se crea una lista de IniSections con el flujo de salida que contiene los informes
		out = (ByteArrayOutputStream) ctr.getSimulator().getOut();
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		try {
			Ini ini = new Ini(in);
			List<IniSection> secs = ini.getSections();
			
			//Se van añadiendo a una cadena las secciones correspondientes a 
			//objetos de la simulacion cuyo id ha sido seleccionado
			String s = "";
			for (IniSection sec : secs) {
				if (!rd.getSelected().isEmpty()
						&& rd.getSelected().get(sec.getValue("id"))) {
					s += sec.toString();
					s += '\n';
				}
			}
			//Se muestra la cadena creada en el JTextArea
			reportsArea.setText(s);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Selected file is not valid: "
					+ e, "Invalid Error", JOptionPane.ERROR_MESSAGE);
		}
		saveReports.setEnabled(true);
		statusBarText.setText("Reports generated");
	}
	
	/**
	 * Carga en la cola de eventos los eventos que aparecen en el area del
	 * editor de eventos.
	 */

	private void cargarEventos() {
		ByteArrayInputStream bytes = new ByteArrayInputStream(eventsEditor
				.getText().getBytes(StandardCharsets.UTF_8));
		//Borra posibles eventos cargados previamente
		resetCargaEventos();
		try {
			ctr.loadEvents(bytes);
			listaEventos = ctr.getSimulator().getEventsList();
			play.setEnabled(true);
			report.setEnabled(false);
			statusBarText.setText("Events loaded");
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(this,
					"Error loading events from file: " + iae, 
					"Events Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Selected file is not valid, "
					+ e, "Invalid File", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Ejecuta tantos pasos de la simulacion como indique el JSpinner steps
	 */

	private void ejecutaSimulacion() {
		try {
		stepper = new Stepper(()->{
			stop.setEnabled(true);
			open.setEnabled(false);
			guardar.setEnabled(false);
			limpiar.setEnabled(false);
			events.setEnabled(false);
			play.setEnabled(false);
			reset.setEnabled(false);
			report.setEnabled(false);
			deleteReports.setEnabled(false);
		}, () -> {
			try {
				ctr.getSimulator().run(1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error when generating reports, " + e, 
						"Reports Error", JOptionPane.ERROR_MESSAGE);
			}
		}, ()-> {
			stop.setEnabled(false);
			open.setEnabled(true);
			guardar.setEnabled(true);
			limpiar.setEnabled(true);
			events.setEnabled(true);
			play.setEnabled(true);
			reset.setEnabled(true);
			report.setEnabled(true);
			deleteReports.setEnabled(true);
			statusBarText.setText("Simulation advanced " + 
					((int)steps.getValue() - stepper.getSteps())
					+ " steps");
		});
		}
		catch(SimulatorException se){
			JOptionPane.showMessageDialog(this, "Error when generating reports, " + se, 
					"Reports Error", JOptionPane.ERROR_MESSAGE);
		}
		
		stepper.start((int)steps.getValue(), (int)delay.getValue());
	}
		
	
	/**
	 * Resetea el simulador devolviendolo a su estado inicial
	 */

	private void reset() {
		resetCargaEventos();
		eventsEditor.setText("");
		reportsArea.setText("");
		eventsQueue.setElements(listaEventos.valuesList());
		eventsQueue.update();
		roadMap.update(map);
		statusBarText.setText("Simulator reseted");
		stop();

		guardar.setEnabled(false);
		events.setEnabled(false);
		play.setEnabled(false);
		reset.setEnabled(false);
		report.setEnabled(false);
		saveReports.setEnabled(false);
		stop.setEnabled(false);

	}
	
	/**
	 * Borra el texto del editor de eventos
	 */

	private void deleteIniText() {
		eventsEditor.setText("");
		statusBarText.setText("Initial text deleted");
		events.setEnabled(false);
		guardar.setEnabled(false);
	}
	
	/**
	 * Borra el texto de la ventana de informes generados
	 */

	private void deleteReportsText() {
		reportsArea.setText("");
		statusBarText.setText("Reports deleted");
		saveReports.setEnabled(false);
	}
	
	private void stop(){
		stepper.stop();
	}
	
	/**
	 * Actualiza las cuatro tablas que se muestran
	 */

	private void updateTables() {
		vehiclesTable.setElements(map.getListaVehiculos());
		roadsTable.setElements(map.getListaCarreteras());
		junctionsTable.setElements(map.getListaCruces());
		vehiclesTable.update();
		roadsTable.update();
		junctionsTable.update();

	}
	
	/**
	 * Guarada el texto que aparece en text en la ubcicacion elegida con fc
	 * @param text JTextArea de la que se leera el texto a guardar 
	 * (eventsEditor o reportsArea)
	 */

	private void saveFile(JTextArea text) {
		try{
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			writeFile(file, text.getText());
		}
		statusBarText.setText("Data saved");
		}
		catch(FileNotFoundException fne){
			JOptionPane.showMessageDialog(this,  "Inexisting file, " + fne, 
					"Saving Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	/**
	 * Escribe el texto contenten el archivo File
	 * @param file archivo en el que queremos escribir el texto
	 * @param content texto a escribir
	 */

	private static void writeFile(File file, String content) throws FileNotFoundException{
			PrintWriter pw = new PrintWriter(file);
			pw.print(content);
			pw.close();
	}
		
	
	/**
	 * Carga un archivo seleccionado mediante fc y lo muestra en events editor
	 */

	private void loadFile() {
		try {
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				String fileName = file.getAbsolutePath();
				if(!fileName.substring(fileName.length() - 4).equals(".ini")){
					JOptionPane.showMessageDialog(null,
							"The file is not valid for the simulator", "Invalid File", JOptionPane.ERROR_MESSAGE);
				}
				String s = readFile(file);
				eventsEditor.setText(s);statusBarText.setText("Data loaded");
				guardar.setEnabled(true);
				events.setEnabled(true);
				reset.setEnabled(true);
			}
			
		} catch (NoSuchElementException se) {
			JOptionPane.showMessageDialog(null,
					"The file is not valid for the simulator, " + se, "Invalid File", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * @param file archivo del que queremos leer
	 * @return un string con los datos leidos de file
	 * @throws NoSuchElementException si no se trata de un fichero apropiado para el simulador
	 */

	private static String readFile(File file) throws NoSuchElementException {
		String s = "";
		try {
			s = new Scanner(file).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return s;
	}
	
	/**
	 *Resetea todo lo necesario para devolver al editor de eventos a su situacion inicial
	 */

	private void resetCargaEventos() {
		ctr.getSimulator().reset();
		currentTime.setText("" + time);
		updateTables();
	}

}
