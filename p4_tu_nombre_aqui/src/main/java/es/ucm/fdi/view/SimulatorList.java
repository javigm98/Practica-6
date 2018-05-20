package es.ucm.fdi.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.ucm.fdi.model.SimObject;

/**
 * Representa graficamente una lista de ids de los objetos simulados
 * 
 * @author Javier Guzman y Jorge Villarrubia
 *
 */

public class SimulatorList extends JPanel implements ListSelectionListener {
	private JList<String> lista;
	private Object[] elementos;
	private HashMap<String, Boolean> seleccionados;
	private List<String> ids;

	/**
	 * @param objetos
	 *            lista de objetos del tipo SimObject que se representaran en la
	 *            lista
	 */

	public SimulatorList(List<? extends SimObject> objetos) {
		// Creamos una lista con los ids de los objetos
		ids = new ArrayList<>();
		for (SimObject so : objetos) {
			ids.add(so.getId());
		}
		// Convertimos la lista en un array
		elementos = ids.toArray();
		// Creamos el componenente JList
		lista = new JList(elementos);
		lista.setVisibleRowCount(10);
		lista.setFixedCellHeight(20);
		lista.setFixedCellWidth(140);
		lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lista.addListSelectionListener(this);
		seleccionados = new HashMap<>();
		deseleccionaTodosInternamente();
		add(new JScrollPane(lista, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}

	/**
	 * 
	 * @return JList con los ids de los objetos
	 */

	public JList<String> getLista() {
		return lista;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == lista && e.getValueIsAdjusting() == false) {
			int[] fromIndex = lista.getSelectedIndices();
			// Deseleccionamos todos los objetos por si ha habido una nueva
			// seleccion
			deseleccionaTodosInternamente();
			// guardamos en un mapa no ordenado los ids con un booleano que
			// indica si estan seleccionados,
			// para que el coste de accseo a elementos sea constante.
			for (int i = 0; i < fromIndex.length; ++i) {
				seleccionados.put((String) elementos[fromIndex[i]], true);
			}
		}
	}

	/**
	 * Deselecciona todos los objetos de la lista
	 */

	private void deseleccionaTodosInternamente() {
		for (String id : ids) {
			seleccionados.put(id, false);
		}
	}

	/**
	 * @return mapa con todos los ids de la lista y un booleano que indica si
	 *         han sido o no seleccionados
	 */

	public HashMap<String, Boolean> getSeleccionados() {
		return seleccionados;
	}

}
