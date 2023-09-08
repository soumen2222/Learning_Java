package com.soumen.reflection.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2EntityManager<T> extends AbstractEntityManager<T> {
	
	public Connection buildConnection() throws SQLException {
		Connection connection = 
				DriverManager.getConnection(
						"jdbc:h2:C:\\Development\\GitRepository\\Java8Examples\\db-files\\soumen-db IFEXISTS=true",
						"sa", "");
		return connection;
	}

}
