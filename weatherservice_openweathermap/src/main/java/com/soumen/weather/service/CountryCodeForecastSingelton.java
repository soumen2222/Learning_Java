package com.soumen.weather.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.ibm.icu.util.TimeZone;


@Singleton
@Startup
public class CountryCodeForecastSingelton {	
	
	private Map<String , String> countryCodetoNameMap = new ConcurrentHashMap<>();
	private Map<String, Set<TimeZone>> availableTimezones =	new ConcurrentHashMap<>();			
	private Map<String , ForecastResponse> countrytoForecastMap = new ConcurrentHashMap<>();
	
	@PostConstruct
	public void init() {	

		for (Locale locale : Locale.getAvailableLocales()) {
			countryCodetoNameMap.put(locale.getCountry(), locale.getDisplayCountry());
			final String countryCode = locale.getCountry();			
			availableTimezones.computeIfAbsent(countryCode, k -> getTimeZones(countryCode));			
		}
    }

	@Lock(LockType.READ)
	public boolean isCountryExists(String countryCode) {
		return countryCodetoNameMap.containsKey(countryCode);
	}

	@Lock(LockType.READ)
	public ForecastResponse getCountrytoForecastMap(String cityName, String countryCode ) {	

		// Invalidate Cache
		ForecastResponse fr = countrytoForecastMap.get(getKey(cityName, countryCode));
		if (fr != null) {
			LocalDate lastday = fr.getCurentDay();
			LocalDate currentDay = LocalDate.now();
			if (currentDay.isAfter(lastday)) {
				countrytoForecastMap.remove(getKey(cityName, countryCode));
				return null;
			}
		}
		return countrytoForecastMap.get(getKey(cityName, countryCode));
	}	
	
	@Lock(LockType.WRITE)
	public void putCountrytoForecastMap(ForecastResponse forecast) {		
		 countrytoForecastMap.put(getKey( forecast.getCityName(),forecast.getCountryCode()),forecast);
	}	
	
	
	private String getKey(String cityName, String countryCode) {
		return  cityName + "_" + countryCode;
	}


	@Lock(LockType.READ)
	public Set<TimeZone> getAvailableTimezones(String countryCode) {
		return availableTimezones.get(countryCode);
	}
	
	private Set<TimeZone> getTimeZones(String countryCode){
		
		Set<TimeZone> timezones = new HashSet<>();		

		for (String id : TimeZone.getAvailableIDs(countryCode))
		{
			timezones.add(TimeZone.getTimeZone(id));
		}
		return timezones;
	}
	
}
