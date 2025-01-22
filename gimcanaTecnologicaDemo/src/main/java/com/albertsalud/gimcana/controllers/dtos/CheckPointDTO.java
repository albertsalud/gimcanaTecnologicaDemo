package com.albertsalud.gimcana.controllers.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckPointDTO {

	@NotNull(message = "Has de seleccionar la ubicació a on et trobes.")
	@Min(value = 8, message = "Has tocat alguna cosa que no deuries. Juga i disfruta sense fer trampes.")
	@Max(value = 23, message = "Has tocat alguna cosa que no deuries. Juga i disfruta sense fer trampes.")
	private Long location;
}
