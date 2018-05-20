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

/**
 * JPanel con una tabla que muestra objetos SimObject
 * 
 * @author Javier Guzman y Jorge Villarrubia
 *
 */
public class SimulatorTable extends JPanel {
	private JTable tabla;
	private String[] fieldNames;
	private List<? extends Describable> elements;
	private ListOfMapsTableModel modelo = new ListOfMapsTableModel();

	/**
	 * 
	 * @param fieldNames
	 *            nombres de las columnas de la tabla
	 * @param elements
	 *            lista de objetos a partir de la cual se rellena la tabla
	 */

	public SimulatorTable(String[] fieldNames,
			List<? extends Describable> elements) {
		super(new BorderLayout());
		this.fieldNames = fieldNames;
		this.elements = elements;
		tabla = new JTable(modelo);
		add(new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}

	/**
	 * Actualiza los datos de la tabla
	 */

	public void update() {
		modelo.update();
	}

	/**
	 * @param elements
	 *            lista con los elementos cuya informacion se mostrara en la
	 *            tabla.
	 */

	public void setElements(List<? extends Describable> elements) {
		this.elements = elements;
	}

	/**
	 * Modelo para la creacion de la tabla
	 * 
	 * @author Javier Guzman y Jorge Villarrubia
	 *
	 */

	private class ListOfMapsTableModel extends AbstractTableModel {
		@Override
		public String getColumnName(int columnIndex) {
			return fieldNames[columnIndex];
		}

		@Override
		public int getRowCount() {
			return elements.size();
		}

		@Override
		public int getColumnCount() {
			return fieldNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (fieldNames[columnIndex].equals("#")) {
				return rowIndex;
			}
			Map<String, String> out = new HashMap<String, String>();
			elements.get(rowIndex).describe(out);
			return out.get(fieldNames[columnIndex]);
		}

		/**
		 * Mantiene la tabla actualizada
		 */
		public void update() {
			fireTableDataChanged();
		}
	}
}
