package com.albertsalud.gimcana.controllers;

import java.util.Collections;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.albertsalud.gimcana.controllers.dtos.StatusDTO;
import com.albertsalud.gimcana.model.entities.CheckPoint;
import com.albertsalud.gimcana.model.entities.Player;
import com.albertsalud.gimcana.model.services.CheckPointService;
import com.albertsalud.gimcana.model.services.LocationService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/checkpoint")
@SessionAttributes("player")
@Slf4j
public class CheckPointController {
	
	private final LocationService locationService;
	private final CheckPointService checkPointService;
	
	public CheckPointController(LocationService locationService, CheckPointService checkPointService) {
		this.locationService = locationService;
		this.checkPointService = checkPointService;
	}
	
	@GetMapping
	public String getStatus(Model model) {
		Player player = (Player) model.getAttribute("player");
		if(player == null || player.getId() == null) {
			log.warn("Player info not setted, redirecting home");
			return "redirect:/";
		}
		
		log.info("Obtaining info for player {}...", player.getName());
		if(player.getCheckedCheckPoints() == CheckPoint.MAX_ALLOWED_CHECKPOINTS) {
			return "congratulations";
		} else {
			model.addAttribute("status", createStatusDTO(player));
			return "checkpoint";
		}
		
	}
	
	@PostMapping
	public String validate(Model model, @RequestParam(name="response") Long locationId) {
		Player player = (Player) model.getAttribute("player");
		if(player == null || player.getId() == null) {
			log.warn("Player info not setted, redirecting home");
			return "redirect:/";
		}
		
		model.addAttribute("correctPlace", checkPointService.validateCheckPoint(player, locationId));
		
		return this.getStatus(model);
		
	}
	
	
	private StatusDTO createStatusDTO(Player player) {
		StatusDTO status = new StatusDTO();
		status.setCurrentLocationDescription(player.getCurrentLocation().getDescription());
		status.setLettersFound(player.getCheckPoints().size() - 1);
		status.setStringFound(player.getSecretWord().substring(0, player.getCheckPoints().size() - 1));
		status.setName(player.getName());
		status.addResponse(player.getCurrentLocation());
		status.addResponse(locationService.getRandomLocation(status.getResponses()));
		status.addResponse(locationService.getRandomLocation(status.getResponses()));
		Collections.shuffle(status.getResponses());
		
		return status;
	}

}
