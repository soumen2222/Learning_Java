package com.soumen.reflection;

import com.soumen.reflection.model.Person;
import com.soumen.reflection.util.ColumnField;
import com.soumen.reflection.util.Metamodel;
import com.soumen.reflection.util.PrimaryKeyField;

import java.util.List;



public class PlayWithMetamodel {
	
	public static void main(String... args) {
		
		Metamodel<Person> metamodel = Metamodel.of(Person.class);
		
		PrimaryKeyField primaryKeyField = metamodel.getPrimaryKey();
		List<ColumnField> columnFields = metamodel.getColumns();
		
		System.out.println("Primary key name = " + primaryKeyField.getName() + 
				", type = " + primaryKeyField.getType().getSimpleName());
		
		for (ColumnField columnField : columnFields) {
			System.out.println("Colum name = " + columnField.getName() + 
					", type = " + columnField.getType().getSimpleName());
		}
	}
}
