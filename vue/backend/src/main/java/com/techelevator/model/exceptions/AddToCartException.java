package com.techelevator.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * AddToCartException
 */

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AddToCartException extends Exception{

	private static final long serialVersionUID = 1L;

}
