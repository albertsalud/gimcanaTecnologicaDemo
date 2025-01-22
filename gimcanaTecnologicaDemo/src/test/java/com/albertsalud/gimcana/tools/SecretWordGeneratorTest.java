package com.albertsalud.gimcana.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SecretWordGeneratorTest {
	
	private SecretWordGenerator secretWordGenerator = new SecretWordGenerator();

	@Test
	public void getSecretWord() {
		String response = secretWordGenerator.getSecretWord();
		assertEquals(5, response.length());
		
	}
}
