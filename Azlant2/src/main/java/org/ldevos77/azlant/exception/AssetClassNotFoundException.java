package org.ldevos77.azlant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Asset class not found")
public class AssetClassNotFoundException extends RuntimeException {

	/**
	 * Serial version ID set manually
	 */
	private static final long serialVersionUID = 1L;

}
