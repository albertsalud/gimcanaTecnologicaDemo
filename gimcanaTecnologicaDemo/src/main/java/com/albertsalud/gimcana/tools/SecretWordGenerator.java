package com.albertsalud.gimcana.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
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
			"NITRO",
			"INVALIDWORD"
	};
	
	public SecretWordGenerator() {
		this.checkSecretWordsLength();
	}
	
	private void checkSecretWordsLength() {
		List<String> validatedWords = new ArrayList<>();
		for(String currentWord:secretWords) {
			if(currentWord == null || currentWord.trim().length() != 5) {
				log.error("Invalid secret word found: {}", currentWord);
				continue;
			}
			validatedWords.add(currentWord);
		}
		
		this.secretWords = validatedWords.toArray(new String[validatedWords.size()]);
		
	}
	
	public String getSecretWord() {
		log.info("Selecting a secret word between {} possibilities...", secretWords.length);
		return secretWords[random.nextInt(secretWords.length)];
	}
	
}
