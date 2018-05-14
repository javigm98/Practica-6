package es.ucm.fdi.model;


import org.junit.Test;

import es.ucm.fdi.control.EventBuilder;
import es.ucm.fdi.control.NewCarEventBuilder;
import es.ucm.fdi.control.NewRoadEventBuilder;
import es.ucm.fdi.ini.IniSection;

public class BuildersTestFails {

	@Test (expected = IllegalArgumentException.class) // Aunque en origen fuese una NumberFormatException
	public void RoadBuilderWithoutMaxSpeedest() {
		EventBuilder eb = new NewRoadEventBuilder();
		IniSection sec = new IniSection("new_road");
		sec.setValue("time", 0);
		sec.setValue("id", "r1");
		sec.setValue("src", "j1");
		sec.setValue("dest", "j2");
		sec.setValue("length", 100);
		eb.parse(sec);	// No necesito la referencia, solo llamarla para que se produzca la excepcion
	}
	
	@Test (expected = IllegalArgumentException.class) // Faltan mas campos. Pero el primero que comprueba y da error es en el ID
	public void CarBuilderWithoutId() {
		EventBuilder eb = new NewCarEventBuilder();
		IniSection sec = new IniSection("new_vehicle");
		sec.setValue("time", 0);
		sec.setValue("type", "car");
		eb.parse(sec);
	}


}
