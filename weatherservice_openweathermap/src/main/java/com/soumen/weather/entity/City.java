
package com.soumen.weather.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.soumen.weather.common.BaseEntity;


@Entity
@Table(name = "city", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"name", "country" }) })
public class City extends BaseEntity {

    private static final long serialVersionUID = 2070056470982359874L;

    private String name = null;
    private String country = null;
    
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "city", cascade = CascadeType.ALL) 
    private ForecastData forecast;
    
    
   
	public ForecastData getForecast() {
		return forecast;
	}
	public void setForecast(ForecastData forecast) {
		this.forecast = forecast;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}