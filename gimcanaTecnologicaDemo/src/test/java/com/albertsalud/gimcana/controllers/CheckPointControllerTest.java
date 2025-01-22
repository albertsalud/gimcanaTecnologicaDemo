package com.albertsalud.gimcana.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.albertsalud.gimcana.controllers.dtos.CheckPointDTO;
import com.albertsalud.gimcana.controllers.dtos.StatusDTO;
import com.albertsalud.gimcana.model.entities.CheckPoint;
import com.albertsalud.gimcana.model.entities.Location;
import com.albertsalud.gimcana.model.entities.Player;
import com.albertsalud.gimcana.model.services.CheckPointService;
import com.albertsalud.gimcana.model.services.LocationService;

@ExtendWith(MockitoExtension.class)
public class CheckPointControllerTest {

	@InjectMocks
	private CheckPointController controller;
	
	@Mock
	private LocationService locationService;
	
	@Mock
	private CheckPointService checkPointService;
	
	@Mock
	private Model mockedModel;
	
	@Mock
	private BindingResult mockedBindingResult;
	
	@Test
	public void getStatus_whenNoSessionUser() {
		Player sessionPlayer = null;
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer);
		
		String response = controller.getStatus(mockedModel);
		assertEquals("redirect:/", response);
		verify(mockedModel).getAttribute(eq("player"));
		
		reset(mockedModel);
		sessionPlayer = new Player();
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer);

		response = controller.getStatus(mockedModel);
		assertEquals("redirect:/", response);
		verify(mockedModel).getAttribute(eq("player"));
		
	}
	
	@Test
	public void getStatus_whenFinished() {
		Player sessionPlayer = new Player();
		sessionPlayer.setId(1L);
		sessionPlayer.setCheckPoints(createCheckPoints(5));
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer);
		
		String response = controller.getStatus(mockedModel);
		assertEquals("congratulations", response);
		verify(mockedModel).getAttribute(eq("player"));
		verify(mockedModel).addAttribute(eq("checkPointDTO"), any(CheckPointDTO.class));
	}
	
	@Test
	public void getStatus_whenNotFinished() {
		Player sessionPlayer = new Player();
		sessionPlayer.setId(1L);
		sessionPlayer.setCheckPoints(createCheckPoints(3));
		sessionPlayer.getCheckPoints().get(0).setCheckedDate(null);
		sessionPlayer.setSecretWord("TEST!");
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer);
		
		String response = controller.getStatus(mockedModel);
		assertEquals("checkpoint", response);
		verify(mockedModel).getAttribute(eq("player"));
		verify(mockedModel).addAttribute(eq("checkPointDTO"), any(CheckPointDTO.class));
		verify(mockedModel).addAttribute(eq("status"), any(StatusDTO.class));
	}
	
	@Test
	public void validate_whenNoSessionPlayer() {
		Player sessionPlayer = null;
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer);
		
		String response = controller.validate(mockedModel, null, mockedBindingResult);
		assertEquals("redirect:/", response);
		verify(mockedModel).getAttribute(eq("player"));
		
		reset(mockedModel);
		sessionPlayer = new Player();
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer);

		response = controller.validate(mockedModel, null, mockedBindingResult);
		assertEquals("redirect:/", response);
		verify(mockedModel).getAttribute(eq("player"));
	}
	
	@Test
	public void validate_whenBindingErrors() {
		Player sessionPlayer = new Player();
		sessionPlayer.setId(1L);
		sessionPlayer.setCheckPoints(createCheckPoints(3));
		sessionPlayer.getCheckPoints().get(0).setCheckedDate(null);
		sessionPlayer.setSecretWord("TEST!");
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer);
		when(mockedBindingResult.hasErrors()).thenReturn(true);
		
		String response = controller.validate(mockedModel, null, mockedBindingResult);
		assertEquals("checkpoint", response);
		verify(mockedModel).addAttribute(eq("status"), any(StatusDTO.class));
	}
	
	@Test
	public void validate() {
		Player sessionPlayer = new Player();
		sessionPlayer.setId(1L);
		sessionPlayer.setCheckPoints(createCheckPoints(3));
		sessionPlayer.getCheckPoints().get(0).setCheckedDate(null);
		sessionPlayer.setSecretWord("TEST!");
		when(mockedModel.getAttribute(anyString())).thenReturn(sessionPlayer);
		when(mockedBindingResult.hasErrors()).thenReturn(false);
		
		CheckPointDTO checkPointDTO = createCheckPointDTO();
		String response = controller.validate(mockedModel, checkPointDTO , mockedBindingResult);
		assertEquals("checkpoint", response);
		verify(mockedModel).addAttribute(eq("correctPlace"), anyBoolean());
		verify(checkPointService).validateCheckPoint(eq(sessionPlayer), eq(checkPointDTO.getLocation()));
	}

	private CheckPointDTO createCheckPointDTO() {
		CheckPointDTO checkPointDTO = new CheckPointDTO();
		checkPointDTO.setLocation(1L);
		return checkPointDTO;
	}

	private List<CheckPoint> createCheckPoints(int checkPointsAmount) {
		List<CheckPoint> checkPoints = new ArrayList<>();
		for(int i = 0; i<checkPointsAmount;i++) {
			checkPoints.add(createCheckPoint());
		}
		return checkPoints;
	}

	private CheckPoint createCheckPoint() {
		CheckPoint checkPoint = new CheckPoint();
		checkPoint.setCheckedDate(new Date());
		checkPoint.setLocation(new Location());
		return checkPoint;
	}
}
