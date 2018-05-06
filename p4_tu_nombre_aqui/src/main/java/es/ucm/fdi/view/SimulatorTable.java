package es.ucm.fdi.view;


import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.ucm.fdi.model.Describable;

public class SimulatorTable extends JPanel{
	private JTable tabla;
	private String[] fieldNames;
	private List<? extends Describable> elements;
	private ListOfMapsTableModel modelo = new ListOfMapsTableModel();
	
	public SimulatorTable(String[] fieldNames, List<? extends Describable> elements) {
		super(new BorderLayout());
		this.fieldNames = fieldNames;
		this.elements = elements;		
		tabla = new JTable(modelo);
		add(new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}
	
	public void update(){
		modelo.update();
	}
	
	public void setElements(List<? extends Describable> elements){
		this.elements = elements;
	}



	private class ListOfMapsTableModel extends AbstractTableModel {
		@Override // fieldNames es un String[] con nombrs de col.
		public String getColumnName(int columnIndex) {
			return fieldNames[columnIndex];
		}
		@Override // elements contiene la lista de elementos
		public int getRowCount() {
			return elements.size();
		}
		@Override
		public int getColumnCount() {
			return fieldNames.length;
		}
		@Override // ineficiente: ¿puedes mejorarlo?
		public Object getValueAt(int rowIndex, int columnIndex) {
			if(fieldNames[columnIndex].equals("#")){
				return rowIndex;
			}
			Map<String, String> out = new HashMap<String, String>();
			elements.get(rowIndex).describe(out);
			return out.get(fieldNames[columnIndex]);
		}
		
		public void update(){
			fireTableDataChanged();
		}
		}
}
