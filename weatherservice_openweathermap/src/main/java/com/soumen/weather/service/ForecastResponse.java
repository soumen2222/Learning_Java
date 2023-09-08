package com.soumen.weather.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.soumen.open.weather.map.api.Coord;

public class ForecastResponse implements Serializable{

	private static final long serialVersionUID = -7472698851035291963L;
	private List<TemperatureDate> forecasts;	
	private String countryCode;
	private String CityName;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate curentDay;



	public List<TemperatureDate> getForecasts() {
		return forecasts;
	}

	public void setForecasts(List<TemperatureDate> forecasts) {
		this.forecasts = forecasts;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}

	public LocalDate getCurentDay() {
		return curentDay;
	}

	public void setCurentDay(LocalDate curentDay) {
		this.curentDay = curentDay;
	}

	
	

}
