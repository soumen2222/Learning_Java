
package com.soumen.weather.forecast;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.persistence.EntityNotFoundException;

import com.soumen.weather.common.BaseEAO;
import com.soumen.weather.entity.City;

/**
 * This class serves all the functions related to the contact management.
 */

public interface CityEAO extends BaseEAO<City> {
    @Remote
    public interface R extends CityEAO {}
    @Local
    public interface L extends CityEAO {}


    City createCity(City city)  throws DuplicateKeyException;

    City updateCity(City city) throws EntityNotFoundException;

    void deleteCity(String cityUUID);

    City getCity(String cityUUID);

}