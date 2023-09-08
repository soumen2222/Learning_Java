package com.soumen.weather.forecast;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jboss.logging.Logger;

import com.ibm.icu.util.TimeZone;
import com.soumen.open.weather.map.api.AppServiceException;
import com.soumen.open.weather.map.api.OpenApiForecast;
import com.soumen.open.weather.map.api.OpenApiTempData;
import com.soumen.open.weather.map.api.OpenWeatherForecast;
import com.soumen.open.weather.map.client.ErrorResponse;
import com.soumen.open.weather.map.client.RestHttpClient;
import com.soumen.queue.ForecastUpdateRequestQueue;
import com.soumen.weather.entity.City;
import com.soumen.weather.entity.ForecastData;
import com.soumen.weather.service.CountryCodeForecastSingelton;
import com.soumen.weather.service.ForecastResponse;
import com.soumen.weather.service.TemperatureDate;

@Stateless
public class ForecastManagerBean implements ForecastManager.L, ForecastManager.R {

	private static final Logger log = Logger.getLogger(ForecastManagerBean.class.getName());

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	@EJB
	private CountryCodeForecastSingelton countryCodeForecast;

	@EJB
	private ForecastEAO.L forecastEAO;

	@EJB
	private ForecastUpdateRequestQueue.L forecastUpdateRequestDispatch;

	@Override
	public ForecastResponse getForecast(String cityName, String countryCode) throws Exception {
		
		ForecastResponse forecasts = countryCodeForecast.getCountrytoForecastMap(cityName,countryCode);		
		if (forecasts == null) {
			
			OpenWeatherForecast openWeatherForecast = getAllWeather(cityName, countryCode);					
			forecasts = populateForecast(cityName, countryCode, openWeatherForecast);			
			forecastUpdateRequestDispatch.forecastMessage(forecasts);
		}				
		return forecasts;
	}

	
	@Override
	public void createForecast(ForecastResponse forecast) {
		
		countryCodeForecast.putCountrytoForecastMap(forecast);		
		ForecastData entity = build(forecast);		
		try {
			forecastEAO.getUpdateForecastData(entity );
		} catch (DuplicateKeyException e) {			
			log.error("Failed to update the Forecast Data" + e.getMessage());
		}		
	}

	

	private OpenWeatherForecast getAllWeather(String cityName, String countryCode) throws AppServiceException {
		String cityCountry = cityName + "," + countryCode;
		RestHttpClient restClient = new RestHttpClient();
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("q", cityCountry));
		urlParameters.add(new BasicNameValuePair("units", "metric"));
		urlParameters.add(new BasicNameValuePair("appid", "563942baee9d145b4943a2f95e66b4ec"));
		
		Object output = restClient.doRestGet("forecast", OpenWeatherForecast.class, urlParameters, null, "yyyy-MM-dd HH:mm a z");
		
		if(output instanceof ErrorResponse) {
			output.toString();
		  throw new AppServiceException(output.toString());			
					
		}
		return (OpenWeatherForecast) restClient.doRestGet("forecast", OpenWeatherForecast.class, urlParameters, null,
				"yyyy-MM-dd HH:mm a z");

	}

	

	private ForecastResponse populateForecast(String cityName, String countryCode,
			OpenWeatherForecast openWeatherForecast) {
		ForecastResponse forecasts = new ForecastResponse();
		Map<LocalDate, TemperatureDate> forecastCastforDay = new HashMap<>();		

		for (OpenApiForecast openApiForecast : openWeatherForecast.getList()) {

			LocalDate day = convertUTCtoCountrySpecificTime(countryCode, openApiForecast);
			OpenApiTempData temperatures = openApiForecast.getMain();
			averageTemperatureForDay(forecastCastforDay, day, temperatures);
		}

		List<TemperatureDate> tempData = new ArrayList<>();
		forecasts.setCurentDay(LocalDate.now());
		for (Entry<LocalDate, TemperatureDate> entry : forecastCastforDay.entrySet()) {
			tempData.add(entry.getValue());					
			if(entry.getValue().getDay().isBefore(forecasts.getCurentDay())) {
				forecasts.setCurentDay(entry.getValue().getDay());
			}				
		}

		Comparator<TemperatureDate> byday = (TemperatureDate o1, TemperatureDate o2)->o1.getDay().compareTo(o2.getDay());
		Collections.sort(tempData, byday);
		forecasts.setForecasts(tempData);
		forecasts.setCountryCode(countryCode);
		forecasts.setCityName(cityName);
		return forecasts;
	}
	

	private LocalDate convertUTCtoCountrySpecificTime(String countryCode, OpenApiForecast openApiForecast) {
		LocalDateTime ldt = LocalDateTime.parse(openApiForecast.getDt_txt(),
				DateTimeFormatter.ofPattern(DATE_FORMAT));
		ZoneId countryZoneId = ZoneId.of(getTimeZoneforCountry(countryCode));
		ZonedDateTime countryZonedDateTime = ldt.atZone(countryZoneId);		
		return countryZonedDateTime.toLocalDate();
	}
	
	private String getTimeZoneforCountry(String countryCode) {

		final Set<TimeZone> timeZonesInCountry = countryCodeForecast.getAvailableTimezones(countryCode);

		if (timeZonesInCountry != null && !timeZonesInCountry.isEmpty()) {
			for (TimeZone timeZone : timeZonesInCountry) {
				return timeZone.getID();   // Return the first time zone of the country.
			}
		}
			TimeZone tzone = TimeZone.getTimeZone("America/Los_Angeles");
			log.info("Setting to Default time Zone");
			return tzone.getID();		
	}
	
	
	private void averageTemperatureForDay(Map<LocalDate, TemperatureDate> forecastCastforDay, LocalDate day,
			OpenApiTempData temperatures) {
	
		if (forecastCastforDay.containsKey(day)) {
			TemperatureDate temp = forecastCastforDay.get(day);
			temp.setForecastTemp((Math.round((temp.getForecastTemp() + temperatures.getTemp()) / 2)*100.0)/100.0);
		} else {
			TemperatureDate newDay = new TemperatureDate();
			newDay.setDay(day);
			newDay.setForecastTemp(temperatures.getTemp());
			forecastCastforDay.put(day, newDay);
		}
	}

	private ForecastData build(ForecastResponse forecast) {
		Map<String,Double> dayTempMap = new HashMap<>();
		ForecastData entity = new ForecastData();
		
		City city = new City();
		city.setCountry(forecast.getCountryCode());
		city.setName(forecast.getCityName());
		entity.setCity(city );
		
		for (int i=1; i<=forecast.getForecasts().size() ; i++) {
			String key= "Forecast"+i;
			dayTempMap.put(key, forecast.getForecasts().get(i-1).getForecastTemp());
		}
		entity.setForecast1(dayTempMap.get("Forecast1"));
		entity.setForecast2(dayTempMap.get("Forecast2"));
		entity.setForecast3(dayTempMap.get("Forecast3"));
		entity.setForecast4(dayTempMap.get("Forecast4"));
		entity.setForecast5(dayTempMap.get("Forecast5"));		
		entity.setDate(forecast.getCurentDay());
		return entity;
	}
}
