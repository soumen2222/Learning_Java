package com.soumen.queue;


import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnectionFactory;

import org.jboss.logging.Logger;

import com.soumen.weather.forecast.ForecastManager;
import com.soumen.weather.service.ForecastResponse;

/**
 * Message-Driven Bean implementation class for: DemandLimitingDispatcher
 * 
 */
@MessageDriven(name = "ForecastUpdateRequestMDB", activationConfig = {
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/forecastDispatchQueue")})
@Resources(@Resource(name = "jms/QueueFactory", type = QueueConnectionFactory.class))
public class ForecastUpdateRequestMDB implements MessageListener {

	 
	private static final Logger log = Logger.getLogger(ForecastUpdateRequestMDB.class);

	@EJB
	private ForecastManager.L forecastManager;
	
	public void onMessage(Message message) {
		try {
		Object obj=((ObjectMessage) message).getObject();
		
			forecastManager.createForecast((ForecastResponse)obj);
		} catch (JMSException e) {
			log.error("Failed to send Event Cache Notification message to drpro");
		} 
		catch (Exception e) {
			log.error("Error in event request processor",e);
		}
		
	}
	
	
}
