package com.albertsalud.gimcana.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorControllerAdvice {
	
	@ExceptionHandler(value = Exception.class)
	public String showGenericErrorPage() {
		return "error";
	}

}
