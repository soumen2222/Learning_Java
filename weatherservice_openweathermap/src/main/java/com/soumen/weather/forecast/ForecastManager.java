package com.soumen.weather.forecast;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.soumen.weather.service.ForecastResponse;

public interface ForecastManager {
    @Remote
    public interface R extends ForecastManager {}
    @Local
    public interface L extends ForecastManager {}
	ForecastResponse getForecast(String cityName, String countryCode) throws Exception;
	void createForecast(ForecastResponse forecast);
    
}