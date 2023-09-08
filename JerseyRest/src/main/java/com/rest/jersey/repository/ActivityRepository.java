package com.rest.jersey.repository;

import java.util.List;

import com.rest.jersey.model.Activity;
import com.rest.jersey.model.ActivitySearch;

public interface ActivityRepository {

	List<Activity> findAllActivtites();

	Activity findActivty(String activityId);

	void createActivty(Activity activty);

	Activity updateActivty(Activity activity);

	void deleteActivty(String activityId);

	List<Activity> findbydescription(List<String> descriptions);

	List<Activity> findbyrange(List<String> descriptions, int durationfrom, int durationto);

	List<Activity> findBySearchObject(ActivitySearch search);

}