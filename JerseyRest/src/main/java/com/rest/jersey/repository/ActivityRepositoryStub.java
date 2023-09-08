package com.rest.jersey.repository;

import java.util.ArrayList;
import java.util.List;

import com.rest.jersey.model.Activity;
import com.rest.jersey.model.ActivitySearch;
import com.rest.jersey.model.User;

public class ActivityRepositoryStub implements ActivityRepository {

	
	/* (non-Javadoc)
	 * @see com.rest.jersey.repository.ActivityRepository#findAllActivtites()
	 */
	public List<Activity> findAllActivtites()
	{
		List<Activity> activties = new ArrayList<Activity>();
		
		Activity activity1 = new Activity();
		activity1.setDescription("Swimming");
		activity1.setDuration(10);
		activties.add(activity1);
		
		Activity activity2 = new Activity();
		activity2.setDescription("Running");
		activity2.setDuration(15);
		activties.add(activity2);
		
		return activties;
	}

	@Override
	public Activity findActivty(String activityId) {
		
		
		Activity activity1 = new Activity();
		activity1.setDescription("Cycling");
		activity1.setId("1234");
		activity1.setDuration(100);
		
		User user = new User();
		user.setId("543");
		user.setName("Soumen");
		activity1.setUser(user);
		return activity1;
	}

	@Override
	public void createActivty(Activity activty) {
		// Insert Record into DB
		
	}

	@Override
	public Activity updateActivty(Activity activity) {
		// Search Database and get activity , Once retreived update the activty. If data is not available create a new record.
		return activity;
	}

	@Override
	public void deleteActivty(String activityId) {
		// Delete the record in database
		
	}

	@Override
	public List<Activity> findbydescription(List<String> descriptions) {
		// select the list of activities from db based on the descriptions
		
		List<Activity> activities = new ArrayList<Activity>();
		Activity activity1 = new Activity();
		activity1.setDescription("Cycling");
		activity1.setId("1234");
		activity1.setDuration(100);
		
		Activity activity2 = new Activity();
		activity2.setDescription("Cycling1");
		activity2.setId("12345");
		activity2.setDuration(100);
		activities.add(activity1);
		activities.add(activity2);
		return activities;
	}

	@Override
	public List<Activity> findbyrange(List<String> descriptions, int durationfrom, int durationto) {
		// TODO Auto-generated method stub
		List<Activity> activities = new ArrayList<Activity>();
		Activity activity1 = new Activity();
		activity1.setDescription("Cycling");
		activity1.setId("1234");
		activity1.setDuration(100);
		
		Activity activity2 = new Activity();
		activity2.setDescription("Cycling1");
		activity2.setId("12345");
		activity2.setDuration(100);
		activities.add(activity1);
		activities.add(activity2);
		return activities;
	}

	@Override
	public List<Activity> findBySearchObject(ActivitySearch search) {
		// Create the search criteria based on the search object
		
		System.out.println(search.getDurationFrom() + " " + search.getDurationTo() + " " +search.getSearchType());
		List<Activity> activities = new ArrayList<Activity>();
		Activity activity1 = new Activity();
		activity1.setDescription("Cycling");
		activity1.setId("1234");
		activity1.setDuration(100);
		
		Activity activity2 = new Activity();
		activity2.setDescription("Cycling1");
		activity2.setId("12345");
		activity2.setDuration(100);
		activities.add(activity1);
		activities.add(activity2);
		return activities;
	}
	
	
}
