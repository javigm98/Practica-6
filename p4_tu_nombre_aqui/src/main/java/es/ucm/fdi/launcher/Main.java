package es.ucm.fdi.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.model.TrafficSimulator;
import es.ucm.fdi.view.SimWindow;
import es.ucm.fdi.control.Controller;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private final static String _defaultOutFile = "ex1.out";
	private static Integer _timeLimit = null;
	private static String _inFile = null;
	private static String _outFile = null;
	private static String _mode = null;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseModeOption(line);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseStepsOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			// new Piece(...) might throw GameError exception
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("h").longOpt("help")
				.desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg()
				.desc("Events input file").build());
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg()
				.desc("Output file, where reports are written.").build());
		cmdLineOptions
				.addOption(Option
						.builder("t")
						.longOpt("ticks")
						.hasArg()
						.desc("Ticks to execute the simulator's main loop (default value is "
								+ _timeLimitDefaultValue + ").").build());
		cmdLineOptions
				.addOption(Option
						.builder("m")
						.longOpt("mode")
						.hasArg()
						.desc("Select application mode: batch for conseole mode, gui for graphic view of the application")
						.build());

		return cmdLineOptions;
	}

	private static void parseModeOption(CommandLine line) throws ParseException {
		_mode = line.getOptionValue("m", "batch");
		try {
			assert (_mode.equals("batch") || _mode.equals("gui"));
		} catch (Exception e) {
			throw new ParseException("Invalid mode for the application: "
					+ _mode);
		}
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions,
					true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line)
			throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null && _mode.equals("batch")) {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line)
			throws ParseException {
		_outFile = line.getOptionValue("o");
	}

	private static void parseStepsOption(CommandLine line)
			throws ParseException {
		String t = line.getOptionValue("t", _timeLimitDefaultValue.toString());
		try {
			_timeLimit = Integer.parseInt(t);
			assert (_timeLimit < 0);
		} catch (Exception e) {
			throw new ParseException("Invalid value for time limit: " + t);
		}
	}

	/**
	 * This method runs the simulator on all files that ends with .ini if the
	 * given path, and compares that output to the expected output. It assumes
	 * that for example "example.ini" the expected output is stored in
	 * "example.ini.eout". The simulator's output will be stored in
	 * "example.ini.out"
	 * 
	 * @throws IOException
	 */
	public static void test(String path) throws IOException {

		File dir = new File(path);

		if (!dir.exists()) {
			throw new FileNotFoundException(path);
		}

		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ini");
			}
		});

		for (File file : files) {
			test(file.getAbsolutePath(), file.getAbsolutePath() + ".out",
					file.getAbsolutePath() + ".eout", 10);
		}

	}

	private static void test(String inFile, String outFile,
			String expectedOutFile, int timeLimit) throws IOException {
		_outFile = outFile;
		_inFile = inFile;
		_timeLimit = timeLimit;
		startBatchMode();
		boolean equalOutput = (new Ini(_outFile)).equals(new Ini(
				expectedOutFile));
		System.out.println("Result for: '"
				+ _inFile
				+ "' : "
				+ (equalOutput ? "OK!" : ("not equal to expected output +'"
						+ expectedOutFile + "'")));
	}

	/**
	 * Run the simulator in batch mode
	 * 
	 * @throws IOException
	 */
	private static void startBatchMode() {
		try {
			InputStream in = new FileInputStream(_inFile);
			OutputStream out = new FileOutputStream(_outFile);
			Controller controller = new Controller(new TrafficSimulator(out),
					_timeLimit);
			BatchMode batch = new BatchMode();
			batch.runBachMode(controller, in);
		} catch (IOException ioe) {
			System.err.println(ioe);
		} catch (IllegalArgumentException e) {
			System.err.println(e);
		}

	}

	private static void startGUIMode() throws IOException,
			InvocationTargetException, InterruptedException {
		OutputStream out1 = new FileOutputStream(_outFile);
		Controller controller = new Controller(new TrafficSimulator(out1),
				_timeLimit);
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				try {
					new SimWindow(controller, _inFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	private static void start(String[] args) throws IOException,
			InvocationTargetException, InterruptedException {
		parseArgs(args);
		if (_timeLimit == null)
			_timeLimit = _timeLimitDefaultValue;
		if (_outFile == null)
			_outFile = _defaultOutFile;
		if (_mode.equals("batch")) {
			startBatchMode();
		} else {
			startGUIMode();
		}
	}

	public static void main(String[] args) throws Exception {

		// test("examples/advanced");

		start(args);
	}
}
