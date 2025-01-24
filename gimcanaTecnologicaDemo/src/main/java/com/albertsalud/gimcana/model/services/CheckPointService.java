package com.albertsalud.gimcana.model.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.albertsalud.gimcana.model.entities.CheckPoint;
import com.albertsalud.gimcana.model.entities.Location;
import com.albertsalud.gimcana.model.entities.Player;
import com.albertsalud.gimcana.model.repositories.CheckPointRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CheckPointService {
	
	private final LocationService locationService;
	private final CheckPointRepository checkPointRepository;
	
	public CheckPointService(LocationService locationService,
			CheckPointRepository checkPointRepository) {
		this.locationService = locationService;
		this.checkPointRepository = checkPointRepository;
	}

	public CheckPoint createCheckPoint(Player player) {
		CheckPoint newCheckPoint = new CheckPoint();
		newCheckPoint.setPlayer(player);
		newCheckPoint.setLocation(locationService.getRandomLocation(
				player.getCheckPoints().stream().map(CheckPoint::getLocation)
					.toList()
				));
		
		return newCheckPoint;
	}

	public boolean validateCheckPoint(Player player, Long locationId) {
		Location expectedLocation = player.getCurrentLocation();
		if(expectedLocation.getId().equals(locationId)) {
			log.info("Correct location!");
			CheckPoint currentCheckPoint = getCurrentCheckPoint(player.getCheckPoints());
			currentCheckPoint.setCheckedDate(new Date());
			this.checkPointRepository.save(currentCheckPoint);
			
			if(player.getCheckPoints().size() < CheckPoint.MAX_ALLOWED_CHECKPOINTS) {
				log.info("Creating new checkpoint for {}", player.getName());
				CheckPoint nextCheckPoint = this.createCheckPoint(player);
				player.getCheckPoints().add(this.checkPointRepository.save(nextCheckPoint));
			}
			return true;
		}
		
		return false;
		
	}

	private CheckPoint getCurrentCheckPoint(List<CheckPoint> checkPoints) {
		return checkPoints.stream()
				.filter(checkPoint -> checkPoint.getCheckedDate() == null)
				.findFirst()
				.orElseThrow(RuntimeException::new);
	}

}
