package com.albertsalud.gimcana.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.albertsalud.gimcana.controllers.dtos.PlayerDTO;
import com.albertsalud.gimcana.model.entities.Player;
import com.albertsalud.gimcana.model.services.PlayerService;

@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {
	
	@InjectMocks
	private HomeController controller;
	
	@Mock
	private PlayerService playerService;
	
	@Mock
	private Model mockedModel;
	
	@Mock
	private BindingResult mockedBindingResult;
	
	@Test
	public void home() {
		Player sessionPlayer = null;
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer); 
		
		String response = controller.home(mockedModel);
		assertEquals("index", response);
		verify(mockedModel).getAttribute(eq("player"));
		verify(mockedModel).addAttribute(eq("playerDTO"), any(PlayerDTO.class));
		
		reset(mockedModel);
		sessionPlayer = new Player();
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer);
		response = controller.home(mockedModel);
		assertEquals("index", response);
		verify(mockedModel).getAttribute(eq("player"));
		verify(mockedModel).addAttribute(eq("playerDTO"), any(PlayerDTO.class));
		
		reset(mockedModel);
		sessionPlayer.setId(1L);
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer);
		response = controller.home(mockedModel);
		assertEquals("redirect:/checkpoint", response);
		verify(mockedModel).getAttribute(eq("player"));
		verify(mockedModel, never()).addAttribute(anyString(), any());
	}
	
	@Test
	public void start_whenBindingErrors() {
		PlayerDTO playerDTO = new PlayerDTO();
		when(mockedBindingResult.hasErrors()).thenReturn(true);
		
		String response = controller.start(mockedModel, playerDTO, mockedBindingResult);
		assertEquals("index", response);
		verify(playerService, never()).createPlayer(anyString());
		verify(mockedModel, never()).addAttribute(anyString(), any());
	}
	
	@Test
	public void start() {
		PlayerDTO playerDTO = new PlayerDTO();
		playerDTO.setName("NAME");
		when(mockedBindingResult.hasErrors()).thenReturn(false);
		when(playerService.createPlayer(anyString())).thenReturn(new Player());
		
		String response = controller.start(mockedModel, playerDTO, mockedBindingResult);
		assertEquals("redirect:/checkpoint", response);
		verify(playerService).createPlayer(eq(playerDTO.getName()));
		verify(mockedModel).addAttribute(eq("player"), any(Player.class));
	}
	
	@Test
	public void restart() {
		Player sessionPlayer = null;
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer); 
		
		String response = controller.restart(mockedModel, mockedBindingResult);
		assertEquals("redirect:/", response);
		verify(mockedModel).getAttribute(eq("player"));
		
		reset(mockedModel);
		sessionPlayer = new Player();
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer); 
		
		response = controller.restart(mockedModel, mockedBindingResult);
		assertEquals("redirect:/", response);
		verify(mockedModel).getAttribute(eq("player"));
		
		reset(mockedModel);
		sessionPlayer.setId(1L);
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer); 
		
		response = controller.restart(mockedModel, mockedBindingResult);
		assertEquals("redirect:/checkpoint", response);
		verify(mockedModel).getAttribute(eq("player"));
	}
	
}
