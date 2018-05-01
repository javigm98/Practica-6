package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextEditor extends JPanel{
	private JFileChooser fc;
	private JTextArea textArea;
	
	public TextEditor(){
		super(new BorderLayout());
		initGUI();
	}
	
	
	private void initGUI(){
		textArea = new JTextArea("");
		textArea.setEditable(true);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		add(new JScrollPane(textArea));
		fc = new JFileChooser();
		
	}
	

	public void loadFile() {
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String s = readFile(file);
			textArea.setText(s);
		}
	}

	public static String readFile(File file) {
		String s = "";
		try {
			s = new Scanner(file).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return s;
	}
	
	public void clearFile(){
		textArea.setText("");
	}
	
	public String getText(){
		return textArea.getText();
	}
}
