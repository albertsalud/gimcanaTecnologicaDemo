package com.albertsalud.gimcana.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlayerDTO {
	
	@NotBlank(message = "No pots introduïr un nom en blanc.")
	@Size(min = 2, max = 20, message = "El teu nom ha de tenir entre 2 i 20 caracters.")
	private String name;

}
