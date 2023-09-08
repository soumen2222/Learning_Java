package com.soumen.open.weather.map.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "temp",
    "temp_min",
    "grnd_level",
    "temp_kf",
    "humidity",
    "pressure",
    "sea_level",
    "temp_max"   
    
})

public class OpenApiTempData
{
    private double temp;

    private double temp_min;

    private double grnd_level;

    private double temp_kf;

    private double humidity;

    private double pressure;

    private double sea_level;

    private double temp_max;

   
    public double getTemp() {
		return temp;
	}


	public void setTemp(double temp) {
		this.temp = temp;
	}


	public double getTemp_min() {
		return temp_min;
	}


	public void setTemp_min(double temp_min) {
		this.temp_min = temp_min;
	}


	public double getGrnd_level() {
		return grnd_level;
	}


	public void setGrnd_level(double grnd_level) {
		this.grnd_level = grnd_level;
	}


	public double getTemp_kf() {
		return temp_kf;
	}


	public void setTemp_kf(double temp_kf) {
		this.temp_kf = temp_kf;
	}


	public double getHumidity() {
		return humidity;
	}


	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}


	public double getPressure() {
		return pressure;
	}


	public void setPressure(double pressure) {
		this.pressure = pressure;
	}


	public double getSea_level() {
		return sea_level;
	}


	public void setSea_level(double sea_level) {
		this.sea_level = sea_level;
	}


	public double getTemp_max() {
		return temp_max;
	}


	public void setTemp_max(double temp_max) {
		this.temp_max = temp_max;
	}


	@Override
    public String toString()
    {
        return "ClassPojo [temp = "+temp+", temp_min = "+temp_min+", grnd_level = "+grnd_level+", temp_kf = "+temp_kf+", humidity = "+humidity+", pressure = "+pressure+", sea_level = "+sea_level+", temp_max = "+temp_max+"]";
    }
}
			