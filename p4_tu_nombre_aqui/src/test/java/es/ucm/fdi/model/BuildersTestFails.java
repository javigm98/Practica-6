package es.ucm.fdi.model;

import static org.junit.Assert.*;

import org.junit.Test;

import es.ucm.fdi.control.EventBuilder;
import es.ucm.fdi.control.NewRoadEventBuilder;
import es.ucm.fdi.ini.IniSection;

public class BuildersTestFails {

	@Test (expected = SimulatorException.class)
	public void RoadBuilderWithoutMaxSpeedest() {
		EventBuilder eb = new NewRoadEventBuilder();
		IniSection sec = new IniSection("new_road");
		sec.setValue("time", 0);
		sec.setValue("id", "r1");
		sec.setValue("src", "j1");
		sec.setValue("dest", "j2");
		sec.setValue("length", 100);
		Event e = eb.parse(sec);	
	}

}
