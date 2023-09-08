package com.soumen.open.weather.map.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dt",
    "temperatures",
    "weather",
    "clouds",
    "wind",
    "sys"    
})
public class OpenApiForecast
{
	
	@JsonProperty("dt")
    private String dt;
	
	@JsonProperty("temperatures")
    private OpenApiTempData main;

	@JsonProperty("weather")
    private List<OpenApiWeather> weather;
	
	@JsonProperty("clouds")
    private OpenApiClouds clouds;

	@JsonProperty("sys")
    private OpenApiSys sys;

    @JsonProperty("wind")
    private OpenApiWind wind;
    
    @JsonProperty("dt_txt")
    private String dt_txt;

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	

	public OpenApiTempData getMain() {
		return main;
	}

	public void setMain(OpenApiTempData main) {
		this.main = main;
	}

	public String getDt_txt() {
		return dt_txt;
	}

	public void setDt_txt(String dt_txt) {
		this.dt_txt = dt_txt;
	}

	public List<OpenApiWeather> getWeather() {
		return weather;
	}

	public void setWeather(List<OpenApiWeather> weather) {
		this.weather = weather;
	}

	public OpenApiClouds getClouds() {
		return clouds;
	}

	public void setClouds(OpenApiClouds clouds) {
		this.clouds = clouds;
	}

	public OpenApiSys getSys() {
		return sys;
	}

	public void setSys(OpenApiSys sys) {
		this.sys = sys;
	}

	public OpenApiWind getWind() {
		return wind;
	}

	public void setWind(OpenApiWind wind) {
		this.wind = wind;
	}

	@Override
	public String toString() {
		return "OpenApiForecast [dt=" + dt + ", main=" + main + ", weather=" + weather + ", clouds=" + clouds + ", sys="
				+ sys + ", wind=" + wind + ", dt_txt=" + dt_txt + "]";
	}



    
}