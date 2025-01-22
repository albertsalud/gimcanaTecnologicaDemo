package com.albertsalud.gimcana.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.albertsalud.gimcana.model.entities.CheckPoint;
import com.albertsalud.gimcana.model.entities.Player;
import com.albertsalud.gimcana.model.repositories.PlayerRepository;
import com.albertsalud.gimcana.tools.SecretWordGenerator;

@ExtendWith(MockitoExtension.class)
public class PlayertServiceTest {
	
	@InjectMocks
	private PlayerService service;
	
	@Mock
	private PlayerRepository playerRepository;
	
	@Mock
	private SecretWordGenerator secretWordGenerator;
	
	@Mock
	private CheckPointService checkPointService;
	
	@Captor
	ArgumentCaptor<Player> playerCaptor;
	
	@Test
	public void createPlayer() {
		String name = "Test";
		
		String mockedSecretWord = "WORD!";
		when(secretWordGenerator.getSecretWord()).thenReturn(mockedSecretWord);
		when(checkPointService.createCheckPoint(any())).thenReturn(new CheckPoint());
		
		service.createPlayer(name);
		
		verify(secretWordGenerator).getSecretWord();
		verify(checkPointService).createCheckPoint(any());
		verify(playerRepository).save(playerCaptor.capture());
		
		Player createdPlayer = playerCaptor.getValue();
		assertEquals(name, createdPlayer.getName());
		assertEquals(1, createdPlayer.getCheckPoints().size());
		
	}

}
