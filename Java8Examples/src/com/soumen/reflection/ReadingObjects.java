package com.soumen.reflection;


import com.soumen.reflection.model.Person;
import com.soumen.reflection.orm.EntityManager;

public class ReadingObjects {

	public static void main(String[] args) throws Exception {
		
		EntityManager<Person> entityManager = EntityManager.of(Person.class);
		
		Person linda = entityManager.find(Person.class, 1L);
		Person james = entityManager.find(Person.class, 2L);
		Person susan = entityManager.find(Person.class, 3L);
		Person john = entityManager.find(Person.class, 4L);
		
		System.out.println(linda);
		System.out.println(james);
		System.out.println(susan);
		System.out.println(john);
	}
}
