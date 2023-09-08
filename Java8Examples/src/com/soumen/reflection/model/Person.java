package com.soumen.reflection.model;


import com.soumen.reflection.annotation.Column;
import com.soumen.reflection.annotation.PrimaryKey;

public class Person {

	@PrimaryKey(name="id")
	private long id;

	@Column(name="name")
	private String name;

	@Column(name="age")
	private int age;
	
	
	public Person() {
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public static Person of(String name, int age) {
		return new Person(name, age);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
}
