
package com.soumen.weather.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.soumen.weather.common.VersionedEntity;

/**
 * The Class DataSource.
 */
@Entity
@Table(name = "forecast")
@NamedQueries({  
          @NamedQuery(name = "ForecastData.findByCityName", query = "select fd from ForecastData fd "
		+ " left join fd.city c where c.name=:cityName and c.country=:countryCode")
})
public class ForecastData extends VersionedEntity {

    private static final long serialVersionUID = 2070056470982359874L;

    private LocalDate date; 
       
   
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) 
    @JoinColumn(name = "city_uuid")
    private City city;
    
    private double forecast1;
    private double forecast2;
    private double forecast3;
    private double forecast4;
    private double forecast5;
    
   
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}

	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public double getForecast1() {
		return forecast1;
	}
	public void setForecast1(double forecast1) {
		this.forecast1 = forecast1;
	}
	public double getForecast2() {
		return forecast2;
	}
	public void setForecast2(double forecast2) {
		this.forecast2 = forecast2;
	}
	public double getForecast3() {
		return forecast3;
	}
	public void setForecast3(double forecast3) {
		this.forecast3 = forecast3;
	}
	public double getForecast4() {
		return forecast4;
	}
	public void setForecast4(double forecast4) {
		this.forecast4 = forecast4;
	}
	public double getForecast5() {
		return forecast5;
	}
	public void setForecast5(double forecast5) {
		this.forecast5 = forecast5;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForecastData other = (ForecastData) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			    return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			    return false;
		return true;
	}
	@Override
	public String toString() {
		return "ForecastData [date=" + date + ", city=" + city + ", forecast1=" + forecast1 + ", forecast2=" + forecast2
				+ ", forecast3=" + forecast3 + ", forecast4=" + forecast4 + ", forecast5=" + forecast5 + "]";
	}	  
    
}