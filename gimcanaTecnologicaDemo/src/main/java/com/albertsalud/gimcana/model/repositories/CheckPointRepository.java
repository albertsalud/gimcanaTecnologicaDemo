package com.albertsalud.gimcana.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.albertsalud.gimcana.model.entities.CheckPoint;

public interface CheckPointRepository extends JpaRepository<CheckPoint, Long>{

}
