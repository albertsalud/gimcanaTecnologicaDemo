package com.albertsalud.gimcana.model.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "PLAYER")
public class Player {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@Column(name = "SECRET_WORD", nullable = false)
	private String secretWord;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "player_id")
	@OrderBy("assignedDate desc")
	private List<CheckPoint> checkPoints = new ArrayList<>();
	
	public Location getCurrentLocation() {
		return this.checkPoints.stream()
					.filter(checkpoint -> checkpoint.getCheckedDate() == null)
					.map(CheckPoint::getLocation)
					.findFirst()
					.orElseThrow(() -> new RuntimeException());
	}
	
	public int getCheckedCheckPoints() {
		return this.checkPoints.stream()
				.filter(checkpoint -> checkpoint.getCheckedDate() != null)
				.toList()
				.size();
	}
	
	

}
