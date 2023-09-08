package com.rest.jersey.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rest.jersey.model.Activity;

public class ActivityClient {

	private Client client;
	
	public ActivityClient()
	{
		client = ClientBuilder.newClient();
	}
	
	public Activity get(String id)
	{
		// http://localhost:8080/exercise/webapi/activties/1234
		
		WebTarget target = client.target("http://localhost:8080/exercise/webapi/");		
		Response response = target.path("activties/" + id).request().get(Response.class);	
		
		if(response.getStatus()!=200)
		{
			throw new RuntimeException(response.getStatus() + " There was an error in the server");
		}
		return response.readEntity(Activity.class);
	}
	
	// List of activities - Use of  Generic Type
	public List<Activity> get()
	{
		// http://localhost:8080/exercise/webapi/activties/1234
		
		WebTarget target = client.target("http://localhost:8080/exercise/webapi/");		
		List<Activity> response = target.path("activties").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Activity>>(){});
		return response;	
	}

	public Activity create(Activity a) {
		
		// http://localhost:8080/exercise/webapi/activties/activty
		WebTarget target = client.target("http://localhost:8080/exercise/webapi/");		
		Response response = target.path("activties/activity")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(a, MediaType.APPLICATION_JSON));	
		
		if(response.getStatus()!=200)
		{
			throw new RuntimeException(response.getStatus() + " There was an error in the server");
		}
		return response.readEntity(Activity.class);
	}

	public Activity update(Activity a) {
		WebTarget target = client.target("http://localhost:8080/exercise/webapi/");		
		Response response = target.path("activties/" + a.getId())
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(a, MediaType.APPLICATION_JSON));	
		
		if(response.getStatus()!=200)
		{
			throw new RuntimeException(response.getStatus() + " There was an error in the server");
		}
		return response.readEntity(Activity.class);
	}

	public void delete(String activityId) {
		WebTarget target = client.target("http://localhost:8080/exercise/webapi/");	
		Response response = target.path("activties/" + activityId)
				.request(MediaType.APPLICATION_JSON)
				.delete();	
		if(response.getStatus()!=200)
		{
			throw new RuntimeException(response.getStatus() + " There was an error in the server");
		}
	}
}
