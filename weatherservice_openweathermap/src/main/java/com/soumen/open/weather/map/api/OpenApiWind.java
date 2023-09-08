package com.soumen.open.weather.map.api;



public class OpenApiWind
{
    private double deg;
    private double speed;
  
    public double getDeg() {
		return deg;
	}

	public void setDeg(double deg) {
		this.deg = deg;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
    public String toString()
    {
        return "OpenApiWind [deg = "+deg+", speed = "+speed+"]";
    }
}
	