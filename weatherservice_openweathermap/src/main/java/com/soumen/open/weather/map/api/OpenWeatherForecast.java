package com.soumen.open.weather.map.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cod",
    "message",
    "cnt",
    "list",
    "city"
})
public class OpenWeatherForecast {

	
	
    private String cod;

    private float message;
   
    private int cnt;
   
    private List<OpenApiForecast> list;
   
    private OpenApiCity city;
    
   

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public float getMessage() {
		return message;
	}

	public void setMessage(float message) {
		this.message = message;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public List<OpenApiForecast> getList() {
		return list;
	}

	public void setList(List<OpenApiForecast> list) {
		this.list = list;
	}

	public OpenApiCity getCity() {
		return city;
	}

	public void setCity(OpenApiCity city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "OpenWeatherForecast [cod=" + cod + ", message=" + message + ", cnt=" + cnt + ", list=" + list
				+ ", city=" + city + "]";
	}
  
    
}
