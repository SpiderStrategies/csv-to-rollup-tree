package com.spider;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CSVtoJSONTest {
	@Test
	public void testTreeBuild() throws Exception {
		CSVtoJSON c2j = new CSVtoJSON("src/test/resources/usa.csv");
		String json = c2j.run();
		String expectedOutput = Utils.readTextFromFile("src/test/resources/usa.json").trim();
		assertEquals(expectedOutput, json);
	}
}

