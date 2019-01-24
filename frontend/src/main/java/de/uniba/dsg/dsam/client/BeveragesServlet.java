package de.uniba.dsg.dsam.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uniba.dsg.dsam.model.Beverage;
import de.uniba.dsg.dsam.model.CustomerOrder;

/*
 * author: Bora Bejleri
 */

@WebServlet("/BeveragesServlet")
public class BeveragesServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = -1531577023074421775L;

		
	@Resource(mappedName = "jmsConn/jmsDest")
	private Queue queue; //the destination is going to be the queue
	@Resource(mappedName = "jmsConn/firstConn")
	private ConnectionFactory connectionFactory;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		//nothing happens
	}		

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		String checkbox1 = req.getParameter("festbier");
		String checkbox2 = req.getParameter("rgluhweinalk");
		String checkbox3 = req.getParameter("rgluhwein");
		
		
		List<Beverage>orderItems = new ArrayList<Beverage>();
		 
		    try {
		        Connection connection = connectionFactory.createConnection();
		        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		        MessageProducer messageProducer = session.createProducer(queue);

		        ObjectMessage message = session.createObjectMessage();
		        
		        //get all parameters from the jsp and constrict beverage objects to pass to order list
		        if (checkbox1!=null) { 
		        	int quantity1;
		        	quantity1 = Integer.parseInt(req.getParameter("quantity1"));
		        	for(int i=0;i<quantity1;i++){
		        		Beverage festbier = new Beverage();
		        		festbier.setName("Festbier");
		        		festbier.setPrice(3.5);
		        		festbier.setIncentive(null);
		        		festbier.setManufacturer("Bamberg Bier");
		        		festbier.setQuantity(100); //in stock, not the order quantity
		        		orderItems.add(festbier);
		        	}
		        }
		        
		        if (checkbox2!=null) { 
		        	int quantity2;
		        	quantity2 = Integer.parseInt(req.getParameter("quantity2"));
		        	for(int i=0;i<quantity2;i++){
		        		Beverage rgluhweinalk = new Beverage();
		        		rgluhweinalk.setName("Rot Glühwein mit Alk");
		        		rgluhweinalk.setPrice(4.5);
		        		rgluhweinalk.setIncentive(null);
		        		rgluhweinalk.setManufacturer("Bamberg Wein");
		        		rgluhweinalk.setQuantity(100); //in stock, not the order quantity
		        		orderItems.add(rgluhweinalk);
		        	}
		        }
		        
		        if (checkbox3!=null) { 
		        	int quantity3;
		        	quantity3 = Integer.parseInt(req.getParameter("quantity3"));
		        	for(int i=0;i<quantity3;i++){
		        		Beverage rgluhwein = new Beverage();
		        		rgluhwein.setName("Rot Glühwein ohne Alk");
		        		rgluhwein.setPrice(4);
		        		rgluhwein.setIncentive(null);
		        		rgluhwein.setManufacturer("Bamberg Wein");
		        		rgluhwein.setQuantity(100); //in stock, not the order quantity
		        		orderItems.add(rgluhwein);
		        	}
		        }
		        
		        //the CustomerOrder object which is going to be sent to the queue
		        CustomerOrder order = new CustomerOrder();
		        order.setOrderItems(orderItems);
		        order.setIssueDate(java.time.LocalDate.now());

		        message.setObject((Serializable) order);                
		        messageProducer.send(message);
		        messageProducer.close();
		        connection.close();

		    } catch (JMSException ex) {
		    	log(ex.getMessage());
		        ex.printStackTrace();
		    }
		
		
	}
	
	//client can delete a document, webpage or information from the server
	//Called by the server (via the service method) to allow a servlet to handle a DELETE request.
	//Since this servlet handles the users requests(order placement), and users in this case can't make a delete request, this method is left intentionally blank
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {}
    
}
