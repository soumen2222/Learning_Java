
package com.soumen.weather.forecast;

import java.util.Date;
import java.util.List;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.soumen.weather.common.BaseEAOBean;
import com.soumen.weather.entity.ForecastData;

@Stateless
public class ForecastEAOBean extends BaseEAOBean<ForecastData> implements ForecastEAO.R,ForecastEAO.L {

    public ForecastEAOBean() {
        super(ForecastData.class);
    }

	@Override
	public ForecastData createForecast(ForecastData forecast) throws DuplicateKeyException {	
		return super.create(forecast);
	}

	
	@Override
	public void deleteForecast(String forecastUUID) {
		super.delete(forecastUUID);
		
	}

	@Override
	public ForecastData getForecast(String forecastUUID) {		
		return super.getById(forecastUUID);
	}

	@Override
	public ForecastData getForeCastDatabyCityCountryName(String cityName, String countryCode) {
        try {        	
        	Query q = em.createNamedQuery("ForecastData.findByCityName");
        	q.setParameter("cityName", cityName);
        	q.setParameter("countryCode", countryCode);  
    		List<ForecastData> val = q.getResultList();
    		if(val.isEmpty()) {
    			return null;
    		} else if (val.size() == 1) {
    			return val.get(0);
    		} else {
    			throw new NonUniqueResultException(q.toString());
    		}
        }catch(NoResultException e){
            return null;
        }
    }
	
	@Override
	public void getUpdateForecastData(ForecastData forecast) throws DuplicateKeyException {
		
		ForecastData fd = getForeCastDatabyCityCountryName(forecast.getCity().getName(),forecast.getCity().getCountry());
		if(fd!=null) {
			// do a merge
			fd.setForecast1(forecast.getForecast1());
			fd.setForecast2(forecast.getForecast2());
			fd.setForecast3(forecast.getForecast3());
			fd.setForecast4(forecast.getForecast4());
			fd.setForecast5(forecast.getForecast5());		
			fd.setDate(forecast.getDate());	
			fd.setModifiedTime(new Date());
			super.merge(fd);
		}else {
			//do a create
			super.create(forecast);
		}
		
	}
	
	
}