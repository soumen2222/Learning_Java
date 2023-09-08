
package com.soumen.weather.forecast;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.Remote;

import com.soumen.weather.common.BaseEAO;
import com.soumen.weather.entity.ForecastData;

/**
 * This class serves all the functions related to the contact management.
 */

public interface ForecastEAO extends BaseEAO<ForecastData> {
    @Remote
    public interface R extends ForecastEAO {}
    @Local
    public interface L extends ForecastEAO {}


    ForecastData createForecast(ForecastData forecast)  throws DuplicateKeyException;

    void deleteForecast(String forecastUUID);

    ForecastData getForecast(String forecastUUID);

	ForecastData getForeCastDatabyCityCountryName(String cityName, String countryCode);

	void getUpdateForecastData(ForecastData forecast) throws DuplicateKeyException;
}