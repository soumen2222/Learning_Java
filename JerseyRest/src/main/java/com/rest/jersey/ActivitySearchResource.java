package com.rest.jersey;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rest.jersey.model.Activity;
import com.rest.jersey.model.ActivitySearch;
import com.rest.jersey.repository.ActivityRepository;
import com.rest.jersey.repository.ActivityRepositoryStub;

@Path("search/activties")
public class ActivitySearchResource {
	
	private ActivityRepository activityRepository = new ActivityRepositoryStub();
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public Response searchActiviesforRange(@QueryParam(value ="descriptions") List<String> descriptions 
			,@QueryParam(value ="durationfrom") int durationfrom 
			, @QueryParam(value ="durationto") int durationto)
	{
		System.out.println(descriptions);
		
		List<Activity> activities = activityRepository.findbyrange(descriptions,durationfrom,durationto);
		
		if(activities.size() <1 || activities ==null )
		{
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok().entity(new GenericEntity<List<Activity>>(activities) {}).build();
	}

	
	@POST
	@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON , javax.ws.rs.core.MediaType.APPLICATION_XML})
	public Response searchForActivties (ActivitySearch search)
	{
		System.out.println(search.getDescriptions());
		System.out.println(search.getDurationFrom() + search.getDurationTo());
		List<Activity> activities= new ArrayList<Activity>();	
		activities = activityRepository.findBySearchObject(search);
		
		if(activities.size() <1 || activities ==null )
		{
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok().entity(new GenericEntity<List<Activity>>(activities) {}).build();
	}
}

