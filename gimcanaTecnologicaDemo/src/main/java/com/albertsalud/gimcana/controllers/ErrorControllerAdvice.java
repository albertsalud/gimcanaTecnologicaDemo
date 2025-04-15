package com.albertsalud.gimcana.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ErrorControllerAdvice {
	
	@ExceptionHandler(value = Exception.class)
	public String showGenericErrorPage(Exception e) {
		log.error(e.getMessage());
		e.printStackTrace();
		return "error";
	}

}
