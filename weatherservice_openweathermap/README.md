# 5 Day Weather Forecast for a City

a)	Build and Set Up Details:

   1) Application Workflow 
   
   ![Alt text](/images/Workflow.png?raw=true "Listeners")

   2) Deployment View and application overview
   
   ![Alt text](/images/application.png?raw=true "Listeners")
   
   ![Alt text](/images/deploymentview.png?raw=true "Listeners")
   
   3) Build the application:
        - We need latest mvn version, and Java 8 JDK .
   4) Setup for the application deployment
        - We need Wildfly 14.0.1 as an application server.
		- Mysql 5.4 (or latest) should be installed and required driver for the mysql version should be downloaded.
		- Configure the datasource and activemq queues in the wildfly standalone to connect to databases and creates queues for asynchronous processing. ( Standalone.xml is attached)
		
		   - Queue details: (snippet from the standalone)
		      <jms-queue name="forecastDispatchQueue" entries="java:/queue/forecastDispatchQueue java:jboss/exported/jms/queue/forecastDispatchQueue"/>  
		   - datasource details: Jndi name is mapped in the persistece.xml. Update the password and connection URL as per the DB configurations.
		    
		   <datasource jta="true" jndi-name="java:jboss/datasources/weatherDS" pool-name="weatherDS" enabled="true" use-ccm="true">
                    <connection-url>jdbc:mysql://localhost:3306/weather</connection-url>
                    <driver-class>com.mysql.jdbc.Driver</driver-class>
                    <driver>mysql-connector-java-5.1.40-bin.jar_com.mysql.jdbc.Driver_5_1</driver>
                    <pool>
                        <min-pool-size>10</min-pool-size>
                        <max-pool-size>100</max-pool-size>
                    </pool>
                    <security>
                        <user-name>root</user-name>
                        <password>password</password>
                    </security>
                    <validation>
                        <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker"/>
                        <background-validation>true</background-validation>
                        <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter"/>
                    </validation>
                </datasource>  
				
	5) Database:
	     - Create a database named : weather 
		 
CREATE SCHEMA `weather` ;
		 
		 - Create Tables: city and forecast
		 
		 
CREATE TABLE `city` (
  `UUID` varchar(255) NOT NULL,
  `creationTime` datetime DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UUID`),
  UNIQUE KEY `UK2lrrotyi39r6umqp6d6scyjw3` (`name`,`country`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `forecast` (
  `UUID` varchar(255) NOT NULL,
  `creationTime` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `modifiedTime` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `forecast1` double NOT NULL,
  `forecast2` double NOT NULL,
  `forecast3` double NOT NULL,
  `forecast4` double NOT NULL,
  `forecast5` double NOT NULL,
  `city_uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UUID`),
  KEY `FKptl3610otekfsjh83nec551gj` (`city_uuid`),
  CONSTRAINT `FKptl3610otekfsjh83nec551gj` FOREIGN KEY (`city_uuid`) REFERENCES `city` (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


  ![Alt text](/images/entityDiagram.png?raw=true "Listeners")
  
    6) Copy the mysql driver and weather-service-rest.war in the deployment folder and Update the standalone file in the configuration folder of wildfly. Start the wildfly server and check if the war is properly deployed. Once deployed access the weather service to get the forecast data.
				
	7) Access the weather data: Provide city name and country code. The application server is deployed with 8080 port.
	
      http://localhost:8080/weather-service-rest/resteasy/weather/getForecast?cityName=Bangalore&countryCode=IN


	  
	  
Response payload:

{
    "forecasts": [
        {
            "day": "2019-03-17",
            "forecastTemp": 20
        },
        {
            "day": "2019-03-18",
            "forecastTemp": 23
        },
        {
            "day": "2019-03-19",
            "forecastTemp": 27
        },
        {
            "day": "2019-03-20",
            "forecastTemp": 25
        },
        {
            "day": "2019-03-21",
            "forecastTemp": 24
        },
        {
            "day": "2019-03-22",
            "forecastTemp": 32
        }
    ],
    "countryCode": "IN",
    "curentDay": "2019-03-17",
    "cityName": "Bangalore"
}

