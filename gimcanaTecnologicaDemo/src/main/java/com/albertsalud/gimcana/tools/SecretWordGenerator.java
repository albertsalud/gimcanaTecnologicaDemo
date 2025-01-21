package com.albertsalud.gimcana.tools;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class SecretWordGenerator {
	
	private Random random = new Random();
	
	private String[] secretWords = {
			"CATAN",
			"FUNGI",
			"VIRUS",
			"BANG!",
			"MIXIT",
			"ZUMOS",
			"DIXIT",
			"COINX",
			"QWIXX",
			"PARIS",
			"YOKAI",
			"TOMA6",
			"PALEO",
			"BRESK",
			"SPICY",
			"SPELL",
			"ABYSS",
			"LOSER",
			"ALICE",
			"GIZEH",
			"PYLOS",
			"CAIRN",
			"TROIA",
			"CACAO",
			"CONEX",
			"4GODS",
			"IWARI",
			"TRIBE",
			"ORBIS",
			"RAMEN",
			"PAPUA",
			"LIKES",
			"ALONE",
			"TUDOR",
			"SKULL",
			"NITRO"
	};
	
	public String getSecretWord() {
		System.out.println("Selecting a secret word between " + secretWords.length + " possibilities...");
		return secretWords[random.nextInt(secretWords.length)];
	}
	
}
