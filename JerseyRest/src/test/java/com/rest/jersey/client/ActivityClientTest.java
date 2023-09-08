package com.rest.jersey.client;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.rest.jersey.client.ActivityClient;
import com.rest.jersey.client.ActivitySearchClient;
import com.rest.jersey.model.Activity;
import com.rest.jersey.model.ActivitySearch;
import com.rest.jersey.model.ActivitySearchType;

public class ActivityClientTest {

	
	@Test
	public void testSearchObject() {
		ActivitySearchClient client = new ActivitySearchClient();	
		List<String> searchValues = new ArrayList<String>();
		searchValues.add("Biking");
		searchValues.add("Running");
		
		ActivitySearch search = new ActivitySearch();
		search.setDescriptions(searchValues);
		search.setDurationFrom(30);
		search.setDurationTo(50);
		search.setSearchType(ActivitySearchType.SEARCH_BY_DESCRIPTION);
		
		List<Activity> activties = client.searchObject(search);
		assertNotNull(activties);		
	}
	
	@Test
	public void testSearch() {
		ActivitySearchClient client = new ActivitySearchClient();	
		String param = "description";
		List<String> searchValue = new ArrayList<String>();
		searchValue.add("Swimming");
		searchValue.add("Running");		
		List<Activity> activties = client.search(param , searchValue );
		assertNotNull(activties);		
	}
	
	@Test
	public void testSearchRange() {
		ActivitySearchClient client = new ActivitySearchClient();	
		String param = "descriptions";
		List<String> searchValue = new ArrayList<String>();
		searchValue.add("Swimming");
		searchValue.add("Running");		
		
		String secondparam = "durationfrom";
		int durationfrom = 30;
		
		String thirdparam = "durationto";
		int durationto = 50;
		
		List<Activity> activties = client.searchRange(param , searchValue ,secondparam , durationfrom, thirdparam, durationto );
		assertNotNull(activties);		
	}
	
	@Test
	public void testDelete() {
		ActivityClient client = new ActivityClient();	
		client.delete("1234");
		
	}
	
	@Test
	public void testPut() {
		ActivityClient client = new ActivityClient();	
		Activity a = new Activity();
		a.setId("7555");
		a.setDescription("Swimming");
		a.setDuration(90);
		a = client.update(a);
		assertNotNull(a);
	}
	
	@Test
	public void testGet() {
		ActivityClient client = new ActivityClient();		
		Activity a = client.get("1234");
		assertNotNull(a);
	}
	
	@Test
	public void testCreate() {
		ActivityClient client = new ActivityClient();		
		Activity a = new Activity();
		a.setDescription("Swimming");
		a.setDuration(90);		
		a = client.create(a);
		assertNotNull(a);
	}

	
	// @Test(expected = RuntimeException.class)
	@Test
	public void testGetList() {
		ActivityClient client = new ActivityClient();		
		List<Activity> activites = client.get();		
		assertNotNull(activites);
	}

}
