
package com.soumen.weather.forecast;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Stateless;
import javax.persistence.EntityNotFoundException;
import com.soumen.weather.common.BaseEAOBean;
import com.soumen.weather.entity.City;


@Stateless
public class CityEAOBean extends BaseEAOBean<City> implements CityEAO.R,CityEAO.L {

    public CityEAOBean() {
        super(City.class);
    }

	@Override
	public City createCity(City city) throws DuplicateKeyException {
		
		return super.create(city);
	}

	@Override
	public City updateCity(City city) throws EntityNotFoundException {
	
		return super.update(city);
	}

	@Override
	public void deleteCity(String cityUUID) {
		super.delete(cityUUID);
		
	}

	@Override
	public City getCity(String cityUUID) {
	
		return super.getById(cityUUID);
	}

	
    
}