package com.albertsalud.gimcana.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.albertsalud.gimcana.controllers.dtos.PlayerDTO;
import com.albertsalud.gimcana.model.entities.Player;
import com.albertsalud.gimcana.model.services.PlayerService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/", ""})
@SessionAttributes("player")
@Slf4j
public class HomeController {
	
	private final PlayerService playerService;
	
	public HomeController(PlayerService playerService) {
		this.playerService = playerService;
	}
	
	@GetMapping
	public String home(Model model) {
		Player sessionPlayer = (Player) model.getAttribute("player");
		if(sessionPlayer != null && sessionPlayer.getId() != null) {
			log.info("Player info setted, redirecting to checkpoint");
			return "redirect:/checkpoint";
		}
		
		model.addAttribute("playerDTO", new PlayerDTO());
		return "index";
	}
	
	@PostMapping("/start")
	public String start(Model model,  @Valid PlayerDTO playerDTO, 
			BindingResult binding) {
		if(binding.hasErrors()) {
			return "index";
		}
		
		model.addAttribute("player", playerService.createPlayer(playerDTO.getName()));
		return "redirect:/checkpoint";
	}
	
	@GetMapping("/restart")
	public String restart(Model model, BindingResult result) {
		Player sessionPlayer = (Player) model.getAttribute("player");
		if(sessionPlayer == null || sessionPlayer.getId() == null) {
			log.warn("Player info not setted, redirecting home");
			return "redirect:/";
		}
		
		PlayerDTO playerDTO = new PlayerDTO();
		playerDTO.setName(sessionPlayer.getName());
		return this.start(model, new PlayerDTO(), result);
	}
	
	@ModelAttribute("player")
	private Player sessionPlayer() {
		return new Player();
	}

}
