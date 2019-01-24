package de.uniba.dsg.dsam.backend.beans;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.uniba.dsg.dsam.backend.entities.CustomerOrderEntity;


/*
 * author: Bora Bejleri
 */

@MessageDriven(mappedName="jms/Queue", activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jmsConn/firstConn"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jmsConn/jmsDest")})



//to receive messages asynchronously.
public class OrderMessageDrivenBean implements MessageListener {
	
	private static final Logger LOGGER = Logger.getLogger(OrderMessageDrivenBean.class.getName());
	
	 @Resource(mappedName="jms/ConnectionFactory")
	 private static ConnectionFactory connectionFactory;
	
	 @Resource(mappedName="jms/Queue")
	 private static Queue queue;
    
	 @Resource
	 private MessageDrivenContext context;
	 
	 @PersistenceContext
	 private EntityManager em;
	 
	 public void persist(Object object) {
		    em.persist(object);
		}	
    
    //Handling the message
    //When the queue receives a message, the EJB container invokes the message listener method or methods.
    public void onMessage(Message message) {

    	if(message == null){
    		LOGGER.warning("Received null message via room insertion queue");
    		return;
    	}
    	ObjectMessage msg = null;
        try {
            if (message instanceof ObjectMessage) {
                msg = (ObjectMessage) message;
                CustomerOrderEntity e = (CustomerOrderEntity) 
                msg.getObject();
                persist(e);//presists the message to the database            
            }
        } catch (JMSException e) {
            LOGGER.severe("An Error has occurred" + e);
            context.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    	
    }
    

}
