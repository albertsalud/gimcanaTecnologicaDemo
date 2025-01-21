package com.albertsalud.gimcana.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.albertsalud.gimcana.model.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

}
