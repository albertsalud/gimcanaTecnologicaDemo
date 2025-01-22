package com.albertsalud.gimcana.model.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.albertsalud.gimcana.model.entities.Location;
import com.albertsalud.gimcana.model.repositories.LocationRepository;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {
	
	@InjectMocks
	private LocationService service;
	
	@Mock
	private LocationRepository locationRepository;
	
	private List<Location> mockedLocations = createMockedLocation();
	
	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(service, "locations", mockedLocations);
	}
	
	private List<Location> createMockedLocation() {
		List<Location> locations = new ArrayList<>();
		locations.add(new Location(1L, "Location1", "Description1", true));
		locations.add(new Location(2L, "Location2", "Description2", true));
		
		return locations;
	}

	@Test
	public void getRandomLocation() {
		List<Location> invalidLocations = Collections.singletonList(mockedLocations.get(0));
		Location response = service.getRandomLocation(invalidLocations);
		assertNotNull(response);
		assertFalse(invalidLocations.contains(response));
		
		response = service.getRandomLocation(mockedLocations);
		assertNotNull(response);
		assertTrue(mockedLocations.contains(response));
		
	}

}
