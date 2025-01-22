package com.albertsalud.gimcana.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.albertsalud.gimcana.model.entities.CheckPoint;
import com.albertsalud.gimcana.model.entities.Location;
import com.albertsalud.gimcana.model.entities.Player;
import com.albertsalud.gimcana.model.repositories.CheckPointRepository;

@ExtendWith(MockitoExtension.class)
public class CheckPointServiceTest {
	
	@InjectMocks
	private CheckPointService service;
	
	@Mock
	private LocationService locationService;
	
	@Mock
	private CheckPointRepository checkPointRepository;
	
	@Test
	public void createCheckPoint() {
		Player player = createPlayer();
		Location mockedLocation = new Location();
		when(locationService.getRandomLocation(anyList())).thenReturn(mockedLocation);
		CheckPoint response = service.createCheckPoint(player);
		
		assertEquals(player, response.getPlayer());
		assertEquals(mockedLocation, response.getLocation());
		assertNull(response.getCheckedDate());
				
	}
	
	@Test
	public void validateCheckPoint() {
		Player player = createPlayer();
		
		boolean response = service.validateCheckPoint(player, 2L);
		assertFalse(response);
		
		response = service.validateCheckPoint(player, 1L);
		assertTrue(response);
	}

	private Player createPlayer() {
		Player player = new Player();
		player.setCheckPoints(createCheckPoints(player));
		
		return player;
	}

	private List<CheckPoint> createCheckPoints(Player player) {
		CheckPoint checkPoint = new CheckPoint();
		checkPoint.setPlayer(player);
		checkPoint.setLocation(new Location(1l, "Location1", "Description1", true));
		
		List<CheckPoint> checkPoints = new ArrayList<>();
		checkPoints.add(checkPoint);
		return checkPoints;
	}
	
	

}
