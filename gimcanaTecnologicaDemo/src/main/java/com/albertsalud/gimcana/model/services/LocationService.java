package com.albertsalud.gimcana.model.services;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.albertsalud.gimcana.model.entities.Location;
import com.albertsalud.gimcana.model.repositories.LocationRepository;

@Service
public class LocationService {
	
	private final List<Location> locations;
	private final LocationRepository locationRepository;
	
	public LocationService(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
		this.locations = this.locationRepository.findAll().stream().filter(Location::getEnabled).toList();
	}
	
	
	public List<Location> getLocations() {
		return locations;
	}
	
	public Location getRandomLocation(List<Location> invalidLocations) {
		Location randomLocation;
		Random r = new Random();
		do {
			int randomIndex = r.nextInt(this.locations.size());
			randomLocation = this.locations.get(randomIndex);
			
		} while (invalidLocations.contains(randomLocation));
		
		return randomLocation;
	}

}
