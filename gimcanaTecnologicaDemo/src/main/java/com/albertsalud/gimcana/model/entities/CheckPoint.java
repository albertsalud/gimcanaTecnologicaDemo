package com.albertsalud.gimcana.model.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CHECKPOINT")
@Data
@NoArgsConstructor
public class CheckPoint {
	
	public static final Integer MAX_ALLOWED_CHECKPOINTS = 5;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;
	
	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player player;
	
	@Column(name="ASSIGNED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date assignedDate;
	
	@Column(name="CHECKED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkedDate;
}
