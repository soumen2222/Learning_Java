package com.rest.jersey.client;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.rest.jersey.model.Activity;
import com.rest.jersey.model.ActivitySearch;

public class ActivitySearchClient {

	
	private Client client;
	
	public ActivitySearchClient()
	{
		client =ClientBuilder.newClient();
	}
	
	public List<Activity> search ( String param , List <String> searchValue)
	{
		//http://localhost:8080/exercise/webapi/search/activties?description=Swimming&description=Running
		URI uri = UriBuilder.fromUri("http://localhost:8080/exercise/webapi")
				.path("search/activties")
				.queryParam(param, searchValue)
				.build();
		
		
		WebTarget target = client.target(uri);
		
		List<Activity> response = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Activity>> () {});
		
		System.out.println(response);
		return response;
	}

	public List<Activity> searchRange(String param, List<String> searchValue, String secondparam, int durationfrom,
			String thirdparam, int durationto) {
		
		URI uri = UriBuilder.fromUri("http://localhost:8080/exercise/webapi")
				.path("search/activties")
				.queryParam(param, searchValue)
				.queryParam(secondparam, durationfrom)
				.queryParam(thirdparam, durationto)
				.build();
		
		
		WebTarget target = client.target(uri);
		
		List<Activity> response = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Activity>> () {});
		
		System.out.println(response);
		return response;
	}

	public List<Activity> searchObject(ActivitySearch search) {

		URI uri = UriBuilder.fromUri("http://localhost:8080/exercise/webapi")
				.path("search/activties")				
				.build();
		
		
		WebTarget target = client.target(uri);
		
		Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(search, MediaType.APPLICATION_JSON));
		
		if(response.getStatus()!=200)
		{
			throw new RuntimeException(response.getStatus() + " There was an error in the server");
		}
		System.out.println(response);
		return response.readEntity(new GenericType<List<Activity>> () {});
	}
}
