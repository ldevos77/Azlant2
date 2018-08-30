package org.ldevos77.azlant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Asset not found")
public class AssetNotFoundException extends RuntimeException {

	/**
	 * generated serial version ID (compiler-generated ID)
	 */
	private static final long serialVersionUID = -7200373810463384336L;

}
