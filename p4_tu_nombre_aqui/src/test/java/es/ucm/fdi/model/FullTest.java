package es.ucm.fdi.model;

import static org.junit.Assert.*;
import es.ucm.fdi.launcher.Main;

import org.junit.Test;

public class FullTest {
private static final String BASE = "src/test/resources";
@Test
public void testError() throws Exception {
	try{
		Main.test(BASE + "examples/err");
		fail("expected an exception while parsing bad ini file");
	}
	catch(SimulatorException se){
		
	}
}
}
