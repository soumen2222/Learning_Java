package com.soumen.queue;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import org.jboss.logging.Logger;


@Stateless
public class ForecastUpdateRequestQueueBean implements ForecastUpdateRequestQueue.L,ForecastUpdateRequestQueue.R{

	private static final Logger log = Logger.getLogger(ForecastUpdateRequestQueueBean.class);

	
	@Resource(mappedName = "java:/queue/forecastDispatchQueue")
	private Queue forecastDispatchQueue;
	@Resource(mappedName = "java:/JmsXA")
	private ConnectionFactory messageQueueFactory;
	
	@Override
	public void forecastMessage(Serializable message) throws Exception {
		try (Connection  connection = messageQueueFactory.createConnection();
			 Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE)){
			sendMessage(session, message);
		} catch (JMSException e) {
			log.error("Exception occurred while sending message to apx dispatcher",e);
			throw e;
		}
	}
	
	private void sendMessage(Session session,Serializable data) {
		try(MessageProducer messageProducer = session.createProducer(forecastDispatchQueue)){
			messageProducer.send(session.createObjectMessage(data));
			log.debug("Sent an apx message");
		}catch(Exception e) {
			log.error("Exception in sending apx message ",e);
		}
	}
}
