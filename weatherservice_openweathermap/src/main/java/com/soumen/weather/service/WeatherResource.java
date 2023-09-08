package com.soumen.weather.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.soumen.open.weather.map.api.AppServiceException;
import com.soumen.weather.forecast.ForecastManager;

@Path("/weather")
@Stateless
public class WeatherResource {

	private Logger logger = Logger.getLogger(getClass());

	@EJB
	private CountryCodeForecastSingelton countryCodeForecast;

	@EJB
	private ForecastManager.L forecastManager;

	@Path("/getForecast")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getForecastData(@QueryParam("cityName") String cityName,
			@QueryParam("countryCode") String countryCode) {

		logger.info("Get Next 5 days forecast data for country: " + countryCode + "and city :" + cityName);

		if (countryCodeForecast.isCountryExists(countryCode.toUpperCase())) {

			try {
				return Response.ok().entity(
						new GenericEntity<ForecastResponse>(forecastManager.getForecast(cityName, countryCode)) {
						}).build();
			} catch (AppServiceException e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE)
						.entity(e.getMessage()).build();
			}catch (Exception e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE)
						.entity(e.getMessage()).build();
			}
			
		} else {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Country Name is not found as per ISO 3166 country codes ").build();
		}
	}
}
