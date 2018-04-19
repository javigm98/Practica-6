package es.ucm.fdi.view;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import es.ucm.fdi.model.Event;
import es.ucm.fdi.util.MultiTreeMap;

public class EventsTableModel extends AbstractTableModel {
	private String[] columnNames = {"#", "time", "type"};
	private Object[][] rowData;
	public EventsTableModel(MultiTreeMap <Integer, Event> listaEventos){
		int i = 0;
		rowData = new Object[listaEventos.size()][columnNames.length];
		for(Event e: listaEventos.innerValues()){
			rowData[i][0] = i;
			rowData[i][1] = e.getTime();
			rowData[i][2] = e.infoParaTabla();
			i++;
		}
	}
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return rowData.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rowData[rowIndex][columnIndex];
	}


}
