package com.soumen.open.weather.map.api;
public class AppServiceException extends Exception {

	private static final long serialVersionUID = -5652593793975686482L;

	public AppServiceException(String key) {
		super(key);
	}

	public AppServiceException(String key, Throwable cause) {
		super(key, cause);
	}
	
	public AppServiceException(Throwable cause) {
		super(cause);
	}

}