package com.albertsalud.gimcana.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.albertsalud.gimcana.model.entities.Player;

public interface PlayerRepository extends JpaRepository<Player, Long>{

}
