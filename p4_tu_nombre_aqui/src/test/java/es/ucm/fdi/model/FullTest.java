package es.ucm.fdi.model;

import static org.junit.Assert.*;
import es.ucm.fdi.launcher.Main;

import org.junit.Test;

public class FullTest {
private static final String BASE = "src/test/resources/";

@Test(expected = SimulatorException.class)
public void testError() throws Exception {
	try{
		Main.test(BASE + "examples/err");
		fail("Expected an exception while parsing bad ini file");
	}
	catch(SimulatorException se){
		//Se esperaba la excepcion
	}
}

@Test
public void testBasic() throws Exception{
	Main.test(BASE + "examples/basic");
}

@Test
public void testAdvanced() throws Exception{
	Main.test(BASE + "examples/advanced");
}

}
