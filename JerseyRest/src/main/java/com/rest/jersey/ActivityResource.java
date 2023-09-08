package com.rest.jersey;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rest.jersey.model.Activity;
import com.rest.jersey.model.User;
import com.rest.jersey.repository.ActivityRepository;
import com.rest.jersey.repository.ActivityRepositoryStub;

@Path("activties") // http://localhost:8080/exercise/webapi/activties
public class ActivityResource {
	
	private ActivityRepository activityRepository = new ActivityRepositoryStub();
	
	

	@DELETE
	@Path("{activityId}")
	@Consumes({javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON , javax.ws.rs.core.MediaType.APPLICATION_XML})
	public Response updateActivtyParams (@PathParam ("activityId") String activityId)
	{
		System.out.println(activityId);
		activityRepository.deleteActivty(activityId);				
		return Response.ok().build();		
	}
	
	
	
	//Put is idempotent
	
	@PUT
	@Path("{activityId}")
	@Consumes({javax.ws.rs.core.MediaType.APPLICATION_JSON})
	@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON , javax.ws.rs.core.MediaType.APPLICATION_XML})
	public Response updateActivtyParams (Activity activity)
	{
		System.out.println(activity.getId());
		Activity activityres = activityRepository.updateActivty(activity);
				
		return Response.ok().entity(activityres).build();
		
	}
	
	
	@POST
	@Path("activity")
	@Consumes({javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON , javax.ws.rs.core.MediaType.APPLICATION_XML})
	public Activity createActivtyParams (MultivaluedMap<String, String> fromparams)
	{
		System.out.println(fromparams.getFirst("description"));
		System.out.println(fromparams.getFirst("duration"));
		Activity activty= new Activity();
		activty.setDescription(fromparams.getFirst("description"));
		activty.setDuration(Integer.parseInt(fromparams.getFirst("duration")));
		activityRepository.createActivty(activty);
		return activty;
	}
	
	
	@POST
	@Path("activity")
	@Consumes({javax.ws.rs.core.MediaType.APPLICATION_JSON})
	@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON , javax.ws.rs.core.MediaType.APPLICATION_XML})
	public Activity createActivty (Activity  activity)
	{
		System.out.println(activity.getDescription());
		System.out.println(activity.getDuration());
		activityRepository.createActivty(activity);
		return activity;
	}
	
	@GET
	@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON , javax.ws.rs.core.MediaType.APPLICATION_XML})
	public List<Activity> getAllActivties ()
	{
		return activityRepository.findAllActivtites();
	}
	

	
	@GET
	@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON , javax.ws.rs.core.MediaType.APPLICATION_XML})
	@Path("{activityId}")  // http://localhost:8080/exercise/webapi/activties/1234
	public Response getActivty (@PathParam ("activityId") String activityId)
	{
		if(activityId == null || activityId.length() <4 )
		{
			return Response.status(Status.BAD_REQUEST).build();
		}
		Activity activity = activityRepository.findActivty(activityId);
		
		if (activity == null)
		{
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok().entity(activity).build();
	}
	
	@GET
	@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON , javax.ws.rs.core.MediaType.APPLICATION_XML})
	@Path("{activityId}/user")  // http://localhost:8080/exercise/webapi/activties/1234/user
	public User getActivtyUser (@PathParam ("activityId") String activityId)
	{
		return activityRepository.findActivty(activityId).getUser();
	}
}
