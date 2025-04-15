package com.albertsalud.gimcana.model.services;

import org.springframework.stereotype.Service;

import com.albertsalud.gimcana.model.entities.Player;
import com.albertsalud.gimcana.model.repositories.PlayerRepository;
import com.albertsalud.gimcana.tools.SecretWordGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlayerService {
	
	private final PlayerRepository playerRepository;
	private final SecretWordGenerator secretWordGenerator;
	private final CheckPointService checkPointService;

	public PlayerService(PlayerRepository playerRepository,
			SecretWordGenerator secretWordGenerator,
			CheckPointService checkPointService) {
		this.playerRepository = playerRepository;
		this.secretWordGenerator = secretWordGenerator;
		this.checkPointService = checkPointService;
	}
	
	
	public Player createPlayer(String name) {
		Player newPlayer = new Player();
		newPlayer.setName(name);
		newPlayer.setSecretWord(this.secretWordGenerator.getSecretWord());
		newPlayer.getCheckPoints().add(checkPointService.createCheckPoint(newPlayer));
		
		log.info("Creating new player {}...", newPlayer.toString());
		return this.playerRepository.save(newPlayer);
	}

}
