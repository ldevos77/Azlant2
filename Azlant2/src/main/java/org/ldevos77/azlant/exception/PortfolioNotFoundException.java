package org.ldevos77.azlant.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Portfolio not found")
public class PortfolioNotFoundException extends RuntimeException {

	/**
	 * Serial version ID set manually
	 */
	private static final long serialVersionUID = 1L;

}
