package com.albertsalud.gimcana.controllers.dtos;

import java.util.ArrayList;
import java.util.List;

import com.albertsalud.gimcana.model.entities.Location;

import lombok.Data;

@Data
public class StatusDTO {
	
	private String name;
	private int lettersFound;
	private String stringFound;
	private String currentLocationDescription;
	private List<Location> responses = new ArrayList<>();
	
	public void addResponse(Location currentLocation) {
		responses.add(currentLocation);
	}

}
