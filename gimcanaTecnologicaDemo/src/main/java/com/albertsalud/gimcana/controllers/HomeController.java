package com.albertsalud.gimcana.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.albertsalud.gimcana.model.entities.Player;
import com.albertsalud.gimcana.model.services.PlayerService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Controller
@RequestMapping(value = {"/", ""})
@SessionAttributes("player")
public class HomeController {
	
	private final PlayerService playerService;
	
	public HomeController(PlayerService playerService) {
		this.playerService = playerService;
	}
	
	@GetMapping
	public String home(Model model) {
		Player sessionPlayer = (Player) model.getAttribute("player");
		if(sessionPlayer != null && sessionPlayer.getId() != null) return "redirect:/checkpoint";
		return "index";
	}
	
	@PostMapping("/start")
	public String start(Model model, @NotBlank @Size(min = 2, max = 20) @RequestParam("name") String name) {
		model.addAttribute("player", playerService.createPlayer(name));
		return "redirect:/checkpoint";
	}
	
	@GetMapping("/restart")
	public String restart(Model model) {
		Player sessionPlayer = (Player) model.getAttribute("player");
		return this.start(model, sessionPlayer.getName());
	}
	
	@ModelAttribute("player")
	private Player sessionPlayer() {
		return new Player();
	}

}
