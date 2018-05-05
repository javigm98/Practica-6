package es.ucm.fdi.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.ucm.fdi.model.SimObject;

public class SimulatorList extends JPanel implements ListSelectionListener {
	private JList<String> lista;
	private Object[] elementos;
	private HashMap<String, Boolean> seleccionados;
	
	public SimulatorList(List<? extends SimObject> objetos){
		List<String> ids = new ArrayList<>();
		for(SimObject so : objetos){
			ids.add(so.getId());
		}
		elementos = ids.toArray();
		lista = new JList(elementos);
		lista.setVisibleRowCount(10); 
		lista.setFixedCellHeight(20); 
		lista.setFixedCellWidth(140);
		lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lista.addListSelectionListener(this);
		seleccionados = new HashMap<>();
		for(String id : ids){
			seleccionados.put(id, false);
		}
		add(new JScrollPane(lista, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if ( e.getSource() == lista && e.getValueIsAdjusting() == false ) {
			int[] fromIndex = lista.getSelectedIndices();
			for(int i = 0; i < fromIndex.length; ++i){
				seleccionados.put((String) elementos[fromIndex[i]], true);
			}
		}
	}

	public HashMap<String, Boolean> getSeleccionados() {
		return seleccionados;
	}
	

}
