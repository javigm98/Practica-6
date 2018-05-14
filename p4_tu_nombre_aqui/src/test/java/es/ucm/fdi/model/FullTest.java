package es.ucm.fdi.model;

import java.io.IOException;

import org.junit.Test;

import es.ucm.fdi.launcher.Main;

public class FullTest {
private static final String BASE = "src/test/resources/";

@Test
public void testError() throws IOException {
		try {
			Main.test(BASE + "examples/err");
		} catch (SimulatorException se) {
			// ok: expected
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
